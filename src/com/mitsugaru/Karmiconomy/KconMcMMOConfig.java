package com.mitsugaru.Karmiconomy;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

public class KconMcMMOConfig
{
	public boolean partyTeleport, partyTeleportDenyPay, partyTeleportDenyLimit;
	public double partyTeleportPay;
	public int partyTeleportLimit;
	public KconMcMMOConfig(Karmiconomy plugin)
	{
		final File file = new File(plugin.getDataFolder().getAbsolutePath()
				+ "/mcmmo.yml");
		// TODO rename
		final YamlConfiguration valueFile = YamlConfiguration
				.loadConfiguration(file);
		// LinkedHashmap of defaults
		final Map<String, Object> defaults = new LinkedHashMap<String, Object>();
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
		if (valueFile.getKeys(false).isEmpty())
		{

		}
		try
		{
			// Save the file
			valueFile.save(file);
		}
		catch (IOException e1)
		{
			// INFO Auto-generated catch block
			plugin.getLogger().warning(
					"File I/O Exception on saving karma list");
			e1.printStackTrace();
		}
	}
}
