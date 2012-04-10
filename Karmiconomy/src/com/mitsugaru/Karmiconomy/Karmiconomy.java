package com.mitsugaru.Karmiconomy;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Karmiconomy extends JavaPlugin
{
	public static final String TAG = "[Karmiconomy]";
	private Commander commander;
	private Config config;
	private PermCheck perm;
	private Economy eco;
	private boolean economyFound;

	/**
	 * Method that is called when plugin is disabled
	 */
	@Override
	public void onDisable()
	{
		// Save config
		this.reloadConfig();
		this.saveConfig();

		// Setup economy
		if (config.economy)
		{
			setupEconomy();
			if (!economyFound)
			{
				getLogger()
						.warning(
								"Economy not setup!");
				this.getServer().getPluginManager().disablePlugin(this);
			}
		}

		getLogger().info("Plugin disabled");
	}

	/**
	 * Method that is called when plugin is enabled
	 */
	@Override
	public void onEnable()
	{
		// Config
		config = new Config(this);
	}

	private void setupEconomy()
	{
		RegisteredServiceProvider<Economy> economyProvider = this.getServer()
				.getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null)
		{
			eco = economyProvider.getProvider();
			economyFound = true;
		}
		else
		{
			// No economy system found, disable
			getLogger().warning(TAG + " No economy found!");
			this.getServer().getPluginManager().disablePlugin(this);
			economyFound = false;
		}
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

	public Economy getEconomy()
	{
		return eco;
	}
}
