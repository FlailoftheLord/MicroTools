package me.flail.microtools.mct.mctool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.flail.microtools.mct.mctool.ArmorType.Armor;
import me.flail.microtools.mct.mctool.ArmorType.Armor.ColorType;
import me.flail.microtools.mct.mctool.MctMaterial.MicroType;
import me.flail.microtools.tools.DataFile;
import me.flail.microtools.tools.Message;
import me.flail.microtools.tools.NotNull;
import me.flail.microtools.user.User;

/**
 * Represents the core of this plugin.
 * The MicroTool represents any ItemStack which is a piece of Armor or a Tool, which can be worn
 * and/or used by a player.
 * <br>
 * <br>
 * There are (and will be) plenty of ways to completely customize and control all statistics,
 * enchantments and attributes of each MicroTool.
 * 
 * @author FlailoftheLord
 */
public class MicroTool extends MctData {
	private User owner = null;
	private ItemStack toolItem;
	private MctData itemData;

	/**
	 * Use {@link MicroTool#fromItem(ItemStack)} to generate a new MicroTool from an existing ItemStack.
	 * Always be sure to set the Owner of this tool by calling {@link #setOwner(User owner)}
	 */
	protected MicroTool(ItemStack item) {
		super(item);
		toolItem = item;

		if (hasTag("owner")) {
			owner = new User(UUID.fromString(getTag("owner")));
		}

		itemData = new MctData(toolItem);

		createItem();
	}

	public static DataFile configuration() {
		return new DataFile("Configuration.yml");
	}

	/**
	 * Generates a new MicroTool from this ItemStack.
	 * <br>
	 * Remember to set the Tool's owner when applying this to a player! ({@link #setOwner(User)})
	 * 
	 * @param item
	 * @return the newly generated MicroTool, without an owner specified.
	 */
	public static MicroTool fromItem(ItemStack item) {
		return new MicroTool(item);
	}

	public static MicroTool fromMaterial(Material m) {
		return fromMaterial(m, null);
	}

	public static MicroTool fromMaterial(@NotNull Material m, @Nullable Armor.ColorType color) {
		ItemStack item = new ItemStack(m);

		if ((color != null) && item.hasItemMeta()) {
			if (item.getItemMeta() instanceof LeatherArmorMeta) {
				LeatherArmorMeta aMeta = (LeatherArmorMeta) item.getItemMeta();
				aMeta.setColor(ArmorType.getColor(color));

				item.setItemMeta(aMeta);
			}

		}

		MicroTool tool = new MicroTool(item);

		return tool;
	}

	protected void createItem() {
		updatePlaceholders(this.placeholders());

		setNextUpgrade();

		create();
	}

	public void setColor(ColorType color) {
		if (toolItem.getItemMeta() instanceof LeatherArmorMeta) {
			LeatherArmorMeta aMeta = (LeatherArmorMeta) toolItem.getItemMeta();
			aMeta.setColor(ArmorType.getColor(color));

			toolItem.setItemMeta(aMeta);
		}

	}

	/**
	 * Checks if the ItemStack is a valid tool.
	 */
	public boolean isValid() {
		return ToolType.materials().contains(material());
	}

	/**
	 * Get the User who owns this tool.
	 * The owner of this tool is the only player allowed to use or hold it in their inventory.
	 * 
	 * @return the owner of this MicroTool.
	 */
	public User owner() {
		return owner;
	}

	public boolean hasOwner() {
		return hasTag("owner");
	}

	/**
	 * Sets the new Owner for this MicroTool.
	 * 
	 * @param newOwner
	 *                     the new {@link User} user to set as the owner of this tool.
	 * @return the new tool with it's updated owner.
	 */
	public MicroTool setOwner(User newOwner) {
		if (newOwner == null) {
			owner = null;
			setClaimed(false);

			return this;
		}

		owner = newOwner;

		setClaimed(true);
		addTag("owner", owner.id());
		return this;
	}

	/**
	 * Removes all Owner data for this tool. Reverting it back to an un-owned, un-claimed MicroTool.
	 */
	public void setClaimed(boolean claimStatus) {
		removeTag("owner");
		removeTag("user");

		List<String> lore = itemData.getLore();

		String claimed = lore.get(lore.size() - 1);
		if (claimed.equalsIgnoreCase(chat(MctData.UNCLAIMED_TOOL_TEXT))) {
			if (claimStatus) {
				removeLoreLine(lore.size() - 1);
				removeTag("unclaimed");

				setLoreLine(chat(MctData.MANAGE_TOOL_TEXT), lore.size() - 1);
			}

			return;
		}

		if (!claimStatus) {
			removeLoreLine(lore.size() - 1);
			setLoreLine(chat(MctData.UNCLAIMED_TOOL_TEXT), lore.size() - 1);

			addTag("unclaimed", "true");
			itemData.setLore(lore);
		}

	}

	/**
	 * @return the Raw ItemStack which this MicroTool represents.
	 */
	public ItemStack item() {
		return toolItem;
	}

	/**
	 * @return this tool's MctData.
	 */
	public MctData getData() {
		return itemData;
	}

	/**
	 * @return true if this {@link #material()} is a valid ArmorType. false otherwise.
	 */
	public boolean isArmor() {
		return ArmorType.materials().contains(material());
	}

	/**
	 * @return true if this {@link #material()} is a valid ToolType. false otherwise.
	 */
	public boolean isTool() {
		return ToolType.materials().contains(material());
	}

	/**
	 * @return true if, somehow, the type is neither Armor nor Tool.
	 */
	public boolean isMisc() {
		return !isTool() && !isArmor() && MicroType.allMaterials().contains(material());
	}

	/**
	 * Proxy method for {@link #getMaterial()}
	 */
	public Material material() {
		return getMaterial();
	}



	public int upgradeLevel() {
		return hasTag("level") ? Integer.parseInt(getTag("level").replaceAll("[^0-9]", "")) : 0;
	}

	public String gradeLevel() {
		return hasTag("grade") ? getTag("grade").replaceAll("[0-9]", "") : "BASIC";
	}

	public void setNextUpgrade() {
		if (MicroType.isUpgradeable(material())) {
			Map<Integer, String> upgrades = ToolType.Tool.upgradeOrder();

			if (isArmor()) {
				upgrades = ArmorType.Armor.upgradeOrder();
			}

			if ((upgradeLevel() + 1) > upgrades.size()) {
				addTag("upgrade", "max");
				return;
			}

			addTag("upgrade", upgrades.get(Integer.valueOf(upgradeLevel() + 1)));
			return;
		}
	}

	public MicroTool upgrade(String upgrade) {
		if (!hasTag("upgrade") && hasOwner()) {
			owner.sendMessage("&cThis tool has no available upgrades!");
			return this;
		}

		String upgradeType = getTag("upgrade");

		if (upgradeType.equalsIgnoreCase("max")) {
			console("Item is at max upgrades, and cannot be upgraded further: " + material().toString());
			return this;
		}

		int level = upgradeLevel();
		removeTag("level");
		addTag("level", level + 1 + "");

		String newItemType = upgradeType.toUpperCase() + "_" + getMaterial().toString().split("_")[1];
		Material upgradedMaterial = Material.matchMaterial(newItemType);
		if (upgradedMaterial != null) {
			toolItem.setType(upgradedMaterial);
		}

		return this;
	}

	public void addBlocksBroken(int amount) {
		if (!hasTag("blocks")) {
			addTag("blocks", amount + "");
			return;
		}
		int blocksBroken = Integer.parseInt(getTag("blocks"));

		blocksBroken += amount;

		removeTag("blocks");
		addTag("blocks", blocksBroken + "");
	}

	public int getBlocksBroken() {
		return hasTag("blocks") ? Integer.parseInt(getTag("blocks").replaceAll("[^0-9]", "")) : 0;
	}

	public void updateBlocksBrokenDisplay() {
		this.removeLoreLine(3);
		this.setLoreLine(BLOCKS_DISPLAY + "%blocks%", 3);

		updatePlaceholders(this.placeholders());
	}




	public Map<String, String> placeholders() {
		Map<String, String> map = new HashMap<>();
		map.put("%level%", upgradeLevel() + "");
		if (owner == null) {
			map.put("%owner%", new Message("ToolDoesntHaveOwner").stringValue());
			map.put("%owner-uuid%", "");
		} else {
			map.put("%owner%", owner.name());
			map.put("%owner-uuid%", owner.id());
		}
		map.put("%tool-grade%", gradeLevel() + "");
		map.put("%tool%", this.getName());
		map.put("%item%", material().toString());
		map.put("%blocks%", getBlocksBroken() + "");

		return map;
	}

}
