package me.flail.microtools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.flail.microtools.listeners.PlayerListener;
import me.flail.microtools.listeners.ToolListener;
import me.flail.microtools.mct.MctHandler;
import me.flail.microtools.mct.mctool.MicroTool;
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
	public BukkitScheduler scheduler = server.getScheduler();
	public PluginManager pm = server.getPluginManager();
	public Map<UUID, User> userMap = new HashMap<>();

	public Map<UUID, Set<String>> msgCooldowns = new TreeMap<>();
	public Map<UUID, Map<Location, MicroTool>> signInputs = new HashMap<>();
	public Map<UUID, MicroTool> toolEditors = new LinkedHashMap<>();

	public DataFile settings;

	private List<Material> disabledRecipes = new ArrayList<>();

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
		long startTime = System.currentTimeMillis();

		Logger logger = new Logger();
		MctHandler tManager = new MctHandler();

		tManager.disableRecipes();

		registerListeners();
		registerCommands();

		logger.console("&7MicroTools is Enabled &8(&7took " + (System.currentTimeMillis() - startTime) + "ms&8)");
	}

	private void registerListeners() {
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new ToolListener(), this);

	}

	private void registerCommands() {
		for (String cmd : getDescription().getCommands().keySet()) {
			getCommand(cmd).setExecutor(this);
		}

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

	/**
	 * Disables all recipes for this Material for all players.
	 * 
	 * @param list
	 *                 List of all Materials to disable recipes for.
	 */
	public void disablePlayerRecipes(List<Material> list) {
		disabledRecipes.clear();
		disabledRecipes.addAll(list);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return new MctCommand(sender, command).run(args);
	}

}
