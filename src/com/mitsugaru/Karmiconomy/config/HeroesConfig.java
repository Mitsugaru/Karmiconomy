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
		// defaults for all events
		defaults.put("change.class.enabled", false);
		defaults.put("change.class.denyOnLackPay", false);
		defaults.put("change.class.denyOnLimit", false);
		defaults.put("change.class.limit", 10);
		defaults.put("change.class.pay", 0.1);
		defaults.put("change.exp.enabled", false);
		defaults.put("change.exp.denyOnLackPay", false);
		defaults.put("change.exp.denyOnLimit", false);
		defaults.put("change.exp.limit", 10);
		defaults.put("change.exp.pay", 0.1);
		defaults.put("change.level.enabled", false);
		defaults.put("change.level.limit", 10);
		defaults.put("change.level.pay", 0.1);
		defaults.put("combat.enter.enabled", false);
		defaults.put("combat.enter.limit", 10);
		defaults.put("combat.enter.pay", 0.1);
		defaults.put("combat.leave.enabled", false);
		defaults.put("combat.leave.limit", 10);
		defaults.put("combat.leave.pay", 0.1);
		defaults.put("kill.attack.player.enabled", false);
		defaults.put("kill.attack.player.limit", 10);
		defaults.put("kill.attack.player.pay", 0.1);
		defaults.put("kill.attack.mob.enabled", false);
		defaults.put("kill.attack.mob.limit", 10);
		defaults.put("kill.attack.mob.pay", 0.1);
		defaults.put("kill.defend.player.enabled", false);
		defaults.put("kill.defend.player.limit", 10);
		defaults.put("kill.defend.player.pay", 0.1);
		defaults.put("kill.defend.mob.enabled", false);
		defaults.put("kill.defend.mob.limit", 10);
		defaults.put("kill.defend.mob.pay", 0.1);
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
		defaults.put("regain.health.enabled", false);
		defaults.put("regain.health.denyOnLackPay", false);
		defaults.put("regain.health.denyOnLimit", false);
		defaults.put("regain.health.limit", 10);
		defaults.put("regain.health.pay", 0.1);
		defaults.put("regain.mana.enabled", false);
		defaults.put("regain.mana.denyOnLackPay", false);
		defaults.put("regain.mana.denyOnLimit", false);
		defaults.put("regain.mana.limit", 10);
		defaults.put("regain.mana.pay", 0.1);
		defaults.put("skill.complete.enabled", false);
		defaults.put("skill.complete.limit", 10);
		defaults.put("skill.complete.pay", 0.1);
		defaults.put("skill.use.enabled", false);
		defaults.put("skill.use.denyOnLackPay", false);
		defaults.put("skill.use.denyOnLimit", false);
		defaults.put("skill.use.limit", 10);
		defaults.put("skill.use.pay", 0.1);
		// Add to config if missing
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
		// load variables
		/**
		 * Change
		 */
		// class
		classChange = config.getBoolean("change.class.enabled", false);
		classChangeDenyPay = config.getBoolean("change.class.denyOnLackPay",
				false);
		classChangeDenyLimit = config.getBoolean("change.class.denyOnLimit",
				false);
		classChangeLimit = config.getInt("change.class.limit", 10);
		classChangePay = config.getDouble("change.class.pay", 0.1);
		// exp
		expChange = config.getBoolean("change.exp.enabled", false);
		expChangeDenyPay = config.getBoolean("change.exp.denyOnLackPay", false);
		expChangeDenyLimit = config.getBoolean("change.exp.denyOnLimit", false);
		expChangeLimit = config.getInt("change.exp.limit", 10);
		expChangePay = config.getDouble("change.exp.pay", 0.1);
		// level
		changeLevel = config.getBoolean("change.level.enabled", false);
		changeLevelLimit = config.getInt("change.level.limit", 10);
		changeLevelPay = config.getDouble("change.level.pay", 0.1);
		/**
		 * Combat
		 */
		// enter
		combatEnter = config.getBoolean("combat.enter.enabled", false);
		combatEnterLimit = config.getInt("combat.enter.limit", 10);
		combatEnterPay = config.getDouble("combat.enter.pay", 0.1);
		// leave
		combatLeave = config.getBoolean("combat.leave.enabled", false);
		combatLeaveLimit = config.getInt("combat.leave.limit", 10);
		combatLeavePay = config.getDouble("combat.leave.pay", 0.1);
		/**
		 * Party
		 */
		// join
		partyJoin = config.getBoolean("party.join.enabled", false);
		partyJoinDenyPay = config.getBoolean("party.join.denyOnLackPay", false);
		partyJoinDenyLimit = config.getBoolean("party.join.denyOnLimit", false);
		partyJoinLimit = config.getInt("party.join.limit", 10);
		partyJoinPay = config.getDouble("party.join.pay", 0.1);
		// leave
		partyLeave = config.getBoolean("party.leave.enabled", false);
		partyLeaveDenyPay = config.getBoolean("party.leave.denyOnLackPay",
				false);
		partyLeaveDenyLimit = config.getBoolean("party.leave.denyOnLimit",
				false);
		partyLeaveLimit = config.getInt("party.leave.limit", 10);
		partyLeavePay = config.getDouble("party.leave.pay", 0.1);
		/**
		 * kill
		 */
		// attack player
		killAttackPlayer = config.getBoolean("kill.attack.player.enabled",
				false);
		killAttackPlayerLimit = config.getInt("kill.attack.player.limit", 10);
		killAttackPlayerPay = config.getDouble("kill.attack.player.pay", 0.1);
		// attack mob
		killAttackMob = config.getBoolean("kill.attack.mob.enabled", false);
		killAttackMobLimit = config.getInt("kill.attack.mob.limit", 10);
		killAttackMobPay = config.getDouble("kill.attack.mob.pay", 0.1);
		// defend player
		killDefendPlayer = config.getBoolean("kill.defend.player.enabled",
				false);
		killDefendPlayerLimit = config.getInt("kill.defend.player.limit", 10);
		killDefendPlayerPay = config.getDouble("kill.defend.player.pay", 0.1);
		// defend mob
		killDefendMob = config.getBoolean("kill.defend.mob.enabled", false);
		killDefendMobLimit = config.getInt("kill.defend.mob.limit", 10);
		killDefendMobPay = config.getDouble("kill.defend.mob.pay", 0.1);
		/**
		 * regain
		 */
		// health
		regainHealth = config.getBoolean("regain.health.enabled", false);
		regainHealthDenyPay = config.getBoolean("regain.health.denyOnLackPay",
				false);
		regainHealthDenyLimit = config.getBoolean("regain.health.denyOnLimit",
				false);
		regainHealthLimit = config.getInt("regain.health.limit", 10);
		regainHealthPay = config.getDouble("regain.health.pay", 0.1);
		// mana
		regainMana = config.getBoolean("regain.mana.enabled", false);
		regainManaDenyPay = config.getBoolean("regain.mana.denyOnLackPay",
				false);
		regainManaDenyLimit = config.getBoolean("regain.mana.denyOnLimit",
				false);
		regainManaLimit = config.getInt("regain.mana.limit", 10);
		regainManaPay = config.getDouble("regain.mana.pay", 0.1);
		/**
		 * skill
		 */
		// Complete
		skillComplete = config.getBoolean("skill.complete.enabled", false);
		skillCompleteLimit = config.getInt("skill.complete.limit", 10);
		skillCompletePay = config.getDouble("skill.complete.pay", 0.1);
		// use
		skillUse = config.getBoolean("skill.use.enabled", false);
		skillUseDenyPay = config.getBoolean("skill.use.denyOnLackPay", false);
		skillUseDenyLimit = config.getBoolean("skill.use.denyOnLimit", false);
		skillUseLimit = config.getInt("skill.use.limit", 10);
		skillUsePay = config.getDouble("skill.use.pay", 0.1);
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
