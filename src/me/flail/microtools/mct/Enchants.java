package me.flail.microtools.mct;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import me.flail.microtools.tools.Logger;

public class Enchants extends Logger {

	public static List<EnchantType> enchantmentTypes = new ArrayList<>();

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

		public static Enchantment fromEnch(EnchantType e) {
			return Enchantment.getByKey(NamespacedKey.minecraft(e.toString().toLowerCase()));
		}

		public static EnchantType fromString(String value) {
			return EnchantType.valueOf(value.toUpperCase().replace(" ", "_"));
		}

		public static Enchantment toEnchantment(EnchantType e) {
			return fromEnch(e);
		}

		public static Enum<?>[] all() {
			List<Enum> list = new LinkedList<>();
			list.addAll(EnchantType.list());
			list.addAll(mctEnchantments.list());

			return list.toArray(new Enum[] {});
		}

	}

}
