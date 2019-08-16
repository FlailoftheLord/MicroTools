package me.flail.microtools.mct.mctool;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.mctool.gui.ToolEditorGui;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.user.User;

public class ToolEditor extends Logger {

	private User operator;
	private MicroTool tool;
	private MicroTool preview;

	public ToolEditor(User operator) {
		this.operator = operator;
	}

	public void guiClick(ItemStack item, InventoryClickEvent event) {
		setPreview(event.getClickedInventory());

		if (hasTag(item, "gui-item")) {
			event.setCancelled(true);
		}

		if (hasTag(item, "preview")) {

			applyChanges();
			return;
		}

		if (hasTag(item, "upgrade-trigger")) {
			preview.upgrade();

			return;
		}

		if (hasTag(item, "change-tool-name")) {
			operator.openToolNameEditor(preview);

			return;
		}

	}

	public void toolClick(ItemStack item, InventoryClickEvent event) {
		if (!hasTag(item, "editing") && hasTag(item, "tool") && !hasTag(item, "preview")) {
			tool = MicroTool.fromItem(item);

			new ToolEditorGui(tool).open(operator);
		}

	}

	void setPreview(Inventory eventInv) {
		if (eventInv != null) {
			for (ItemStack item : eventInv.getContents()) {
				if (!hasTag(item, "editing") && hasTag(item, "tool")) {
					preview = MicroTool.fromItem(item);
					return;
				}
			}

		}

	}

	// apply the changes from the preview item to the real tool.
	void applyChanges() {

		tool.setItemStack(preview.item());

		tool.removeTag("preview");
		tool.removeTag("gui-item");
		tool.updateItem();
	}

}
