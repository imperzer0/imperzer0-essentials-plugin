package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Rail;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.minecart.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class BoostMinecartListener implements Listener
{
	private static double speed;
	private static boolean rideable;
	private static boolean explosive;
	private static boolean hopper;
	private static boolean powered;
	private static boolean storage;
	private static String carts;

	private static final HashMap<UUID, Vector> previous_velocities = new HashMap<>();

	public BoostMinecartListener()
	{
		load_config();
	}

	public static double get_speed()
	{
		return speed;
	}

	public static String get_carts()
	{
		return carts;
	}

	public static void load_config()
	{
		try
		{
			speed = Double.parseDouble(Objects.requireNonNull(Main.getInstance().getConfig().getString("bc.properties.speed")));
		}
		catch (NumberFormatException var2)
		{
			Main.getInstance().getLogger().log(Level.SEVERE, "Configuration contains INVALID Double(s), using Defaults!");
			speed = 1;
			return;
		}

		if (speed > 6) speed = 6;
		if (speed <= 0) speed = 1;

		carts = "";
		if (Main.getInstance().getConfig().getBoolean("bc.carts.rideable"))
		{
			rideable = true;
			carts = carts + "[Rideable]";
		}
		else
		{
			rideable = false;
		}

		if (Main.getInstance().getConfig().getBoolean("bc.carts.explosive"))
		{
			explosive = true;
			carts = carts + "[Explosive]";
		}
		else
		{
			explosive = false;
		}

		if (Main.getInstance().getConfig().getBoolean("bc.carts.hopper"))
		{
			hopper = true;
			carts = carts + "[Hopper]";
		}
		else
		{
			hopper = false;
		}

		if (Main.getInstance().getConfig().getBoolean("bc.carts.powered"))
		{
			powered = true;
			carts = carts + "[Powered]";
		}
		else
		{
			powered = false;
		}

		if (Main.getInstance().getConfig().getBoolean("bc.carts.storage"))
		{
			storage = true;
			carts = carts + "[Storage]";
		}
		else
		{
			storage = false;
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onVehicleMove(@NotNull VehicleMoveEvent event)
	{
		Vehicle vehicle = event.getVehicle();
		if (!((vehicle instanceof Minecart cart) &&
				(!(cart instanceof RideableMinecart) || rideable) &&
				(!(cart instanceof PoweredMinecart) || powered) &&
				(!(cart instanceof ExplosiveMinecart) || explosive) &&
				(!(cart instanceof HopperMinecart) || hopper) &&
				(!(cart instanceof StorageMinecart) || storage)))
			return;

		Block block = cart.getLocation().getBlock();
		if (!(block.getBlockData() instanceof Rail))
			return;

		cart.setMaxSpeed(speed * 0.4D);

		if (block.getType() == Material.POWERED_RAIL)
		{
			boolean powered = (block.getData() & 8) != 0;
			Vector v;
			if (powered)
			{
				v = cart.getVelocity().multiply(1.5);
			}
			else
			{
				v = new Vector(0, 0, 0);
			}

			cart.setVelocity(v);
		}
		else if (block.getType() == Material.ACTIVATOR_RAIL)
		{
			cart.setMaxSpeed(0.8);
		}
		else if (block.getType() == Material.DETECTOR_RAIL)
		{
			cart.setMaxSpeed(0.4);
		}

		if (block.getBlockData() instanceof Rail nrl)
		{
			if (nrl.getShape().equals(Rail.Shape.ASCENDING_EAST) ||
					nrl.getShape().equals(Rail.Shape.ASCENDING_WEST) ||
					nrl.getShape().equals(Rail.Shape.ASCENDING_SOUTH) ||
					nrl.getShape().equals(Rail.Shape.ASCENDING_NORTH))
			{
				Vector vel = cart.getVelocity();
				if (vel.getX() == 0 && vel.getZ() == 0)
				{
					vel = previous_velocities.getOrDefault(cart.getUniqueId(), new Vector(0, 0, 0));
				}
				cart.setVelocity(vel.multiply(2));
			}
			else if (nrl.getShape().equals(Rail.Shape.NORTH_EAST) ||
					nrl.getShape().equals(Rail.Shape.NORTH_WEST) ||
					nrl.getShape().equals(Rail.Shape.SOUTH_EAST) ||
					nrl.getShape().equals(Rail.Shape.SOUTH_WEST))
			{
				cart.setMaxSpeed(0.4);
			}
		}

		previous_velocities.put(cart.getUniqueId(), cart.getVelocity());
	}

	@EventHandler(ignoreCancelled = true)
	public void onVehicleDestory(@NotNull VehicleDestroyEvent v)
	{
		Vehicle vehicle = v.getVehicle();
		if (!(vehicle instanceof Minecart cart)) return;

		previous_velocities.remove(cart.getUniqueId());
	}
}
