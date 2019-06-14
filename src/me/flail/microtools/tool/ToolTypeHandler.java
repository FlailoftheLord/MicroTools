package me.flail.microtools.tool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import me.flail.microtools.tool.ToolType.Armor;
import me.flail.microtools.tools.DataFile;
import me.flail.microtools.tools.Logger;

public class ToolTypeHandler extends Logger {
	private DataFile settings;

	public ToolTypeHandler() {
		settings = plugin.settings;

	}

	public void disableRecipes(ToolType defaultType, Armor defaultArmor) {
		List<Recipe> recipes = new ArrayList<>();
		List<ItemStack> toolItems = new ArrayList<>();

		for (Material m : ToolType.toolMaterials()) {
			toolItems.add(new ItemStack(m));
		}

		for (ItemStack item : toolItems) {

		}

	}

}
