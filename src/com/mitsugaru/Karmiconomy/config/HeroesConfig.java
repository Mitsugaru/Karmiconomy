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
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (InvalidConfigurationException e)
		{
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
			case HEROES_CHANGE_LEVEL:
				return changeLevelLimit;
			case HEROES_CLASS_CHANGE:
				return classChangeLimit;
			case HEROES_COMBAT_ENTER:
				return combatEnterLimit;
			case HEROES_COMBAT_LEAVE:
				return combatLeaveLimit;
			case HEROES_EXP_CHANGE:
				return expChangeLimit;
			case HEROES_KILL_ATTACK_MOB:
				return killAttackMobLimit;
			case HEROES_KILL_ATTACK_PLAYER:
				return killAttackPlayerLimit;
			case HEROES_KILL_DEFEND_MOB:
				return killDefendMobLimit;
			case HEROES_KILL_DEFEND_PLAYER:
				return killDefendPlayerLimit;
			case HEROES_PARTY_JOIN:
				return partyJoinLimit;
			case HEROES_PARTY_LEAVE:
				return partyLeaveLimit;
			case HEROES_REGAIN_HEALTH:
				return regainHealthLimit;
			case HEROES_REGAIN_MANA:
				return regainManaLimit;
			case HEROES_SKILL_COMPLETE:
				return skillCompleteLimit;
			case HEROES_SKILL_USE:
				return skillUseLimit;
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
			case HEROES_CHANGE_LEVEL:
				return changeLevelPay;
			case HEROES_CLASS_CHANGE:
				return classChangePay;
			case HEROES_COMBAT_ENTER:
				return combatEnterPay;
			case HEROES_COMBAT_LEAVE:
				return combatLeavePay;
			case HEROES_EXP_CHANGE:
				return expChangePay;
			case HEROES_KILL_ATTACK_MOB:
				return killAttackMobPay;
			case HEROES_KILL_ATTACK_PLAYER:
				return killAttackPlayerPay;
			case HEROES_KILL_DEFEND_MOB:
				return killDefendMobPay;
			case HEROES_KILL_DEFEND_PLAYER:
				return killDefendPlayerPay;
			case HEROES_PARTY_JOIN:
				return partyJoinPay;
			case HEROES_PARTY_LEAVE:
				return partyLeavePay;
			case HEROES_REGAIN_HEALTH:
				return regainHealthPay;
			case HEROES_REGAIN_MANA:
				return regainManaPay;
			case HEROES_SKILL_COMPLETE:
				return skillCompletePay;
			case HEROES_SKILL_USE:
				return skillUsePay;
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
