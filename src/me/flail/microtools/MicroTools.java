package me.flail.microtools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microtools.listeners.PlayerListener;
import me.flail.microtools.tool.ToolHandler;
import me.flail.microtools.tools.DataFile;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.tools.Settings;
import me.flail.microtools.user.User;

/**
 * Main class for MicroTools, it's gotta have everything in it right?
 * 
 * @author FlailoftheLord
 *         <br>
 *         <br>
 *         <i>How many times can you say "tools" in 3 seconds?</i>
 */
public class MicroTools extends JavaPlugin {
	public static MicroTools instance;

	public Server server = getServer();
	public PluginManager pm = server.getPluginManager();
	public Map<UUID, User> userMap = new HashMap<>();
	public DataFile settings;

	@Override
	public void onLoad() {
		instance = this;
		Settings sManager = new Settings();

		settings = new DataFile("Settings.yml");

		sManager.load();
		sManager.loadEnchantsFile();

	}

	@Override
	public void onEnable() {
		Logger logger = new Logger();
		ToolHandler tManager = new ToolHandler();

		tManager.disableRecipes();

		registerListeners();
		registerCommands();

	}

	private void registerListeners() {
		pm.registerEvents(new PlayerListener(), this);

	}

	private void registerCommands() {

	}

	/**
	 * Removes the recipe which has a result matching <code>item</code>
	 * 
	 * @param item
	 *                 the ItemStack result to remove any associated recipes.
	 */
	public void removeRecipe(ItemStack item) {
		Iterator<Recipe> recipes = server.recipeIterator();
		while(recipes.hasNext()) {
			ItemStack result = recipes.next().getResult();

			if (result.equals(item) || (result == item)) {
				recipes.remove();
			}

		}

	}

}
