package me.flail.microtools.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.tool.ToolType.Armor;
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

		for (Material m : ToolType.typeMaterials()) {
			toolItems.add(new ItemStack(m));
		}

		for (ItemStack item : toolItems) {
			if ((item != null) && !ToolType.isDefault(item.getType())) {
				plugin.removeRecipe(item);
			}

		}

	}

	public static ItemStack newArmor(Armor type) {
		ItemStack item = new ItemStack(ToolType.defaultType().toMaterial());

		return item;
	}

	public static ItemStack newTool(ToolType type) {

		return null;
	}

}
