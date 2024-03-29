package me.flail.microtools.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.MicroTools;
import me.flail.microtools.mct.mctool.MctMaterial;
import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.mct.mctool.ToolEditor;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.tools.Message;
import me.flail.microtools.user.User;

public class ToolListener extends Logger implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void invClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		User user = new User(event.getWhoClicked().getUniqueId());

		if ((item != null) && !event.isCancelled()) {
			ToolEditor listener = new ToolEditor(user);

			if (hasTag(item, "gui-item")) {

				listener.guiClick(item, event);
			}

			if (hasTag(item, "tool")) {

				listener.toolClick(item, event);
			}

		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void invClose(InventoryCloseEvent event) {
		if (plugin.signInputs.containsKey(event.getPlayer().getUniqueId())) {
			return;
		}

		for (ItemStack item : event.getPlayer().getInventory().getContents()) {
			if (hasTag(item, "tool")) {
				MicroTool tool = MicroTool.fromItem(item);

				tool.removeTag("editing");
				tool.updateItem();
			}

		}

		plugin.toolEditors.remove(event.getPlayer().getUniqueId());
		((Player) event.getPlayer()).updateInventory();
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

		item = removeTag(item, "gui-item");
		item = removeTag(item, "tool-editor-info");
		itemEntity.setItemStack(item);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void blockBreak(BlockBreakEvent event) {
		if (!event.isCancelled()) {
			User user = new User(event.getPlayer().getUniqueId());
			if (user.gamemode().equalsIgnoreCase("creative") && !MicroTools.blockBreakingInCreative) {
				return;
			}

			ItemStack item = user.player().getInventory().getItemInMainHand();

			if (hasTag(item, "tool")) {
				MicroTool tool = MicroTool.fromItem(item);

				if (tool.isTool()) {
					tool.addStat("blocks", 1);

					tool.updateItem();
				}

			}

		}

	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void entityKill(EntityDamageByEntityEvent event) {
		LivingEntity entity =(LivingEntity) event.getEntity();
		double damage = event.getFinalDamage();


		if (!event.isCancelled()) {
			Entity damager = event.getDamager();
			if (damager instanceof Player) {
				User user = new User(((Player) damager).getUniqueId());
				ItemStack item = user.player().getInventory().getItemInMainHand();

				if (hasTag(item, "tool")) {
					MicroTool tool = MicroTool.fromItem(item);

					if (tool.isTool()) {
						if (damage >= entity.getHealth()) {
							tool.addStat("kills", 1);

							tool.updateItem();
						}

					}

				}

			}

		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void toolUseOnEntity(PlayerInteractAtEntityEvent event) {



	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onToolUse(PlayerInteractEvent event) {

		if (event.useItemInHand().equals(Result.DENY) && event.useInteractedBlock().equals(Result.DENY)) {
			return;
		}

		ItemStack item = event.getItem();

		switch (event.getAction()) {
		case RIGHT_CLICK_BLOCK:

			if (!MctMaterial.isUseable(item)) {
				return;
			}

			MicroTool tool = MicroTool.fromItem(item);

			Location loc = event.getClickedBlock().getLocation();
			String blockType = loc.getBlock().getType().toString();
			String itemType = item.getType().toString();

			plugin.scheduler.scheduleSyncDelayedTask(plugin, () -> {
				boolean wasUsedOnce = false;

				if (itemType.contains("_AXE") && !blockType.contains("STRIPPED_")) {
					if (blockType.contains("_LOG") || blockType.contains("_WOOD")) {

						if (loc.getBlock().getType().toString().contains("STRIPPED_")) {
							wasUsedOnce = true;
						}
					}
				}

				if (itemType.contains("_SHOVEL")) {
					if (blockType.contains("GRASS_BLOCK")) {

						if (loc.getBlock().getType().toString().contains("GRASS_PATH")) {
							wasUsedOnce = true;
						}
					}
				}

				if (itemType.contains("_HOE")) {
					if (blockType.contains("MYCELIUM") || blockType.contains("PODZOL") || blockType.contains("DIRT")
							|| blockType.contains("GRASS_")) {

						if (loc.getBlock().getType().toString().contains("FARMLAND")) {
							wasUsedOnce = true;
						}
					}

				}

				if (wasUsedOnce) {

					tool.addStat("uses", 1);
					tool.updateItem();
				}

			}, 1L);



			break;
		default:
			;
		}



	}

}
