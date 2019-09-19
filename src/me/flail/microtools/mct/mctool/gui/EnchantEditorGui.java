package me.flail.microtools.mct.mctool.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.flail.microtools.mct.Enchants;
import me.flail.microtools.mct.Enchants.EnchantType;
import me.flail.microtools.mct.mctool.MicroTool;

public class EnchantEditorGui extends EditorGui {

	public static final String ENCHANT_GUI_TITLE = "&5&lEnchants&7:  &8&l";

	public EnchantEditorGui(ItemStack toolItem) {
		super(MicroTool.fromItem(toolItem));

		createInv(ENCHANT_GUI_TITLE + tool.getName(), 45);
		this.generate();
	}

	@Override
	protected void generate() {
		generatePreview();
		mainMenuButton(36);

		for (EnchantType e : Enchants.enchantsFor(tool)) {
			ItemStack eDisplay = new ItemStack(Material.ENCHANTED_BOOK);
			eDisplay = addTag(eDisplay, "tool-enchant", e.toString());

			ItemMeta meta = eDisplay.getItemMeta();
			meta.setDisplayName(chat("&e" + e.friendlyName(e)));

			List<String> lore = new ArrayList<>();
			lore.add(chat(" "));
			lore.add(chat(" &7Enchantments will stack."));

			meta.setLore(lore);
			eDisplay.setItemMeta(meta);

			items.put(items.size() + 8, eDisplay);
		}

		fillEmptySpace(new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE));
	}

}
