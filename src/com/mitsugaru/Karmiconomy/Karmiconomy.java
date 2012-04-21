package com.mitsugaru.Karmiconomy;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.mitsugaru.Karmiconomy.events.KarmiconomyListener;
import com.mitsugaru.Karmiconomy.events.KconEventLogic;
import com.mitsugaru.Karmiconomy.events.McMMOListener;

public class Karmiconomy extends JavaPlugin
{
	public static final String TAG = "[Karmiconomy]";
	private Commander commander;
	private Config config;
	private PermCheck perm;
	private DatabaseHandler database;
	private boolean economyFound;
	public boolean mcmmo;
	public static Map<String, String> sentMessages = new HashMap<String, String>();

	/**
	 * Method that is called when plugin is disabled
	 */
	@Override
	public void onDisable()
	{
		// Save config
		this.reloadConfig();
		this.saveConfig();
		// Disconnect from sql database
		if (database.checkConnection())
		{
			// Close connection
			database.close();
		}
		if (economyFound)
		{
			// Finish up anything economy related?
		}
		// getLogger().info("Plugin disabled");
	}

	/**
	 * Method that is called when plugin is enabled
	 */
	@Override
	public void onEnable()
	{
		// Config
		config = new Config(this);
		// Grab database
		database = new DatabaseHandler(this, config);
		// Check update
		config.checkUpdate();
		// Setup economy
		KarmicEcon ke = new KarmicEcon(this);
		economyFound = ke.setupEconomy();
		if (!economyFound)
		{
			// No economy system found, disable
			getLogger().warning(Karmiconomy.TAG + " No economy found!");
			getServer().getPluginManager().disablePlugin(this);
		}

		// Set permission handler
		perm = new PermCheck(this);

		// Setup commander
		commander = new Commander(this);
		getCommand("kcon").setExecutor(commander);
		// Setup listeners
		KconEventLogic.init(this);
		KarmiconomyListener listener = new KarmiconomyListener(this);
		this.getServer().getPluginManager().registerEvents(listener, this);
		Plugin mcmmoPlugin = getServer().getPluginManager()
				.getPlugin("mcMMO");
		if(mcmmoPlugin != null)
		{
			McMMOListener mcmmoListener = new McMMOListener(this);
			this.getServer().getPluginManager().registerEvents(mcmmoListener, this);
			mcmmo = true;
			getLogger().info(TAG + " Hooked into mcMMO");
		}
		else
		{
			mcmmo = false;
		}
		// getLogger().info(TAG + " Enabled v" + getDescription().getVersion());
	}

	public Commander getCommander()
	{
		return commander;
	}

	public PermCheck getPermissionHandler()
	{
		return perm;
	}

	/**
	 * Returns Config object
	 * 
	 * @return Config object
	 */
	public Config getPluginConfig()
	{
		return config;
	}

	/**
	 * Returns database handler
	 * 
	 * @return Database handler
	 */
	public DatabaseHandler getDatabaseHandler()
	{
		return database;
	}
}
