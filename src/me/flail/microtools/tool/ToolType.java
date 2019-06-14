package me.flail.microtools.tool;

import org.bukkit.Material;

import me.flail.microtools.MicroTools;

public enum ToolType {
	WOOD, STONE, IRON, GOLD, DIAMOND;

	static MicroTools plugin = MicroTools.instance;

	public enum Armor {
		LEATHER, CHAIN, IRON, GOLD, DIAMOND;

		public static Armor defaultArmorType() {
			String type = plugin.settings.getValue("Armor.DefaultMaterial").toLowerCase();
			if (type != null) {
				switch (type) {
				case "leather":
					return LEATHER;
				case "chain":
					return CHAIN;
				case "iron":
					return IRON;
				case "gold":
					return GOLD;
				case "diamond":
					return DIAMOND;
				default:
					return LEATHER;

				}
			}

			return null;
		}
	}

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


	public static ToolType defaultToolType() {
		String type = plugin.settings.getValue("Tools.DefaultMaterial").toLowerCase();
		if (type != null) {
			switch (type) {
			case "wood":
				return WOOD;
			case "stone":
				return STONE;
			case "iron":
				return IRON;
			case "gold":
				return GOLD;
			case "diamond":
				return DIAMOND;
			default:
				return WOOD;

			}
		}

		return WOOD;
	}

	public static ToolType fromMaterial(Material itemMaterial) {
		String type = itemMaterial.toString().toLowerCase().split("_")[0];
		if (isArmor(type)) {

		}
		switch (type) {
		case "wooden":
			return WOOD;
		case "stone":
			return STONE;

		}

		return WOOD;
	}

}
