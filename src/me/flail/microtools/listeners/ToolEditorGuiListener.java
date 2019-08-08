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
		if ((preview != null) && !preview.hasOwner()) {
			preview = preview.setOwner(operator);
			return;
		}

		if (hasTag(item, "preview")) {
			ItemStack newTool = preview.item().clone();
			newTool = removeTag(newTool, "preview");
			newTool = removeTag(newTool, "gui-item");

			newTool = addTag(newTool, "editing", "true");
			tool = MicroTool.fromItem(newTool);

			return;
		}

		if (hasTag(item, "change-tool-name")) {
			if (preview != null) {
				operator.player().closeInventory();

				operator.openToolNameEditor(preview);
			}

		}

		if (hasTag(item, "close-tool-editor")) {

			operator.player().closeInventory();
		}

		if (hasTag(item, "upgrade-trigger")) {
			preview.upgrade();
		}

	}

}
