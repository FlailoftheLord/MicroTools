package me.flail.microtools.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.flail.microtools.mct.Enchants.EnchantType;
import me.flail.microtools.user.User;

public class Settings extends Logger {
	private DataFile settings;

	protected Settings(User user) {
		settings = user.dataFile();
	}

	public Settings() {
		settings = new DataFile("Settings.yml");

	}

	public void load() {
		settings.setHeader(header);

		loadDefaultValues();
	}

	private void loadDefaultValues() {
		Map<String, Object> values = new HashMap<>();

		values.put("General.KeepOnDeath", Boolean.valueOf(true));
		values.put("General.BlockTrackingInCreativeMode", Boolean.valueOf(false));
		values.put("General.MaxLevelPoints", "100");

		values.put("Upgrades.Tools.Cost", "100");
		values.put("Upgrades.Armor.Cost", "100");
		values.put("Upgrades.Enchants.Cost.Add", "50");
		values.put("Upgrades.Enchants.Cost.Remove", "50");

		values.put("Tools.UniversalEnchants", "unbreaking,mending");

		values.put("Tools.Enchants.sword", "sharpness,smite,bane_of_arthropods,looting,knockback,fire_aspect,sweeping_edge");
		values.put("Tools.Enchants.pickaxe", "efficiency,fortune,silk_touch");
		values.put("Tools.Enchants.axe", "efficiency,fortune,looting,knockback,sharpness");
		values.put("Tools.Enchants.hoe", "efficiency");
		values.put("Tools.Enchants.shovel", "efficiency,silk_touch");
		values.put("Tools.Enchants.bow", "power,punch,flame");
		values.put("Tools.Enchants.trident", "impaling,loyalty,riptide,channeling");
		values.put("Tools.Enchants.crossbow", "multishot,quick_charge,piercing");

		values.put("Armor.UniversalEnchants",
				"protection,fire_protection,blast_protection,projectile_protection,thorns,unbreaking,mending");

		values.put("Armor.Enchants.helmet", "respiration,aqua_affinity");
		values.put("Armor.Enchants.chestplate", "knockback_resistance");
		values.put("Armor.Enchants.leggings", " ");
		values.put("Armor.Enchants.boots", "depth_strider,frost_walker");

		setValues(settings, values);
	}

	private String header =  "-----------------------------------------------------------------\r\n" +
			"==================================================================#\r\n" +
			"                                                                  #\r\n" +
			"                MicroTools by FlailoftheLord.                     #\r\n" +
			"         -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                  #\r\n" +
			"           If you have any Questions or feedback                  #\r\n" +
			"              Join my discord server here:                        #\r\n" +
			"               https://discord.gg/wuxW5PS                         #\r\n" +
			"   ______               __        _____                           #\r\n" +
			"   |       |           /  \\         |        |                    #\r\n" +
			"   |__     |          /____\\        |        |                    #\r\n" +
			"   |       |         /      \\       |        |                    #\r\n" +
			"   |       |_____   /        \\    __|__      |______              #\r\n" +
			"                                                                  #\r\n" +
			"==================================================================#\r\n" +
			"-----------------------------------------------------------------\r\n" +
			"- - -\r\n"+
			"The possible Default Tool types are \"wooden\", \"stone\", \"iron\", \"golden\" and \"diamond\"\r\n" +
			"And the valid Default Armor types are \"leather\", \"chainmail\", \"iron\", \"golden\" and \"diamond\"\r\n" +
			"Also, all Dyed Leather Armor types are their own individual Armor type.\r\n" +
			" ColoredArmor types: \r\n" +
			"WHITE, SILVER, GRAY, BLACK, RED, MAROON, YELLOW, OLIVE, LIME, GREEN, AQUA, TEAL, BLUE, NAVY, FUCHSIA, PURPLE, ORANGE" +
			"\r\n- - -\r\n" +
			"Note that, if you enter an invalid value for the default tool material, the plugin will default to wooden tools\r\n" +
			"A full list of enchants you can use with this plugin can be seen in the Enchants.yml file.\r\n"+
			"When listing enchants, seperate them with a comma ( , )\r\n" +
			"- - -\r\n" +
			"GradesPerLevel is how many Grade points go into each level.\r\n" +
			"Grades are increased each time a tool (or armor) is used\r\n" +
			"LevelScaling defines how many additional Grade points from the previous cost it takes to get to the next level\r\n";

	public void loadEnchantsFile() {
		DataFile file = new DataFile("Enchants.yml");
		Map<String, Object> values = new HashMap<>();

		List<String> enchNames = new ArrayList<>();
		for (Enum<?> e : EnchantType.all()) {
			enchNames.add(e.toString().toLowerCase());
		}

		values.put("Enchantments", enchNames);

		setValues(file, values);
	}

	protected void setValues(DataFile file, Map<String, Object> values) {
		for (String key : values.keySet()) {
			if (!file.hasValue(key)) {
				file.setValue(key, values.get(key));
			}
		}
	}

}

