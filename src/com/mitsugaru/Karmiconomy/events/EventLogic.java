package com.mitsugaru.Karmiconomy.events;

import java.util.EnumMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.KarmicEcon;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.LocalString;
import com.mitsugaru.Karmiconomy.config.Config;
import com.mitsugaru.Karmiconomy.config.KConfig;
import com.mitsugaru.Karmiconomy.config.LocalizeConfig;
import com.mitsugaru.Karmiconomy.database.DatabaseHandler;
import com.mitsugaru.Karmiconomy.database.Field;

public class EventLogic
{
	private static Karmiconomy plugin;
	private static Config rootConfig;
	private static DatabaseHandler db;

	public static void init(Karmiconomy kcon)
	{
		plugin = kcon;
		rootConfig = kcon.getPluginConfig();
		db = kcon.getDatabaseHandler();
	}

	public static void debugEvent(Event event, Map<String, String> details)
	{
		plugin.getLogger().info("Event: " + event.getEventName());
		for (Map.Entry<String, String> entry : details.entrySet())
		{
			plugin.getLogger().info(entry.getKey() + " : " + entry.getValue());
		}
	}

	public static boolean deny(Field field, Player player, KConfig config,
			Item item, String command)
	{
		if (config.getDenyPay(field, item, command))
		{
			if (KarmicEcon.denyPay(player,
					config.getPayValue(field, item, command)))
			{
				switch (field.getTable())
				{
					case COMMAND:
					{
						sendLackMessage(player, DenyType.MONEY, field.name(),
								command);
						break;
					}
					case ITEMS:
					{
						sendLackMessage(player, DenyType.MONEY, field.name(),
								item.name);
						break;
					}
					default:
					{
						sendLackMessage(player, DenyType.MONEY, field.name(),
								null);
						break;
					}
				}

				if (rootConfig.debugEvents)
				{
					plugin.getLogger().info(
							"Denied " + field + " for player "
									+ player.getName() + " for "
									+ DenyType.MONEY);
				}
				return true;
			}
		}
		if (config.getDenyLimit(field, item, command))
		{
			final int configLimit = config.getLimitValue(field, item, command);
			if (configLimit == 0)
			{
				switch (field.getTable())
				{
					case COMMAND:
					{
						sendLackMessage(player, DenyType.LIMIT, field.name(),
								command);
						break;
					}
					case ITEMS:
					{
						sendLackMessage(player, DenyType.LIMIT, field.name(),
								item.name);
						break;
					}
					default:
					{
						sendLackMessage(player, DenyType.LIMIT, field.name(),
								null);
						break;
					}
				}
				if (rootConfig.debugEvents)
				{
					plugin.getLogger().info(
							"Denied " + field + " for player "
									+ player.getName() + " for "
									+ DenyType.LIMIT);
				}
				return true;
			}
			else if (configLimit > 0)
			{
				// Deny by player limit
				final int limit = db.getData(field, player.getName(), item,
						command);
				if (limit >= configLimit)
				{
					switch (field.getTable())
					{
						case COMMAND:
						{
							sendLackMessage(player, DenyType.LIMIT,
									field.name(), command);
							break;
						}
						case ITEMS:
						{
							sendLackMessage(player, DenyType.LIMIT,
									field.name(), item.name);
							break;
						}
						default:
						{
							sendLackMessage(player, DenyType.LIMIT,
									field.name(), null);
							break;
						}
					}
					if (rootConfig.debugEvents)
					{
						plugin.getLogger().info(
								"Denied " + field + " for player "
										+ player.getName() + " for "
										+ DenyType.LIMIT);
					}
					return true;
				}
			}
		}
		return false;
	}

	private static boolean hitLimit(Field field, Player player,
			int configLimit, Item item, String command)
	{
		final int limit = db.getData(field, player.getName(), item, command);
		if (configLimit >= 0)
		{
			if (limit >= configLimit)
			{
				// They hit the config limit
				return true;
			}
		}
		return false;
	}

	public static void hitPayIncrement(Field field, Player player,
			KConfig config, Item item, String command)
	{
		final int configLimit = config.getLimitValue(field, item, command);
		// Check if hit limit
		if (!EventLogic.hitLimit(field, player, configLimit, item, command))
		{
			// Attempt to pay
			if (KarmicEcon.pay(field, player, config, item, command))
			{
				// Increment
				db.incrementData(field, player.getName(), item, command);
			}
		}
	}

	public static void sendLackMessage(Player player, DenyType type,
			String action, String extra)
	{
		final EnumMap<LocalString.Flag, String> info = new EnumMap<LocalString.Flag, String>(
				LocalString.Flag.class);
		info.put(LocalString.Flag.TAG, Karmiconomy.TAG);
		switch (type)
		{
			case MONEY:
				info.put(LocalString.Flag.REASON, LocalizeConfig.reasonMoney);
				break;
			case LIMIT:
				info.put(LocalString.Flag.REASON, LocalizeConfig.reasonLimit);
				break;
			default:
				info.put(LocalString.Flag.REASON, LocalizeConfig.reasonUnknown);
				break;
		}
		info.put(LocalString.Flag.EVENT, action);
		if (extra != null)
		{
			info.put(LocalString.Flag.EXTRA, " of " + ChatColor.GOLD + extra);
		}
		final String out = LocalString.LACK_MESSAGE.parseString(info);
		boolean send = true;
		// Only send message if they haven't already gotten it. Should stop
		// against spamming.
		if (Karmiconomy.sentMessages.containsKey(player.getName()))
		{
			if (Karmiconomy.sentMessages.get(player.getName()).equals(out))
			{
				send = false;
			}
		}
		if (send)
		{
			Karmiconomy.sentMessages.put(player.getName(), out);
			player.sendMessage(out);
		}

	}

	public enum DenyType
	{
		MONEY, LIMIT, FORCE;
	}
}
