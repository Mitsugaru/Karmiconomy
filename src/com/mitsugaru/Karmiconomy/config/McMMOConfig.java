package com.mitsugaru.Karmiconomy.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.database.Field;

public class McMMOConfig implements KConfig
{
	private static Karmiconomy plugin;
	private static File file;
	private static YamlConfiguration config;

	public McMMOConfig(Karmiconomy kcon)
	{
		plugin = kcon;
		// Grab file
		file = new File(plugin.getDataFolder().getAbsolutePath() + "/mcmmo.yml");
		config = YamlConfiguration.loadConfiguration(file);
		loadDefaults();
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
		defaults.put("party.join.localMessage", false);
		defaults.put("party.leave.enabled", false);
		defaults.put("party.leave.denyOnLackPay", false);
		defaults.put("party.leave.denyOnLimit", false);
		defaults.put("party.leave.limit", 10);
		defaults.put("party.leave.pay", 0.1);
		defaults.put("party.leave.localMessage", false);
		defaults.put("party.kick.enabled", false);
		defaults.put("party.kick.denyOnLackPay", false);
		defaults.put("party.kick.denyOnLimit", false);
		defaults.put("party.kick.limit", 10);
		defaults.put("party.kick.pay", 0.1);
		defaults.put("party.kick.localMessage", false);
		defaults.put("party.change.enabled", false);
		defaults.put("party.change.denyOnLackPay", false);
		defaults.put("party.change.denyOnLimit", false);
		defaults.put("party.change.limit", 10);
		defaults.put("party.change.pay", 0.1);
		defaults.put("party.change.localMessage", false);
		defaults.put("party.teleport.enabled", false);
		defaults.put("party.teleport.denyOnLackPay", false);
		defaults.put("party.teleport.denyOnLimit", false);
		defaults.put("party.teleport.limit", 10);
		defaults.put("party.teleport.pay", 0.1);
		defaults.put("party.teleport.localMessage", false);
		defaults.put("acrobatics.levelup.enabled", false);
		defaults.put("acrobatics.levelup.limit", 10);
		defaults.put("acrobatics.levelup.pay", 0.1);
		defaults.put("acrobatics.levelup.localMessage", false);
		defaults.put("acrobatics.xpgain.enabled", false);
		defaults.put("acrobatics.xpgain.limit", 10);
		defaults.put("acrobatics.xpgain.pay", 0.1);
		defaults.put("acrobatics.xpgain.localMessage", false);
		defaults.put("archery.levelup.enabled", false);
		defaults.put("archery.levelup.limit", 10);
		defaults.put("archery.levelup.pay", 0.1);
		defaults.put("archery.levelup.localMessage", false);
		defaults.put("archery.xpgain.enabled", false);
		defaults.put("archery.xpgain.limit", 10);
		defaults.put("archery.xpgain.pay", 0.1);
		defaults.put("archery.xpgain.localMessage", false);
		defaults.put("axes.levelup.enabled", false);
		defaults.put("axes.levelup.limit", 10);
		defaults.put("axes.levelup.pay", 0.1);
		defaults.put("axes.levelup.localMessage", false);
		defaults.put("axes.xpgain.enabled", false);
		defaults.put("axes.xpgain.limit", 10);
		defaults.put("axes.xpgain.pay", 0.1);
		defaults.put("axes.xpgain.localMessage", false);
		defaults.put("excavation.levelup.enabled", false);
		defaults.put("excavation.levelup.limit", 10);
		defaults.put("excavation.levelup.pay", 0.1);
		defaults.put("excavation.levelup.localMessage", false);
		defaults.put("excavation.xpgain.enabled", false);
		defaults.put("excavation.xpgain.limit", 10);
		defaults.put("excavation.xpgain.pay", 0.1);
		defaults.put("excavation.xpgain.localMessage", false);
		defaults.put("fishing.levelup.enabled", false);
		defaults.put("fishing.levelup.limit", 10);
		defaults.put("fishing.levelup.pay", 0.1);
		defaults.put("fishing.levleup.localMessage", false);
		defaults.put("fishing.xpgain.enabled", false);
		defaults.put("fishing.xpgain.limit", 10);
		defaults.put("fishing.xpgain.pay", 0.1);
		defaults.put("fishing.xpgain.localMessage", false);
		defaults.put("herbalism.levelup.enabled", false);
		defaults.put("herbalism.levelup.limit", 10);
		defaults.put("herbalism.levelup.pay", 0.1);
		defaults.put("herbalism.levelup.localMessage", false);
		defaults.put("herbalism.xpgain.enabled", false);
		defaults.put("herbalism.xpgain.limit", 10);
		defaults.put("herbalism.xpgain.pay", 0.1);
		defaults.put("herbalism.xpgain.localMessage", false);
		defaults.put("mining.levelup.enabled", false);
		defaults.put("mining.levelup.limit", 10);
		defaults.put("mining.levelup.pay", 0.1);
		defaults.put("mining.levelup.localMessage", false);
		defaults.put("mining.xpgain.enabled", false);
		defaults.put("mining.xpgain.limit", 10);
		defaults.put("mining.xpgain.pay", 0.1);
		defaults.put("mining.xpgain.localMessage", false);
		defaults.put("repair.levelup.enabled", false);
		defaults.put("repair.levelup.limit", 10);
		defaults.put("repair.levelup.pay", 0.1);
		defaults.put("repair.levelup.localMessage", false);
		defaults.put("repair.xpgain.enabled", false);
		defaults.put("repair.xpgain.limit", 10);
		defaults.put("repair.xpgain.pay", 0.1);
		defaults.put("repair.xpgain.localMessage", false);
		defaults.put("swords.levelup.enabled", false);
		defaults.put("swords.levelup.limit", 10);
		defaults.put("swords.levelup.pay", 0.1);
		defaults.put("swords.levelup.localMessage", false);
		defaults.put("swords.xpgain.enabled", false);
		defaults.put("swords.xpgain.limit", 10);
		defaults.put("swords.xpgain.pay", 0.1);
		defaults.put("swords.xpgain.localMessage", false);
		defaults.put("taming.levelup.enabled", false);
		defaults.put("taming.levelup.limit", 10);
		defaults.put("taming.levelup.pay", 0.1);
		defaults.put("taming.levelup.localMessage", false);
		defaults.put("taming.xpgain.enabled", false);
		defaults.put("taming.xpgain.limit", 10);
		defaults.put("taming.xpgain.pay", 0.1);
		defaults.put("taming.xpgain.localMessage", false);
		defaults.put("unarmed.levelup.enabled", false);
		defaults.put("unarmed.levelup.limit", 10);
		defaults.put("unarmed.levelup.pay", 0.1);
		defaults.put("unarmed.levelup.localMessage", false);
		defaults.put("unarmed.xpgain.enabled", false);
		defaults.put("unarmed.xpgain.limit", 10);
		defaults.put("unarmed.xpgain.pay", 0.1);
		defaults.put("unarmed.xpgain.localMessage", false);
		defaults.put("woodcutting.levelup.enabled", false);
		defaults.put("woodcutting.levelup.limit", 10);
		defaults.put("woodcutting.levelup.pay", 0.1);
		defaults.put("woodcutting.levelup.localMessage", false);
		defaults.put("woodcutting.xpgain.enabled", false);
		defaults.put("woodcutting.xpgain.limit", 10);
		defaults.put("woodcutting.xpgain.pay", 0.1);
		defaults.put("woodcutting.xpgain.localMessage", false);
		for (final Entry<String, Object> e : defaults.entrySet())
		{
			if (!config.contains(e.getKey()))
			{
				config.set(e.getKey(), e.getValue());
			}
		}
		save();
	}

	@Override
	public int getLimitValue(Field field, Item item, String command)
	{
		return config.getInt(field.getConfigPath() + ".limit", -1);
	}

	@Override
	public double getPayValue(Field field, Item item, String command)
	{
		return config.getDouble(field.getConfigPath() + ".pay", 0.0);
	}
	
	@Override
	public boolean sendBroadcast(Field field)
	{
		return config.getBoolean(
				field.getConfigPath() + ".localMessage", false);
	}
	
	@Override
	public boolean checkWorld(Field field, String worldName)
	{
		boolean valid = false;
		final List<String> list = config.getStringList(field.getConfigPath() + ".worlds");
		if(list == null)
		{
			//No worlds specified, so allow all
			valid = true;
		}
		else if(list.isEmpty())
		{
			valid = true;
		}
		else
		{
			for(String world : list)
			{
				if(world.equalsIgnoreCase(worldName))
				{
					valid = true;
					break;
				}
			}
		}
		return valid;
	}

	@Override
	public boolean isEnabled(Field field)
	{
		return config.getBoolean(field.getConfigPath() + ".enabled", false);
	}

	@Override
	public boolean getDenyPay(Field field, Item item, String command)
	{
		return config.getBoolean(field.getConfigPath() + ".denyOnLackPay",
				false);
	}

	@Override
	public boolean getDenyLimit(Field field, Item item, String command)
	{
		return config.getBoolean(field.getConfigPath() + ".denyOnLimit", false);
	}
}
