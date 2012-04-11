/**
 * Config file mimicking DiddiZ's Config class file in LB. Tailored for this
 * plugin.
 * 
 * @author Mitsugaru
 */
package com.mitsugaru.Karmiconomy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config
{
	// Class variables
	private Karmiconomy plugin;
	public boolean debugTime, debugEvents, chat, blockPlace, blockDestroy, blockIgnite,
			craftItem, enchantItem, portalCreate, portalEnter, shootBow, tame,
			paintingPlace, bedEnter, bedLeave, bucketEmpty, bucketFill,
			worldChange, death, itemDrop, eggThrow, gameMode, kick, join, quit,
			sneak, sprint, vehicleEnter, vehicleExit;
	public int listlimit, chatLimit;
	public double chatPay;
	public final Map<Item, Integer> karma = new HashMap<Item, Integer>();

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
		chat = config.getBoolean("player.chat.enabled", false);
		chatLimit = config.getInt("player.chat.limit", 10);
		chatPay = config.getDouble("player.chat.pay", 0.1);
		listlimit = config.getInt("listlimit", 10);
		debugTime = config.getBoolean("debug.time", false);
		debugEvents = config.getBoolean("debug.events", false);
		// Load config for item specific karma if not using static karma
		this.loadKarmaMap();

		// Finally, do a bounds check on parameters to make sure they are legal
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
		if (chatLimit <= 0)
		{

		}
	}

	/**
	 * Loads the per-item karma values into a hashmap for later usage
	 */
	private void loadKarmaMap()
	{
		// Load karma file
		final YamlConfiguration karmaFile = this.karmaFile();
		// Load custom karma file into map
		for (final String entry : karmaFile.getKeys(false))
		{
			try
			{
				// Attempt to parse the nodes
				int key = Integer.parseInt(entry);
				// If it has child nodes, parse those as well
				if (karmaFile.isConfigurationSection(entry))
				{
					ConfigurationSection sec = karmaFile
							.getConfigurationSection(entry);
					for (final String dataValue : sec.getKeys(false))
					{
						int secondKey = Integer.parseInt(dataValue);
						int secondValue = sec.getInt(dataValue);
						if (key != 373)
						{
							// TODO use vault's version of items
							karma.put(
									new Item(key, Byte
											.parseByte("" + secondKey),
											(short) secondKey), secondValue);
						}
						else
						{
							karma.put(new Item(key, Byte.parseByte("" + 0),
									(short) secondKey), secondValue);
						}
					}
				}
				else
				{
					int value = karmaFile.getInt(entry);
					karma.put(new Item(key, Byte.valueOf("" + 0), (short) 0),
							value);
				}
			}
			catch (final NumberFormatException ex)
			{
				plugin.getLogger().warning("Non-integer value in karma list");
				ex.printStackTrace();
			}
		}
		plugin.getLogger().info("Loaded custom karma values");
	}

	/**
	 * Loads the karma file. Contains default values If the karma file isn't
	 * there, or if its empty, then load defaults.
	 * 
	 * @return YamlConfiguration file
	 */
	// TODO set this to custom values file? for per use item or whatever
	private YamlConfiguration karmaFile()
	{
		final File file = new File(plugin.getDataFolder().getAbsolutePath()
				+ "/karma.yml");
		// TODO rename
		final YamlConfiguration karmaFile = YamlConfiguration
				.loadConfiguration(file);
		if (karmaFile.getKeys(false).isEmpty())
		{
			// TODO all-inclusive defaults
			// Defaults
			karmaFile.set("14", 5);
			karmaFile.set("15", 2);
			karmaFile.set("17.0", 2);
			karmaFile.set("17.1", 2);
			karmaFile.set("17.2", 2);
			karmaFile.set("19", 10);
			karmaFile.set("20", 3);
			karmaFile.set("22", 36);
			karmaFile.set("24", 2);
			karmaFile.set("35.0", 2);
			karmaFile.set("35.1", 2);
			karmaFile.set("35.2", 2);
			karmaFile.set("35.3", 2);
			karmaFile.set("35.4", 2);
			karmaFile.set("35.5", 2);
			karmaFile.set("35.6", 2);
			karmaFile.set("35.7", 2);
			karmaFile.set("35.8", 2);
			karmaFile.set("35.9", 2);
			karmaFile.set("35.10", 2);
			karmaFile.set("35.11", 2);
			karmaFile.set("35.12", 2);
			karmaFile.set("35.13", 2);
			karmaFile.set("35.14", 2);
			karmaFile.set("35.15", 2);
			karmaFile.set("41", 54);
			karmaFile.set("45", 6);
			karmaFile.set("47", 6);
			karmaFile.set("49", 6);
			karmaFile.set("57", 225);
			karmaFile.set("89", 4);
			karmaFile.set("102", 12);
			karmaFile.set("264", 25);
			karmaFile.set("265", 3);
			karmaFile.set("266", 6);
			karmaFile.set("322", 10);
			karmaFile.set("331", 2);
			karmaFile.set("351.4", 4);
			// Insert defaults into config file if they're not present
			try
			{
				// Save the file
				karmaFile.save(file);
			}
			catch (IOException e1)
			{
				// INFO Auto-generated catch block
				plugin.getLogger().warning(
						"File I/O Exception on saving karma list");
				e1.printStackTrace();
			}
		}
		return karmaFile;
	}
	
	//TODO private class to hold specific information
}
