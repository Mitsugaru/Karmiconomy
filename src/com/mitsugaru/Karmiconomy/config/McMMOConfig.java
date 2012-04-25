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
import com.mitsugaru.Karmiconomy.database.DatabaseHandler;
import com.mitsugaru.Karmiconomy.database.Field;

public class McMMOConfig
{
	private static Karmiconomy plugin;
	private static File file;
	private static YamlConfiguration config;
	public static boolean partyTeleport, partyTeleportDenyPay,
			partyTeleportDenyLimit, partyJoin, partyJoinDenyPay,
			partyJoinDenyLimit, partyLeave, partyLeaveDenyPay,
			partyLeaveDenyLimit, partyKick, partyKickDenyPay,
			partyKickDenyLimit, partyChange, partyChangeDenyPay,
			partyChangeDenyLimit, acrobaticsLevel, acrobaticsXp, archeryLevel,
			archeryXp, axesLevel, axesXp, excavationLevel, excavationXp,
			fishingLevel, fishingXp, herbalismLevel, herbalismXp, miningLevel,
			miningXp, repairLevel, repairXp, swordsLevel, swordsXp,
			tamingLevel, tamingXp, unarmedLevel, unarmedXp, woodcuttingLevel,
			woodcuttingXp;
	public static double partyTeleportPay, partyJoinPay, partyLeavePay,
			partyKickPay, partyChangePay, acrobaticsLevelPay, acrobaticsXpPay, archeryLevelPay,
			archeryXpPay, axesLevelPay, axesXpPay, excavationLevelPay, excavationXpPay,
			fishingLevelPay, fishingXpPay, herbalismLevelPay, herbalismXpPay, miningLevelPay,
			miningXpPay, repairLevelPay, repairXpPay, swordsLevelPay, swordsXpPay,
			tamingLevelPay, tamingXpPay, unarmedLevelPay, unarmedXpPay, woodcuttingLevelPay,
			woodcuttingXpPay;
	public static int partyTeleportLimit, partyJoinLimit, partyLeaveLimit,
			partyKickLimit, partyChangeLimit, acrobaticsLevelLimit, acrobaticsXpLimit, archeryLevelLimit,
			archeryXpLimit, axesLevelLimit, axesXpLimit, excavationLevelLimit, excavationXpLimit,
			fishingLevelLimit, fishingXpLimit, herbalismLevelLimit, herbalismXpLimit, miningLevelLimit,
			miningXpLimit, repairLevelLimit, repairXpLimit, swordsLevelLimit, swordsXpLimit,
			tamingLevelLimit, tamingXpLimit, unarmedLevelLimit, unarmedXpLimit, woodcuttingLevelLimit,
			woodcuttingXpLimit;

	public McMMOConfig(Karmiconomy kcon)
	{
		plugin = kcon;
		// Grab file
		file = new File(plugin.getDataFolder().getAbsolutePath() + "/mcmmo.yml");
		config = YamlConfiguration.loadConfiguration(file);
		loadDefaults();
		loadVariables();
	}

	public static void save()
	{
		//Set config 
		try
		{
			// Save the file
			config.save(file);
		}
		catch (IOException e1)
		{
			plugin.getLogger().warning(
					"File I/O Exception on saving mcmmo config");
			e1.printStackTrace();
		}
	}

	public static void reload()
	{
		//Not sure if this is necessary...?
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
		defaults.put("party.join.enabled", false);
		defaults.put("party.join.denyOnLackPay", false);
		defaults.put("party.join.denyOnLimit", false);
		defaults.put("party.join.limit", 10);
		defaults.put("party.join.pay", 0.1);
		defaults.put("party.leave.enabled", false);
		defaults.put("party.leave.denyOnLackPay", false);
		defaults.put("party.leave.denyOnLimit", false);
		defaults.put("party.leave.limit", 10);
		defaults.put("party.leave.pay", 0.1);
		defaults.put("party.kick.enabled", false);
		defaults.put("party.kick.denyOnLackPay", false);
		defaults.put("party.kick.denyOnLimit", false);
		defaults.put("party.kick.limit", 10);
		defaults.put("party.kick.pay", 0.1);
		defaults.put("party.change.enabled", false);
		defaults.put("party.change.denyOnLackPay", false);
		defaults.put("party.change.denyOnLimit", false);
		defaults.put("party.change.limit", 10);
		defaults.put("party.change.pay", 0.1);
		defaults.put("party.teleport.enabled", false);
		defaults.put("party.teleport.denyOnLackPay", false);
		defaults.put("party.teleport.denyOnLimit", false);
		defaults.put("party.teleport.limit", 10);
		defaults.put("party.teleport.pay", 0.1);
		defaults.put("acrobatics.levelup.enabled", false);
		defaults.put("acrobatics.levelup.limit", 10);
		defaults.put("acrobatics.levelup.pay", 0.1);
		defaults.put("acrobatics.xpgain.enabled", false);
		defaults.put("acrobatics.xpgain.limit", 10);
		defaults.put("acrobatics.xpgain.pay", 0.1);
		defaults.put("archery.levelup.enabled", false);
		defaults.put("archery.levelup.limit", 10);
		defaults.put("archery.levelup.pay", 0.1);
		defaults.put("archery.xpgain.enabled", false);
		defaults.put("archery.xpgain.limit", 10);
		defaults.put("archery.xpgain.pay", 0.1);
		defaults.put("axes.levelup.enabled", false);
		defaults.put("axes.levelup.limit", 10);
		defaults.put("axes.levelup.pay", 0.1);
		defaults.put("axes.xpgain.enabled", false);
		defaults.put("axes.xpgain.limit", 10);
		defaults.put("axes.xpgain.pay", 0.1);
		defaults.put("excavation.levelup.enabled", false);
		defaults.put("excavation.levelup.limit", 10);
		defaults.put("excavation.levelup.pay", 0.1);
		defaults.put("excavation.xpgain.enabled", false);
		defaults.put("excavation.xpgain.limit", 10);
		defaults.put("excavation.xpgain.pay", 0.1);
		defaults.put("fishing.levelup.enabled", false);
		defaults.put("fishing.levelup.limit", 10);
		defaults.put("fishing.levelup.pay", 0.1);
		defaults.put("fishing.xpgain.enabled", false);
		defaults.put("fishing.xpgain.limit", 10);
		defaults.put("fishing.xpgain.pay", 0.1);
		defaults.put("herbalism.levelup.enabled", false);
		defaults.put("herbalism.levelup.limit", 10);
		defaults.put("herbalism.levelup.pay", 0.1);
		defaults.put("herbalism.xpgain.enabled", false);
		defaults.put("herbalism.xpgain.limit", 10);
		defaults.put("herbalism.xpgain.pay", 0.1);
		defaults.put("mining.levelup.enabled", false);
		defaults.put("mining.levelup.limit", 10);
		defaults.put("mining.levelup.pay", 0.1);
		defaults.put("mining.xpgain.enabled", false);
		defaults.put("mining.xpgain.limit", 10);
		defaults.put("mining.xpgain.pay", 0.1);
		defaults.put("repair.levelup.enabled", false);
		defaults.put("repair.levelup.limit", 10);
		defaults.put("repair.levelup.pay", 0.1);
		defaults.put("repair.xpgain.enabled", false);
		defaults.put("repair.xpgain.limit", 10);
		defaults.put("repair.xpgain.pay", 0.1);
		defaults.put("swords.levelup.enabled", false);
		defaults.put("swords.levelup.limit", 10);
		defaults.put("swords.levelup.pay", 0.1);
		defaults.put("swords.xpgain.enabled", false);
		defaults.put("swords.xpgain.limit", 10);
		defaults.put("swords.xpgain.pay", 0.1);
		defaults.put("taming.levelup.enabled", false);
		defaults.put("taming.levelup.limit", 10);
		defaults.put("taming.levelup.pay", 0.1);
		defaults.put("taming.xpgain.enabled", false);
		defaults.put("taming.xpgain.limit", 10);
		defaults.put("taming.xpgain.pay", 0.1);
		defaults.put("unarmed.levelup.enabled", false);
		defaults.put("unarmed.levelup.limit", 10);
		defaults.put("unarmed.levelup.pay", 0.1);
		defaults.put("unarmed.xpgain.enabled", false);
		defaults.put("unarmed.xpgain.limit", 10);
		defaults.put("unarmed.xpgain.pay", 0.1);
		defaults.put("woodcutting.levelup.enabled", false);
		defaults.put("woodcutting.levelup.limit", 10);
		defaults.put("woodcutting.levelup.pay", 0.1);
		defaults.put("woodcutting.xpgain.enabled", false);
		defaults.put("woodcutting.xpgain.limit", 10);
		defaults.put("woodcutting.xpgain.pay", 0.1);
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
		/**
		 * Party
		 */
		// Join
		partyJoin = config.getBoolean("party.join.enabled", false);
		partyJoinDenyPay = config.getBoolean("party.join.denyOnLackPay", false);
		partyJoinDenyLimit = config.getBoolean("party.join.denyOnLimit", false);
		partyJoinLimit = config.getInt("party.join.limit", 10);
		partyJoinPay = config.getDouble("party.join.pay", 0.1);
		// Leave
		partyLeave = config.getBoolean("party.leave.enabled", false);
		partyLeaveDenyPay = config.getBoolean("party.leave.denyOnLackPay",
				false);
		partyLeaveDenyLimit = config.getBoolean("party.leave.denyOnLimit",
				false);
		partyLeaveLimit = config.getInt("party.leave.limit", 10);
		partyLeavePay = config.getDouble("party.leave.pay", 0.1);
		// Kick
		partyKick = config.getBoolean("party.kick.enabled", false);
		partyKickDenyPay = config.getBoolean("party.kick.denyOnLackPay", false);
		partyKickDenyLimit = config.getBoolean("party.kick.denyOnLimit", false);
		partyKickLimit = config.getInt("party.kick.limit", 10);
		partyKickPay = config.getDouble("party.kick.pay", 0.1);
		// Change
		partyChange = config.getBoolean("party.change.enabled", false);
		partyChangeDenyPay = config.getBoolean("party.change.denyOnLackPay",
				false);
		partyChangeDenyLimit = config.getBoolean("party.change.denyOnLimit",
				false);
		partyChangeLimit = config.getInt("party.change.limit", 10);
		partyChangePay = config.getDouble("party.change.pay", 0.1);
		// Teleport
		partyTeleport = config.getBoolean("party.teleport.enabled", false);
		partyTeleportDenyPay = config.getBoolean(
				"party.teleport.denyOnLackPay", false);
		partyTeleportDenyLimit = config.getBoolean(
				"party.teleport.denyOnLimit", false);
		partyTeleportLimit = config.getInt("party.teleport.limit", 10);
		partyTeleportPay = config.getDouble("party.teleport.pay", 0.1);
		/**
		 * Skills
		 */
		// Acrobatics
		acrobaticsLevel = config
				.getBoolean("acrobatics.levelup.enabled", false);
		acrobaticsLevelLimit = config.getInt("acrobatics.levelup.limit", 10);
		acrobaticsLevelPay = config.getDouble("acrobatics.levelup.pay", 0.1);
		acrobaticsXp = config.getBoolean("acrobatics.xpgain.enabled", false);
		acrobaticsXpLimit = config.getInt("acrobatics.xpgain.limit", 10);
		acrobaticsXpPay = config.getDouble("acrobatics.xpgain.pay", 0.1);
		// Archery
		archeryLevel = config.getBoolean("archery.levelup.enabled", false);
		archeryLevelLimit = config.getInt("archery.levelup.limit", 10);
		archeryLevelPay = config.getDouble("archery.levelup.pay", 0.1);
		archeryXp = config.getBoolean("archery.xpgain.enabled", false);
		archeryXpLimit = config.getInt("archery.xpgain.limit", 10);
		archeryXpPay = config.getDouble("archery.xpgain.pay", 0.1);
		// Axes
		axesLevel = config.getBoolean("axes.levelup.enabled", false);
		axesLevelLimit = config.getInt("axes.levelup.limit", 10);
		axesLevelPay = config.getDouble("axes.levelup.pay", 0.1);
		axesXp = config.getBoolean("axes.xpgain.enabled", false);
		axesXpLimit = config.getInt("axes.xpgain.limit", 10);
		axesXpPay = config.getDouble("axes.xpgain.pay", 0.1);
		// Excavation
		excavationLevel = config.getBoolean("excavation.levelup.enabled", false);
		excavationLevelLimit = config.getInt("excavation.levelup.limit", 10);
		excavationLevelPay = config.getDouble("excavation.levelup.pay", 0.1);
		excavationXp = config.getBoolean("excavation.xpgain.enabled", false);
		excavationXpLimit = config.getInt("excavation.xpgain.limit", 10);
		excavationXpPay = config.getDouble("excavation.xpgain.pay", 0.1);
		// Fishing
		fishingLevel = config.getBoolean("fishing.levelup.enabled", false);
		fishingLevelLimit =config.getInt("fishing.levelup.limit", 10);
		fishingLevelPay =config.getDouble("fishing.levelup.pay", 0.1);
		fishingXp = config.getBoolean("fishing.xpgain.enabled", false);
		fishingXpLimit = config.getInt("fishing.xpgain.limit", 10);
		fishingXpPay = config.getDouble("fishing.xpgain.pay", 0.1);
		// Herbalism
		herbalismLevel =config.getBoolean("herbalism.levelup.enabled", false);
		herbalismLevelLimit =config.getInt("herbalism.levelup.limit", 10);
		herbalismLevelPay =config.getDouble("herbalism.levelup.pay", 0.1);
		herbalismXp = config.getBoolean("herbalism.xpgain.enabled", false);
		herbalismXpLimit = config.getInt("herbalism.xpgain.limit", 10);
		herbalismXpPay = config.getDouble("herbalism.xpgain.pay", 0.1);
		// Mining
		miningLevel =config.getBoolean("mining.levelup.enabled", false);
		miningLevelLimit =config.getInt("mining.levelup.limit", 10);
		miningLevelPay =config.getDouble("mining.levelup.pay", 0.1);
		miningXp = config.getBoolean("mining.xpgain.enabled", false);
		miningXpLimit = config.getInt("mining.xpgain.limit", 10);
		miningXpPay = config.getDouble("mining.xpgain.pay", 0.1);
		// Repair
		repairLevel =config.getBoolean("repair.levelup.enabled", false);
		repairLevelLimit =config.getInt("repair.levelup.limit", 10);
		repairLevelPay =config.getDouble("repair.levelup.pay", 0.1);
		repairXp = config.getBoolean("repair.xpgain.enabled", false);
		repairXpLimit = config.getInt("repair.xpgain.limit", 10);
		repairXpPay = config.getDouble("repair.xpgain.pay", 0.1);
		// Swords
		swordsLevel =config.getBoolean("swords.levelup.enabled", false);
		swordsLevelLimit =config.getInt("swords.levelup.limit", 10);
		swordsLevelPay =config.getDouble("swords.levelup.pay", 0.1);
		swordsXp = config.getBoolean("swords.xpgain.enabled", false);
		swordsXpLimit = config.getInt("swords.xpgain.limit", 10);
		swordsXpPay = config.getDouble("swords.xpgain.pay", 0.1);
		// Taming
		tamingLevel =config.getBoolean("taming.levelup.enabled", false);
		tamingLevelLimit =config.getInt("taming.levelup.limit", 10);
		tamingLevelPay =config.getDouble("taming.levelup.pay", 0.1);
		tamingXp = config.getBoolean("taming.xpgain.enabled", false);
		tamingXpLimit = config.getInt("taming.xpgain.limit", 10);
		tamingXpPay = config.getDouble("taming.xpgain.pay", 0.1);
		// Unarmed
		unarmedLevel =config.getBoolean("unarmed.levelup.enabled", false);
		unarmedLevelLimit =config.getInt("unarmed.levelup.limit", 10);
		unarmedLevelPay =config.getDouble("unarmed.levelup.pay", 0.1);
		unarmedXp = config.getBoolean("unarmed.xpgain.enabled", false);
		unarmedXpLimit = config.getInt("unarmed.xpgain.limit", 10);
		unarmedXpPay = config.getDouble("unarmed.xpgain.pay", 0.1);
		// Woodcutting
		woodcuttingLevel =config.getBoolean("woodcutting.levelup.enabled", false);
		woodcuttingLevelLimit =config.getInt("woodcutting.levelup.limit", 10);
		woodcuttingLevelPay =config.getDouble("woodcutting.levelup.pay", 0.1);
		woodcuttingXp = config.getBoolean("woodcutting.xpgain.enabled", false);
		woodcuttingXpLimit = config.getInt("woodcutting.xpgain.limit", 10);
		woodcuttingXpPay = config.getDouble("woodcutting.xpgain.pay", 0.1);
	}

	public int getLimitValue(Field field, Item item, String command)
	{
		int limit = -1;
		switch (field)
		{
			case MCMMO_PARTY_JOIN:
				return partyJoinLimit;
			case MCMMO_PARTY_LEAVE:
				return partyLeaveLimit;
			case MCMMO_PARTY_KICK:
				return partyKickLimit;
			case MCMMO_PARTY_CHANGE:
				return partyChangeLimit;
			case MCMMO_PARTY_TELEPORT:
				return partyTeleportLimit;
			case MCMMO_LEVEL_ACROBATICS:
				return acrobaticsLevelLimit;
			case MCMMO_LEVEL_ARCHERY:
				return archeryLevelLimit;
			case MCMMO_LEVEL_AXES:
				return axesLevelLimit;
			case MCMMO_LEVEL_EXCAVATION:
				return excavationLevelLimit;
			case MCMMO_LEVEL_FISHING:
				return fishingLevelLimit;
			case MCMMO_LEVEL_HERBALISM:
				return herbalismLevelLimit;
			case MCMMO_LEVEL_MINING:
				return miningLevelLimit;
			case MCMMO_LEVEL_REPAIR:
				return repairLevelLimit;
			case MCMMO_LEVEL_SWORDS:
				return swordsLevelLimit;
			case MCMMO_LEVEL_TAMING:
				return tamingLevelLimit;
			case MCMMO_LEVEL_UNARMED:
				return unarmedLevelLimit;
			case MCMMO_LEVEL_WOODCUTTING:
				return woodcuttingLevelLimit;
			case MCMMO_GAIN_ACROBATICS:
				return acrobaticsXpLimit;
			case MCMMO_GAIN_ARCHERY:
				return archeryXpLimit;
			case MCMMO_GAIN_AXES:
				return axesXpLimit;
			case MCMMO_GAIN_EXCAVATION:
				return excavationXpLimit;
			case MCMMO_GAIN_FISHING:
				return fishingXpLimit;
			case MCMMO_GAIN_HERBALISM:
				return herbalismXpLimit;
			case MCMMO_GAIN_MINING:
				return miningXpLimit;
			case MCMMO_GAIN_REPAIR:
				return repairXpLimit;
			case MCMMO_GAIN_SWORDS:
				return swordsXpLimit;
			case MCMMO_GAIN_TAMING:
				return tamingXpLimit;
			case MCMMO_GAIN_UNARMED:
				return unarmedXpLimit;
			case MCMMO_GAIN_WOODCUTTING:
				return woodcuttingXpLimit;
			default:
			{
				if (plugin.getPluginConfig().debugUnhandled)
				{
					plugin.getLogger().warning(
							"Unhandled mcmmo limit for " + field.name());
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
			case MCMMO_PARTY_JOIN:
				return partyJoinPay;
			case MCMMO_PARTY_LEAVE:
				return partyLeavePay;
			case MCMMO_PARTY_KICK:
				return partyKickPay;
			case MCMMO_PARTY_CHANGE:
				return partyChangePay;
			case MCMMO_PARTY_TELEPORT:
				return partyTeleportPay;
			case MCMMO_LEVEL_ACROBATICS:
				return acrobaticsLevelPay;
			case MCMMO_LEVEL_ARCHERY:
				return archeryLevelPay;
			case MCMMO_LEVEL_AXES:
				return axesLevelPay;
			case MCMMO_LEVEL_EXCAVATION:
				return excavationLevelPay;
			case MCMMO_LEVEL_FISHING:
				return fishingLevelPay;
			case MCMMO_LEVEL_HERBALISM:
				return herbalismLevelPay;
			case MCMMO_LEVEL_MINING:
				return miningLevelPay;
			case MCMMO_LEVEL_REPAIR:
				return repairLevelPay;
			case MCMMO_LEVEL_SWORDS:
				return swordsLevelPay;
			case MCMMO_LEVEL_TAMING:
				return tamingLevelPay;
			case MCMMO_LEVEL_UNARMED:
				return unarmedLevelPay;
			case MCMMO_LEVEL_WOODCUTTING:
				return woodcuttingLevelPay;
			case MCMMO_GAIN_ACROBATICS:
				return acrobaticsXpPay;
			case MCMMO_GAIN_ARCHERY:
				return archeryXpPay;
			case MCMMO_GAIN_AXES:
				return axesXpPay;
			case MCMMO_GAIN_EXCAVATION:
				return excavationXpPay;
			case MCMMO_GAIN_FISHING:
				return fishingXpPay;
			case MCMMO_GAIN_HERBALISM:
				return herbalismXpPay;
			case MCMMO_GAIN_MINING:
				return miningXpPay;
			case MCMMO_GAIN_REPAIR:
				return repairXpPay;
			case MCMMO_GAIN_SWORDS:
				return swordsXpPay;
			case MCMMO_GAIN_TAMING:
				return tamingXpPay;
			case MCMMO_GAIN_UNARMED:
				return unarmedXpPay;
			case MCMMO_GAIN_WOODCUTTING:
				return woodcuttingXpPay;
			default:
			{
				if (plugin.getPluginConfig().debugUnhandled)
				{
					plugin.getLogger().warning(
							"Unhandled mcmmo pay for " + field.name());
				}
				break;
			}
		}
		return pay;
	}
}
