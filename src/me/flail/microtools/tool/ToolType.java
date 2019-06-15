package me.flail.microtools.tool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import me.flail.microtools.MicroTools;
import me.flail.microtools.tool.types.MicroTool;

public class ToolType {
	static MicroTools plugin = MicroTools.instance;

	public static boolean isArmor(Material material) {
		return isArmor(material.toString());
	}

	public static boolean isArmor(String type) {
		type = type.toLowerCase();
		String[] armorTypes = { "shield", "helmet", "chestplate", "leggings", "boots" };

		for (String aT : armorTypes) {
			if (type.contains(aT)) {
				return true;
			}
		}

		return false;
	}


	public static List<String> toolAttributes() {
		List<String> list = new ArrayList<>();

		list.add("max_health");
		list.add("knockback_resistance");
		list.add("attack_speed");
		list.add("movement_speed");

		return list;
	}

	/**
	 * A Subset of tools, specifically for wearable tools (aka. Armor)
	 * 
	 * @author FlailoftheLord
	 */
	public enum Armor {
		;
		public static Material[] defaultType() {
			String type = plugin.settings.getValue("Armor.DefaultMaterial").toLowerCase();
			if (type != null) {
				return new Material[] { MicroTool.fromString(type) };
			}

			return MicroTool.defaultArmor().toArray(new Material[] {});
		}

	}

}
