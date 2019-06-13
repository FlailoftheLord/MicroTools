package me.flail.microtools.tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microtools.MicroTools;
import me.flail.microtools.user.User;

public class TabCompleter extends ArrayList<String> {
	private MicroTools plugin;

	/**
	 * UID
	 */
	private static final long serialVersionUID = 43097891222L;
	private Command command;

	public TabCompleter(Command command) {
		plugin = JavaPlugin.getPlugin(MicroTools.class);
		this.command = command;
	}

	public TabCompleter construct(String label, String[] args) {
		if (command.getName().equalsIgnoreCase("microtools")) {
			List<String> baseArgs = new ArrayList<>();
			boolean completed = false;


		}

		return this;
	}

	private List<String> usernames() {
		List<String> names = new ArrayList<>();
		for (User user : plugin.userMap.values()) {
			names.add(user.name());
		}
		return names;
	}

}
