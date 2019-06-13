package me.flail.microtools.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommonUtilities extends BaseUtilities {

	protected String chat(String message) {
		message = message.toString();
		message = message.replace("%prefix%", new Message("Prefix").stringValue());

		return ChatColor.translateAlternateColorCodes('&', message);
	}

	/**
	 * Converts a string, by translating the following placeholders with their counterparts defined in
	 * the provided Map of placeholders.
	 * 
	 * @param message
	 * @param placeholders
	 *                         Formatted as
	 *                         <code>{@literal Map<String placeholder, String value>}</code>
	 * @return the new String.
	 */
	public String placeholders(String message, Map<String, String> placeholders) {
		if (!placeholders.isEmpty() && (message != null)) {
			for (String p : placeholders.keySet()) {
				if (p != null) {
					message = message.replace(p, placeholders.get(p));
				}
			}
		}
		return chat(message);
	}

	public ItemStack itemPlaceholders(ItemStack item, Map<String, String> placeholders) {
		if ((item != null) && item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if (meta.hasLore()) {
				List<String> lore = meta.getLore();

				List<String> newLore = new ArrayList<>();
				for (String line : lore) {
					newLore.add(this.placeholders(line, placeholders));
				}
				meta.setLore(newLore);
				meta.setDisplayName(this.placeholders(meta.getDisplayName(), placeholders));

				item.setItemMeta(meta);
			}
		}

		return item;
	}

	public Inventory updateItemPlaceholders(Inventory inv, Map<String, String> placeholders) {
		if (inv != null) {
			for (ItemStack item : inv.getContents()) {
				item = this.itemPlaceholders(item, placeholders);
			}
		}

		return inv;
	}

	public boolean msgCheck(String message, String text, String type) {
		switch (type.toLowerCase()) {
		case "starts":
			return message.startsWith(text.toLowerCase());
		case "ends":
			return message.endsWith(text.toLowerCase());
		case "contains":
			return message.contains(text.toLowerCase());
		default:
			return false;

		}
	}

	public String replaceText(String message, String text, String replacement) {
		return message = message.replaceAll("(?i)" + Pattern.quote(text), replacement);
	}

	public String convertArray(String[] values, int start) {
		StringBuilder builder = new StringBuilder();
		while (start < values.length) {
			builder.append(values[start] + " ");

			start++;
		}

		return builder.toString();
	}

}
