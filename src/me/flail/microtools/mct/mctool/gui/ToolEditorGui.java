package me.flail.microtools.mct.mctool.gui;

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

public class ToolEditorGui extends Logger {

	public static final String MAIN_GUI_TITLE = "&2&lEditor&7: &8&l";
	public static final Material ITEM_CHANGE_NAME = Material.NAME_TAG;
	public static final Material ITEM_MODIFY_OWNER = Material.WRITABLE_BOOK;
	public static final Material ITEM_UPGRADE_ENCHANTS = Material.ENCHANTED_BOOK;
	public static final Material FILLER_ITEM = Material.LIME_STAINED_GLASS_PANE;

	public static Material infoItemType;


	private Inventory gui;
	private Map<Integer, ItemStack> items = new TreeMap<>();

	private MicroTool tool;

	public ToolEditorGui(MicroTool tool) {
		this.tool = tool;

		infoItemType = tool.getMaterial();
		gui = Bukkit.createInventory(null, 54);

		generate();
	}

	public void open(User user) {
		if (user.isOnline()) {

		}

	}

	private void generate() {
		ItemStack infoItem = new ItemStack(infoItemType);


		fillEmptySpace();
	}

	private void fillEmptySpace() {
		ItemStack fillerItem = new ItemStack(FILLER_ITEM);

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
