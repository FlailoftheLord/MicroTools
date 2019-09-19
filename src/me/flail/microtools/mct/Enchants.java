package me.flail.microtools.mct;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import me.flail.microtools.mct.mctool.MicroTool;
import me.flail.microtools.tools.DataFile;
import me.flail.microtools.tools.Logger;

public class Enchants extends Logger {

	public static List<EnchantType> enchantmentTypes = new ArrayList<>();

	public static EnchantType[] enchantsFor(MicroTool tool) {
		List<EnchantType> list = new ArrayList<>();
		if (tool == null) {
			return list.toArray(new EnchantType[] {});
		}

		String type = tool.getMaterial().toString().toLowerCase();
		String[] ts = tool.getMaterial().toString().split("_");
		if (ts.length > 1) {
			type = ts[1].toLowerCase();
		}

		DataFile settings = plugin.settings;
		String toolType = "Armor";

		if (tool.isTool()) {
			toolType = "Tools";
		}

		String key = toolType + ".Enchants." + type;
		if (settings.hasValue(key)) {
			for (String s : settings.getValue(key).split(",")) {

				try {
					list.add(EnchantType.fromString(s));
				} catch (Exception e) {
					new Logger().console("&cInvalid Enchant Type&8: &7" + s);
				}
			}

		}

		for (String s : settings.getValue(toolType + ".UniversalEnchants").split(",")) {

			try {
				list.add(EnchantType.fromString(s));
			} catch (Exception e) {
				new Logger().console("&cInvalid Enchant Type&8: &7" + s);
			}
		}

		return list.toArray(new EnchantType[] {});
	}

	public enum EnchantType {
		SHARPNESS, SWEEPING_EDGE, KNOCKBACK, LOOTING, SMITE, BANE_OF_ARTHROPODS, EFFICIENCY, UNBREAKING, LOYALTY, RIPTIDE, FORTUNE,
		CHANNELING, IMPALING, MENDING, PROTECTION, FIRE_PROTECTION, BLAST_PROTECTION, PROJECTILE_PROTECTION, FIRE_ASPECT, POWER,
		PUNCH, FLAME, AQUA_AFFINITY, FROST_WALKER, DEPTH_STRIDER, RESPIRATION, QUICK_CHARGE, PIERCING, MULTISHOT, THORNS, INFINITY,
		CURSE_OF_BINDING, CURSE_OF_VANISHING, SILK_TOUCH, LUCK_OF_THE_SEA, LURE, FEATHER_FALLING;

		public static List<EnchantType> list() {
			List<EnchantType> list = new ArrayList<>();
			for (EnchantType t : values()) {
				list.add(t);
			}

			return list;
		}

		public String friendlyName(EnchantType type) {
			String temp = type.toString().toLowerCase();
			String name = "";

			for (String s : temp.split("_")) {
				s.toLowerCase();

				name += (s.charAt(0) + "").toUpperCase() + s.substring(1) + " ";
			}

			return name;
		}

		public enum mctEnchantments {
			EXPERIENCE, VAMPIRE, SHIELD;

			public static List<mctEnchantments> list() {
				List<mctEnchantments> list = new ArrayList<>();
				for (mctEnchantments t : values()) {
					list.add(t);
				}

				return list;
			}
		}

		public static EnchantType fromEnchantment(Enchantment e) {
			return EnchantType.fromString(e.getKey().getKey().toUpperCase());
		}

		public static EnchantType fromString(String value) {
			return EnchantType.valueOf(value.toUpperCase().replace(" ", "_"));
		}

		public static Enchantment toEnchantment(EnchantType e) {
			return Enchantment.getByKey(NamespacedKey.minecraft(e.toString().toLowerCase()));
		}

		public static Enum<?>[] all() {
			List<Enum<?>> list = new LinkedList<>();
			list.addAll(EnchantType.list());
			list.addAll(mctEnchantments.list());

			return list.toArray(new Enum[] {});
		}

	}

}
