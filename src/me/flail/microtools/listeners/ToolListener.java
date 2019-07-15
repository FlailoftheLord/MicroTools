package me.flail.microtools.listeners;

import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.mct.mctool.gui.ToolEditorGui;
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

		if (click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)) {
			if (hasTag(item, "tool") && !hasTag(item, "tool-editor-info")) {
				User user = new User(event.getWhoClicked().getUniqueId());
				MicroTool tool = MicroTool.fromItem(item);

				if (!tool.hasOwner()) {
					tool = tool.setOwner(user);
				} else {
					User owner = tool.owner();

					if ((owner != null) && owner.uuid().equals(user.uuid())) {

						new ToolEditorGui(tool).open(owner);

					} else {
						if (owner.isOnline()) {
							user.player().getInventory().remove(item);

							if (owner.player().getInventory().firstEmpty() != -1) {

								owner.player().getInventory().addItem(tool.item());
							} else {

								owner.player().getWorld().dropItem(owner.player().getLocation(), tool.item());
							}

							new Message("StolenItemReturned").send(owner, null);

						}

					}

				}

				tool.updatePlaceholders(tool.placeholders());
				return;
			}

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
				event.setCancelled(true);

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

	@EventHandler
	public void interact(BlockIgniteEvent event) {
		if (!event.isCancelled()) {

		}

	}

}
