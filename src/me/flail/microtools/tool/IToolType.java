package me.flail.microtools.tool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import me.flail.microtools.MicroTools;

public class IToolType {
	static MicroTools plugin = MicroTools.instance;

	public static boolean isArmor(Material m) {
		String type = m.toString().toLowerCase();
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

}
