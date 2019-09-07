package me.flail.microtools.mct.mctool;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.mctool.gui.ToolEditorGui;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.tools.Message;
import me.flail.microtools.user.User;

public class ToolEditor extends Logger {

	private User operator;
	private User owner;
	private MicroTool tool;
	private MicroTool preview;

	public ToolEditor(User operator) {
		this.operator = operator;

	}

	public void guiClick(ItemStack item, InventoryClickEvent event) {
		setTool();
		setPreview(event.getInventory());

		if (plugin.toolEditors.containsKey(operator.uuid())) {
			if (hasTag(item, "tool") || hasTag(item, "gui-item") || hasTag(item, "editing")) {
				event.setCancelled(true);
			}
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

			if (!tool.hasOwner()) {
				tool.setOwner(operator);
				tool.updateItem();
			}

			owner = tool.owner();

			if (event.getClick().equals(ClickType.RIGHT) || event.getClick().equals(ClickType.SHIFT_RIGHT)) {
				if (owner.uuid().equals(operator.uuid())) {
					new ToolEditorGui(tool.item()).open(operator);

					return;
				}
			}

			if (!owner.uuid().equals(operator.uuid())) {
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

	void setTool() {
		for (ItemStack item : operator.player().getInventory().getContents()) {
			if (hasTag(item, "editing")) {
				tool = MicroTool.fromItem(item);

				return;
			}

		}

	}

	void setPreview(Inventory eventInv) {

		if (eventInv != null) {
			for (ItemStack item : eventInv.getContents()) {
				if (hasTag(item, "preview")) {

					preview = MicroTool.fromItem(item);
					preview.updateItem();
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
