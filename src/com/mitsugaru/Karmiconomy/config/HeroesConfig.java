package com.mitsugaru.Karmiconomy.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.database.Field;

public class HeroesConfig
{
	private static Karmiconomy plugin;
	private static File file;
	private static YamlConfiguration config;
	public static boolean classChange, classChangeDenyPay,
			classChangeDenyLimit, expChange, expChangeDenyPay,
			expChangeDenyLimit, changeLevel, combatEnter, combatLeave,
			partyJoin, partyJoinDenyPay, partyJoinDenyLimit, partyLeave,
			partyLeaveDenyPay, partyLeaveDenyLimit, killAttackPlayer,
			killAttackMob, killDefendPlayer, killDefendMob, regainHealth,
			regainHealthDenyPay, regainHealthDenyLimit, regainMana,
			regainManaDenyPay, regainManaDenyLimit, skillComplete, skillUse,
			skillUseDenyPay, skillUseDenyLimit;
	public static double classChangePay, expChangePay, changeLevelPay,
			combatEnterPay, combatLeavePay, partyJoinPay, partyLeavePay,
			killAttackPlayerPay, killAttackMobPay, killDefendPlayerPay,
			killDefendMobPay, regainHealthPay, regainManaPay, skillCompletePay,
			skillUsePay;
	public static int classChangeLimit, expChangeLimit, changeLevelLimit,
			combatEnterLimit, combatLeaveLimit, partyJoinLimit,
			partyLeaveLimit, killAttackPlayerLimit, killAttackMobLimit,
			killDefendPlayerLimit, killDefendMobLimit, regainHealthLimit,
			regainManaLimit, skillCompleteLimit, skillUseLimit;

	public HeroesConfig(Karmiconomy kcon)
	{
		plugin = kcon;
		// Grab file
		file = new File(plugin.getDataFolder().getAbsolutePath()
				+ "/heroes.yml");
		config = YamlConfiguration.loadConfiguration(file);
		loadDefaults();
		loadVariables();
	}

	public static void save()
	{
		// Set config
		try
		{
			// Save the file
			config.save(file);
		}
		catch (IOException e1)
		{
			plugin.getLogger().warning(
					"File I/O Exception on saving heroes config");
			e1.printStackTrace();
		}
	}

	public static void reload()
	{
		// Not sure if this is necessary...?
		try
		{
			config.load(file);
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvalidConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadDefaults();
		loadVariables();
	}

	private static void loadDefaults()
	{
		// LinkedHashmap of defaults
		final Map<String, Object> defaults = new LinkedHashMap<String, Object>();
		// TODO defaults for all events
		for (final Entry<String, Object> e : defaults.entrySet())
		{
			if (!config.contains(e.getKey()))
			{
				config.set(e.getKey(), e.getValue());
			}
		}
		save();
	}

	private static void loadVariables()
	{
		// TODO load variables
	}

	public int getLimitValue(Field field, Item item, String command)
	{
		int limit = -1;
		switch (field)
		{
			default:
			{
				if (plugin.getPluginConfig().debugUnhandled)
				{
					plugin.getLogger().warning(
							"Unhandled limit for " + field.name());
				}
				break;
			}
		}
		return limit;
	}

	public double getPayValue(Field field, Item item, String command)
	{
		double pay = 0.0;
		switch (field)
		{
			default:
			{
				if (plugin.getPluginConfig().debugUnhandled)
				{
					plugin.getLogger().warning(
							"Unhandled pay for " + field.name());
				}
				break;
			}
		}
		return pay;
	}
}
