package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.minecart.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.material.Rails;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class BoostMinecartListener implements Listener
{
	private static final ArrayList<Cart> CL = new ArrayList<>();
	private static int speed;
	private static int momentum;
	private static boolean derail;
	private static boolean rideable;
	private static boolean explosive;
	private static boolean hopper;
	private static boolean powered;
	private static boolean storage;
	private static String carts;

	public BoostMinecartListener()
	{
		load_config();
	}

	public static int get_speed()
	{
		return speed;
	}

	public static int get_momentum()
	{
		return momentum;
	}

	public static String get_carts()
	{
		return carts;
	}

	public static void load_config()
	{
		try
		{
			speed = Integer.parseInt(Objects.requireNonNull(Main.getInstance().getConfig().getString("bc.properties.speed")));
			momentum = Integer.parseInt(Objects.requireNonNull(Main.getInstance().getConfig().getString("bc.properties.momentum")));
		}
		catch (NumberFormatException var2)
		{
			Main.getInstance().getLogger().log(Level.SEVERE, "Configuration contains INVALID Integer(s), using Defaults!");
			speed = 8;
			momentum = 32;
			return;
		}

		derail = Main.getInstance().getConfig().getBoolean("bc.properties.derail");

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
		if (!(block.getState().getData() instanceof Rails rail))
			return;

		cart.setMaxSpeed(speed);

		if (block.getType() == Material.POWERED_RAIL)
		{
			boolean powered = (block.getData() & 8) != 0;
			Vector v;
			if (powered)
			{
				v = cart.getVelocity();
				v.normalize();
				v.multiply((double) momentum / 100.0);
			}
			else
			{
				v = new Vector(0, 0, 0);
			}

			cart.setVelocity(v);
		}
		else if (block.getType() == Material.ACTIVATOR_RAIL)
		{
			cart.setMaxSpeed((double) speed / 20.0);
		}
		else if (block.getType() == Material.DETECTOR_RAIL)
		{
			cart.setMaxSpeed(0.4);
		}

		if (derail) return;

		int cnt;
		Cart CR;
		if (!rail.isCurve() && !rail.isOnSlope())
		{
			cnt = 0;

			while (true)
			{
				if (cnt >= CL.size())
				{
					CL.add(new Cart(cart.getUniqueId(), Math.max(Math.max(Math.abs(cart.getVelocity().getX()), Math.abs(cart.getVelocity().getY())), Math.abs(cart.getVelocity().getZ())), cart.getMaxSpeed(), rail));
					break;
				}

				CR = CL.get(cnt);
				if (cart.getUniqueId() == CR.cart && (CR.rail.isCurve() || CR.rail.isOnSlope()))
				{
					cart.setMaxSpeed(CR.maxspeed);
					Vector v = cart.getVelocity();
					v.normalize();
					v.multiply(CR.vel);
					cart.setVelocity(v);
					CL.get(cnt).rail = rail;
					return;
				}

				if (cart.getUniqueId() == CR.cart)
				{
					Location nb = cart.getLocation().add(cart.getVelocity().normalize());
					if (nb.getBlock().getState().getData() instanceof Rails nrl)
					{
						if (nrl.isCurve() || nrl.isOnSlope())
						{
							cart.setMaxSpeed(0.4);
							return;
						}
					}

					CL.get(cnt).maxspeed = cart.getMaxSpeed();
					CL.get(cnt).vel = Math.max(Math.max(Math.abs(cart.getVelocity().getX()), Math.abs(cart.getVelocity().getY())), Math.abs(cart.getVelocity().getZ()));
					CL.get(cnt).rail = rail;
					return;
				}

				++cnt;
			}
		}
		else
		{
			cart.setMaxSpeed(0.4);

			for (cnt = 0; cnt < CL.size(); ++cnt)
			{
				CR = CL.get(cnt);
				if (cart.getUniqueId() == CR.cart)
				{
					CL.get(cnt).rail = rail;
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onVehicleDestory(@NotNull VehicleDestroyEvent v)
	{
		if (derail) return;

		Vehicle vehicle = v.getVehicle();
		if (!(vehicle instanceof Minecart cart)) return;

		for (int cnt = 0; cnt < CL.size(); ++cnt)
		{
			Cart CR = CL.get(cnt);
			if (cart.getUniqueId() == CR.cart)
			{
				CL.remove(cnt);
				return;
			}
		}
	}

	public static final class Cart
	{
		public UUID cart;
		public double vel;
		public double maxspeed;
		public Rails rail;

		public Cart(UUID c, double v, double ms, Rails r)
		{
			this.cart = c;
			this.vel = v;
			this.maxspeed = ms;
			this.rail = r;
		}
	}
}
