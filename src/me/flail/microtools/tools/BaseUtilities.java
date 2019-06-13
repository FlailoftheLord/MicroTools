package me.flail.microtools.tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.flail.microtools.MicroTools;

public class BaseUtilities extends LegacyUtils {
	protected MicroTools plugin = MicroTools.getPlugin(MicroTools.class);

	protected ItemStack addTag(ItemStack item, String key, String tag) {
		return addLegacyTag(item, key, tag);
	}

	protected ItemStack removeTag(ItemStack item, String key, String tag) {
		return removeLegacyTag(item, key, tag);
	}

	protected String getTag(ItemStack item, String key) {
		return getLegacyTag(item, key);
	}

	protected boolean hasTag(ItemStack item, String key) {
		return hasLegacyTag(item, key);
	}

	/**
	 * Grabs the color code which modifies the substring <code>before</code> in the string
	 * <code>string</code>
	 * 
	 * @param string
	 * @param before
	 */
	public String getColor(String string, String before) {
		String first = string.split(before)[0];
		char c = first.charAt(first.lastIndexOf("&") + 1);
		return "&" + c;
	}

	public ItemStack fillerItem(DataFile file) {
		ItemStack item = new ItemStack(Material.AIR);
		String filler = new DataFile("GuiConfig.yml").getValue("FillerItem").replaceAll("[0-9]", "").toUpperCase();

		if (file.hasValue("Format.FillerItem")) {
			filler = file.getValue("Format.FillerItem").replaceAll("[0-9]", "").toUpperCase();
		}

		if (Material.matchMaterial(filler) != null) {
			item.setType(Material.matchMaterial(filler));
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(" ");
			item.setItemMeta(meta);
		}

		return item;
	}

	protected List<EntityType> validMobs(String mode) {
		List<EntityType> mobs = new ArrayList<>(16);

		switch (mode) {
		case "hostile":
			mobs.addAll(this.hostileMobs());
			break;
		case "passive":
			mobs.addAll(this.passiveMobs());
			break;
		default:
			mobs.addAll(passiveMobs());
			mobs.addAll(hostileMobs());
		}

		return mobs;
	}

	private List<EntityType> passiveMobs() {
		List<EntityType> list = new ArrayList<>();
		list.add(EntityType.BAT);
		list.add(EntityType.CHICKEN);
		list.add(EntityType.COD);
		list.add(EntityType.COW);
		list.add(EntityType.DOLPHIN);
		list.add(EntityType.DONKEY);
		list.add(EntityType.HORSE);
		list.add(EntityType.IRON_GOLEM);
		list.add(EntityType.LLAMA);
		list.add(EntityType.MULE);
		list.add(EntityType.MUSHROOM_COW);
		list.add(EntityType.OCELOT);
		list.add(EntityType.PARROT);
		list.add(EntityType.PIG);
		list.add(EntityType.POLAR_BEAR);
		list.add(EntityType.RABBIT);
		list.add(EntityType.SALMON);
		list.add(EntityType.SHEEP);
		list.add(EntityType.SKELETON_HORSE);
		list.add(EntityType.SNOWMAN);
		list.add(EntityType.SQUID);
		list.add(EntityType.TROPICAL_FISH);
		list.add(EntityType.TURTLE);
		list.add(EntityType.VILLAGER);
		list.add(EntityType.WOLF);
		list.add(EntityType.ZOMBIE_HORSE);

		return list;
	}

	private List<EntityType> hostileMobs() {
		List<EntityType> list = new ArrayList<>();
		list.add(EntityType.BLAZE);
		list.add(EntityType.CAVE_SPIDER);
		list.add(EntityType.CREEPER);
		list.add(EntityType.DROWNED);
		list.add(EntityType.ELDER_GUARDIAN);
		list.add(EntityType.ENDERMAN);
		list.add(EntityType.ENDERMITE);
		list.add(EntityType.EVOKER);
		list.add(EntityType.GHAST);
		list.add(EntityType.GUARDIAN);
		list.add(EntityType.HUSK);
		list.add(EntityType.ILLUSIONER);
		list.add(EntityType.MAGMA_CUBE);
		list.add(EntityType.PHANTOM);
		list.add(EntityType.PIG_ZOMBIE);
		list.add(EntityType.PUFFERFISH);
		list.add(EntityType.SHULKER);
		list.add(EntityType.SILVERFISH);
		list.add(EntityType.SKELETON);
		list.add(EntityType.SLIME);
		list.add(EntityType.SPIDER);
		list.add(EntityType.STRAY);
		list.add(EntityType.VEX);
		list.add(EntityType.VINDICATOR);
		list.add(EntityType.WITCH);
		list.add(EntityType.WITHER_SKELETON);
		list.add(EntityType.ZOMBIE);
		list.add(EntityType.ZOMBIE_VILLAGER);

		return list;
	}

	public Enchantment fromEnch(Enchants ench) {

		return Enchantment.getByKey(NamespacedKey.minecraft(ench.toString().toLowerCase()));
	}

	public enum Enchants {
		SHARPNESS, SWEEPING_EDGE, KNOCKBACK, LOOTING, SMITE, BANE_OF_ARTHROPODS, EFFICIENCY, UNBREAKING, LOYALTY, RIPTIDE, FORTUNE,
		CHANNELING, IMPALING, MENDING, PROTECTION, FIRE_PROTECTION, BLAST_PROTECTION, PROJECTILE_PROTECTION, FIRE_ASPECT, POWER,
		PUNCH, FLAME, AQUA_AFFINITY, FROST_WALKER, DEPTH_STRIDER, RESPIRATION, QUICK_CHARGE, PIERCING, MULTISHOT, THORNS, INFINITY,
		CURSE_OF_BINDING, CURSE_OF_VANISHING, SILK_TOUCH, LUCK_OF_THE_SEA, LURE, FEATHER_FALLING

	}

}
