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
import me.flail.microtools.tools.Logger;

/**
 * Basically an easy way to modify and change the ItemMeta of this Data's parent MicroTool.
 * 
 * @author FlailoftheLord
 */
public class MctData extends Logger {
	private ItemStack toolItem;

	public static final String UNCLAIMED_TOOL_TEXT = "&7This tool is unclaimed, right-click to claim.";
	public static final String MANAGE_TOOL_TEXT = "&8right-click to manage item.";
	public static final String PREVIEW_TOOL_TEXT = "&eclick to apply upgrades";
	public static final String EDITING_TOOL_TEXT = "&eyou are currently editing this tool!";
	public static final String LEVEL_DISPLAY = "&aLevel&8: &7";
	public static final String GRADE_DISPLAY = "&aGrade&8: &7";
	public static final String BLOCKS_DISPLAY = "&aBlocks Broken&8: &7";
	public static final String KILLS_DISPLAY = "&aKills&8: &7";
	public static final String USES_DISPLAY = "&aTimes Used&8: &7";
	public static final String DAMAGE_ABSORBED_DISPLAY = "&aDamage Negated&8: &7";

	protected MctData(ItemStack item) {
		toolItem = item;
	}

	/**
	 * Generates the new Tool from the provided ItemStack.
	 */
	protected void create() {

		setLoreLine("", 0);
		setLoreLine(chat(LEVEL_DISPLAY + "%level%"), 1);

		if (MicroType.isUpgradeable(toolItem.getType())) {
			setLoreLine(chat(GRADE_DISPLAY + "%tool-grade%"), 2);
		}

		if (isTool()) {
			setLoreLine(chat(BLOCKS_DISPLAY + "%blocks%"), 3);
			setLoreLine(chat(KILLS_DISPLAY + "%kills%"), 4);
		}

		if (isArmor()) {
			setLoreLine(chat(DAMAGE_ABSORBED_DISPLAY + "%damage-abs%"), 3);
			setLoreLine("", 4);
		}


		setLoreLine("", 5);
		if (hasTag("editing")) {

			setLoreLine(chat(EDITING_TOOL_TEXT), -1);
		} else if (hasTag("preview")) {

			setLoreLine(chat(PREVIEW_TOOL_TEXT), -1);
		} else if (!hasTag("owner")) {
			addTag("unclaimed", "true");

			setLoreLine(chat(UNCLAIMED_TOOL_TEXT), -1);
		} else {

			setLoreLine(chat(MANAGE_TOOL_TEXT), -1);
		}

		this.purgeLore();

		if (hasTag("tool")) {
			return;
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

		setItemMeta(meta);

	}

	public void setItemStack(ItemStack item) {
		setItemMeta(item.getItemMeta());
		toolItem.setType(item.getType());
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

		for (String line : lore) {

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

		if (line < 0) {
			line = lore.size() - 1;
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
	 * @return true if this {@link #material()} is a valid ArmorType. false otherwise.
	 */
	public boolean isArmor() {
		return ArmorType.materials().contains(getMaterial());
	}

	/**
	 * @return true if this {@link #material()} is a valid ToolType. false otherwise.
	 */
	public boolean isTool() {
		return ToolType.materials().contains(getMaterial());
	}

	/**
	 * @return true if, somehow, the type is neither Armor nor Tool.
	 */
	public boolean isMisc() {
		return !isTool() && !isArmor() && MicroType.allMaterials().contains(getMaterial());
	}

	public MctData addEnchant(EnchantType type) {
		if (hasEnchants() && enchants().containsKey(type)) {
			String errorWhileEnchanting = "%prefix% &cthis tool already has enchantment&8: &7" + type.toString()
			+ " &cto upgrade it use the upgrade option in the menu &8&o(#upgradeEnchant(type))";

			console(errorWhileEnchanting);
			return this;
		}

		ItemMeta meta = getItemMeta();
		meta.addEnchant(EnchantType.toEnchantment(type), 1, true);
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
