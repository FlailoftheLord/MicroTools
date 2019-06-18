package me.flail.microtools.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.tool.MicroTool;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.user.User;

public class ToolListener extends Logger implements Listener {

	@EventHandler
	public void invClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		ClickType click = event.getClick();

		if (click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)) {
			if (hasTag(item, "tool")) {
				User user = new User(event.getWhoClicked().getUniqueId());
				MicroTool tool = new MicroTool(user, item);

				if (!tool.hasTag("owner")) {
					tool = tool.setOwner(user);
				}

				tool.updatePlaceholders(tool.placeholders());

				event.setCancelled(true);
			}

		}

	}

}
