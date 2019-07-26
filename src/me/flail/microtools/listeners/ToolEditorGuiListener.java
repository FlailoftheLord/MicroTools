package me.flail.microtools.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.tools.Logger;
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

		if (hasTag(item, "change-tool-name")) {
			if (tool != null) {

				operator.openToolNameEditor(tool);
			}

		}

		if (hasTag(item, "close-tool-editor")) {

			operator.player().closeInventory();
		}



	}

}
