/**
 * Config file mimicking DiddiZ's Config class
 * file in LB. Tailored for this plugin.
 *
 * @author Mitsugaru
 */
package com.mitsugaru.Karmiconomy;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	// Class variables
	private Karmiconomy plugin;
	public String host, port, database, user, password, tablePrefix;
	public boolean useMySQL, statickarma, effects, debugTime, karmaDisabled,
			chests, importSQL, economy, blacklist;
	public int upper, lower, listlimit, playerKarmaDefault, karmaChange;
	public double upperPercent, lowerPercent;
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
	public Config(Karmiconomy plugin) {
		this.plugin = plugin;
		// Grab config
		final ConfigurationSection config = plugin.getConfig();
		// Hashmap of defaults
		final Map<String, Object> defaults = new HashMap<String, Object>();
		defaults.put("version", plugin.getDescription().getVersion());
		defaults.put("listlimit", 10);
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
		listlimit = config.getInt("listlimit", 10);
		debugTime = config.getBoolean("debugTime", false);
		// Load config for item specific karma if not using static karma
			this.loadKarmaMap();
		
		// Finally, do a bounds check on parameters to make sure they are legal
	}

	public void set(String path, Object o) {
		final ConfigurationSection config = plugin.getConfig();
		config.set(path, o);
		plugin.saveConfig();
	}

	/**
	 * Check if updates are necessary
	 */
	public void checkUpdate() {
		// Check if need to update
		ConfigurationSection config = plugin.getConfig();
		if (Double.parseDouble(plugin.getDescription().getVersion()) > Double
				.parseDouble(config.getString("version")))
		{
			// Update to latest version
			plugin.getLogger().info("Updating to v"
							+ plugin.getDescription().getVersion());
			this.update();
		}
	}

	/**
	 * This method is called to make the appropriate changes, most likely only
	 * necessary for database schema modification, for a proper update.
	 */
	private void update() {
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
	public void reloadConfig() {
		// Initial relaod
		plugin.reloadConfig();
		// Grab config
		ConfigurationSection config = plugin.getConfig();
		listlimit = config.getInt("listlimit", 10);
		debugTime = config.getBoolean("debugTime", false);
		// Check bounds
		this.boundsCheck();
		plugin.getLogger().info("Config reloaded");
	}

	/**
	 * Check the bounds on the parameters to make sure that all config variables
	 * are legal and usable by the plugin
	 */
	private void boundsCheck() {
		
	}
	
	/**
	 * Loads the per-item karma values into a hashmap for later usage
	 */
	private void loadKarmaMap() {
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
							//TODO use vault's version of items
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
	//TODO set this to custom values file? for per use item or whatever
	private YamlConfiguration karmaFile() {
		final File file = new File(plugin.getDataFolder().getAbsolutePath()
				+ "/karma.yml");
		//TODO rename
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
				plugin.getLogger().warning("File I/O Exception on saving karma list");
				e1.printStackTrace();
			}
		}
		return karmaFile;
	}
}
