package me.flail.microtools.mct.mctool.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.Enchants.EnchantType;
import me.flail.microtools.mct.mctool.MicroTool;

public class ToolEditorGui extends EditorGui {

	public static String MAIN_GUI_TITLE = "&2&lEditor&7: &8&l";
	public static String DISPLAY_CHANGE_NAME = "&e&lChange Name";
	public static String DISPLAY_MODIFY_OWNER = "&c&lModify Owner";
	public static String DISPLAY_MANAGE_ENCHANTS = "&d&lMange Enchants";
	public static String DISPLAY_UPGRADE_ITEM = "&a&lUpgrade to next Tier";

	public static final Material ITEM_CHANGE_NAME = Material.NAME_TAG;
	public static final Material ITEM_MODIFY_OWNER = Material.WRITABLE_BOOK;
	public static final Material ITEM_MANAGE_ENCHANTS = Material.ENCHANTED_BOOK;
	public static final Material FILLER_ITEM = Material.GREEN_STAINED_GLASS_PANE;

	public ToolEditorGui(ItemStack toolItem) {
		super(MicroTool.fromItem(toolItem));

		createInv(MAIN_GUI_TITLE + tool.getName(), 45);

		this.generate();
	}

	@Override
	protected void generate() {
		List<String> lore = new ArrayList<>();

		generatePreview();
		closeButton(36);

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
		upgradeItem = setDisplayname(DISPLAY_UPGRADE_ITEM, upgradeItem);
		upgradeItem = addTag(upgradeItem, "upgrade-trigger", "true");
		lore.clear();
		lore.add("");
		lore.add("&7Cost&8: &9" + plugin.maxLevelPoints + " &7&lLP");
		upgradeItem = setLore(lore, upgradeItem);

		changeNameItem = addTag(changeNameItem, "change-tool-name", " ");
		modifyOwnerItem = addTag(modifyOwnerItem, "change-tool-owner", " ");
		manageEnchantsItem = addTag(manageEnchantsItem, "change-tool-enchants", " ");

		items.put(19, changeNameItem);
		items.put(21, modifyOwnerItem);
		items.put(23, manageEnchantsItem);
		items.put(25, upgradeItem);

		fillEmptySpace(new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
	}


}
