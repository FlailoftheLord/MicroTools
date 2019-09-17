package me.flail.microtools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.tools.Logger;
import me.flail.microtools.tools.Message;
import me.flail.microtools.user.User;

public class MctCommand extends Logger {

	private CommandSender sender;
	private Command command;

	public MctCommand(CommandSender sender, Command command) {
		this.sender = sender;
		this.command = command;
	}

	public boolean run(String[] args) {

		String arg0 = "";
		if (args.length > 0) {
			arg0 = args[0].toLowerCase();
		}

		switch (args.length) {
		case 0:
			sender.sendMessage(chat("&c/mct help"));

			break;
		case 1:
			switch (arg0) {
			case "help":
				if (sender.hasPermission("microtools.command")) {
					new Message("CommandHelp").send(sender, sender);

					break;
				}

				new Message("NoPermissionForCommand").replace("%command%", command.getName() + " " + arg0).send(sender, sender);
				break;
			case "reload":
				if (sender.hasPermission("microtools.command.reload")) {
					plugin.reload();

					new Message("PluginReloaded").send(sender, sender);
					break;
				}

				new Message("NoPermissionForCommand").replace("%command%", command.getName() + " " + arg0).send(sender, sender);
				break;
			case "numeral":
				sender.sendMessage(chat("&c/mct numeral [number]"));

				break;
			case "levelpoints":
				levelPointsCommand(args);

				break;
			case "get":
				sender.sendMessage(chat("&c/mct get [type]"));

				break;
			case "give":
				sender.sendMessage(chat("&c/mct give [type] [player]"));

				break;
			default:

				sender.sendMessage(chat("&c/mct help"));
			}

		case 2:
			switch (arg0) {
			case "numeral":
				int number = Integer.parseInt(args[1].replaceAll("[^0-9]", "0"));

				sender.sendMessage(this.romanNumeral(number));
				break;
			case "levelpoints":
				levelPointsCommand(args);
				break;
			case "get":
				if (sender.hasPermission("microtools.command.get")) {

					break;
				}

				new Message("NoPermissionForCommand").replace("%command%", command.getName() + " " + arg0).send(sender, sender);
				break;
			default:

				sender.sendMessage(chat("&c/mct help"));
			}

			break;
		case 3:
			switch (arg0) {
			case "levelpoints":
				levelPointsCommand(args);
				break;
			case "give":

				break;
			default:

				sender.sendMessage(chat("&c/mct help"));

			}

		}

		if (args.length > 1) {
			if (args[0].equalsIgnoreCase("numeral")) {

			}

		}

		return true;
	}

	public void levelPointsCommand(String[] args) {
		String arg0 = args[0].toLowerCase();

		if (!(sender instanceof Player)) {
			sender.sendMessage(chat("&cThis command is for in-game use only!"));

			return;
		}

		User operator = new User(((Player) sender).getUniqueId());

		if (!sender.hasPermission("microtools.command.levelpoints")) {
			new Message("NoPermissionForCommand").replace("%command%", command.getName() + " " + arg0).send(operator, null);

			return;
		}

		ItemStack item = operator.player().getInventory().getItemInMainHand();
		if (!(MicroTool.isValid(item))) {
			new Message("ItemNotATool").send(operator, null);

			return;
		}

		MicroTool tool = MicroTool.fromItem(item);
		switch (args.length) {
		case 1:
			sender.sendMessage(chat("&c/mct levelpoints [get:add:remove] [amount]"));

			break;
		case 2:
			switch (args[1].toLowerCase()) {
			case "get":
				sender.sendMessage(chat("&aLevelPoints&8: &7" + tool.getLevelPoints() + "&8/&e" + plugin.maxLevelPoints));

				break;
			default:
				sender.sendMessage(chat("&c/mct levelpoints " + args[1].toLowerCase() + " [amount]"));

				break;
			}

			break;
		case 3:
			switch (args[1].toLowerCase()) {
			case "get":
				sender.sendMessage(chat("&c/mct levelpoints get"));

				break;
			case "add":
				try {
					int amount = Integer.parseInt(args[2].replaceAll("[^0-9]", ""));

					tool.addLevelPoints(amount);
					sender.sendMessage(chat("&cAdded " + amount + " points."));
					break;
				} catch (Exception e) {

					sender.sendMessage(chat("&cInvalid number, " + args[2] + " is not a number!"));
					break;
				}
			case "remove":
				try {
					int amount = Integer.parseInt(args[2].replaceAll("[^0-9]", ""));

					tool.removeLevelPoints(amount);
					sender.sendMessage(chat("&cRemoved " + amount + " points."));
					break;
				} catch (Exception e) {

					sender.sendMessage(chat("&cInvalid number, " + args[2] + " is not a number!"));
					break;
				}

			}


		}

		tool.updateItem();
	}

}
