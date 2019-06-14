package me.flail.microtools.tool;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import me.flail.microtools.tools.Logger;
import me.flail.microtools.user.User;

public class MicroTool extends Logger {
	private User owner = null;
	private ToolType type;
	private ItemStack toolItem;

	public MicroTool(ItemStack item) {
		toolItem = item;
		if (this.hasTag(toolItem, "user")) {
			owner = new User(UUID.fromString(this.getTag(toolItem, "user")));
		}

		type = ToolType.fromMaterial(item.getType());
	}

	public User owner() {
		return owner;
	}

	public ToolType type() {
		return type;
	}

	public Map<String, Integer> enchants() {
		Map<String, Integer> map = new TreeMap<>();

		return map;
	}

}
