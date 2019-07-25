package me.flail.microtools.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.mct.mctool.gui.ToolEditorGui;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.tools.Message;
import me.flail.microtools.user.User;

public class ToolEditorGuiListener extends Logger implements Listener {

	private User operator;
	private MicroTool tool;

	public ToolEditorGuiListener(User operator, MicroTool tool) {

		this.operator = operator;
		this.tool = tool;
	}

	public void onClick(ItemStack item, ClickType clickType) {
		if ((tool != null) && !tool.hasOwner()) {
			tool = tool.setOwner(operator);
			return;
		}

		User owner = tool.owner();
		if ((owner != null) && owner.uuid().equals(operator.uuid())) {

			new ToolEditorGui(tool).open(owner);

		} else {
			if (owner.isOnline()) {
				operator.player().getInventory().remove(tool.item());

				if (owner.player().getInventory().firstEmpty() != -1) {

					owner.player().getInventory().addItem(tool.item());
				} else {

					owner.player().getWorld().dropItem(owner.player().getLocation(), tool.item());
				}

				new Message("StolenItemReturned").send(owner, null);
			}

		}



	}

}
