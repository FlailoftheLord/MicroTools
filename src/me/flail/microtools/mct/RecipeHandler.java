package me.flail.microtools.mct;

import java.util.List;

import org.bukkit.Material;

import me.flail.microtools.tools.Logger;

public class RecipeHandler extends Logger {
	private List<Material> disabled;

	public RecipeHandler(List<Material> disabledRecipes) {
		disabled = disabledRecipes;
	}


}
