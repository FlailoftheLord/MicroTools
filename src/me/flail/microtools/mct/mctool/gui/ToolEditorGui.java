package me.flail.microtools.mct.mctool.gui;

import org.bukkit.Material;

import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.tools.Logger;

public class ToolEditorGui extends Logger {

	public static final String MAIN_GUI_TITLE = "&2&lEditor&7: &8&l";
	public static final Material ITEM_CHANGE_NAME = Material.NAME_TAG;
	public static final Material ITEM_MODIFY_OWNER = Material.WRITABLE_BOOK;
	public static final Material ITEM_UPGRADE_ENCHANTS = Material.ENCHANTED_BOOK;

	public static Material itemInfo;

	public ToolEditorGui(MicroTool tool) {
		itemInfo = tool.getMaterial();

		generate();
	}

	private void generate() {

	}

}
