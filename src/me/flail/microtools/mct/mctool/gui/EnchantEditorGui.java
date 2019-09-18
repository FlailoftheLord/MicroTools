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

import me.flail.microtools.mct.Enchants;
import me.flail.microtools.mct.Enchants.EnchantType;
import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.user.User;

public class EnchantEditorGui extends Logger {

	public static final String ENCHANT_GUI_TITLE = "&5&lEnchants&7:  &8&l";

	protected Inventory gui;
	protected Map<Integer, ItemStack> items = new TreeMap<>();
	private MicroTool tool;

	public EnchantEditorGui(ItemStack toolItem) {
		tool = MicroTool.fromItem(toolItem);

		gui = Bukkit.createInventory(null, 45, chat(ENCHANT_GUI_TITLE + tool.getName()));

		generate();
	}

	public void open(User user) {
		if (user.isOnline() && !plugin.toolEditors.containsKey(user.uuid())) {
			for (Integer i : items.keySet()) {
				ItemStack item = items.get(i);
				item = addTag(item, "gui-item", "true");

				gui.setItem(i.intValue(), item);
			}

			tool.addTag("editing", "true");
			tool.updateItem();
			user.player().openInventory(gui);
			plugin.toolEditors.put(user.uuid(), tool.item());
		}

	}

	private void generate() {
		ItemStack preview = tool.item().clone();
		preview = addTag(preview, "preview", "yes");
		preview = removeTag(preview, "editing");

		items.put(4, preview);

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

		fillEmptySpace();
	}

	private void fillEmptySpace() {
		ItemStack fillerItem = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);

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
