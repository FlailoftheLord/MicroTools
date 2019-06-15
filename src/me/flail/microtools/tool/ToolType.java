package me.flail.microtools.tool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;

import me.flail.microtools.MicroTools;

public enum ToolType {
	WOOD, STONE, IRON, GOLD, DIAMOND;

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

	public static boolean isDefault(Material item) {
		if (fromMaterial(item).equals(defaultToolType()) || Armor.fromMaterial(item).equals(Armor.defaultArmorType())) {
			return true;
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
		if (!isArmor(type)) {

			switch (type) {
			case "wooden":
				return WOOD;
			case "stone":
				return STONE;
			case "iron":
				return IRON;
			case "golden":
				return GOLD;
			case "diamond":
				return DIAMOND;
			default:
				return null;

			}

		}
		return null;
	}

	public static List<String> toolAttributes() {
		List<String> list = new ArrayList<>();

		list.add("max_health");
		list.add("knockback_resistance");
		list.add("attack_speed");
		list.add("movement_speed");

		return list;
	}

	public static List<Material> toolMaterials() {
		List<Material> list = new LinkedList<>();
		String[] keys = { "_SWORD", "_AXE", "_PICKAXE", "_SHOVEL", "_HOE", "_HELMET", "_CHESTPLATE", "_LEGGINGS", "_BOOTS" };
		for (Material m : Material.values()) {
			for (String key : keys) {
				if (m.toString().contains(key)) {
					list.add(m);
				}
			}
		}

		return list;
	}

	/**
	 * A Subset of tools, specifically for wearable tools (aka. Armor)
	 * 
	 * @author FlailoftheLord
	 */
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

		public static Armor fromMaterial(Material itemMaterial) {
			String type = itemMaterial.toString().toLowerCase().split("_")[0];
			if (isArmor(type)) {

				switch (type) {
				case "leather":
					return Armor.LEATHER;
				case "chain":
					return Armor.CHAIN;
				case "iron":
					return Armor.IRON;
				case "golden":
					return Armor.GOLD;
				case "diamond":
					return Armor.DIAMOND;
				default:
					return null;

				}

			}
			return null;
		}

	}

}
