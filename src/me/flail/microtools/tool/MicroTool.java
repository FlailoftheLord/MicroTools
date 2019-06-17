package me.flail.microtools.tool;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.tool.types.ToolType;
import me.flail.microtools.tool.types.ToolType.Armor;
import me.flail.microtools.tool.types.ToolType.Tool;
import me.flail.microtools.tools.DataFile;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.user.User;

public class MicroTool extends Logger {
	private User owner;
	private ItemStack toolItem;

	public MicroTool(ItemStack item) {
		toolItem = item;
		if (!this.hasTag(toolItem, "user")) {
			return;
		}

		owner = new User(UUID.fromString(this.getTag(toolItem, "user")));

	}

	private void create() {
		DataFile conf = new DataFile("Configuration.yml");
		List<String> lore = conf.getList("Tool.Item.Lore");
		String name = conf.getValue("Tool.Item.Name");

	}

	public boolean isValid() {
		return ToolType.materials().contains(type());
	}

	public User owner() {
		return owner;
	}

	public MicroTool setOwner(User user) {
		owner = user;

		addTag("user", owner.id());
		return this;
	}

	public ItemStack item() {
		return toolItem;
	}

	public boolean isArmor() {
		return Armor.materials().contains(type());
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

	public int upgradeLevel() {
		return Integer.parseInt(getTag("level").replace("[^0-9]", ""));
	}

	public void setNextUpgrade(String value) {
		if (!isMisc()) {
			Map<Integer, String> upgrades = ToolType.Tool.upgradeOrder();

			if (isArmor()) {
				upgrades = ToolType.Armor.upgradeOrder();
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

	public Map<Enchants, Integer> enchants() {
		Map<Enchants, Integer> map = new TreeMap<>();

		if (toolItem.hasItemMeta() && toolItem.getItemMeta().hasEnchants()) {
			for (Enchantment e : toolItem.getEnchantments().keySet()) {
				map.put(Enchants.fromString(e.getKey().getKey()), Integer.valueOf(toolItem.getItemMeta().getEnchantLevel(e)));
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
}
