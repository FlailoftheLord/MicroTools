package me.flail.microtools.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.flail.microtools.tool.ToolType;
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

		values.put("Tools.DefaultMaterial", "wooden");
		values.put("Tools.UpgradeChain", "wooden,stone,iron,golden,diamond");
		values.put("Tools.EnchantUpgradePerType", Boolean.valueOf(true));

		values.put("Tools.UniversalEnchants", "unbreaking,mending");

		values.put("Tools.Enchants.Sword", "sharpness,smite,bane_of_arthropods,looting,knockback,fire_aspect,sweeping_edge");
		values.put("Tools.Enchants.Pickaxe", "efficiency,fortune,silk_touch");
		values.put("Tools.Enchants.Axe", "efficiency,fortune,looting,knockback,sharpness");
		values.put("Tools.Enchants.Hoe", "efficiency");
		values.put("Tools.Encahnts.Shovel", "efficiency,silk_touch");
		values.put("Tools.Enchants.Bow", "power,punch,flame");
		values.put("Tools.Enchants.Trident", "impaling,loyalty,riptide,channeling");
		values.put("Tools.Enchants.CrossBow", "multishot,quick_charge,piercing");

		values.put("Armor.DefaultMaterial", "leather");
		values.put("Armor.UpgradeChain", "leather,chainmail,iron,golden,diamond");
		values.put("Armor.EnchantUpgradePerType", Boolean.valueOf(true));

		values.put("Armor.UniversalEnchants",
				"protection,fire_protection,blast_protection,projectile_protection,thorns,unbreaking,mending");

		values.put("Armor.Enchants.Helmet", "respiration,aqua_affinity");
		values.put("Armor.Enchants.Chestplate", "knockback_resistance");
		values.put("Armor.Enchants.Leggings", " ");
		values.put("Armor.Enchants.Boots", "depth_strider,frost_walker");
		values.put("Armor.Enchants.Shield", " ");
		values.put("Armor.Enchants.Turtle_Helmet", " ");

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
			"- - -\r\n" +
			"Note that, if you enter an invalid value for the default tool material, the plugin will default to wooden tools\r\n" +
			"A full list of enchants you can use with this plugin can be seen in the Enchants.yml file.\r\n"+
			"When listing enchants, seperate them with a comma ( , )\r\n";

	public void loadEnchantsFile() {
		DataFile file = new DataFile("Enchants.yml");
		Map<String, Object> values = new HashMap<>();

		List<String> enchNames = new ArrayList<>();
		for (Enchants e : Enchants.values()) {
			enchNames.add(e.toString().toLowerCase());
		}

		values.put("Enchantments", enchNames);
		values.put("Attributes", ToolType.toolAttributes());

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

