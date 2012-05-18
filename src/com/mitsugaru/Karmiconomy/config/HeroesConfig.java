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

public class HeroesConfig implements KConfig
{
	private static Karmiconomy plugin;
	private static File file;
	private static YamlConfiguration config;

	public HeroesConfig(Karmiconomy kcon)
	{
		plugin = kcon;
		// Grab file
		file = new File(plugin.getDataFolder().getAbsolutePath()
				+ "/heroes.yml");
		config = YamlConfiguration.loadConfiguration(file);
		loadDefaults();
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
		defaults.put("change.class.localMessage", false);
		defaults.put("change.exp.enabled", false);
		defaults.put("change.exp.denyOnLackPay", false);
		defaults.put("change.exp.denyOnLimit", false);
		defaults.put("change.exp.limit", 10);
		defaults.put("change.exp.pay", 0.1);
		defaults.put("change.exp.localMessage", false);
		defaults.put("change.level.enabled", false);
		defaults.put("change.level.limit", 10);
		defaults.put("change.level.pay", 0.1);
		defaults.put("change.level.localMessage", false);
		defaults.put("combat.enter.enabled", false);
		defaults.put("combat.enter.limit", 10);
		defaults.put("combat.enter.pay", 0.1);
		defaults.put("combat.enter.localMessage", false);
		defaults.put("combat.leave.enabled", false);
		defaults.put("combat.leave.limit", 10);
		defaults.put("combat.leave.pay", 0.1);
		defaults.put("combat.leave.localMessage", false);
		defaults.put("kill.attack.player.enabled", false);
		defaults.put("kill.attack.player.limit", 10);
		defaults.put("kill.attack.player.pay", 0.1);
		defaults.put("kill.attack.player.localMessage", false);
		defaults.put("kill.attack.mob.enabled", false);
		defaults.put("kill.attack.mob.limit", 10);
		defaults.put("kill.attack.mob.pay", 0.1);
		defaults.put("kill.attack.mob.localMessage", false);
		defaults.put("kill.defend.player.enabled", false);
		defaults.put("kill.defend.player.limit", 10);
		defaults.put("kill.defend.player.pay", 0.1);
		defaults.put("kill.defend.player.localMessage", false);
		defaults.put("kill.defend.mob.enabled", false);
		defaults.put("kill.defend.mob.limit", 10);
		defaults.put("kill.defend.mob.pay", 0.1);
		defaults.put("kill.defend.mob.localMessage", false);
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
		defaults.put("regain.health.enabled", false);
		defaults.put("regain.health.denyOnLackPay", false);
		defaults.put("regain.health.denyOnLimit", false);
		defaults.put("regain.health.limit", 10);
		defaults.put("regain.health.pay", 0.1);
		defaults.put("regain.health.localMessage", false);
		defaults.put("regain.mana.enabled", false);
		defaults.put("regain.mana.denyOnLackPay", false);
		defaults.put("regain.mana.denyOnLimit", false);
		defaults.put("regain.mana.limit", 10);
		defaults.put("regain.mana.pay", 0.1);
		defaults.put("regain.mana.localMessage", false);
		defaults.put("skill.complete.enabled", false);
		defaults.put("skill.complete.limit", 10);
		defaults.put("skill.complete.pay", 0.1);
		defaults.put("skill.complete.localMessage", false);
		defaults.put("skill.use.enabled", false);
		defaults.put("skill.use.denyOnLackPay", false);
		defaults.put("skill.use.denyOnLimit", false);
		defaults.put("skill.use.limit", 10);
		defaults.put("skill.use.pay", 0.1);
		defaults.put("skill.use.localMessage", false);
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
		return config
				.getBoolean(field.getConfigPath() + ".localMessage", false);
	}

	@Override
	public boolean checkWorld(Field field, String worldName)
	{
		boolean valid = false;
		final List<String> list = config.getStringList(field.getConfigPath()
				+ ".worlds");
		if (list == null)
		{
			// No worlds specified, so allow all
			valid = true;
		}
		else if (list.isEmpty())
		{
			valid = true;
		}
		else
		{
			for (String world : list)
			{
				if (world.equalsIgnoreCase(worldName))
				{
					valid = true;
					break;
				}
			}
		}
		return valid;
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

	@Override
	public boolean isEnabled(Field field)
	{
		return config.getBoolean(field.getConfigPath() + ".enabled", false);
	}
}
