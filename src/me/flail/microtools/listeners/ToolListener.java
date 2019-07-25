package me.flail.microtools.listeners;

import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.tools.Message;
import me.flail.microtools.user.User;

public class ToolListener extends Logger implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void invClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		ClickType click = event.getClick();

		if (hasTag(item, "gui-item")) {
			event.setCancelled(true);
		}

		if (hasTag(item, "change-tool-name")) {

		}

		if (hasTag(item, "tool") && !hasTag(item, "tool-editor-info")) {
			User user = new User(event.getWhoClicked().getUniqueId());
			MicroTool tool = MicroTool.fromItem(item);

			new ToolEditorGuiListener(user, tool).onClick(item, click);
		}



		if (hasTag(item, "close-tool-editor")) {
			Player player = (Player) event.getWhoClicked();

			player.closeInventory();

		}

	}

	@EventHandler
	public void pickUp(EntityPickupItemEvent event) {
		LivingEntity entity = event.getEntity();
		if (!(entity instanceof Player)) {
			return;
		}

		User user = new User(((Player) entity).getUniqueId());

		ItemStack item = event.getItem().getItemStack();
		boolean isTool = hasTag(item, "tool");

		if (isTool) {
			MicroTool tool = MicroTool.fromItem(item);

			if (!tool.hasOwner()) {
				tool = tool.setOwner(user);

				tool.updatePlaceholders(tool.placeholders());
				return;
			}
			if (tool.hasOwner() && !tool.getTag("owner").equalsIgnoreCase(user.id())) {
				event.getItem().setPickupDelay(1);

				new Message("CannotPickupTool").send(user, null);
				user.setMessageCooldown("CannotPickupTool", 16);

			}

		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void dropItem(EntityDropItemEvent event) {
		Item itemEntity = event.getItemDrop();
		ItemStack item = itemEntity.getItemStack();

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void interact(BlockBreakEvent event) {
		if (!event.isCancelled()) {
			User user = new User(event.getPlayer().getUniqueId());
			ItemStack item = user.player().getInventory().getItemInMainHand();

			if (hasTag(item, "tool")) {
				MicroTool tool = MicroTool.fromItem(item);

				if (tool.isTool()) {
					tool.addBlocksBroken(1);

					tool.updateBlocksBrokenDisplay();
				}

			}

		}

	}

}
