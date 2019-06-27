package me.flail.microtools.mct.mctool;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.flail.microtools.tools.DataFile;
import me.flail.microtools.tools.Logger;

/**
 * Basically an easy way to modify and change the ItemMeta of this Data's parent MicroTool.
 * 
 * @author FlailoftheLord
 */
public class MctData extends Logger {
	private ItemStack toolItem;

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

		DataFile conf = new DataFile("Configuration.yml");
		List<String> lore = conf.getList("Tool.Item.Lore");
		String name = conf.getValue("Tool.Item.Name");

		addTag("tool", ChatColor.stripColor(chat(name)));
		addTag("tool-type", toolItem.getType().toString());
		addTag("level", "0");
		addTag("tool-level", "0");

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
