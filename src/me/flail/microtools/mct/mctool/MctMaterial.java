package me.flail.microtools.mct.mctool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import me.flail.microtools.mct.mctool.ArmorType.Armor.ColorType;
import me.flail.microtools.tools.Logger;

public class MctMaterial extends Logger {

	public static final String FLINT_AND_STEEL = "FLINT_AND_STEEL";
	public static final String BOW = "BOW";
	public static final String CROSSBOW = "CROSSBOW";
	public static final String SHEARS = "SHEARS";
	public static final String FISHING_ROD = "FISHING_ROD";
	public static final String TURTLE_HELMET = "TURTLE_HELMET";
	public static final String SHIELD = "SHIELD";

	public static final String WOODEN_AXE = "WOODEN_AXE";
	public static final String WOODEN_HOE = "WOODEN_HOE";
	public static final String WOODEN_PICKAXE = "WOODEN_PICKAXE";
	public static final String WOODEN_SHOVEL = "WOODEN_SHOVEL";
	public static final String WOODEN_SWORD = "WOODEN_SWORD";

	public static final String STONE_AXE = "STONE_AXE";
	public static final String STONE_HOE = "STONE_HOE";
	public static final String STONE_PICKAXE = "STONE_PICKAXE";
	public static final String STONE_SHOVEL = "STONE_SHOVEL";
	public static final String STONE_SWORD = "STONE_SWORD";

	public static final String IRON_AXE = "IRON_AXE";
	public static final String IRON_HOE = "IRON_HOE";
	public static final String IRON_PICKAXE = "IRON_PICKAXE";
	public static final String IRON_SHOVEL = "IRON_SHOVEL";
	public static final String IRON_SWORD = "IRON_SWORD";

	public static final String GOLDEN_AXE = "GOLDEN_AXE";
	public static final String GOLDEN_HOE = "GOLDEN_HOE";
	public static final String GOLDEN_PICKAXE = "GOLDEN_PICKAXE";
	public static final String GOLDEN_SHOVEL = "GOLDEN_SHOVEL";
	public static final String GOLDEN_SWORD = "GOLDEN_SWORD";

	public static final String DIAMOND_AXE = "DIAMOND_AXE";
	public static final String DIAMOND_HOE = "DIAMOND_HOE";
	public static final String DIAMOND_PICKAXE = "DIAMOND_PICKAXE";
	public static final String DIAMOND_SHOVEL = "DIAMOND_SHOVEL";
	public static final String DIAMOND_SWORD = "DIAMOND_SWORD";

	public static final String LEATHER_HELMET = "LEATHER_HELMET";
	public static final String LEATHER_CHESTPLATE = "LEATHER_CHESTPLATE";
	public static final String LEATHER_LEGGINGS = "LEATHER_LEGGINGS";
	public static final String LEATHER_BOOTS = "LEATHER_BOOTS";

	public static final String CHAINMAIL_HELMET = "CHAINMAIL_HELMET";
	public static final String CHAINMAIL_CHESTPLATE = "CHAINMAIL_CHESTPLATE";
	public static final String CHAINMAIL_LEGGINGS = "CHAINMAIL_LEGGINGS";
	public static final String CHAINMAIL_BOOTS = "CHAINMAIL_BOOTS";

	public static final String IRON_HELMET = "IRON_HELMET";
	public static final String IRON_CHESTPLATE = "IRON_CHESTPLATE";
	public static final String IRON_LEGGINGS = "IRON_LEGGINGS";
	public static final String IRON_BOOTS = "IRON_BOOTS";


	public static final String GOLDEN_HELMET = "GOLDEN_HELMET";
	public static final String GOLDEN_CHESTPLATE = "GOLDEN_CHESTPLATE";
	public static final String GOLDEN_LEGGINGS = "GOLDEN_LEGGINGS";
	public static final String GOLDEN_BOOTS = "GOLDEN_BOOTS";

	public static final String DIAMOND_HELMET = "DIAMOND_HELMET";
	public static final String DIAMOND_CHESTPLATE = "DIAMOND_CHESTPLATE";
	public static final String DIAMOND_LEGGINGS = "DIAMOND_LEGGINGS";
	public static final String DIAMOND_BOOTS = "DIAMOND_BOOTS";

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

	public static String friendlyName(Material material) {
		return enumName(material);
	}

	public interface MicroType {

		/**
		 * @return A full list of all Materials, both ArmorType and ToolType
		 */
		static List<Material> allMaterials() {
			List<Material> list = new ArrayList<>();

			list.addAll(ToolType.materials());
			list.addAll(ArmorType.materials());

			return list;
		}

		static boolean isUpgradeable(Material material) {
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
