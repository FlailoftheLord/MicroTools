package me.flail.microtools.mct.mctool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.flail.microtools.mct.Enchants.EnchantType;
import me.flail.microtools.mct.mctool.MctMaterial.MicroType;
import me.flail.microtools.tools.DataFile;
import me.flail.microtools.tools.Logger;

/**
 * Basically an easy way to modify and change the ItemMeta of this Data's parent MicroTool.
 * 
 * @author FlailoftheLord
 */
public class MctData extends Logger {
	private ItemStack toolItem;

	public static String UNCLAIMED_TOOL_TEXT = "&7this tool is unclaimed, left-click to claim";
	public static String MANAGE_TOOL_TEXT = "&8left-click to manage item";
	public static String PREVIEW_TOOL_TEXT = "&eclick to apply upgrades";
	public static String EDITING_TOOL_TEXT = "&eyou are currently editing this tool";
	public static String LEVEL_DISPLAY = "&aLevel&8: &7%level%";
	public static String GRADE_DISPLAY = "&aGrade&8: &7%tool-grade%";
	public static String BLOCKS_DISPLAY = "&aBlocks Broken&8: &7%blocks%";
	public static String KILLS_DISPLAY = "&aKills&8: &7%kills%";
	public static String USES_DISPLAY = "&aTimes Used&8: &7%times-used%";
	public static String DAMAGE_ABSORBED_DISPLAY = "&aDamage Negated&8: &7%damage-abs%";

	protected MctData(ItemStack item) {
		toolItem = item;
		updateDisplayValues();
	}

	public static void updateDisplayValues() {
		DataFile c = plugin.toolConfig;

		UNCLAIMED_TOOL_TEXT = c.getValue("DataDisplay.Status.Unclaimed");
		MANAGE_TOOL_TEXT = c.getValue("DataDisplay.Status.Claimed");
		PREVIEW_TOOL_TEXT = c.getValue("DataDisplay.Status.Preview");
		EDITING_TOOL_TEXT = c.getValue("DataDisplay.Status.Editing");
		LEVEL_DISPLAY = c.getValue("DataDisplay.ToolLevel");
		GRADE_DISPLAY = c.getValue("DataDisplay.ToolGrade");
		BLOCKS_DISPLAY = c.getValue("DataDisplay.BlocksBroken");
		KILLS_DISPLAY = c.getValue("DataDisplay.EntityKills");
		USES_DISPLAY = c.getValue("DataDisplay.ItemUses");
		DAMAGE_ABSORBED_DISPLAY = c.getValue("DataDisplay.DamageAbsorbed");

	}

	/**
	 * Generates the new Tool from the provided ItemStack.
	 */
	protected void create() {
		setLore(null);

		setLoreLine("", 0);
		setLoreLine(LEVEL_DISPLAY, 1);

		if (isTool()) {
			setLoreLine(BLOCKS_DISPLAY, 2);
			setLoreLine(KILLS_DISPLAY, 3);
			setLoreLine("", 4);
			setLoreLine("", 5);
		}

		if (isArmor()) {
			setLoreLine(DAMAGE_ABSORBED_DISPLAY, 2);
			setLoreLine("", 3);
			setLoreLine("", 4);
		}

		setLoreLine(plugin.toolConfig.getValue("PointDisplay.Prefix") + getLevelPointsDisplay()
		+ plugin.toolConfig.getValue("PointDisplay.Suffix"), -1);

		if (hasTag("preview")) {
			setLoreLine(plugin.toolConfig.getValue("PointDisplay.PreviewText"), -1);
		}

		addLoreLine("");

		if (MctMaterial.isUseable(toolItem)) {
			insertLoreLine(USES_DISPLAY, 3);
		}

		if (MicroType.isUpgradeable(toolItem.getType())) {
			insertLoreLine(GRADE_DISPLAY, 2);
		}

		if (hasEnchants()) {

			for (EnchantType e : this.enchants().keySet()) {

				setLoreLine("&7" + e.friendlyName(e) + " " + romanNumeral(enchants().get(e).intValue()), -1);
			}

			addLoreLine("");
		}

		if (hasTag("editing")) {

			setLoreLine(EDITING_TOOL_TEXT, -1);
		} else if (hasTag("preview")) {

			setLoreLine(PREVIEW_TOOL_TEXT, -1);
		} else if (!hasTag("owner")) {
			addTag("unclaimed", "true");

			setLoreLine(UNCLAIMED_TOOL_TEXT, -1);
		} else {

			setLoreLine(MANAGE_TOOL_TEXT, -1);
		}

		String name = MctMaterial.friendlyName(toolItem.getType());
		addTag("tool", ChatColor.stripColor(chat(name)));

		if (!hasTag("tool-type")) {
			addTag("tool-type", toolItem.getType().toString());
		}
		if (!hasTag("level")) {
			addTag("level", "0");
		}
		if (!hasTag("tool-grade")) {
			addTag("tool-grade", "BASIC");
		}

		ItemMeta meta = getItemMeta();


		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		setItemMeta(meta);

	}

	public void setItemStack(ItemStack item) {
		setItemMeta(item.getItemMeta());
		toolItem.setType(item.getType());
	}



	public Material getMaterial() {
		return toolItem.getType();
	}

	/**
	 * Gets the displayname of this tool item.
	 */
	public String getName() {
		if (toolItem.getItemMeta().hasDisplayName()) {
			return toolItem.getItemMeta().getDisplayName();
		}

		return MctMaterial.friendlyName(getMaterial());
	}

	/**
	 * Set the displayname of this Tool item.
	 * 
	 * @param name
	 *                 will translate color codes with the '&' character.
	 */
	public void setName(String name) {
		ItemMeta meta = getItemMeta();

		meta.setDisplayName(chat(name));
		setItemMeta(meta);
		removeTag("name");
		addTag("name", chat(name));
	}

	public List<String> getLore() {
		return getItemMeta().hasLore() ? getItemMeta().getLore() : new ArrayList<>(8);
	}

	public boolean setLore(List<String> lore) {
		ItemMeta meta = getItemMeta();
		meta.setLore(lore);

		return setItemMeta(meta);
	}

	public void purgeLore() {
		List<String> lore = getLore();
		boolean wasLastEmpty = false;

		for (String line : lore.toArray(new String[] {})) {

			if (line.equals("")) {
				if (wasLastEmpty) {
					lore.remove(line);

					wasLastEmpty = false;
					continue;
				}

				wasLastEmpty = true;
			}
		}

	}

	protected void addLoreLine(String value) {
		List<String> lore = getLore();
		lore.add(value);

		setLore(lore);
	}

	public void removeLoreLine(int index) {
		List<String> lore = getLore();
		try {
			lore.remove(index);
		} catch (Throwable t) {
		}

		setLore(lore);
	}

	public boolean setLoreLine(String value, int line) {
		List<String> lore = getLore();

		for (int i = lore.size(); i <= Math.abs(line); i++) {

			lore.add("");
		}

		if (line < 0) {
			line = lore.size() - Math.abs(line);
		}


		lore.set(line, value);
		return setLore(lore);
	}

	public boolean insertLoreLine(String value, int line) {
		List<String> lore = getLore();

		for (int i = lore.size(); i <= Math.abs(line); i++) {

			lore.add("");
		}

		if (line < 0) {
			line = lore.size() - Math.abs(line);
		}

		lore.add(line, value);
		return setLore(lore);
	}

	public ItemMeta getItemMeta() {
		return toolItem.getItemMeta();
	}

	public boolean setItemMeta(ItemMeta meta) {
		return toolItem.setItemMeta(meta);
	}

	public Class<? extends MicroType> getType() {
		List<Material> armor = ArmorType.materials();

		Material material = getMaterial();
		if (armor.contains(material)) {
			return ArmorType.class;
		}

		return ToolType.class;
	}

	/**
	 * @return true if this {@link #getMaterial()} is a valid ArmorType. false otherwise.
	 */
	public boolean isArmor() {
		return ArmorType.materials().contains(getMaterial());
	}

	/**
	 * @return true if this {@link #getMaterial()} is a valid ToolType. false otherwise.
	 */
	public boolean isTool() {
		return ToolType.materials().contains(getMaterial());
	}

	public MctData addEnchant(EnchantType type) {
		int level = 0;
		ItemMeta meta = getItemMeta();

		if (meta.hasEnchant(EnchantType.toEnchantment(type))) {

			level = meta.getEnchantLevel(EnchantType.toEnchantment(type));
		}

		meta.removeEnchant(EnchantType.toEnchantment(type));
		meta.addEnchant(EnchantType.toEnchantment(type), level + 1, true);
		setItemMeta(meta);
		return this;
	}

	public boolean hasEnchants() {
		return toolItem.hasItemMeta() ? toolItem.getItemMeta().hasEnchants() : false;
	}

	public Map<EnchantType, Integer> enchants() {
		Map<EnchantType, Integer> map = new TreeMap<>();

		if (hasEnchants()) {
			for (Enchantment e : toolItem.getEnchantments().keySet()) {
				map.put(EnchantType.fromEnchantment(e), Integer.valueOf(toolItem.getItemMeta().getEnchantLevel(e)));
			}

		}

		return map;
	}

	/*
	 *******************************************8
	 */

	public int getLevelPoints() {
		return hasTag("lp") ? Integer.parseInt(getTag("lp")) : 0;
	}

	public String getLevelPointsDisplay() {
		String display = plugin.toolConfig.getValue("PointDisplay.Icon");

		String line = "";
		if (hasTag("lp")) {
			int maxLP = plugin.maxLevelPoints;
			int points = getLevelPoints();

			int displayMax = 48;
			int displayLP = 0;

			int diff = maxLP/displayMax;
			displayLP = (points / diff) - 1;

			for (int lp = 0; (lp <= displayMax); lp++) {

				if (lp <= displayLP) {
					line += plugin.toolConfig.getValue("PointDisplay.FullColor") + display;

					continue;
				}

				line += plugin.toolConfig.getValue("PointDisplay.EmptyColor") + display;
			}



		}

		return chat(line);
	}

	public void addTag(String key, String value) {
		toolItem = addTag(toolItem, key, value);
	}

	public void removeTag(String key) {
		toolItem = removeTag(toolItem, key);
	}

	public String getTag(String key) {
		return getTag(toolItem, key);
	}

	public boolean hasTag(String key) {
		return hasTag(toolItem, key);
	}

	/**
	 * Update any placeholders in this Items' String name or Lore.
	 * 
	 * @param pl
	 *               the Map of {@literal <Placeholder, Value>}'s
	 */
	public void updatePlaceholders(Map<String, String> pl) {
		toolItem = this.itemPlaceholders(toolItem, pl);
	}

}
