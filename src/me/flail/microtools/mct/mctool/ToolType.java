package me.flail.microtools.mct.mctool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import me.flail.microtools.MicroTools;
import me.flail.microtools.mct.mctool.MctMaterial.MicroType;

public enum ToolType implements MicroType {
	FLINT_AND_STEEL, SHIELD, BOW, CROSSBOW, SHEARS, FISHING_ROD;

	static MicroTools plugin = MicroTools.instance;

	public static final String DEFAULT_TYPE = "WOODEN";

	public enum Tool {
		AXE, HOE, PICKAXE, SHOVEL, SWORD;

		public enum Type {
			WOODEN, STONE, IRON, GOLDEN, DIAMOND;
		}

		public static Map<Integer, String> upgradeOrder() {
			Map<Integer, String> map = new HashMap<>();

			map.put(0, "wooden");
			map.put(1, "stone");
			map.put(2, "iron");
			map.put(3, "golden");
			map.put(4, "diamond");

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

	}


	public static boolean isDefault(Material type) {
		for (ToolType t : values()) {
			if (type == Material.matchMaterial(t.toString())) {
				return true;
			}

		}

		for (Material m : defaultTool()) {
			if (m == type) {
				return true;
			}
		}

		return false;
	}

	public static boolean isValid(Material type) {
		return materials().contains(type);
	}

	public static List<Material> defaultTool() {
		String type = DEFAULT_TYPE + "_";
		List<Material> list = new ArrayList<>();

		for (Material m : materials()) {
			if (m.toString().startsWith(type)) {
				list.add(m);
			}
		}

		return list;
	}



	/**
	 * @return A complete list of all Tool & Miscelaneous materials.
	 */
	public static List<Material> materials() {
		List<Material> list = new LinkedList<>();

		for (ToolType.Tool.Type type : Tool.Type.values()) {
			for (ToolType.Tool tool : Tool.values()) {
				list.add(Material.valueOf(type.toString() + "_" + tool.toString()));
			}
		}

		for (ToolType tool : values()) {
			list.add(Material.valueOf(tool.toString()));
		}

		return list;
	}

}
