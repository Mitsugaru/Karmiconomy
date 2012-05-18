/**
 * Config file mimicking DiddiZ's Config class file in LB. Tailored for this
 * plugin.
 * 
 * @author Mitsugaru
 */
package com.mitsugaru.Karmiconomy.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.database.Field;

public class Config implements KConfig
{
	// Class variables
	private Karmiconomy plugin;
	public String host, port, database, user, password;
	public static String tablePrefix;
	public boolean debugTime, debugEvents, debugEconomy, debugUnhandled,
			useMySQL, importSQL, portalCreateNether, portalCreateEnd,
			portalCreateCustom, blockPlaceStatic, blockDestroyStatic,
			craftItemStatic, enchantItemStatic, itemDropStatic, commandStatic,
			pickupStatic, shootBowDenyForce;
	public int listlimit;
	public double shootBowForce;
	private final Map<Item, KCItemInfo> values = new HashMap<Item, KCItemInfo>();

	// TODO ability to change config in-game
	/**
	 * Constructor and initializer
	 * 
	 * @param Karmiconomy
	 *            plugin
	 */
	public Config(Karmiconomy plugin)
	{
		this.plugin = plugin;
		// Grab config
		final ConfigurationSection config = plugin.getConfig();
		// LinkedHashmap of defaults
		final Map<String, Object> defaults = new LinkedHashMap<String, Object>();
		defaults.put("listlimit", 10);
		defaults.put("bed.enter.enabled", false);
		defaults.put("bed.enter.denyOnLackPay", false);
		defaults.put("bed.enter.denyOnLimit", false);
		defaults.put("bed.enter.limit", 10);
		defaults.put("bed.enter.pay", 0.1);
		defaults.put("bed.enter.localMessage", false);
		defaults.put("bed.leave.enabled", false);
		defaults.put("bed.leave.limit", 10);
		defaults.put("bed.leave.pay", 0.1);
		defaults.put("bed.leave.localMessage", false);
		defaults.put("block.destroy.enabled", false);
		defaults.put("block.destroy.denyOnLackPay", false);
		defaults.put("block.destroy.denyOnLimit", false);
		defaults.put("block.destroy.static", true);
		defaults.put("block.destroy.limit", 100);
		defaults.put("block.destroy.pay", 0.1);
		defaults.put("block.destroy.localMessage", false);
		defaults.put("block.place.enabled", false);
		defaults.put("block.place.denyOnLackPay", false);
		defaults.put("block.place.denyOnLimit", false);
		defaults.put("block.place.static", true);
		defaults.put("block.place.limit", 100);
		defaults.put("block.place.pay", 0.1);
		defaults.put("block.place.localMessage", false);
		defaults.put("bow.shoot.enabled", false);
		defaults.put("bow.shoot.denyOnLackPay", false);
		defaults.put("bow.shoot.denyOnLimit", false);
		defaults.put("bow.shoot.denyOnLowForce", false);
		defaults.put("bow.shoot.minimumforce", 0.0);
		defaults.put("bow.shoot.limit", 100);
		defaults.put("bow.shoot.pay", 0.1);
		defaults.put("bow.shoot.localMessage", false);
		defaults.put("bucket.empty.lava.enabled", false);
		defaults.put("bucket.empty.lava.denyOnLackPay", false);
		defaults.put("bucket.empty.lava.denyOnLimit", false);
		defaults.put("bucket.empty.lava.limit", 100);
		defaults.put("bucket.empty.lava.pay", -10.0);
		defaults.put("bucket.empty.lava.localMessage", false);
		defaults.put("bucket.empty.water.enabled", false);
		defaults.put("bucket.empty.water.denyOnLackPay", false);
		defaults.put("bucket.empty.water.denyOnLimit", false);
		defaults.put("bucket.empty.water.limit", 100);
		defaults.put("bucket.empty.water.pay", 0.1);
		defaults.put("bucket.empty.water.localMessage", false);
		defaults.put("bucket.fill.lava.enabled", false);
		defaults.put("bucket.fill.lava.denyOnLackPay", false);
		defaults.put("bucket.fill.lava.denyOnLimit", false);
		defaults.put("bucket.fill.lava.limit", 100);
		defaults.put("bucket.fill.lava.pay", 0.1);
		defaults.put("bucket.fill.lava.localMessage", false);
		defaults.put("bucket.fill.water.enabled", false);
		defaults.put("bucket.fill.water.denyOnLackPay", false);
		defaults.put("bucket.fill.water.denyOnLimit", false);
		defaults.put("bucket.fill.water.limit", 100);
		defaults.put("bucket.fill.water.pay", 0.1);
		defaults.put("bucket.fill.water.localMessage", false);
		defaults.put("item.craft.enabled", false);
		defaults.put("item.craft.static", true);
		defaults.put("item.craft.denyOnLackPay", false);
		defaults.put("item.craft.denyOnLimit", false);
		defaults.put("item.craft.limit", 100);
		defaults.put("item.craft.pay", 0.1);
		defaults.put("item.craft.localMessage", false);
		defaults.put("item.enchant.enabled", false);
		defaults.put("item.enchant.denyOnLackPay", false);
		defaults.put("item.enchant.denyOnLimit", false);
		defaults.put("item.enchant.static", true);
		defaults.put("item.enchant.limit", 100);
		defaults.put("item.enchant.pay", 0.1);
		defaults.put("item.enchant.localMessage", false);
		defaults.put("item.drop.enabled", false);
		defaults.put("item.drop.denyOnLackPay", false);
		defaults.put("item.drop.denyOnLimit", false);
		defaults.put("item.drop.static", true);
		defaults.put("item.drop.limit", 100);
		defaults.put("item.drop.pay", 0.1);
		defaults.put("item.drop.localMessage", false);
		defaults.put("item.pickup.enabled", false);
		defaults.put("item.pickup.denyOnLackPay", false);
		defaults.put("item.pickup.denyOnLimit", false);
		defaults.put("item.pickup.static", true);
		defaults.put("item.pickup.limit", 100);
		defaults.put("item.pickup.pay", 0.1);
		defaults.put("item.pickup.localMessage", false);
		defaults.put("item.egg.enabled", false);
		defaults.put("item.egg.limit", 100);
		defaults.put("item.egg.pay", 0.1);
		defaults.put("item.egg.localMessage", false);
		defaults.put("painting.enabled", false);
		defaults.put("painting.denyOnLackPay", true);
		defaults.put("painting.denyOnLimit", true);
		defaults.put("painting.limit", 100);
		defaults.put("painting.pay", 0.1);
		defaults.put("painting.localMessage", false);
		defaults.put("player.chat.enabled", false);
		defaults.put("player.chat.denyOnLackPay", false);
		defaults.put("player.chat.denyOnLimit", false);
		defaults.put("player.chat.limit", 10);
		defaults.put("player.chat.pay", 0.1);
		defaults.put("player.chat.localMessage", false);
		defaults.put("player.command.enabled", false);
		defaults.put("player.command.denyOnLackPay", false);
		defaults.put("player.command.denyOnLimit", false);
		defaults.put("player.command.static", true);
		defaults.put("player.command.limit", 10);
		defaults.put("player.command.pay", 0.1);
		defaults.put("player.command.localMessage", false);
		defaults.put("player.death.enabled", false);
		defaults.put("player.death.limit", 100);
		defaults.put("player.death.pay", -1);
		defaults.put("player.death.localMessage", false);
		defaults.put("player.gamemode.creative.enabled", false);
		defaults.put("player.gamemode.creative.denyOnLackPay", false);
		defaults.put("player.gamemode.creative.denyOnLimit", false);
		defaults.put("player.gamemode.creative.limit", 10);
		defaults.put("player.gamemode.creative.pay", -10);
		defaults.put("player.gamemode.creative.localMessage", false);
		defaults.put("player.gamemode.survival.enabled", false);
		defaults.put("player.gamemode.survival.denyOnLackPay", false);
		defaults.put("player.gamemode.survival.denyOnLimit", false);
		defaults.put("player.gamemode.survival.limit", 1);
		defaults.put("player.gamemode.survival.pay", 0.1);
		defaults.put("player.gamemode.survival.localMessage", false);
		defaults.put("player.join.enabled", false);
		defaults.put("player.join.limit", 1);
		defaults.put("player.join.pay", 10);
		defaults.put("player.join.localMessage", false);
		defaults.put("player.kick.enabled", false);
		defaults.put("player.kick.limit", 10);
		defaults.put("player.kick.pay", -10);
		defaults.put("player.kick.localMessage", false);
		defaults.put("player.quit.enabled", false);
		defaults.put("player.quit.limit", 1);
		defaults.put("player.quit.pay", 0.1);
		defaults.put("player.quit.localMessage", false);
		defaults.put("player.respawn.enabled", false);
		defaults.put("player.respawn.limit", 100);
		defaults.put("player.respawn.pay", -0.1);
		defaults.put("player.respawn.localMessage", false);
		defaults.put("player.sneak.enabled", false);
		defaults.put("player.sneak.denyOnLackPay", false);
		defaults.put("player.sneak.denyOnLimit", false);
		defaults.put("player.sneak.limit", 10);
		defaults.put("player.sneak.pay", 0.1);
		defaults.put("player.sneak.localMessage", false);
		defaults.put("player.sprint.enabled", false);
		defaults.put("player.sprint.denyOnLackPay", false);
		defaults.put("player.sprint.denyOnLimit", false);
		defaults.put("player.sprint.limit", 10);
		defaults.put("player.sprint.pay", 0.1);
		defaults.put("player.sprint.localMessage", false);
		defaults.put("portal.createNether.enabled", false);
		defaults.put("portal.createNether.denyOnLackPay", false);
		defaults.put("portal.createNether.denyOnLimit", false);
		defaults.put("portal.createNether.limit", 10);
		// TODO fix so that it is no longer redundant
		defaults.put("portal.createNether.pay.nether", 0.1);
		defaults.put("portal.createNether.localMessage", false);
		defaults.put("portal.createEnd.enabled", false);
		defaults.put("portal.createEnd.denyOnLackPay", false);
		defaults.put("portal.createEnd.denyOnLimit", false);
		defaults.put("portal.createEnd.limit", 10);
		defaults.put("portal.createEnd.pay.ender", 0.1);
		defaults.put("portal.createEnd.localMessage", false);
		defaults.put("portal.createCustom.enabled", false);
		defaults.put("portal.createCustom.denyOnLackPay", false);
		defaults.put("portal.createCustom.denyOnLimit", false);
		defaults.put("portal.createCustom.limit", 10);
		defaults.put("portal.createCustom.pay.custom", 0.1);
		defaults.put("portal.createCustom.localMessage", false);
		defaults.put("portal.enter.enabled", false);
		defaults.put("portal.enter.limit", 10);
		defaults.put("portal.enter.pay", 0.1);
		defaults.put("portal.enter.localMessage", false);
		defaults.put("tame.ocelot.enabled", false);
		defaults.put("tame.ocelot.limit", 10);
		defaults.put("tame.ocelot.pay", 10);
		defaults.put("tame.ocelot.localMessage", false);
		defaults.put("tame.wolf.enabled", false);
		defaults.put("tame.wolf.limit", 10);
		defaults.put("tame.wolf.pay", 10);
		defaults.put("tame.wolf.localMessage", false);
		// TODO implement
		/*
		 * defaults.put("vehicle.enter.enabled", false);
		 * defaults.put("vehicle.enter.denyOnLackPay", false);
		 * defaults.put("vehicle.enter.denyOnLimit", false);
		 * defaults.put("vehicle.enter.limit", 100);
		 * defaults.put("vehicle.enter.pay", 0.1);
		 * defaults.put("vehicle.exit.enabled", false);
		 * defaults.put("vehicle.exit.denyOnLackPay", false);
		 * defaults.put("vehicle.exit.denyOnLimit", false);
		 * defaults.put("vehicle.exit.limit", 100);
		 * defaults.put("vehicle.exit.pay", 0.1);
		 */
		defaults.put("world.change.enabled", false);
		defaults.put("world.change.limit", 15);
		defaults.put("world.change.pay", 1.0);
		defaults.put("world.change.localMessage", false);
		defaults.put("mysql.use", false);
		defaults.put("mysql.host", "localhost");
		defaults.put("mysql.port", 3306);
		defaults.put("mysql.database", "minecraft");
		defaults.put("mysql.user", "username");
		defaults.put("mysql.password", "pass");
		defaults.put("mysql.tablePrefix", "kcon_");
		defaults.put("mysql.import", false);
		defaults.put("debug.events", false);
		defaults.put("debug.time", false);
		defaults.put("debug.economy", false);
		defaults.put("debug.unhandled", false);
		defaults.put("version", plugin.getDescription().getVersion());
		// Insert defaults into config file if they're not present
		for (final Entry<String, Object> e : defaults.entrySet())
		{
			if (!config.contains(e.getKey()))
			{
				config.set(e.getKey(), e.getValue());
			}
		}
		// Save config
		plugin.saveConfig();
		// Load variables from config
		/**
		 * SQL info
		 */
		useMySQL = config.getBoolean("mysql.use", false);
		host = config.getString("mysql.host", "localhost");
		port = config.getString("mysql.port", "3306");
		database = config.getString("mysql.database", "minecraft");
		user = config.getString("mysql.user", "user");
		password = config.getString("mysql.password", "password");
		tablePrefix = config.getString("mysql.prefix", "kcon_");
		importSQL = config.getBoolean("mysql.import", false);
		// Load all other settings
		this.loadSettings(config);
		// Load config for item specific value
		this.loadItemValueMap();
		// Finally, do a bounds check on parameters to make sure they are legal
		this.boundsCheck();
	}

	public void set(String path, Object o)
	{
		final ConfigurationSection config = plugin.getConfig();
		config.set(path, o);
		plugin.saveConfig();
	}

	/**
	 * Check if updates are necessary
	 */
	public void checkUpdate()
	{
		// Check if need to update
		ConfigurationSection config = plugin.getConfig();
		if (Double.parseDouble(plugin.getDescription().getVersion()) > Double
				.parseDouble(config.getString("version")))
		{
			// Update to latest version
			plugin.getLogger().info(
					"Updating to v" + plugin.getDescription().getVersion());
			this.update();
		}
	}

	/**
	 * This method is called to make the appropriate changes, most likely only
	 * necessary for database schema modification, for a proper update.
	 */
	@SuppressWarnings("unused")
	private void update()
	{
		// Grab current version
		final double ver = Double.parseDouble(plugin.getConfig().getString(
				"version"));
		// can remove old config options using the following:
		// plugin.getConfig().set("path.to.remove", null);
		// Update version number in config.yml
		plugin.getConfig().set("version", plugin.getDescription().getVersion());
		plugin.saveConfig();
		plugin.getLogger().info("Upgrade complete");
	}

	/**
	 * Reloads info from yaml file(s)
	 */
	public void reloadConfig()
	{
		// Initial relaod
		plugin.reloadConfig();
		// Grab config
		final ConfigurationSection config = plugin.getConfig();
		//Load settings
		this.loadSettings(config);
		// Load config for item specific values
		this.loadItemValueMap();
		// Check bounds
		this.boundsCheck();
		plugin.getLogger().info("Config reloaded");
	}

	private void loadSettings(ConfigurationSection config)
	{
		/**
		 * General Settings
		 */
		listlimit = config.getInt("listlimit", 10);
		debugTime = config.getBoolean("debug.time", false);
		debugEvents = config.getBoolean("debug.events", false);
		debugEconomy = config.getBoolean("debug.economy", false);
		debugUnhandled = config.getBoolean("debug.unhandled", false);
		/**
		 * Event Settings
		 */
		// destroy
		blockDestroyStatic = config.getBoolean("block.destroy.static", true);
		// place
		blockPlaceStatic = config.getBoolean("block.place.static", true);
		/**
		 * Item
		 */
		// craft
		craftItemStatic = config.getBoolean("item.craft.static", true);
		// enchant
		enchantItemStatic = config.getBoolean("item.enchant.static", true);
		// drop
		itemDropStatic = config.getBoolean("item.drop.static", true);
		// pickup
		pickupStatic = config.getBoolean("item.drop.static", true);
		/**
		 * Bow
		 */
		// shoot
		shootBowDenyForce = config
				.getBoolean("bow.shoot.denyOnLowForce", false);
		shootBowForce = config.getDouble("bow.shoot.minimumforce", 0.0);
		/**
		 * Player section
		 */
		// command
		commandStatic = config.getBoolean("player.command.static", true);
		/**
		 * Portal
		 */
		// create nether
		portalCreateNether = config.getBoolean("portal.createNether.enabled",
				false);
		// create end
		portalCreateEnd = config.getBoolean("portal.createEnd.enabled", false);
		// create custom
		portalCreateCustom = config.getBoolean("portal.createCustom.enabled",
				false);
		/**
		 * Vehicle
		 * 
		 * TODO implement
		 */
		// enter
		/*
		 * vehicleEnter = config.getBoolean("vehicle.enter.enabled", false);
		 * vehicleEnterDenyPay =
		 * config.getBoolean("vehicle.enter.denyOnLackPay", false);
		 * vehicleEnterDenyLimit =
		 * config.getBoolean("vehicle.enter.denyOnLimit", false);
		 * vehicleEnterLimit = config.getInt("vehicle.enter.limit", 100);
		 * vehicleEnterPay = config.getDouble("vehicle.enter.pay", 0.1);
		 */
		// exit
		/*
		 * vehicleExit = config.getBoolean("vehicle.exit.enabled", false);
		 * vehicleExitLimit = config.getInt("vehicle.exit.limit", 100);
		 * vehicleExitPay = config.getDouble("vehicle.exit.pay", 0.1);
		 */
	}

	/**
	 * Check the bounds on the parameters to make sure that all config variables
	 * are legal and usable by the plugin
	 */
	private void boundsCheck()
	{
		// TODO format all doubles to 2 decimal places
	}

	@Override
	public double getPayValue(Field type, Item item, String command)
	{
		double pay = 0.0;
		boolean found = false;
		if (item != null)
		{
			switch (type)
			{
				case BLOCK_PLACE:
				{
					if (!blockPlaceStatic)
					{
						if (values.containsKey(item))
						{
							pay = values.get(item).placePay;
							found = true;
						}
					}
					break;
				}
				case BLOCK_DESTROY:
				{
					if (!blockDestroyStatic)
					{
						if (values.containsKey(item))
						{
							pay = values.get(item).destroyPay;
							found = true;
						}
					}
					break;
				}
				case ITEM_CRAFT:
				{
					if (!craftItemStatic)
					{
						if (values.containsKey(item))
						{
							pay = values.get(item).craftPay;
							found = true;
						}
					}
					break;
				}
				case ITEM_DROP:
				{
					if (!itemDropStatic)
					{
						if (values.containsKey(item))
						{
							pay = values.get(item).dropPay;
							found = true;
						}
					}
					break;
				}
				case ITEM_ENCHANT:
				{
					if (!enchantItemStatic)
					{
						if (values.containsKey(item))
						{
							pay = values.get(item).enchantPay;
							found = true;
						}
					}
					break;
				}
				case ITEM_PICKUP:
				{
					if (!pickupStatic)
					{
						if (values.containsKey(item))
						{
							pay = values.get(item).pickupPay;
							found = true;
						}
					}
					break;
				}
				default:
				{
					if (debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled pay for field " + type.name());
					}
					break;
				}
			}
			if (!found)
			{
				pay = plugin.getConfig().getDouble(
						type.getConfigPath() + ".pay", 0.0);
			}
		}
		else if (command != null)
		{
			pay = getCommandPay(type, command);
		}
		else
		{
			pay = plugin.getConfig().getDouble(type.getConfigPath() + ".pay",
					0.0);
		}
		return pay;
	}
	
	private double getCommandPay(Field field, String command)
	{
		// TODO implement
		return plugin.getConfig().getDouble(field.getConfigPath() + ".pay", 0.0);
	}

	@Override
	public int getLimitValue(Field type, Item item, String command)
	{
		int limit = -1;
		boolean found = false;
		if (item != null)
		{
			switch (type)
			{
				case BLOCK_PLACE:
				{
					if (!blockPlaceStatic)
					{
						if (values.containsKey(item))
						{
							limit = values.get(item).placeLimit;
							found = true;
						}
					}
					break;
				}
				case BLOCK_DESTROY:
				{
					if (!blockDestroyStatic)
					{
						if (values.containsKey(item))
						{
							limit = values.get(item).destroyLimit;
							found = true;
						}
					}
					break;
				}
				case ITEM_CRAFT:
				{
					if (!craftItemStatic)
					{
						if (values.containsKey(item))
						{
							limit = values.get(item).craftLimit;
							found = true;
						}
					}
					break;
				}
				case ITEM_DROP:
				{
					if (!itemDropStatic)
					{
						if (values.containsKey(item))
						{
							limit = values.get(item).dropLimit;
							found = true;
						}
					}
					break;
				}
				case ITEM_ENCHANT:
				{
					if (!enchantItemStatic)
					{
						if (values.containsKey(item))
						{
							limit = values.get(item).enchantLimit;
							found = true;
						}
					}
					break;
				}
				case ITEM_PICKUP:
				{
					if (!pickupStatic)
					{
						if (values.containsKey(item))
						{
							limit = values.get(item).pickupLimit;
							found = true;
						}
					}
					break;
				}
				default:
				{
					if (debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled limit for field " + type.name());
					}
					break;
				}
			}
			if (!found)
			{
				limit = plugin.getConfig().getInt(
						type.getConfigPath() + ".limit", -1);
			}
		}
		else if (command != null)
		{
			limit = getCommandLimit(type, command);
		}
		else
		{
			limit = plugin.getConfig().getInt(type.getConfigPath() + ".limit",
					-1);
		}
		return limit;
	}
	
	private int getCommandLimit(Field field, String command)
	{
		//TODO implement
		return plugin.getConfig().getInt(
				field.getConfigPath() + ".limit", -1);
	}

	@Override
	public boolean getDenyPay(Field field, Item item, String command)
	{
		if (item != null)
		{
			return getItemDenyPay(field, item);
		}
		else if (command != null)
		{
			return getCommandDenyPay(field, command);
		}
		else
		{
			return plugin.getConfig().getBoolean(
					field.getConfigPath() + ".denyOnLackPay", false);
		}
	}

	private boolean getCommandDenyPay(Field field, String command)
	{
		// TODO implement
		return plugin.getConfig().getBoolean(
				field.getConfigPath() + ".denyOnLackPay", false);
	}

	private boolean getItemDenyPay(Field type, Item item)
	{
		switch (type)
		{
			case BLOCK_PLACE:
			{
				if (!blockPlaceStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).placeDenyPay;
					}
				}
				break;
			}
			case BLOCK_DESTROY:
			{
				if (!blockDestroyStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).destroyDenyPay;
					}
				}
				break;
			}
			case ITEM_CRAFT:
			{
				if (!craftItemStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).craftDenyPay;
					}
				}
				break;
			}
			case ITEM_DROP:
			{
				if (!itemDropStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).dropDenyPay;
					}
				}
				break;
			}
			case ITEM_ENCHANT:
			{
				if (!enchantItemStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).enchantDenyPay;
					}
				}
				break;
			}
			case ITEM_PICKUP:
			{
				if (!pickupStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).pickupDenyPay;
					}
				}
				break;
			}
			default:
			{
				if (debugUnhandled)
				{
					plugin.getLogger().warning(
							"Unhandled Deny Pay for " + type.name());
				}
				break;
			}
		}
		// Non-specifc, so return default
		return plugin.getConfig().getBoolean(
				type.getConfigPath() + ".denyOnLackPay", false);
	}

	@Override
	public boolean getDenyLimit(Field field, Item item, String command)
	{
		if (item != null)
		{
			return getItemDenyLimit(field, item);
		}
		else if (command != null)
		{
			return getCommandDenyLimit(field, command);
		}
		else
		{
			return plugin.getConfig().getBoolean(
					field.getConfigPath() + ".denyOnLimit", false);
		}
	}

	private boolean getCommandDenyLimit(Field field, String command)
	{
		// TODO implement
		return plugin.getConfig().getBoolean(
				field.getConfigPath() + ".denyOnLimit", false);
	}

	private boolean getItemDenyLimit(Field type, Item item)
	{
		switch (type)
		{
			case BLOCK_PLACE:
			{
				if (!blockPlaceStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).placeDenyLimit;
					}
				}
				break;
			}
			case BLOCK_DESTROY:
			{
				if (!blockDestroyStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).destroyDenyLimit;
					}
				}
				break;
			}
			case ITEM_CRAFT:
			{
				if (!craftItemStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).craftDenyLimit;
					}
				}
				break;
			}
			case ITEM_DROP:
			{
				if (!itemDropStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).dropDenyLimit;
					}
				}
				break;
			}
			case ITEM_ENCHANT:
			{
				if (!enchantItemStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).enchantDenyLimit;
					}
				}
				break;
			}
			case ITEM_PICKUP:
			{
				if (!pickupStatic)
				{
					if (values.containsKey(item))
					{
						return values.get(item).pickupDenyLimit;
					}
				}
				break;
			}
			default:
			{
				if (debugUnhandled)
				{
					plugin.getLogger().warning(
							"Unhandled Deny Limit for " + type.name());
				}
				break;
			}
		}
		return plugin.getConfig().getBoolean(
				type.getConfigPath() + ".denyOnLimit", false);
	}

	@Override
	public boolean sendBroadcast(Field field)
	{
		return plugin.getConfig().getBoolean(
				field.getConfigPath() + ".localMessage", false);
	}

	@Override
	public boolean checkWorld(Field field, String worldName)
	{
		boolean valid = false;
		final List<String> list = plugin.getConfig().getStringList(
				field.getConfigPath() + ".worlds");
		if (list == null)
		{
			// No worlds specified, so allow all
			valid = true;
		}
		else if (list.isEmpty())
		{
			valid = true;
		}
		else
		{
			for (String world : list)
			{
				if (world.equalsIgnoreCase(worldName))
				{
					valid = true;
					break;
				}
			}
		}
		return valid;
	}

	/**
	 * Loads the per-item karma values into a hashmap for later usage
	 */
	private void loadItemValueMap()
	{
		// Load karma file
		final YamlConfiguration valueFile = this.itemValuesFile();
		// Load custom karma file into map
		for (final String entry : valueFile.getKeys(false))
		{
			try
			{
				// Attempt to parse non data value nodes
				int key = Integer.parseInt(entry);
				if (key <= 0)
				{
					plugin.getLogger().warning(
							Karmiconomy.TAG
									+ " Zero or negative item id for entry: "
									+ entry);
				}
				else
				{
					// If it has child nodes, parse those as well
					if (valueFile.isConfigurationSection(entry))
					{
						values.put(new Item(key, Byte.parseByte("" + 0),
								(short) 0), parseInfo(valueFile, entry));
					}
					else
					{
						plugin.getLogger().warning("No section for " + entry);
					}
				}
			}
			catch (final NumberFormatException ex)
			{
				// Potential data value entry
				if (entry.contains("&"))
				{
					try
					{
						final String[] split = entry.split("&");
						final int item = Integer.parseInt(split[0]);
						final int data = Integer.parseInt(split[1]);
						if (item <= 0)
						{
							plugin.getLogger()
									.warning(
											Karmiconomy.TAG
													+ " Zero or negative item id for entry: "
													+ entry);
						}
						else
						{
							if (valueFile.isConfigurationSection(entry))
							{
								if (item != 373)
								{
									values.put(
											new Item(item, Byte.parseByte(""
													+ data), (short) data),
											parseInfo(valueFile, entry));
								}
								else
								{
									values.put(
											new Item(item, Byte
													.parseByte("" + 0),
													(short) data),
											parseInfo(valueFile, entry));
								}
							}
							else
							{
								plugin.getLogger().warning(
										"No section for " + entry);
							}
						}
					}
					catch (ArrayIndexOutOfBoundsException a)
					{
						plugin.getLogger()
								.warning(
										"Wrong format for "
												+ entry
												+ ". Must follow '<itemid>&<datavalue>:' entry.");
					}
					catch (NumberFormatException exa)
					{
						plugin.getLogger().warning(
								"Non-integer number for " + entry);
					}
				}
				else
				{
					plugin.getLogger().warning("Invalid entry for " + entry);
				}
			}
		}
		plugin.getLogger().info("Loaded custom values");
	}

	public Map<Item, KCItemInfo> getItemValueMap()
	{
		return values;
	}

	private KCItemInfo parseInfo(YamlConfiguration config, String path)
	{
		final double iCraftPay = config.getDouble(path + ".craftPay",
				this.getPayValue(Field.ITEM_CRAFT, null, null));
		final double iEnchantPay = config.getDouble(path + ".enchantPay",
				this.getPayValue(Field.ITEM_ENCHANT, null, null));
		final double iPlacePay = config.getDouble(path + ".placePay",
				this.getPayValue(Field.BLOCK_PLACE, null, null));
		final double iDestroyPay = config.getDouble(path + ".destroyPay",
				this.getPayValue(Field.BLOCK_DESTROY, null, null));
		final double iDropPay = config.getDouble(path + ".dropPay",
				this.getPayValue(Field.ITEM_DROP, null, null));
		final int iCraftLimit = config.getInt(path + ".craftLimit",
				this.getLimitValue(Field.ITEM_CRAFT, null, null));
		final int iEnchantLimit = config.getInt(path + ".enchantLimit",
				this.getLimitValue(Field.ITEM_ENCHANT, null, null));
		final int iPlaceLimit = config.getInt(path + ".placeLimit",
				this.getLimitValue(Field.BLOCK_PLACE, null, null));
		final int iDestroyLimit = config.getInt(path + ".destroyLimit",
				this.getLimitValue(Field.BLOCK_DESTROY, null, null));
		final int iDropLimit = config.getInt(path + ".dropLimit",
				this.getLimitValue(Field.ITEM_DROP, null, null));
		final boolean iCraftDenyPay = config.getBoolean(
				path + ".craftDenyOnPay",
				plugin.getConfig().getBoolean(
						Field.ITEM_CRAFT.getConfigPath() + ".denyOnLackPay",
						false));
		final boolean iCraftDenyLimit = config.getBoolean(
				path + ".craftDenyOnLimit",
				plugin.getConfig().getBoolean(
						Field.ITEM_CRAFT.getConfigPath() + ".denyOnLimit",
						false));
		final boolean iEnchantDenyPay = config.getBoolean(
				path + ".enchantDenyOnPay",
				plugin.getConfig().getBoolean(
						Field.ITEM_ENCHANT.getConfigPath() + ".denyOnLackPay",
						false));
		final boolean iEnchantDenyLimit = config.getBoolean(
				path + ".enchantDenyOnLimit",
				plugin.getConfig().getBoolean(
						Field.ITEM_ENCHANT.getConfigPath() + ".denyOnLimit",
						false));
		final boolean iPlaceDenyPay = config.getBoolean(
				path + ".placeDenyOnPay",
				plugin.getConfig().getBoolean(
						Field.BLOCK_PLACE.getConfigPath() + ".denyOnLackPay",
						false));
		final boolean iPlaceDenyLimit = config.getBoolean(
				path + ".placeDenyOnLimit",
				plugin.getConfig().getBoolean(
						Field.BLOCK_PLACE.getConfigPath() + ".denyOnLimit",
						false));
		final boolean iDestroyDenyPay = config.getBoolean(
				path + ".destroyDenyOnPay",
				plugin.getConfig().getBoolean(
						Field.BLOCK_DESTROY.getConfigPath() + ".denyOnLackPay",
						false));
		final boolean iDestroyDenyLimit = config.getBoolean(
				path + ".destroyDenyOnLimit",
				plugin.getConfig().getBoolean(
						Field.BLOCK_DESTROY.getConfigPath() + ".denyOnLimit",
						false));
		final boolean iDropDenyPay = config.getBoolean(
				path + ".dropDenyOnPay",
				plugin.getConfig().getBoolean(
						Field.ITEM_DROP.getConfigPath() + ".denyOnLackPay",
						false));
		final boolean iDropDenyLimit = config.getBoolean(
				path + ".dropDenyOnLimit",
				plugin.getConfig().getBoolean(
						Field.ITEM_PICKUP.getConfigPath() + ".denyOnLimit",
						false));
		final int iPickupLimit = config.getInt(path + ".pickupLimit",
				this.getLimitValue(Field.ITEM_PICKUP, null, null));
		final double iPickupPay = config.getDouble(path + ".pickupPay",
				this.getPayValue(Field.ITEM_PICKUP, null, null));
		final boolean iPickupDenyPay = config.getBoolean(
				path + ".pickupDenyOnPay",
				plugin.getConfig().getBoolean(
						Field.ITEM_PICKUP.getConfigPath() + ".denyOnLackPay",
						false));
		final boolean iPickupDenyLimit = config.getBoolean(
				path + ".pickupDenyOnLimit",
				plugin.getConfig().getBoolean(
						Field.ITEM_PICKUP.getConfigPath() + ".denyOnLimit",
						false));
		KCItemInfo info = new KCItemInfo(iCraftLimit, iCraftPay, iCraftDenyPay,
				iCraftDenyLimit, iEnchantLimit, iEnchantPay, iEnchantDenyPay,
				iEnchantDenyLimit, iPlaceLimit, iPlacePay, iPlaceDenyPay,
				iPlaceDenyLimit, iDestroyLimit, iDestroyPay, iDestroyDenyPay,
				iDestroyDenyLimit, iDropLimit, iDropPay, iDropDenyPay,
				iDropDenyLimit, iPickupLimit, iPickupPay, iPickupDenyPay,
				iPickupDenyLimit);
		return info;
	}

	// TODO command value file

	/**
	 * Loads the value file. Contains default values If the value file isn't
	 * there, or if its empty, then load defaults.
	 * 
	 * @return YamlConfiguration file
	 */
	private YamlConfiguration itemValuesFile()
	{
		final File file = new File(plugin.getDataFolder().getAbsolutePath()
				+ "/values.yml");
		// TODO rename
		final YamlConfiguration valueFile = YamlConfiguration
				.loadConfiguration(file);
		if (valueFile.getKeys(false).isEmpty())
		{
			// Defaults
			valueFile.set("14.dropPay", 0.0);
			valueFile.set("14.dropDenyOnPay", false);
			valueFile.set("14.dropDenyOnLimit", false);
			valueFile.set("14.dropLimit", 0);
			valueFile.set("14.placePay", 0.0);
			valueFile.set("14.placeDenyOnPay", false);
			valueFile.set("14.placeDenyOnLimit", false);
			valueFile.set("14.placeLimit", 0);
			valueFile.set("14.pickupPay", 0.0);
			valueFile.set("14.pickupDenyOnPay", false);
			valueFile.set("14.pickupDenyOnLimit", false);
			valueFile.set("14.pickupLimit", 0);
			valueFile.set("35&7.destroyPay", 5.0);
			valueFile.set("35&7.destroyDenyOnPay", false);
			valueFile.set("35&7.destroyDenyOnLimit", false);
			valueFile.set("35&7.destroyLimit", 0);
			valueFile.set("290.craftPay", 0);
			valueFile.set("290.craftDenyOnPay", false);
			valueFile.set("290.craftDenyOnLimit", false);
			valueFile.set("290.craftLimit", 0);
			valueFile.set("290.enchantPay", 0.0);
			valueFile.set("290.enchantDenyOnPay", false);
			valueFile.set("290.enchantDenyOnLimit", false);
			valueFile.set("290.enchantLimit", 0);
			// Insert defaults into file if they're not present
			try
			{
				// Save the file
				valueFile.save(file);
			}
			catch (IOException e1)
			{
				plugin.getLogger().warning(
						"File I/O Exception on saving karma list");
				e1.printStackTrace();
			}
		}
		return valueFile;
	}

	// Private class to hold item specific information
	public class KCItemInfo
	{
		public double craftPay, enchantPay, placePay, destroyPay, dropPay,
				pickupPay;
		public int craftLimit, enchantLimit, placeLimit, destroyLimit,
				dropLimit, pickupLimit;
		public boolean craftDenyPay, craftDenyLimit, enchantDenyPay,
				enchantDenyLimit, destroyDenyPay, destroyDenyLimit,
				placeDenyPay, placeDenyLimit, dropDenyPay, dropDenyLimit,
				pickupDenyPay, pickupDenyLimit;

		public KCItemInfo(int craftLimit, double craftPay,
				boolean craftDenyPay, boolean craftDenyLimit, int enchantLimit,
				double enchantPay, boolean enchantDenyPay,
				boolean enchantDenyLimit, int placeLimit, double placePay,
				boolean placeDenyPay, boolean placeDenyLimit, int destroyLimit,
				double destroyPay, boolean destroyDenyPay,
				boolean destroyDenyLimit, int dropLimit, double dropPay,
				boolean dropDenyPay, boolean dropDenyLimit, int pickupLimit,
				double pickupPay, boolean pickupDenyPay, boolean pickupDenyLimit)
		{
			this.craftPay = craftPay;
			this.enchantPay = enchantPay;
			this.placePay = placePay;
			this.destroyPay = destroyPay;
			this.dropPay = dropPay;
			this.craftLimit = craftLimit;
			this.enchantLimit = enchantLimit;
			this.placeLimit = placeLimit;
			this.destroyLimit = destroyLimit;
			this.dropLimit = dropLimit;
			this.craftDenyPay = craftDenyPay;
			this.craftDenyLimit = craftDenyLimit;
			this.enchantDenyPay = enchantDenyPay;
			this.enchantDenyLimit = enchantDenyLimit;
			this.destroyDenyPay = destroyDenyPay;
			this.destroyDenyLimit = destroyDenyLimit;
			this.placeDenyPay = placeDenyPay;
			this.placeDenyLimit = placeDenyLimit;
			this.dropDenyPay = dropDenyPay;
			this.dropDenyLimit = dropDenyLimit;
			this.pickupLimit = pickupLimit;
			this.pickupPay = pickupPay;
			this.pickupDenyLimit = pickupDenyLimit;
			this.pickupDenyPay = pickupDenyPay;
		}
	}

	@Override
	public boolean isEnabled(Field field)
	{
		return plugin.getConfig().getBoolean(
				field.getConfigPath() + ".enabled", false);
	}
}
