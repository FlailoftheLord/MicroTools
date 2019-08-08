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
	private MicroTool preview;

	public ToolEditorGuiListener(User operator, MicroTool tool, MicroTool preview) {

		this.operator = operator;
		this.tool = tool;
		this.preview = preview;
	}

	public void onClick(ItemStack item, ClickType clickType) {
		if ((tool != null) && !tool.hasOwner()) {
			tool = tool.setOwner(operator);
			return;
		}

		if (hasTag(item, "preview")) {
			ItemStack newTool = preview.item().clone();
			newTool = removeTag(newTool, "preview");
			newTool = removeTag(newTool, "gui-item");

			tool = MicroTool.fromItem(newTool);

			return;
		}

		if (hasTag(item, "change-tool-name")) {
			if (tool != null) {
				operator.player().closeInventory();

				operator.openToolNameEditor(tool);
			}

		}

		if (hasTag(item, "close-tool-editor")) {

			for (ItemStack invItem : operator.player().getInventory().getContents()) {
				if (invItem != null) {
					invItem = removeTag(invItem, "editing");
					invItem = removeTag(invItem, "tool-editor-info");
				}
			}

			operator.player().closeInventory();
		}

		if (hasTag(item, "upgrade-trigger")) {
			tool.upgrade();
		}




	}

}
