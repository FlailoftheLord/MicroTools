package me.flail.microtools.listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;

import me.flail.microtools.tools.Logger;

public class RecipeControl extends Logger implements Listener {
	private List<Material> disabled;

	public RecipeControl(List<Material> disabledRecipes) {
		disabled = disabledRecipes;
	}

	@EventHandler
	public void playerAction(PlayerRecipeDiscoverEvent event) {


	}

}
