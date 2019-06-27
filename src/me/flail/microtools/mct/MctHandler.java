package me.flail.microtools.mct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.tool.ToolType;
import me.flail.microtools.tools.DataFile;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.user.User;

public class MctHandler extends Logger {
	private DataFile settings;

	public Map<ToolType, Material> toolmap = new HashMap<>();

	public MctHandler() {
		settings = plugin.settings;

	}

	public void disableRecipes() {
		List<ItemStack> toolItems = new ArrayList<>();
		List<Material> materials = new ArrayList<>();

		for (Material m : ToolType.materials()) {
			toolItems.add(new ItemStack(m));
		}

		for (ItemStack item : toolItems) {
			if ((item != null) && !ToolType.isDefault(item.getType())) {
				plugin.removeRecipe(item);
				materials.add(item.getType());
			}

		}

		plugin.disablePlayerRecipes(materials);
	}

	public static MicroTool newTool(User user, ItemStack item) {
		return new MicroTool(user, item);
	}

}
