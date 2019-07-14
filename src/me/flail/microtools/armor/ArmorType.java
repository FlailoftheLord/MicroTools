package me.flail.microtools.armor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Color;
import org.bukkit.Material;

import me.flail.microtools.MicroTools;
import me.flail.microtools.armor.ArmorType.Armor.ColorType;
import me.flail.microtools.armor.ArmorType.Armor.Type;
import me.flail.microtools.mct.mctool.MctMaterial.MicroType;
import me.flail.microtools.tools.Logger;

public enum ArmorType implements MicroType {
	SHIELD, TURTLE_HELMET;

	static MicroTools plugin = MicroTools.instance;
	private static Logger logger = new Logger();

	public static final String DEFAULT_TYPE = "LEATHER";

	public enum Armor {
		LEATHER, CHAINMAIL, IRON, GOLDEN, DIAMOND;

		public enum Type {
			HELMET, CHESTPLATE, LEGGINGS, BOOTS;
		}

		public enum ColorType {
			WHITE, SILVER, GRAY, BLACK, RED, MAROON, YELLOW, OLIVE, LIME, GREEN, AQUA, TEAL, BLUE, NAVY, FUCHSIA, PURPLE, ORANGE;

			public static String values = "WHITE, SILVER, GRAY, BLACK, RED, MAROON, YELLOW, OLIVE, LIME, GREEN, AQUA, TEAL, BLUE, NAVY, FUCHSIA, PURPLE, ORANGE ";
		}

		public List<Material> defaultType() {
			List<Material> defaults = new LinkedList<>();

			return defaults;
		}

		public static Map<Integer, String> upgradeOrder() {
			Map<Integer, String> upgrades = new TreeMap<>();

			upgrades.put(0, "leather");
			upgrades.put(1, "chainmail");
			upgrades.put(2, "iron");
			upgrades.put(3, "golden");
			upgrades.put(4, "diamond");

			return upgrades;
		}


	}

	public static Color getColor(ColorType name) {
		try {
			return Color.class.cast(Color.class.getField(name.toString()));
		} catch (Exception e) {
			logger.console("&cInvalid Color&8: &7" + name);
		}

		logger.console("&cDefaulting to Color&8: &7" + Color.fromRGB(3).toString());
		return Color.fromRGB(3);
	}

	public static List<Material> defaultArmor() {
		String type = DEFAULT_TYPE + "_";
		List<Material> list = new ArrayList<>();

		for (Material m : materials()) {
			if (m.toString().startsWith(type)) {
				list.add(m);
			}
		}

		return list;
	}

	public static List<Material> materials() {
		List<Material> list = new LinkedList<>();

		for (ArmorType t : ArmorType.values()) {
			list.add(Material.matchMaterial(t.toString()));
		}

		for (Armor a : Armor.values()) {
			for (Type t : Armor.Type.values()) {
				list.add(Material.matchMaterial(a.toString() + "_" + t.toString()));
			}

		}

		return list;
	}

}
