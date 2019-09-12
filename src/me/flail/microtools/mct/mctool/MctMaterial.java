package me.flail.microtools.mct.mctool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.flail.microtools.mct.mctool.ArmorType.Armor.ColorType;
import me.flail.microtools.tools.Logger;

public class MctMaterial extends Logger {

	public static final String[] types = { "FLINT_AND_STEEL",
			"BOW",
			"CROSSBOW",
			"SHEARS",
			"TRIDENT",
			"FISHING_ROD",
			"SHIELD",
			"TURTLE_HELMET",

			"WOODEN_AXE",
			"WOODEN_HOE",
			"WOODEN_PICKAXE",
			"WOODEN_SHOVEL",
			"WOODEN_SWORD",

			"STONE_AXE",
			"STONE_HOE",
			"STONE_PICKAXE",
			"STONE_SHOVEL",
			"STONE_SWORD",

			"IRON_AXE",
			"IRON_HOE",
			"IRON_PICKAXE",
			"IRON_SHOVEL",
			"IRON_SWORD",

			"GOLDEN_AXE",
			"GOLDEN_HOE",
			"GOLDEN_PICKAXE",
			"GOLDEN_SHOVEL",
			"GOLDEN_SWORD",

			"DIAMOND_AXE",
			"DIAMOND_HOE",
			"DIAMOND_PICKAXE",
			"DIAMOND_SHOVEL",
			"DIAMOND_SWORD",

			"LEATHER_HELMET",
			"LEATHER_CHESTPLATE",
			"LEATHER_LEGGINGS",
			"LEATHER_BOOTS",

			"CHAINMAIL_HELMET",
			"CHAINMAIL_CHESTPLATE",
			"CHAINMAIL_LEGGINGS",
			"CHAINMAIL_BOOTS",

			"IRON_HELMET",
			"IRON_CHESTPLATE",
			"IRON_LEGGINGS",
			"IRON_BOOTS",


			"GOLDEN_HELMET",
			"GOLDEN_CHESTPLATE",
			"GOLDEN_LEGGINGS",
			"GOLDEN_BOOTS",

			"DIAMOND_HELMET",
			"DIAMOND_CHESTPLATE",
			"DIAMOND_LEGGINGS",
	"DIAMOND_BOOTS" };

	/**
	 * Get a colored Helmet.
	 * <br>
	 * <br>
	 * Valid colors are: {@link me.flail.microtools.mct.mctool.ArmorType.Armor.ColorType#values}<br>
	 * <code> WHITE, SILVER, GRAY, BLACK, RED, MAROON, YELLOW, OLIVE, LIME, GREEN, AQUA, TEAL, BLUE, NAVY, FUCHSIA, PURPLE, ORANGE</code>
	 */
	public static final String HELMET(ColorType color) {
		return color.toString() + "_HELMET";
	}

	/**
	 * Get a colored Chestplate.
	 * <br>
	 * <br>
	 * Valid colors are: {@link me.flail.microtools.mct.mctool.ArmorType.Armor.ColorType#values}<br>
	 * <code> WHITE, SILVER, GRAY, BLACK, RED, MAROON, YELLOW, OLIVE, LIME, GREEN, AQUA, TEAL, BLUE, NAVY, FUCHSIA, PURPLE, ORANGE</code>
	 */
	public static final String CHESTPLATE(ColorType color) {
		return color.toString() + "_CHESTPLATE";
	}

	/**
	 * Get colored leggings.
	 * <br>
	 * <br>
	 * Valid colors are: {@link me.flail.microtools.mct.mctool.ArmorType.Armor.ColorType#values}<br>
	 * <code> WHITE, SILVER, GRAY, BLACK, RED, MAROON, YELLOW, OLIVE, LIME, GREEN, AQUA, TEAL, BLUE, NAVY, FUCHSIA, PURPLE, ORANGE</code>
	 */
	public static final String LEGGINGS(ColorType color) {
		return color.toString() + "_LEGGINGS";
	}

	/**
	 * Get colored boots.
	 * <br>
	 * <br>
	 * Valid colors are: {@link me.flail.microtools.mct.mctool.ArmorType.Armor.ColorType#values}<br>
	 * <code> WHITE, SILVER, GRAY, BLACK, RED, MAROON, YELLOW, OLIVE, LIME, GREEN, AQUA, TEAL, BLUE, NAVY, FUCHSIA, PURPLE, ORANGE</code>
	 */
	public static final String BOOTS(ColorType color) {
		return color.toString() + "_BOOTS";
	}

	public static String[] values() {
		return types.clone();
	}

	public static String[] toolTypes() {
		List<String> types = new ArrayList<>();
		for (String s : values()) {
			if (s.contains("SWORD") || s.contains("SHOVEL") || s.contains("HOE") || s.contains("AXE")) {
				types.add(s);
			}
		}

		for (int t = 0; t < 7; t++) {
			types.add(MctMaterial.types[t]);
		}

		return types.toArray(new String[] {});
	}

	public static String[] armorTypes() {
		List<String> types = new ArrayList<>();
		for (String s : values()) {
			if (s.contains("HELMET") || s.contains("CHESTPLATE") || s.contains("LEGGINGS") || s.contains("BOOTS")) {
				types.add(s);
			}
		}

		for (ColorType t : ColorType.values()) {

			types.add(t.toString() + "_HELMET");
			types.add(t.toString() + "_CHESTPLATE");
			types.add(t.toString() + "_LEGGINGS");
			types.add(t.toString() + "_BOOTS");
		}

		types.add(MctMaterial.types[7]);

		return types.toArray(new String[] {});
	}

	public static String friendlyName(Material material) {
		return enumName(material);
	}

	public static boolean isValid(ItemStack item) {
		return hasTag(item, "material");
	}

	public interface MicroType {

		/**
		 * @return A full list of all Materials, both ArmorType and ToolType
		 */
		static Set<String> allMaterials() {
			Set<String> list = new HashSet<>();

			for (String a : armorTypes()) {

				list.add(a);
			}
			for (String t : toolTypes()) {

				list.add(t);
			}

			return list;
		}

		static boolean isUpgradeable(Material material) {
			if (material.toString().contains("TURTLE")) {
				return false;
			}

			String[] upgradeables = { "axe", "hoe", "pickaxe", "shovel", "sword", "helmet", "chestplate", "leggings", "boots" };
			for (String s : upgradeables) {
				if (material.toString().toLowerCase().contains("_" + s)) {
					return true;
				}
			}

			return false;
		}

	}




















}
