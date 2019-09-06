package me.flail.microtools.mct.mctool.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.mctool.MicroTool;

public class EnchantEditorGui extends ToolEditorGui {

	public static final String ENCHANT_GUI_TITLE = "&5&lEnchants&7:  &8&l";

	private MicroTool tool;

	public EnchantEditorGui(ItemStack toolItem) {
		super(toolItem);
		tool = MicroTool.fromItem(toolItem);

		gui = Bukkit.createInventory(null, 45, chat(ENCHANT_GUI_TITLE + tool.getName()));

		generate();
	}

	private void generate() {

	}

}
