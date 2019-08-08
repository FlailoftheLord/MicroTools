package me.flail.microtools.mct.mctool.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.flail.microtools.mct.Enchants.EnchantType;
import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.user.User;

public class ToolEditorGui extends Logger {

	public static final String MAIN_GUI_TITLE = "&2&lEditor&7: &8&l";
	public static final String DISPLAY_CHANGE_NAME = "&e&lChange Name";
	public static final String DISPLAY_MODIFY_OWNER = "&c&lModify Owner";
	public static final String DISPLAY_MANAGE_ENCHANTS = "&d&lMange Enchants";

	public static final Material ITEM_CHANGE_NAME = Material.NAME_TAG;
	public static final Material ITEM_MODIFY_OWNER = Material.WRITABLE_BOOK;
	public static final Material ITEM_MANAGE_ENCHANTS = Material.ENCHANTED_BOOK;
	public static final Material FILLER_ITEM = Material.GREEN_STAINED_GLASS_PANE;

	public static Material infoItemType;


	protected Inventory gui;
	protected Map<Integer, ItemStack> items = new TreeMap<>();

	private MicroTool tool;

	public ToolEditorGui(MicroTool tool) {
		this.tool = tool;

		infoItemType = tool.getMaterial();
		gui = Bukkit.createInventory(null, 45, chat(MAIN_GUI_TITLE + tool.getName()));

		generate();
	}

	public void open(User user) {
		if (user.isOnline()) {
			for (Integer i : items.keySet()) {
				ItemStack item = items.get(i);
				item = addTag(item, "gui-item", "true");

				gui.setItem(i.intValue(), item);
			}

			tool.addTag("editing", "true");
			user.player().openInventory(gui);
		}

	}


	/**
	 * Creates all the items to put into the Inventory.
	 */
	private void generate() {
		List<String> lore = new ArrayList<>();

		ItemStack infoItem = tool.item().clone();
		infoItem = addTag(infoItem, "preview", " ");
		infoItem = removeTag(infoItem, "editing");


		// Item for modifying the Tool's displayname.
		ItemStack changeNameItem = new ItemStack(ITEM_CHANGE_NAME);
		changeNameItem = setDisplayname(DISPLAY_CHANGE_NAME, changeNameItem);

		lore.clear();
		lore.add(" ");
		lore.add("&7click to change the");
		lore.add("&7displayname of this item.");
		changeNameItem = setLore(lore, changeNameItem);


		// Item for modifying the Tool owner.
		ItemStack modifyOwnerItem = new ItemStack(ITEM_MODIFY_OWNER);
		modifyOwnerItem = setDisplayname(DISPLAY_MODIFY_OWNER, modifyOwnerItem);

		lore.clear();
		lore.add(" ");
		lore.add("&7You can change or remove");
		lore.add("&7the owner of this item.");
		lore.add("&7As long as you are the current owner.");
		modifyOwnerItem = setLore(lore, modifyOwnerItem);


		// Item for managing enchantments.
		ItemStack manageEnchantsItem = new ItemStack(ITEM_MANAGE_ENCHANTS);
		manageEnchantsItem = setDisplayname(DISPLAY_MANAGE_ENCHANTS, manageEnchantsItem);

		lore.clear();
		lore.add(" ");
		lore.add("&7Click to manage the enchantments");

		lore.add("&7on this item.");
		if (tool.hasEnchants()) {
			lore.add(" ");
			lore.add(" &aCurrent Enchantments&8:");

			for (EnchantType e : tool.enchants().keySet()) {
				String level = romanNumeral(tool.enchants().get(e).intValue());

				lore.add("  &7- " + enumName(e) + " " + level);
			}

		}
		manageEnchantsItem = setLore(lore, manageEnchantsItem);

		// Temporary item for upgrade testing.
		ItemStack upgradeItem = new ItemStack(Material.ANVIL);
		upgradeItem = addTag(upgradeItem, "upgrade-trigger", "true");

		// Close button item.
		ItemStack closeButton = new ItemStack(Material.BARRIER);
		closeButton = setDisplayname("&c&l&nClose", closeButton);
		closeButton = addTag(closeButton, "close-tool-editor", "true");

		changeNameItem = addTag(changeNameItem, "change-tool-name", " ");
		modifyOwnerItem = addTag(modifyOwnerItem, "change-tool-owner", " ");
		manageEnchantsItem = addTag(manageEnchantsItem, "change-tool-enchants", " ");

		items.put(4, infoItem);
		items.put(19, changeNameItem);
		items.put(21, modifyOwnerItem);
		items.put(23, manageEnchantsItem);
		items.put(36, closeButton);
		items.put(8, upgradeItem);

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

	private ItemStack setDisplayname(String name, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(chat(name));
		item.setItemMeta(meta);

		return item;
	}

	private ItemStack setLore(List<String> lore, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		for (String line : lore.toArray(new String[] {})) {
			lore.set(lore.indexOf(line), chat(line));
		}

		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}

}
