package me.flail.microtools.mct;

import java.util.HashMap;
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
import me.flail.microtools.tool.ToolType;
import me.flail.microtools.tool.ToolType.Tool;
import me.flail.microtools.tools.DataFile;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.tools.Message;
import me.flail.microtools.user.User;

public class MicroTool extends Logger {
	private User owner = null;
	private ItemStack toolItem;

	/**
	 * Use this to generate a new MicroTool from an existing ItemStack.
	 * Always be sure to set the Owner of this tool by calling {@link #setOwner(User owner)}
	 */
	public MicroTool(User owner, ItemStack item) {
		toolItem = item;
		this.owner = owner;

		create();
	}

	/**
	 * Generates the new Tool from the provided ItemStack.
	 */
	private void create() {
		if (hasTag("tool")) {
			return;
		}

		this.setOwner(owner);

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

		updatePlaceholders(this.placeholders());

		setNextUpgrade();

	}

	/**
	 * Checks if the ItemStack is a valid tool.
	 */
	public boolean isValid() {
		return ToolType.materials().contains(type());
	}

	public User owner() {
		return owner;
	}

	public MicroTool setOwner(User newOwner) {
		owner = newOwner;

		removeTag("owner");
		addTag("owner", owner.id());
		return this;
	}

	public ItemStack item() {
		return toolItem;
	}

	public boolean isArmor() {
		return false;
	}

	public boolean isTool() {
		return Tool.materials().contains(type());
	}

	public boolean isMisc() {
		return !isTool() && !isArmor() && ToolType.materials().contains(type());
	}

	public Material type() {
		return toolItem.getType();
	}

	public String getName() {
		if (toolItem.hasItemMeta()) {
			return toolItem.getItemMeta().getDisplayName();
		}
		return type().toString().toLowerCase();
	}

	public int upgradeLevel() {
		return Integer.parseInt(getTag("level").replaceAll("[^0-9]", ""));
	}

	public int gradeLevel() {
		return Integer.parseInt(getTag("tool-level").replaceAll("[^0-9]", ""));
	}

	public void setNextUpgrade() {
		if (!isMisc()) {
			Map<Integer, String> upgrades = ToolType.Tool.upgradeOrder();

			if (isArmor()) {

			}

			if ((upgradeLevel() + 1) > upgrades.size()) {
				addTag("upgrade", "max");
				return;
			}

			addTag("upgrade", upgrades.get(Integer.valueOf(upgradeLevel() + 1)));
			return;
		}
	}

	public MicroTool upgrade(String upgrade) {
		if (getTag("upgrade").equalsIgnoreCase("max")) {
			console("Item is at max upgrades, and cannot be upgraded further: " + type().toString());
			return this;
		}

		int level = upgradeLevel();
		removeTag("level");
		addTag("level", level + 1 + "");

		if (isArmor()) {

			return this;
		}

		return this;
	}

	public MicroTool upgradeEnchants(int increment) {
		if (increment < 0) {
			increment = Integer.MAX_VALUE;
		}

		return this;
	}

	public Map<EnchantType, Integer> enchants() {
		Map<EnchantType, Integer> map = new TreeMap<>();

		if (toolItem.hasItemMeta() && toolItem.getItemMeta().hasEnchants()) {
			for (Enchantment e : toolItem.getEnchantments().keySet()) {
				map.put(EnchantType.fromString(e.getKey().getKey()), Integer.valueOf(toolItem.getItemMeta().getEnchantLevel(e)));
			}

		}

		return map;
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

	public void updatePlaceholders(Map<String, String> pl) {
		toolItem = this.itemPlaceholders(toolItem, pl);
	}

	public Map<String, String> placeholders() {
		Map<String, String> map = new HashMap<>();
		map.put("%level%", upgradeLevel() + "");
		if (owner == null) {
			map.put("%owner%", new Message("ToolDoesntHaveOwner").stringValue());
			map.put("%owner-uuid%", "");
		} else {
			map.put("%owner%", owner.name());
			map.put("%owner-uuid%", owner.id());
		}
		map.put("%tool-level%", gradeLevel() + "");
		map.put("%tool%", this.getName());
		map.put("%item%", type().toString());

		return map;
	}

}
