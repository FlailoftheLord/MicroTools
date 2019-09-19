package me.flail.microtools.mct.mctool.gui;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.tools.Logger;

public class EditorGui extends Logger {

	protected MicroTool tool;
	protected Inventory gui;
	protected Map<Integer, ItemStack> items = new TreeMap<>();

	public EditorGui(MicroTool tool) {
		this.tool = tool;

	}

	protected void createInv(String title, int size) {
		gui = Bukkit.createInventory(null, size, chat(title));
	}

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

}
