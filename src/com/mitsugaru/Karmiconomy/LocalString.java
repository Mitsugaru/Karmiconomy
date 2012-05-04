package com.mitsugaru.Karmiconomy;

import java.util.EnumMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;

import com.mitsugaru.Karmiconomy.config.LocalizeConfig;

public enum LocalString
{
	PERMISSION_DENY(LocalizeConfig.permissionDeny), LACK_MESSAGE(
			LocalizeConfig.lackMessage), ECONOMY_FAILURE(
			LocalizeConfig.econFailure), UNKNOWN_COMMAND(
			LocalizeConfig.unknownCommand), RELOAD_CONFIG(
			LocalizeConfig.reloadConfig), HELP_HELP(LocalizeConfig.helpHelp), HELP_VERSION(
			LocalizeConfig.helpVersion), HELP_ADMIN_RELOAD(
			LocalizeConfig.helpAdminReload);

	private String string;

	private LocalString(String s)
	{
		this.string = s;
	}

	public String parseString(EnumMap<Field, String> replace)
	{
		String out = colorizeText(string);
		if (replace != null)
		{
			for (Entry<Field, String> entry : replace.entrySet())
			{
				out = out.replaceAll(entry.getKey().getField(), entry.getValue());
			}
		}
		return out;
	}

	/**
	 * Colorizes a given string to Bukkit standards
	 * 
	 * http://forums.bukkit.org/threads/methode-to-colorize.69543/#post-1063437
	 * 
	 * @param string
	 * @return String with appropriate Bukkit ChatColor in them
	 * @author AmberK
	 */
	public String colorizeText(String string)
	{
		string = string.replaceAll("&0", "" + ChatColor.BLACK);
		string = string.replaceAll("&1", "" + ChatColor.DARK_BLUE);
		string = string.replaceAll("&2", "" + ChatColor.DARK_GREEN);
		string = string.replaceAll("&3", "" + ChatColor.DARK_AQUA);
		string = string.replaceAll("&4", "" + ChatColor.DARK_RED);
		string = string.replaceAll("&5", "" + ChatColor.DARK_PURPLE);
		string = string.replaceAll("&6", "" + ChatColor.GOLD);
		string = string.replaceAll("&7", "" + ChatColor.GRAY);
		string = string.replaceAll("&8", "" + ChatColor.DARK_GRAY);
		string = string.replaceAll("&9", "" + ChatColor.BLUE);
		string = string.replaceAll("&a", "" + ChatColor.GREEN);
		string = string.replaceAll("&b", "" + ChatColor.AQUA);
		string = string.replaceAll("&c", "" + ChatColor.RED);
		string = string.replaceAll("&d", "" + ChatColor.LIGHT_PURPLE);
		string = string.replaceAll("&e", "" + ChatColor.YELLOW);
		string = string.replaceAll("&f", "" + ChatColor.WHITE);
		return string;
	}

	public enum Field
	{
		NAME("%name"), EVENT("%event"), REASON("%reason"), EXTRA("%extra"), TAG(
				"%tag"), AMOUNT("%amount");

		private String field;

		private Field(String field)
		{
			this.field = field;
		}

		public String getField()
		{
			return field;
		}
	}
}
