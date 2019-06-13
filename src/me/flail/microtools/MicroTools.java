package me.flail.microtools;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microtools.tools.DataFile;
import me.flail.microtools.tools.Settings;
import me.flail.microtools.user.User;

public class MicroTools extends JavaPlugin {
	public static MicroTools instance;

	public Server server = getServer();
	public PluginManager pm = server.getPluginManager();
	public Map<UUID, User> userMap = new HashMap<>();
	public DataFile settings;

	@Override
	public void onLoad() {
		instance = this;

		settings = new DataFile("Settings.yml");

		new Settings().load();
	}

}
