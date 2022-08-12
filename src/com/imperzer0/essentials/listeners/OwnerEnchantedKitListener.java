package com.imperzer0.essentials.listeners;

import com.imperzer0.essentials.Main;
import com.imperzer0.essentials.constants.OwnerConstants;
import com.imperzer0.essentials.utils.Loger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;

import static com.imperzer0.essentials.constants.OwnerConstants.ENCHANTMENTS;

public class OwnerEnchantedKitListener implements Listener
{
	public final Main plugin;
	private final Loger loger;
	public static final int DISTANCE = 10000;
	public static final int R = 30;
	
	public static final String PERMISSION_USE = "imperzer0-essentials.command.owner_kit.use.";
	private Map<UUID, UUID> player_target = new HashMap<>();
	
	public OwnerEnchantedKitListener(@NotNull Loger loger)
	{
		this.loger = loger;
		this.plugin = loger.plugin;
		
		load_permissions();
	}
	
	final void load_permissions()
	{
		PluginManager manager = Bukkit.getPluginManager();
		manager.addPermission(new Permission(PERMISSION_USE + "bow", "owner kit bow's auto aim", PermissionDefault.FALSE));
		manager.addPermission(new Permission(PERMISSION_USE + "rod", "owner kit stick and rod", PermissionDefault.FALSE));
		manager.addPermission(new Permission(PERMISSION_USE + "axe", "owner kit lightning bold axe", PermissionDefault.FALSE));
		manager.addPermission(new Permission(PERMISSION_USE + "head", "owner kit spawner", PermissionDefault.FALSE));
		manager.addPermission(new Permission(PERMISSION_USE + "bowl", "owner kit bowl", PermissionDefault.FALSE));
		manager.addPermission(new Permission(PERMISSION_USE + "arrow", "owner kit arrow", PermissionDefault.FALSE));
		manager.addPermission(new Permission(PERMISSION_USE + "ore", "owner kit ore tips", PermissionDefault.FALSE));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void on_player_interacts(@NotNull PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (!player.hasPermission(PERMISSION_USE)) return;
		
		ItemStack item = event.getItem();
		if (!(item != null && item.getEnchantments().equals(ENCHANTMENTS))) return;
		
		switch (item.getType())
		{
			case BLAZE_ROD, STICK -> rod_click(event, player, item);
			case NETHERITE_AXE -> axe_click(event, player, item);
			case BOWL -> bowl_click(event, player, item);
			case BOW -> bow_click(event, player);
			case ARROW -> arrow_click(event, player, item);
			case ZOMBIE_HEAD -> zombie_head_click(event, player, item);
			case IRON_NUGGET ->
			{
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
					iron_nugget_click(event, player);
			}
			case GOLD_NUGGET ->
			{
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
					gold_nugget_click(event, player);
			}
			case GOLD_BLOCK ->
			{
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
					gold_block_click(event, player);
			}
			case DIAMOND ->
			{
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
					diamond_click(event, player);
			}
		}
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void on_shoot(@NotNull ProjectileLaunchEvent event)
	{
		if (event.getEntity().getShooter() instanceof Player player)
		{
			if (!player.hasPermission(PERMISSION_USE + "ore")) return;
			{
				if (player.getInventory().getItemInMainHand().isSimilar(
						OwnerConstants.owner_items[OwnerConstants.OWNER_ITEMS.AUTO_AIM_BOW.index()]))
				{
					Projectile projectile = event.getEntity();
					Entity target = Bukkit.getEntity(player_target.getOrDefault(player.getUniqueId(), player.getUniqueId()));
					if (target == null)
					{
						target = player;
					}
					AutoAimRunnable runnable = new AutoAimRunnable(projectile, target.getLocation(), player);
					runnable.runTaskLater(plugin, 20L);
				}
			}
		}
	}
	
	private void rod_click(@NotNull PlayerInteractEvent event, @NotNull Player player, @NotNull ItemStack rod)
	{
		if (!player.hasPermission(PERMISSION_USE + "rod")) return;
		switch (event.getAction())
		{
			case RIGHT_CLICK_BLOCK, RIGHT_CLICK_AIR ->
			{
				Block block = event.getClickedBlock();
				if (block == null || block.getType().equals(Material.AIR))
					block = player.getTargetBlock(null, DISTANCE);
				
				if (block.getType() != Material.AIR)
				{
					int n = rod.getAmount();
					for (int i = 0; i < n; ++i)
					{
						TNTPrimed tnt = player.getWorld().spawn(block.getLocation(), TNTPrimed.class);
						tnt.setFuseTicks(5);
						tnt.setGravity(false);
						tnt.setYield(100.f);
					}
				}
				
				event.setCancelled(true);
			}
			case LEFT_CLICK_BLOCK, LEFT_CLICK_AIR ->
			{
				player.getInventory().setArmorContents(OwnerConstants.filter_owner_items_for_armor());
				OwnerConstants.apply_owner_effects(player);
			}
		}
	}
	
	private void axe_click(@NotNull PlayerInteractEvent event, @NotNull Player player, @NotNull ItemStack axe)
	{
		
		if (!player.hasPermission(PERMISSION_USE + "axe")) return;
		switch (event.getAction())
		{
			case RIGHT_CLICK_BLOCK, RIGHT_CLICK_AIR ->
			{
				Block block = event.getClickedBlock();
				if (block == null || block.getType().equals(Material.AIR))
					block = player.getTargetBlock(null, DISTANCE);
				
				if (block.getType() != Material.AIR)
				{
					int n = axe.getAmount();
					for (int i = 0; i < n; i++)
						player.getWorld().strikeLightning(block.getLocation());
				}
				
				event.setCancelled(true);
			}
		}
	}
	
	private void bowl_click(@NotNull PlayerInteractEvent event, @NotNull Player player, @NotNull ItemStack bowl)
	{
		if (!player.hasPermission(PERMISSION_USE + "bowl")) return;
		Location location = player.getLocation().add(0, 1.63, 0);
		int n = bowl.getAmount();
		for (int i = 0; i < n; ++i)
		{
			switch (event.getAction())
			{
				case LEFT_CLICK_BLOCK, LEFT_CLICK_AIR ->
				{
					Fireball fireball = player.getWorld().spawn(location, Fireball.class);
					fireball.setGravity(true);
					fireball.setDirection(location.getDirection().multiply(1000));
					fireball.setBounce(false);
					fireball.setIsIncendiary(true);
					fireball.setYield(100.f);
					fireball.setCustomName(Ghast.class.getName());
					
					event.setCancelled(true);
				}
				case RIGHT_CLICK_BLOCK, RIGHT_CLICK_AIR ->
				{
					Vector vel = location.getDirection().multiply(1000);
					Arrow arrow = player.getWorld().spawnArrow(location, vel, 100.f, 0.f, Arrow.class);
					arrow.setGravity(true);
					arrow.setPierceLevel(100);
					arrow.setDamage(100000.0);
					arrow.setKnockbackStrength(100);
					arrow.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 255), true);
					arrow.setPickupStatus(AbstractArrow.PickupStatus.ALLOWED);
					arrow.setCustomName(Skeleton.class.getName());
					
					event.setCancelled(true);
				}
			}
		}
		event.setCancelled(true);
	}
	
	private void bow_click(@NotNull PlayerInteractEvent event, @NotNull Player player)
	{
		if (!player.hasPermission(PERMISSION_USE + "axe")) return;
		switch (event.getAction())
		{
			case LEFT_CLICK_BLOCK, LEFT_CLICK_AIR ->
			{
				Pair<Double, LivingEntity> most_looking_at = new Pair<>(0.0, null);
				for (Entity e : player.getNearbyEntities(50.0, 50.0, 50.0))
				{
					try
					{
						double dot = angle_looking_at(player, (LivingEntity)e);
						if (dot >= 0.9D)
							if (most_looking_at.key < dot)
								most_looking_at = new Pair<>(dot, (LivingEntity)e);
					}
					catch (Exception ignored) { }
				}
				
				if (most_looking_at.val != null)
				{
					player_target.put(event.getPlayer().getUniqueId(), most_looking_at.val.getUniqueId());
					String msg = "Target selected: ";
					if (most_looking_at.val instanceof Mob)
						msg += ChatColor.BLUE;
					else msg += ChatColor.LIGHT_PURPLE;
					msg += most_looking_at.val.getName();
					loger.message(event.getPlayer(), msg);
				}
				
				event.setCancelled(true);
			}
		}
		
	}
	
	private double angle_looking_at(@NotNull Player player, @NotNull LivingEntity entity)
	{
		Location playerLocation = player.getEyeLocation();
		Location entityLocation = entity.getEyeLocation();
		org.bukkit.util.Vector vector = new Vector(
				entityLocation.getX() - playerLocation.getX(),
				entityLocation.getY() - playerLocation.getY(),
				entityLocation.getZ() - playerLocation.getZ()
		);
		
		return vector.normalize().dot(playerLocation.getDirection());
	}
	
	private void arrow_click(@NotNull PlayerInteractEvent event, @NotNull Player player, @NotNull ItemStack bow)
	{
		if (!player.hasPermission(PERMISSION_USE + "arrow")) return;
		switch (event.getAction())
		{
			case RIGHT_CLICK_BLOCK, RIGHT_CLICK_AIR ->
			{
				Location location = player.getLocation();
				location.add(0, 1.63, 0);
				int n = bow.getAmount();
				Random random = new Random(LocalDateTime.now().getNano());
				for (int i = 0; i < n; ++i)
				{
					Vector vel = new Vector(
							random.nextDouble() * (random.nextInt() % 2 == 1 ? 1 : -1),
							random.nextDouble() * (random.nextInt() % 2 == 1 ? 1 : -1),
							random.nextDouble() * (random.nextInt() % 2 == 1 ? 1 : -1)
					).normalize().multiply(1000);
					Arrow arrow = player.getWorld().spawnArrow(location, vel, 100.f, 0.f, Arrow.class);
					arrow.setGravity(true);
					arrow.setPierceLevel(100);
					arrow.setDamage(100000.0);
					arrow.setKnockbackStrength(100);
					arrow.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 255), true);
					arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
					arrow.setCustomName(Skeleton.class.getName());
				}
				event.setCancelled(true);
			}
		}
	}
	
	private void zombie_head_click(@NotNull PlayerInteractEvent event, @NotNull Player player, @NotNull ItemStack head)
	{
		if (!player.hasPermission(PERMISSION_USE + "head")) return;
		switch (event.getAction())
		{
			case RIGHT_CLICK_BLOCK, RIGHT_CLICK_AIR ->
			{
				Block block = event.getClickedBlock();
				if (block == null || block.getType().equals(Material.AIR))
					block = player.getTargetBlock(null, DISTANCE);
				
				if (block.getType() != Material.AIR)
				{
					int n = head.getAmount();
					for (int i = 0; i < n; ++i)
					{
						ItemStack helmet = OwnerConstants.owner_items[OwnerConstants.OWNER_ITEMS.HELMET.index()];
						ItemStack weapon = OwnerConstants.owner_items[OwnerConstants.OWNER_ITEMS.LIGHTNING_AXE.index()];
						ItemStack boots = OwnerConstants.owner_items[OwnerConstants.OWNER_ITEMS.BOOTS.index()];
						
						ArrayList<PotionEffect> effects1 = new ArrayList<>();
						effects1.add(new PotionEffect(PotionEffectType.REGENERATION, 3600, 50));
						effects1.add(new PotionEffect(PotionEffectType.ABSORPTION, 3600, 50));
						effects1.add(new PotionEffect(PotionEffectType.SATURATION, 3600, 50));
						effects1.add(new PotionEffect(PotionEffectType.NIGHT_VISION, 3600, 1));
						effects1.add(new PotionEffect(PotionEffectType.WATER_BREATHING, 3600, 1));
						effects1.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 3600, 1));
						effects1.add(new PotionEffect(PotionEffectType.LUCK, 3600, 50));
						
						Zombie zombie = (Zombie)player.getWorld().spawnEntity(block.getLocation(), EntityType.ZOMBIE);
						Objects.requireNonNull(zombie.getEquipment()).setHelmet(helmet);
						zombie.getEquipment().setItemInMainHand(weapon);
						zombie.getEquipment().setItemInOffHand(weapon);
						zombie.getEquipment().setBoots(boots);
						zombie.setAI(true);
						zombie.setGliding(true);
						zombie.setSilent(true);
						zombie.setMaxHealth(100.0);
						zombie.getEquipment().setItemInMainHandDropChance(0.f);
						zombie.getEquipment().setHelmetDropChance(0.f);
						zombie.getEquipment().setBootsDropChance(0.f);
						zombie.getActivePotionEffects().clear();
						zombie.getActivePotionEffects().addAll(effects1);
						
						PigZombie zombie_pigman = (PigZombie)player.getWorld().spawnEntity(
								block.getLocation(), EntityType.ZOMBIFIED_PIGLIN);
						Objects.requireNonNull(zombie_pigman.getEquipment()).setHelmet(helmet);
						zombie_pigman.getEquipment().setItemInMainHand(weapon);
						zombie_pigman.getEquipment().setItemInOffHand(weapon);
						zombie_pigman.getEquipment().setBoots(boots);
						zombie_pigman.setAI(true);
						zombie_pigman.setGliding(true);
						zombie_pigman.setSilent(true);
						zombie_pigman.setMaxHealth(100.0);
						zombie_pigman.getEquipment().setItemInMainHandDropChance(0.f);
						zombie_pigman.getEquipment().setHelmetDropChance(0.f);
						zombie_pigman.getEquipment().setBootsDropChance(0.f);
						zombie_pigman.getActivePotionEffects().clear();
						zombie_pigman.getActivePotionEffects().addAll(effects1);
						
						Vindicator vindicator = (Vindicator)player.getWorld().spawnEntity(
								block.getLocation(), EntityType.VINDICATOR);
						Objects.requireNonNull(vindicator.getEquipment()).setHelmet(helmet);
						vindicator.getEquipment().setItemInMainHand(weapon);
						vindicator.getEquipment().setItemInOffHand(weapon);
						vindicator.getEquipment().setBoots(boots);
						vindicator.setAI(true);
						vindicator.setGliding(true);
						vindicator.setSilent(true);
						vindicator.setMaxHealth(100.0);
						vindicator.getEquipment().setItemInMainHandDropChance(0.f);
						vindicator.getEquipment().setHelmetDropChance(0.f);
						vindicator.getEquipment().setBootsDropChance(0.f);
						vindicator.getActivePotionEffects().clear();
						vindicator.getActivePotionEffects().addAll(effects1);
					}
					
					event.setCancelled(true);
				}
			}
		}
	}
	
	private void iron_nugget_click(@NotNull PlayerInteractEvent event, @NotNull Player player)
	{
		if (!player.hasPermission(PERMISSION_USE + "ore")) return;
		loger.message(player, ChatColor.GRAY + "==========================");
		Location location = player.getLocation();
		Location loc;
		ArrayList<Pair<Double, String>> list = new ArrayList<>();
		for (int i = location.getBlockX() - R; i <= location.getBlockX() + R; ++i)
		{
			for (int j = location.getBlockY() - R; j <= location.getBlockY() + R; ++j)
			{
				for (int k = location.getBlockZ() - R; k <= location.getBlockZ() + R; ++k)
				{
					loc = new Location(player.getWorld(), i, j, k);
					if (loc.getBlock().getType() == Material.GOLD_ORE ||
					    loc.getBlock().getType() == Material.NETHER_GOLD_ORE ||
					    loc.getBlock().getType() == Material.DEEPSLATE_GOLD_ORE ||
					    loc.getBlock().getType() == Material.GOLD_BLOCK)
					{
						double sub_x = location.getBlockX() - loc.getBlockX();
						double sub_y = location.getBlockY() - loc.getBlockY();
						double sub_z = location.getBlockZ() - loc.getBlockZ();
						double dist = Math.sqrt(sub_x * sub_x + sub_y * sub_y + sub_z * sub_z);
						if (dist <= 15.0)
						{
							list.add(new Pair<>(
									dist,
									"[x=" + i + ", y=" + j + ", z=" + k + "] " + ChatColor.GREEN + "dist=" + dist
							));
						}
						else
						{
							list.add(new Pair<>(dist, "[x=" + i + ", y=" + j + ", z=" + k + "] dist=" + dist));
						}
					}
				}
			}
		}
		list.sort((left, right) ->
		          {
			          if (left.key < right.key) return 1;
			          else if (left.key.equals(right.key)) return 0;
			          else return -1;
		          });
		for (Pair<Double, String> p : list)
		{
			loger.message(player, p.getValue());
		}
		loger.message(player, ChatColor.GRAY + "==========================");
		
		event.setCancelled(true);
	}
	
	private void gold_nugget_click(@NotNull PlayerInteractEvent event, @NotNull Player player)
	{
		if (!player.hasPermission(PERMISSION_USE + "ore")) return;
		loger.message(player, ChatColor.YELLOW + "==========================");
		Location location = player.getLocation();
		Location loc;
		ArrayList<Pair<Double, String>> list = new ArrayList<>();
		for (int i = location.getBlockX() - R; i <= location.getBlockX() + R; ++i)
		{
			for (int j = location.getBlockY() - R; j <= location.getBlockY() + R; ++j)
			{
				for (int k = location.getBlockZ() - R; k <= location.getBlockZ() + R; ++k)
				{
					loc = new Location(player.getWorld(), i, j, k);
					if (loc.getBlock().getType() == Material.DIAMOND_ORE ||
					    loc.getBlock().getType() == Material.DEEPSLATE_DIAMOND_ORE ||
					    loc.getBlock().getType() == Material.DIAMOND_BLOCK)
					{
						double sub_x = location.getBlockX() - loc.getBlockX();
						double sub_y = location.getBlockY() - loc.getBlockY();
						double sub_z = location.getBlockZ() - loc.getBlockZ();
						double dist = Math.sqrt(sub_x * sub_x + sub_y * sub_y + sub_z * sub_z);
						if (dist <= 15.0)
						{
							list.add(new Pair<>(
									dist,
									"[x=" + i + ", y=" + j + ", z=" + k + "] " + ChatColor.GREEN + "dist=" + dist
							));
						}
						else
						{
							list.add(new Pair<>(dist, "[x=" + i + ", y=" + j + ", z=" + k + "] dist=" + dist));
						}
					}
				}
			}
		}
		list.sort((left, right) ->
		          {
			          if (left.key < right.key) return 1;
			          else if (left.key.equals(right.key)) return 0;
			          else return -1;
		          });
		for (Pair<Double, String> p : list)
		{
			player.sendMessage(p.getValue());
		}
		loger.message(player, ChatColor.YELLOW + "==========================");
		
		event.setCancelled(true);
	}
	
	private void gold_block_click(@NotNull PlayerInteractEvent event, @NotNull Player player)
	{
		if (!player.hasPermission(PERMISSION_USE + "ore")) return;
		loger.message(player, ChatColor.YELLOW + "==========================");
		Location location = player.getLocation();
		Location loc;
		ArrayList<Pair<Double, String>> list = new ArrayList<>();
		for (int i = location.getBlockX() - R; i <= location.getBlockX() + R; ++i)
		{
			for (int j = location.getBlockY() - R; j <= location.getBlockY() + R; ++j)
			{
				for (int k = location.getBlockZ() - R; k <= location.getBlockZ() + R; ++k)
				{
					loc = new Location(player.getWorld(), i, j, k);
					if (loc.getBlock().getType() == Material.EMERALD_ORE ||
					    loc.getBlock().getType() == Material.DEEPSLATE_EMERALD_ORE ||
					    loc.getBlock().getType() == Material.EMERALD_BLOCK)
					{
						double sub_x = location.getBlockX() - loc.getBlockX();
						double sub_y = location.getBlockY() - loc.getBlockY();
						double sub_z = location.getBlockZ() - loc.getBlockZ();
						double dist = Math.sqrt(sub_x * sub_x + sub_y * sub_y + sub_z * sub_z);
						if (dist <= 15.0)
						{
							list.add(new Pair<>(
									dist,
									"[x=" + i + ", y=" + j + ", z=" + k + "] " + ChatColor.GREEN + "dist=" + dist
							));
						}
						else
						{
							list.add(new Pair<>(dist, "[x=" + i + ", y=" + j + ", z=" + k + "] dist=" + dist));
						}
					}
				}
			}
		}
		list.sort((left, right) ->
		          {
			          if (left.key < right.key) return 1;
			          else if (left.key.equals(right.key)) return 0;
			          else return -1;
		          });
		for (Pair<Double, String> p : list)
		{
			player.sendMessage(p.getValue());
		}
		loger.message(player, ChatColor.YELLOW + "==========================");
		
		event.setCancelled(true);
	}
	
	private void diamond_click(@NotNull PlayerInteractEvent event, @NotNull Player player)
	{
		if (!player.hasPermission(PERMISSION_USE + "ore")) return;
		loger.message(player, ChatColor.AQUA + "==========================");
		Location location = player.getLocation();
		Location loc;
		ArrayList<Pair<Double, String>> list = new ArrayList<>();
		for (int i = location.getBlockX() - R; i <= location.getBlockX() + R; ++i)
		{
			for (int j = location.getBlockY() - R; j <= location.getBlockY() + R; ++j)
			{
				for (int k = location.getBlockZ() - R; k <= location.getBlockZ() + R; ++k)
				{
					loc = new Location(player.getWorld(), i, j, k);
					if (loc.getBlock().getType() == Material.EMERALD_ORE ||
					    loc.getBlock().getType() == Material.DEEPSLATE_EMERALD_ORE ||
					    loc.getBlock().getType() == Material.EMERALD_BLOCK)
					{
						double sub_x = location.getBlockX() - loc.getBlockX();
						double sub_y = location.getBlockY() - loc.getBlockY();
						double sub_z = location.getBlockZ() - loc.getBlockZ();
						double dist = Math.sqrt(sub_x * sub_x + sub_y * sub_y + sub_z * sub_z);
						if (dist <= 15.0)
						{
							list.add(new Pair<>(
									dist,
									"[x=" + i + ", y=" + j + ", z=" + k + "] " + ChatColor.GREEN + "dist=" + dist
							));
						}
						else
						{
							list.add(new Pair<>(dist, "[x=" + i + ", y=" + j + ", z=" + k + "] dist=" + dist));
						}
					}
				}
			}
		}
		list.sort((left, right) ->
		          {
			          if (left.key < right.key) return 1;
			          else if (left.key.equals(right.key)) return 0;
			          else return -1;
		          });
		for (Pair<Double, String> p : list)
			player.sendMessage(p.getValue());
		loger.message(player, ChatColor.AQUA + "==========================");
		
		event.setCancelled(true);
	}
	
	private static class Pair<K, V> implements Map.Entry<K, V>
	{
		private final K key;
		private V val;
		
		public Pair(K key, V val)
		{
			this.key = key;
			this.val = val;
		}
		
		@Override
		public K getKey()
		{
			return key;
		}
		
		@Override
		public V getValue()
		{
			return val;
		}
		
		@Override
		public V setValue(V value)
		{
			V val = this.val;
			this.val = value;
			return val;
		}
	}
	
	private static class AutoAimRunnable extends BukkitRunnable
	{
		private final Projectile projectile;
		private final Location target;
		private final Player player;
		
		public AutoAimRunnable(Projectile projectile, Location target, Player player)
		{
			
			this.projectile = projectile;
			this.target = target;
			this.player = player;
		}
		
		@Override
		public void run()
		{
			Vector vel = target.toVector().subtract(projectile.getLocation().toVector()).normalize().multiply(10.0);
			projectile.setVelocity(vel);
			double yaw = Math.atan2(vel.getZ(), vel.getX());
			double pitch = Math.atan2(Math.sqrt(vel.getZ() * vel.getZ() + vel.getX() * vel.getX()), vel.getY());
			projectile.setRotation((float)yaw, (float)pitch);
			player.sendMessage("velocity changed");
		}
	}
}

