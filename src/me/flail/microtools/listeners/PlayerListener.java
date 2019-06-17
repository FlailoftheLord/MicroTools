package me.flail.microtools.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.tool.ToolHandler;
import me.flail.microtools.tool.types.ToolType;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.tools.Message;
import me.flail.microtools.user.User;

public class PlayerListener extends Logger implements Listener {

	@EventHandler
	public void playerCraft(CraftItemEvent event) {
		ItemStack item = event.getCurrentItem();
		User user = new User(((Player) event.getWhoClicked()).getUniqueId());

		if (ToolType.materials().contains(item.getType())) {
			if (!ToolType.isDefault(item.getType())) {
				event.setResult(Result.DENY);
				user.player().closeInventory();

				new Message("CantCraftMustUpgrade").send(user, null);
				return;
			}

			event.setCurrentItem(new ToolHandler().newTool(user, item.getType()));

		}

	}


}
