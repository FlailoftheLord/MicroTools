package me.flail.microtools.armor;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;

import me.flail.microtools.MicroTools;
import me.flail.microtools.armor.ArmorType.Armor.ColorType;
import me.flail.microtools.armor.ArmorType.Armor.Type;
import me.flail.microtools.tools.Logger;

public enum ArmorType {
	SHIELD, TURTLE_HELMET;

	static MicroTools plugin = MicroTools.instance;
	private static Logger logger = new Logger();

	public enum Armor {
		LEATHER, CHAINMAIL, IRON, GOLDEN, DIAMOND;

		public enum Type {
			HELMET, CHESTPLATE, LEGGINGS, BOOTS;
		}

		public enum ColorType {
			WHITE, SILVER, GRAY, BLACK, RED, MAROON, YELLOW, OLIVE, LIME, GREEN, AQUA, TEAL, BLUE, NAVY, FUCHSIA, PURPLE, ORANGE;
		}

		public List<Material> defaultType() {
			List<Material> defaults = new LinkedList<>();

			return defaults;
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
