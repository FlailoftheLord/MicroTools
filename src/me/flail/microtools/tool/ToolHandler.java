package me.flail.microtools.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.tool.types.MicroTool;
import me.flail.microtools.tool.types.MicroTool.Armor;
import me.flail.microtools.tool.types.MicroTool.Tool;
import me.flail.microtools.tools.DataFile;
import me.flail.microtools.tools.Logger;

public class ToolHandler extends Logger {
	private DataFile settings;

	public Map<ToolType, Material> toolmap = new HashMap<>();

	public ToolHandler() {
		settings = plugin.settings;

	}

	public void disableRecipes() {
		List<ItemStack> toolItems = new ArrayList<>();

		for (Material m : MicroTool.materials()) {
			toolItems.add(new ItemStack(m));
		}

		for (ItemStack item : toolItems) {
			if ((item != null) && !MicroTool.isDefault(item.getType())) {
				plugin.removeRecipe(item);
			}

		}

	}

	public static ItemStack newArmor(Armor type) {
		ItemStack item = new ItemStack(Armor.getDefault(type));

		return item;
	}

	public static ItemStack newTool(Tool type) {
		ItemStack item = new ItemStack(Tool.getDefault(type));

		return null;
	}

}
