package me.flail.microtools.mct.mctool.gui;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.user.User;

public class EditorGui extends Logger {

	protected MicroTool tool;
	protected Inventory gui;
	protected Map<Integer, ItemStack> items = new TreeMap<>();

	public EditorGui(MicroTool tool) {
		this.tool = tool;

	}

	public void open(User user) {
		if (user.isOnline()) {
			for (Integer i : items.keySet()) {
				ItemStack item = items.get(i);
				item = addTag(item, "gui-item", "true");

				gui.setItem(i.intValue(), item);
			}

			tool.addTag("editing", "true");
			tool.updateItem();
			user.player().openInventory(gui);

			generatePreview();
			plugin.toolEditors.put(user.uuid(), tool.item());
		}

	}

	protected void createInv(String title, int size) {
		gui = Bukkit.createInventory(null, size, chat(title));
	}

	protected void generatePreview() {
		ItemStack previewItem = tool.item().clone();
		previewItem = addTag(previewItem, "preview", "yes");
		previewItem = removeTag(previewItem, "editing");

		MicroTool preview = MicroTool.fromItem(previewItem);
		preview.updateItem();

		gui.setItem(4, preview.item());

	}

	/**
	 * Button to head back to {@link ToolEditorGui}
	 */
	protected void mainMenuButton(int slot) {
		ItemStack button = new ItemStack(Material.BARRIER);
		button = setDisplayname("&c&l&nBack to Editor", button);
		button = addTag(button, "back-to-tool-editor", "true");

		items.put(slot, button);
	}

	/**
	 * Button to close the Inventory
	 */
	protected void closeButton(int slot) {
		ItemStack closeButton = new ItemStack(Material.BARRIER);
		closeButton = setDisplayname("&c&l&nClose", closeButton);
		closeButton = addTag(closeButton, "close-tool-editor", "true");

		items.put(slot, closeButton);
	}

	/**
	 * Creates all the items to put into the Inventory.
	 */
	protected void generate() {
	}

	protected void fillEmptySpace(ItemStack fillerItem) {
		ItemMeta meta = fillerItem.getItemMeta();
		meta.setDisplayName(" ");
		fillerItem.setItemMeta(meta);

		for (int i = 0; i < gui.getSize(); i++) {
			if (!items.containsKey(Integer.valueOf(i))) {
				items.put(Integer.valueOf(i), fillerItem);
			}
		}

	}

	protected ItemStack setDisplayname(String name, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(chat(name));
		item.setItemMeta(meta);

		return item;
	}

	protected ItemStack setLore(List<String> lore, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		for (String line : lore.toArray(new String[] {})) {
			lore.set(lore.indexOf(line), chat(line));
		}

		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}

}
