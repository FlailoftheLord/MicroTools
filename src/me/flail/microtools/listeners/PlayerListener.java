package me.flail.microtools.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.mct.mctool.ToolType;
import me.flail.microtools.mct.mctool.gui.ToolEditorGui;
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
				event.setCancelled(true);
				user.player().closeInventory();

				new Message("CantCraftMustUpgrade").send(user, null);
				return;
			}
			MicroTool tool = MicroTool.fromItem(item);

			event.setCurrentItem(tool.item());


		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void playerDeath(PlayerDeathEvent event) {
		boolean keepOnDeath = plugin.settings.getBoolean("General.KeepOnDeath");
		if (keepOnDeath) {
			User user = new User(event.getEntity().getUniqueId());

			List<ItemStack> keptItems = new ArrayList<>();
			ItemStack[] items = event.getDrops().toArray(new ItemStack[] {});
			for (ItemStack item : items) {
				if (ToolType.isValid(item.getType())) {
					keptItems.add(item);
					event.getDrops().remove(item);
				}
			}

			plugin.server.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
				user.player().spigot().respawn();

				user.player().getInventory().addItem(keptItems.toArray(new ItemStack[] {}));
			}, 64L);

		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onSignChange(SignChangeEvent event) {
		if (!event.isCancelled()) {
			User user = new User(event.getPlayer().getUniqueId());
			Sign sign = (Sign) event.getBlock().getState();

			if (plugin.signInputs.containsKey(user.uuid())
					&& plugin.signInputs.get(user.uuid()).containsKey(sign.getLocation())) {

				String inputValue = event.getLine(0);
				ItemStack toolItem = plugin.signInputs.get(user.uuid()).get(sign.getLocation());

				MicroTool tool = MicroTool.fromItem(toolItem);

				tool.setName(chat(inputValue));

				plugin.signInputs.remove(user.uuid());
				new Message("ToolNameChanged").replace("%tool%", tool.getName()).send(user, null);

				new ToolEditorGui(tool.item()).open(user);

				event.getBlock().setType(Material.AIR);
			}

		}

	}


}
