package me.flail.microtools.tool.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import me.flail.microtools.MicroTools;

public enum ToolType {
	FLINT_AND_STEEL, SHIELD, BOW, CROSSBOW, SHEARS, FISHING_ROD;

	static MicroTools plugin = MicroTools.instance;

	public enum Tool {
		AXE, HOE, PICKAXE, SHOVEL, SWORD;

		public enum type {
			WOODEN, STONE, IRON, GOLDEN, DIAMOND;
		}

		public static Map<Integer, String> upgradeOrder() {
			Map<Integer, String> map = new HashMap<>();
			int order = 0;

			for (String s : plugin.settings.getValue("Tools.UpgradeChain").split(",")) {
				map.put(Integer.valueOf(order), s);
				order++;
			}

			return map;
		}

		public static Material getDefault(Tool type) {
			for (Material m : materials()) {
				if (m.toString().endsWith(type.toString())) {
					return m;
				}
			}

			return null;
		}

		public static List<Material> materials() {
			List<Material> list = new LinkedList<>();
			for (ToolType.Tool.type type : Tool.type.values()) {
				for (ToolType.Tool tool : Tool.values()) {
					list.add(Material.valueOf(type.toString() + "_" + tool.toString()));
				}
			}

			return list;
		}

	}

	public enum Armor {
		BOOTS, CHESTPLATE, HELMET, LEGGINGS;

		public enum type {
			LEATHER, CHAINMAIL, IRON, GOLDEN, DIAMOND;
		}

		public static Material getDefault(Armor type) {
			for (Material m : materials()) {
				if (m.toString().endsWith(type.toString())) {
					return m;
				}
			}

			return null;
		}

		public static Map<Integer, String> upgradeOrder() {
			Map<Integer, String> map = new HashMap<>();
			int order = 0;

			for (String s : plugin.settings.getValue("Armor.UpgradeChain").split(",")) {
				map.put(Integer.valueOf(order), s);
				order++;
			}

			return map;
		}

		public static List<Material> materials() {
			List<Material> list = new LinkedList<>();
			for (ToolType.Armor.type type : Armor.type.values()) {
				for (ToolType.Armor armor : Armor.values()) {
					list.add(Material.valueOf(type.toString() + "_" + armor.toString()));
				}
			}

			return list;
		}

	}

	public static boolean isDefault(Material type) {
		for (ToolType t : ToolType.values()) {
			if (type == Material.matchMaterial(t.toString())) {
				return true;
			}

		}

		for (Material m : defaultTool()) {
			if (m == type) {
				return true;
			}
		}
		for (Material m : defaultArmor()) {
			if (m == type) {
				return true;
			}
		}

		return false;
	}

	public static boolean isValid(Material type) {
		return list().contains(type);
	}

	public static List<Material> defaultTool() {
		String type = plugin.settings.getValue("Tools.DefaultMaterial").toUpperCase() + "_";
		List<Material> list = new ArrayList<>();

		for (Material m : Tool.materials()) {
			if (m.toString().startsWith(type)) {
				list.add(m);
			}
		}

		return list;

	}

	public static List<Material> defaultArmor() {
		String type = plugin.settings.getValue("Armor.DefaultMaterial").toUpperCase() + "_";
		List<Material> list = new ArrayList<>();

		for (Material m : Armor.materials()) {
			if (m.toString().startsWith(type)) {
				list.add(m);
			}
		}

		return list;
	}

	/**
	 * @return A complete list of all Tool, Armor & Miscelaneous materials.
	 */
	public static List<Material> materials() {
		List<Material> list = new LinkedList<>();

		list.addAll(Tool.materials());
		list.addAll(Armor.materials());

		for (ToolType tool : ToolType.values()) {
			list.add(Material.valueOf(tool.toString()));
		}

		return list;
	}

	/**
	 * Same as {@link #materials()}
	 */
	public static List<Material> list() {
		return materials();
	}


}
