/**
 * Config file mimicking DiddiZ's Config class file in LB. Tailored for this
 * plugin.
 * 
 * @author Mitsugaru
 */
package com.mitsugaru.Karmiconomy;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config
{
	// Class variables
	private Karmiconomy plugin;
	public String host, port, database, user, password, tablePrefix;
	public boolean debugTime, debugEvents, useMySQL, importSQL, chat,
			blockPlace, blockDestroy, blockIgnite, craftItem, enchantItem,
			portalCreate, portalEnter, shootBow, tame, paintingPlace, bedEnter,
			bedLeave, bucketEmpty, bucketFill, worldChange, death, respawn,
			itemDrop, eggThrow, gameMode, kick, join, quit, sneak, sprint,
			vehicleEnter, vehicleExit;
	public int listlimit, bedEnterLimit, bedLeaveLimit, blockDestroyLimit,
			blockIgniteLimit, blockPlaceLimit, shootBowLimit, bucketEmptyLimit,
			bucketFillLimit, craftLimit, enchantLimit, itemDropLimit,
			eggThrowLimmit, chatLimit, deathLimit, gameModeLimit, kickLimit,
			joinLimit, quitLimit, respawnLimit, sneakLimit, sprintLimit,
			vehicleEnterLimit, vehicleExitLimit, paintingPlaceLimit;
	public double bedEnterPay, bedLeavePay, blockDestroyPay, blockIgnitePay,
			blockPlacePay, shootBowPay, bucketEmptyPay, bucketFillPay,
			craftPay, enchantPay, itemDropPay, eggThrowPay, chatPay, deathPay,
			gameModePay, kickPay, joinPay, quitPay, respawnPay, sneakPay,
			sprintPay, vehicleEnterPay, vehicleExitPay, paintingPlacePay,
			tamePay, protalCreatePay, protalEnterPay;
	private final Map<Item, KCItemInfo> values = new HashMap<Item, KCItemInfo>();

	// TODO ability to change config in-game

	// IDEA Ability to change the colors for all parameters
	// such as item name, amount, data value, id value, enchantment name,
	// enchantment lvl, page numbers, maybe even header titles
	/**
	 * Constructor and initializer
	 * 
	 * @param KarmicShare
	 *            plugin
	 */
	public Config(Karmiconomy plugin)
	{
		this.plugin = plugin;
		// Grab config
		final ConfigurationSection config = plugin.getConfig();
		// Hashmap of defaults
		final Map<String, Object> defaults = new HashMap<String, Object>();
		defaults.put("listlimit", 10);
		defaults.put("bed.enter.enabled", false);
		defaults.put("bed.enter.limit", 10);
		defaults.put("bed.enter.pay", 0.1);
		defaults.put("bed.leave.enabled", false);
		defaults.put("bed.leave.limit", 10);
		defaults.put("bed.leave.pay", 0.1);
		defaults.put("block.destroy.enabled", false);
		defaults.put("block.destroy.static", true);
		defaults.put("block.destroy.limit", 100);
		defaults.put("block.destroy.pay", 0.1);
		defaults.put("block.ignite.enabled", false);
		defaults.put("block.ignite.static", true);
		defaults.put("block.ignite.limit", 100);
		defaults.put("block.ignite.pay", 0.1);
		defaults.put("block.place.enabled", false);
		defaults.put("block.place.static", true);
		defaults.put("block.place.limit", 100);
		defaults.put("block.place.pay", 0.1);
		defaults.put("bow.shoot.enabled", false);
		defaults.put("bow.shoot.forcefactor", 1.0);
		defaults.put("bow.shoot.limit", 100);
		defaults.put("bow.shoot.pay", 0.1);
		defaults.put("bucket.empty.enabled", false);
		defaults.put("bucket.empty.static", true);
		defaults.put("bucket.empty.limit", 100);
		defaults.put("bucket.empty.pay", 0.1);
		defaults.put("bucket.fill.enabled", false);
		defaults.put("bucket.fill.static", true);
		defaults.put("bucket.fill.limit", 100);
		defaults.put("bucket.fill.pay", 0.1);
		defaults.put("item.craft.enabled", false);
		defaults.put("item.craft.static", true);
		defaults.put("item.craft.limit", 100);
		defaults.put("item.craft.pay", 0.1);
		defaults.put("item.enchant.enabled", false);
		defaults.put("item.enchant.static", true);
		defaults.put("item.enchant.limit", 100);
		defaults.put("item.enchant.pay", 0.1);
		defaults.put("item.drop.enabled", false);
		defaults.put("item.drop.static", true);
		defaults.put("item.drop.limit", 100);
		defaults.put("item.drop.pay", 0.1);
		defaults.put("item.egg.enabled", false);
		defaults.put("item.egg.limit", 100);
		defaults.put("item.egg.pay", 0.1);
		defaults.put("player.chat.enabled", false);
		defaults.put("player.chat.limit", 10);
		defaults.put("player.chat.pay", 0.1);
		defaults.put("player.death.enabled", false);
		defaults.put("player.death.limit", 100);
		defaults.put("player.death.pay", -1);
		defaults.put("player.join.enabled", false);
		defaults.put("player.join.limit", 1);
		defaults.put("player.join.pay", 10);
		defaults.put("player.kick.enabled", false);
		defaults.put("player.kick.limit", 10);
		defaults.put("player.kick.pay", 10);
		defaults.put("player.quit.enabled", false);
		defaults.put("player.quit.limit", 1);
		defaults.put("player.quit.pay", 0.1);
		defaults.put("player.respawn.enabled", false);
		defaults.put("player.respawn.limit", 100);
		defaults.put("player.respawn.pay", -0.1);
		defaults.put("player.sneak.enabled", false);
		defaults.put("player.sneak.limit", 10);
		defaults.put("player.sneak.pay", 0.1);
		defaults.put("player.sprint.enabled", false);
		defaults.put("player.sprint.limit", 10);
		defaults.put("player.sprint.pay", 0.1);
		defaults.put("portal.create.enabled", false);
		defaults.put("portal.create.limit", 10);
		defaults.put("portal.create.pay.nether", 0.1);
		defaults.put("portal.create.pay.ender", 0.1);
		defaults.put("portal.create.pay.custom", 0.1);
		defaults.put("portal.enter.enabled", false);
		defaults.put("portal.enter.limit", 10);
		defaults.put("portal.enter.pay.nether", 0.1);
		defaults.put("portal.enter.pay.ender", 0.1);
		defaults.put("portal.enter.pay.custom", 0.1);
		defaults.put("vehicle.enter.enabled", false);
		defaults.put("vehicle.enter.limit", 100);
		defaults.put("vehicle.enter.pay", 0.1);
		defaults.put("vehicle.exit.enabled", false);
		defaults.put("vehicle.exit.limit", 100);
		defaults.put("vehicle.exit.pay", 0.1);
		defaults.put("world.change.enabled", false);
		defaults.put("world.change.limit", 15);
		defaults.put("world.change.pay", 1.0);
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
		tablePrefix = config.getString("mysql.prefix", "kj_");
		importSQL = config.getBoolean("mysql.import", false);
		/**
		 * General
		 */
		listlimit = config.getInt("listlimit", 10);
		debugTime = config.getBoolean("debug.time", false);
		debugEvents = config.getBoolean("debug.events", false);
		/**
		 * Events
		 */
		bedEnter = config.getBoolean("bed.enter.enabled", false);
		defaults.put("bed.enter.limit", 10);
		defaults.put("bed.enter.pay", 0.1);
		bedLeave = config.getBoolean("bed.leave.enabled", false);
		defaults.put("bed.leave.limit", 10);
		defaults.put("bed.leave.pay", 0.1);
		blockDestroy = config.getBoolean("block.destroy.enabled", false);
		defaults.put("block.destroy.static", true);
		defaults.put("block.destroy.limit", 100);
		defaults.put("block.destroy.pay", 0.1);
		blockIgnite = config.getBoolean("block.ignite.enabled", false);
		defaults.put("block.ignite.static", true);
		defaults.put("block.ignite.limit", 100);
		defaults.put("block.ignite.pay", 0.1);
		blockPlace = config.getBoolean("block.place.enabled", false);
		defaults.put("block.place.static", true);
		defaults.put("block.place.limit", 100);
		defaults.put("block.place.pay", 0.1);
		shootBow = config.getBoolean("bow.shoot.enabled", false);
		defaults.put("bow.shoot.forcefactor", 1.0);
		defaults.put("bow.shoot.limit", 100);
		defaults.put("bow.shoot.pay", 0.1);
		bucketEmpty = config.getBoolean("bucket.empty.enabled", false);
		defaults.put("bucket.empty.static", true);
		defaults.put("bucket.empty.limit", 100);
		defaults.put("bucket.empty.pay", 0.1);
		bucketFill = config.getBoolean("bucket.fill.enabled", false);
		defaults.put("bucket.fill.static", true);
		defaults.put("bucket.fill.limit", 100);
		defaults.put("bucket.fill.pay", 0.1);
		craftItem = config.getBoolean("item.craft.enabled", false);
		defaults.put("item.craft.static", true);
		defaults.put("item.craft.limit", 100);
		defaults.put("item.craft.pay", 0.1);
		enchantItem = config.getBoolean("item.enchant.enabled", false);
		defaults.put("item.enchant.static", true);
		defaults.put("item.enchant.limit", 100);
		defaults.put("item.enchant.pay", 0.1);
		itemDrop = config.getBoolean("item.drop.enabled", false);
		defaults.put("item.drop.static", true);
		defaults.put("item.drop.limit", 100);
		defaults.put("item.drop.pay", 0.1);
		eggThrow = config.getBoolean("item.egg.enabled", false);
		defaults.put("item.egg.limit", 100);
		defaults.put("item.egg.pay", 0.1);
		chat = config.getBoolean("player.chat.enabled", false);
		chatLimit = config.getInt("player.chat.limit", 10);
		chatPay = config.getDouble("player.chat.pay", 0.1);
		death = config.getBoolean("player.death.enabled", false);
		defaults.put("player.death.limit", 100);
		defaults.put("player.death.pay", -1);
		join = config.getBoolean("player.join.enabled", false);
		defaults.put("player.join.limit", 1);
		defaults.put("player.join.pay", 10);
		kick = config.getBoolean("player.kick.enabled", false);
		defaults.put("player.kick.limit", 10);
		defaults.put("player.kick.pay", 10);
		quit = config.getBoolean("player.quit.enabled", false);
		defaults.put("player.quit.limit", 1);
		defaults.put("player.quit.pay", 0.1);
		respawn = config.getBoolean("player.respawn.enabled", false);
		defaults.put("player.respawn.limit", 100);
		defaults.put("player.respawn.pay", -0.1);
		sneak = config.getBoolean("player.sneak.enabled", false);
		defaults.put("player.sneak.limit", 10);
		defaults.put("player.sneak.pay", 0.1);
		sprint = config.getBoolean("player.sprint.enabled", false);
		defaults.put("player.sprint.limit", 10);
		defaults.put("player.sprint.pay", 0.1);
		portalCreate = config.getBoolean("portal.create.enabled", false);
		defaults.put("portal.create.limit", 10);
		defaults.put("portal.create.pay.nether", 0.1);
		defaults.put("portal.create.pay.ender", 0.1);
		defaults.put("portal.create.pay.custom", 0.1);
		portalEnter = config.getBoolean("portal.enter.enabled", false);
		defaults.put("portal.enter.limit", 10);
		defaults.put("portal.enter.pay.nether", 0.1);
		defaults.put("portal.enter.pay.ender", 0.1);
		defaults.put("portal.enter.pay.custom", 0.1);
		vehicleEnter = config.getBoolean("vehicle.enter.enabled", false);
		defaults.put("vehicle.enter.limit", 100);
		defaults.put("vehicle.enter.pay", 0.1);
		vehicleExit = config.getBoolean("vehicle.exit.enabled", false);
		defaults.put("vehicle.exit.limit", 100);
		defaults.put("vehicle.exit.pay", 0.1);
		defaults.put("world.change.enabled", false);
		defaults.put("world.change.limit", 15);
		defaults.put("world.change.pay", 1.0);
		// Load config for item specific value
		this.loadValueMap();
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
		ConfigurationSection config = plugin.getConfig();
		chat = config.getBoolean("player.chat.enabled", false);
		chatLimit = config.getInt("player.chat.limit", 10);
		listlimit = config.getInt("listlimit", 10);
		debugTime = config.getBoolean("debug.time", false);
		debugEvents = config.getBoolean("debug.events", false);
		// Load config for item specific values
		this.loadValueMap();
		// Check bounds
		this.boundsCheck();
		plugin.getLogger().info("Config reloaded");
	}

	/**
	 * Check the bounds on the parameters to make sure that all config variables
	 * are legal and usable by the plugin
	 */
	private void boundsCheck()
	{
		// necessary?
	}

	/**
	 * Loads the per-item karma values into a hashmap for later usage
	 */
	private void loadValueMap()
	{
		// Load karma file
		final YamlConfiguration valueFile = this.valuesFile();
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

	public Map<Item, KCItemInfo> getValueMap()
	{
		return values;
	}

	private KCItemInfo parseInfo(YamlConfiguration config, String path)
	{
		final double craftPay = config.getDouble(path + ".craftPay", 0.0);
		final double enchantPay = config.getDouble(path + ".enchantPay", 0.0);
		final double placePay = config.getDouble(path + ".placePay", 0.0);
		final double ignitePay = config.getDouble(path + ".ignitePay", 0.0);
		final double destroyPay = config.getDouble(path + ".destroyPay", 0.0);
		final double dropPay = config.getDouble(path + ".dropPay", 0.0);
		final int craftLimit = config.getInt(path + ".craftLimit", 0);
		final int enchantLimit = config.getInt(path + ".enchantLimit", 0);
		final int placeLimit = config.getInt(path + ".placeLimit", 0);
		final int igniteLimit = config.getInt(path + ".igniteLimit", 0);
		final int destroyLimit = config.getInt(path + ".destroyLimit", 0);
		final int dropLimit = config.getInt(path + ".dropLimit", 0);
		KCItemInfo info = new KCItemInfo(craftLimit, craftPay, enchantLimit,
				enchantPay, placeLimit, placePay, igniteLimit, ignitePay,
				destroyLimit, destroyPay, dropLimit, dropPay);
		return info;
	}

	/**
	 * Loads the value file. Contains default values If the value file isn't
	 * there, or if its empty, then load defaults.
	 * 
	 * @return YamlConfiguration file
	 */
	// TODO set this to custom values file? for per use item or whatever
	private YamlConfiguration valuesFile()
	{
		final File file = new File(plugin.getDataFolder().getAbsolutePath()
				+ "/values.yml");
		// TODO rename
		final YamlConfiguration valueFile = YamlConfiguration
				.loadConfiguration(file);
		if (valueFile.getKeys(false).isEmpty())
		{
			// TODO all-inclusive defaults
			// Defaults
			valueFile.set("14", 5);
			valueFile.set("15", 2);
			valueFile.set("17&0", 2);
			valueFile.set("17&1", 2);
			valueFile.set("17&2", 2);
			valueFile.set("19", 10);
			valueFile.set("20", 3);
			valueFile.set("22", 36);
			valueFile.set("24", 2);
			valueFile.set("35&0", 2);
			valueFile.set("35&1", 2);
			valueFile.set("35&2", 2);
			valueFile.set("35&3", 2);
			valueFile.set("35&4", 2);
			valueFile.set("35&5", 2);
			valueFile.set("35&6", 2);
			valueFile.set("35&7", 2);
			valueFile.set("35&8", 2);
			valueFile.set("35&9", 2);
			valueFile.set("35&10", 2);
			valueFile.set("35&11", 2);
			valueFile.set("35&12", 2);
			valueFile.set("35&13", 2);
			valueFile.set("35&14", 2);
			valueFile.set("35&15", 2);
			valueFile.set("41", 54);
			valueFile.set("45", 6);
			valueFile.set("47", 6);
			valueFile.set("49", 6);
			valueFile.set("57", 225);
			valueFile.set("89", 4);
			valueFile.set("102", 12);
			valueFile.set("264", 25);
			valueFile.set("265", 3);
			valueFile.set("266", 6);
			valueFile.set("322", 10);
			valueFile.set("331", 2);
			valueFile.set("351&4", 4);
			// Insert defaults into file if they're not present
			try
			{
				// Save the file
				valueFile.save(file);
			}
			catch (IOException e1)
			{
				// INFO Auto-generated catch block
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
		public double craftPay, enchantPay, placePay, ignitePay, destroyPay,
				dropPay;
		public int craftLimit, enchantLimit, placeLimit, igniteLimit,
				destroyLimit, dropLimit;

		public KCItemInfo(int craftLimit, double craftPay, int enchantLimit,
				double enchantPay, int placeLimit, double placePay,
				int igniteLimit, double ignitePay, int destroyLimit,
				double destroyPay, int dropLimit, double dropPay)
		{
			this.craftPay = craftPay;
			this.enchantPay = enchantPay;
			this.placePay = placePay;
			this.ignitePay = ignitePay;
			this.destroyPay = destroyPay;
			this.dropPay = dropPay;
			this.craftLimit = craftLimit;
			this.enchantLimit = enchantLimit;
			this.placeLimit = placeLimit;
			this.igniteLimit = igniteLimit;
			this.destroyLimit = destroyLimit;
			this.dropLimit = dropLimit;
		}
	}
}
