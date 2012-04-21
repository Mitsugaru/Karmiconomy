package com.mitsugaru.Karmiconomy;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.file.YamlConfiguration;

import com.mitsugaru.Karmiconomy.DatabaseHandler.Field;

public class KconMcMMOConfig
{
	private Karmiconomy plugin;
	private File file;
	private YamlConfiguration config;
	public boolean partyTeleport, partyTeleportDenyPay, partyTeleportDenyLimit,
			partyJoin, partyJoinDenyPay, partyJoinDenyLimit, partyLeave,
			partyLeaveDenyPay, partyLeaveDenyLimit, partyKick,
			partyKickDenyPay, partyKickDenyLimit, partyChange,
			partyChangeDenyPay, partyChangeDenyLimit;
	public double partyTeleportPay, partyJoinPay, partyLeavePay, partyKickPay,
			partyChangePay;
	public int partyTeleportLimit, partyJoinLimit, partyLeaveLimit,
			partyKickLimit, partyChangeLimit;

	public KconMcMMOConfig(Karmiconomy plugin)
	{
		this.plugin = plugin;
		// Grab file
		file = new File(plugin.getDataFolder().getAbsolutePath() + "/mcmmo.yml");
		config = YamlConfiguration.loadConfiguration(file);
		loadDefaults();
		loadVariables();
	}

	public void save()
	{
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

	public void reload()
	{
		save();
		loadDefaults();
		loadVariables();
	}

	private void loadDefaults()
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

		defaults.put("levelup.enabled", false);
		defaults.put("levelup.limit", 10);
		defaults.put("levelup.pay", 0.1);
		defaults.put("xpgain.enabled", false);
		defaults.put("xpgain.limit", 10);
		defaults.put("xpgain.pay", 0.1);
		for (final Entry<String, Object> e : defaults.entrySet())
		{
			if (!config.contains(e.getKey()))
			{
				config.set(e.getKey(), e.getValue());
			}
		}
		save();
	}
	
	private void loadVariables()
	{
		//TODO implement
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
		// TODO Auto-generated method stub
		double pay = 0;
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
