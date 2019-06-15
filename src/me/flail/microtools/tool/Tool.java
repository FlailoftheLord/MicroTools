package me.flail.microtools.tool;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import me.flail.microtools.tools.Logger;
import me.flail.microtools.user.User;

public class Tool extends Logger {
	private User owner = null;
	private ItemStack toolItem;

	public Tool(ItemStack item) {
		toolItem = item;
		if (this.hasTag(toolItem, "user")) {
			owner = new User(UUID.fromString(this.getTag(toolItem, "user")));
		}

	}

	public User owner() {
		return owner;
	}

	public Map<String, Integer> enchants() {
		Map<String, Integer> map = new TreeMap<>();

		return map;
	}

}
