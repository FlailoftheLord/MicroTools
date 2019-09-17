package me.flail.microtools.tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microtools.MicroTools;
import me.flail.microtools.mct.mctool.MctMaterial;
import me.flail.microtools.user.User;

public class TabCompleter {
	private MicroTools plugin;

	private Command command;

	public TabCompleter(Command command) {
		plugin = JavaPlugin.getPlugin(MicroTools.class);
		this.command = command;
	}

	public List<String> construct(String label, String[] args) {
		List<String> baseArgs = new ArrayList<>();
		if (!command.getName().equalsIgnoreCase("microtools")) {
			return baseArgs;
		}

		switch (args.length) {
		case 1:
			baseArgs.add("levelpoints");
			baseArgs.add("help");
			baseArgs.add("give");
			baseArgs.add("get");
			break;
		case 2:
			String arg0 = args[0].toLowerCase();

			if (arg0.equals("levelpoints")) {
				baseArgs.add("get");
				baseArgs.add("add");
				baseArgs.add("remove");
			} else if (arg0.equals("give") || arg0.equals("get")) {
				baseArgs.addAll(microToolTypes());
			}

			break;
		case 3:
			arg0 = args[0].toLowerCase();

			if (arg0.equals("levelpoints")) {
				switch (args[1].toLowerCase()) {
				case "add":
					for (int n = 0; n <= plugin.maxLevelPoints; n++) {
						baseArgs.add(n + "");
					}

					break;

				}

				break;
			} else if (arg0.equals("give")) {

				baseArgs.addAll(usernames());
			}

		}

		for (String s : baseArgs.toArray(new String[] {})) {
			if (!s.startsWith(args[args.length - 1].toLowerCase())) {

				baseArgs.remove(s);
			}

		}

		return baseArgs;
	}

	protected List<String> microToolTypes() {
		List<String> list = new ArrayList<>();
		for (String m : MctMaterial.values()) {
			list.add(m.toLowerCase());
		}

		return list;
	}

	protected List<String> usernames() {
		List<String> names = new ArrayList<>();
		for (User user : plugin.userMap.values()) {
			names.add(user.name());
		}
		return names;
	}

}
