package me.flail.microtools.mct.mctool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.flail.microtools.armor.ArmorType;
import me.flail.microtools.mct.mctool.MctMaterial.MicroType;
import me.flail.microtools.tool.ToolType;
import me.flail.microtools.tools.Logger;

/**
 * Basically an easy way to modify and change the ItemMeta of this Data's parent MicroTool.
 * 
 * @author FlailoftheLord
 */
public class MctData extends Logger {
	private ItemStack toolItem;

	public static final String UNCLAIMED_TOOL_TEXT = "&7This tool is unclaimed, right-click to claim.";
	public static final String LEVEL_DISPLAY = "&aLevel&8: &7";
	public static final String GRADE_DISPLAY = "&aGrade&8: &7";

	protected MctData(ItemStack item) {
		toolItem = item;
	}

	/**
	 * Generates the new Tool from the provided ItemStack.
	 */
	protected void create() {
		if (hasTag("tool")) {
			return;
		}

		List<String> lore = new ArrayList<>();
		String name = MctMaterial.friendlyName(toolItem.getType());

		lore.add(" ");
		lore.add(chat(LEVEL_DISPLAY + "0"));
		lore.add(chat(GRADE_DISPLAY + "BASIC"));
		lore.add(" ");


		if (!hasTag("tool")) {
			addTag("tool", ChatColor.stripColor(chat(name)));
		}
		if (!hasTag("tool-type")) {
			addTag("tool-type", toolItem.getType().toString());
		}
		if (!hasTag("level")) {
			addTag("level", "0");
		}
		if (hasTag("tool-grade")) {
			addTag("tool-grade", "BASIC");
		}

		if (!hasTag("owner")) {
			addTag("unclaiemd", "true");

			lore.add(chat(UNCLAIMED_TOOL_TEXT));
		}

		ItemMeta meta = toolItem.getItemMeta();

		meta.setDisplayName(chat(name));
		meta.setLore(lore);

		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

		toolItem.setItemMeta(meta);

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

	public List<String> getLore() {
		return getItemMeta().hasLore() ? getItemMeta().getLore() : new ArrayList<>();
	}

	public boolean setLore(List<String> lore) {
		ItemMeta meta = getItemMeta();
		meta.setLore(lore);

		return setItemMeta(meta);
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

	public boolean hasTag(String key) {
		return hasTag(toolItem, key);
	}

	/**
	 * Update any placeholders in this Item's String name or Lore.
	 * 
	 * @param pl
	 *               the Map of {@literal <Placeholder, Value>}'s
	 */
	public void updatePlaceholders(Map<String, String> pl) {
		toolItem = this.itemPlaceholders(toolItem, pl);
	}

}
