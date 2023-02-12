package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Rail;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.minecart.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class BoostMinecartListener implements Listener
{
	private static final HashMap<UUID, Vector> previous_velocities = new HashMap<>();
	private static double speed;
	private static boolean rideable_empty;
	private static boolean rideable_player;
	private static boolean rideable_entity;
	private static boolean explosive;
	private static boolean hopper;
	private static boolean powered;
	private static boolean storage;
	private static boolean command;
	private static String carts;

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

		if (Main.getInstance().getConfig().getBoolean("bc.carts.rideable.empty"))
		{
			rideable_empty = true;
			carts = carts + "[Rideable (empty)]";
		}
		else
		{
			rideable_empty = false;
		}

		if (Main.getInstance().getConfig().getBoolean("bc.carts.rideable.player"))
		{
			rideable_player = true;
			carts = carts + "[Rideable (player)]";
		}
		else
		{
			rideable_player = false;
		}

		if (Main.getInstance().getConfig().getBoolean("bc.carts.rideable.entity"))
		{
			rideable_entity = true;
			carts = carts + "[Rideable (entity)]";
		}
		else
		{
			rideable_entity = false;
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

		if (Main.getInstance().getConfig().getBoolean("bc.carts.command"))
		{
			command = true;
			carts = carts + "[Command]";
		}
		else
		{
			command = false;
		}
	}

	private static void process_cart_move(@NotNull Minecart cart)
	{
		Block block = cart.getLocation().getBlock();
		if (!(block.getBlockData() instanceof Rail))
			return;

		Block block_under_rail = block.getRelative(BlockFace.DOWN);
		if (block_under_rail.getType().equals(Material.OBSIDIAN))
		{
			Vector vel = cart.getVelocity();
			vel.setX(vel.getX() / (speed > 2 ? speed - 1.5 : speed));
			vel.setZ(vel.getZ() / (speed > 2 ? speed - 1.5 : speed));
			cart.setVelocity(vel);
		}

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

		if (block.getBlockData() instanceof Rail rl)
		{
			if (rl.getShape().equals(Rail.Shape.ASCENDING_EAST) ||
					rl.getShape().equals(Rail.Shape.ASCENDING_WEST) ||
					rl.getShape().equals(Rail.Shape.ASCENDING_SOUTH) ||
					rl.getShape().equals(Rail.Shape.ASCENDING_NORTH))
			{
				Vector vel = cart.getVelocity();
				if (vel.getX() == 0 && vel.getZ() == 0)
				{
					vel = previous_velocities.getOrDefault(cart.getUniqueId(), new Vector(0, 0, 0));
				}

				if ((block.getData() & 8) != 0)
				{
					cart.setVelocity(vel.multiply(1.5));
				}
			}
		}

		previous_velocities.put(cart.getUniqueId(), cart.getVelocity());
	}

	@EventHandler
	public void onVehicleMove(@NotNull VehicleMoveEvent event)
	{
		Vehicle vehicle = event.getVehicle();
		if (!(vehicle instanceof Minecart cart))
			return;

		if (!((!(cart instanceof PoweredMinecart) || powered) &&
				(!(cart instanceof ExplosiveMinecart) || explosive) &&
				(!(cart instanceof HopperMinecart) || hopper) &&
				(!(cart instanceof StorageMinecart) || storage) &&
				(!(cart instanceof CommandMinecart) || command)))
		{
			return;
		}
		else if (cart instanceof RideableMinecart rc)
		{
			List<Entity> passengers = rc.getPassengers();

			if (passengers.isEmpty() && rideable_empty)
			{
				process_cart_move(cart);
				return;
			}

			boolean is_any_player = false;
			for (Entity entity : passengers)
			{
				if (entity instanceof Player)
				{
					is_any_player = true;
					break;
				}
			}

			if (is_any_player && rideable_player)
			{
				process_cart_move(cart);
				return;
			}

			if (!is_any_player && rideable_entity)
			{
				process_cart_move(cart);
				return;
			}

			return;
		}

		process_cart_move(cart);
	}

	@EventHandler
	public void onVehicleDestroy(@NotNull VehicleDestroyEvent v)
	{
		Vehicle vehicle = v.getVehicle();
		if (!(vehicle instanceof Minecart cart)) return;

		previous_velocities.remove(cart.getUniqueId());
	}
}
