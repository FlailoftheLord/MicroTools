package me.flail.microtools.tools;

import java.lang.reflect.Field;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_14_R1.block.CraftSign;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microtools.MicroTools;
import me.flail.microtools.tools.BaseUtilities.Reflection;
import me.flail.microtools.user.User;
import net.minecraft.server.v1_14_R1.TileEntitySign;

public class SignInput extends CraftSign {
	private MicroTools plugin = JavaPlugin.getPlugin(MicroTools.class);
	private Sign sign;

	public SignInput(Block block) {
		super(block);

		sign = (Sign) block.getState();
	}


	protected TileEntitySign getSignTile() {
		return this.getTileEntity();
	}

	public boolean open(User user) {

		try {

			Object handle = user.player().getClass().getMethod("getHandle").invoke(user.player());
			Object connection = handle.getClass().getField("playerConnection").get(handle);

			TileEntitySign tileSign = getSignTile();

			Field editable = tileSign.getClass().getDeclaredField("isEditable");
			editable.setAccessible(true);
			editable.set(tileSign, true);

			Field handler = tileSign.getClass().getDeclaredField("j");
			handler.setAccessible(true);
			handler.set(tileSign, handle);

			Object position = Reflection.getClass("BlockPosition$PooledBlockPosition")
					.getMethod("d", double.class, double.class, double.class)
					.invoke(null, sign.getX(), sign.getY(), sign.getZ());

			Object packet = Reflection.getClass("PacketPlayOutOpenSignEditor").getConstructor(Reflection.getClass("BlockPosition"))
					.newInstance(position);

			connection.getClass().getDeclaredMethod("sendPacket", Reflection.getClass("Packet")).invoke(connection, packet);

			return true;
		} catch (Throwable t) {
			t.printStackTrace();

			plugin.settings.console("&cERROR while sending sign editor packets to user: " + user.name());
		}

		return false;
	}

}
