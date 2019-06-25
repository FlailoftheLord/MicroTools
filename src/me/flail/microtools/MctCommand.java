package me.flail.microtools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.flail.microtools.tools.Logger;

public class MctCommand extends Logger {

	private CommandSender sender;
	private Command command;

	public MctCommand(CommandSender sender, Command command) {
		this.sender = sender;
		this.command = command;
	}

	public boolean run(String[] args) {

		if (args.length > 1) {
			if (args[0].equalsIgnoreCase("numeral")) {
				int number = Integer.parseInt(args[1].replaceAll("[^0-9]", "0"));

				sender.sendMessage(this.romanNumeral(number));
			}
		}

		return true;
	}

}
