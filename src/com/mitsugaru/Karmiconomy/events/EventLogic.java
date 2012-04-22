package com.mitsugaru.Karmiconomy.events;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.mitsugaru.Karmiconomy.DatabaseHandler;
import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.KarmicEcon;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.DatabaseHandler.Field;
import com.mitsugaru.Karmiconomy.config.Config;

public class EventLogic
{
	private static Karmiconomy plugin;
	private static Config config;
	private static DatabaseHandler db;

	public static void init(Karmiconomy kcon)
	{
		plugin = kcon;
		config = kcon.getPluginConfig();
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

	public static boolean deny(Field field, Player player, boolean denyPay,
			double pay, boolean denyLimit, int configLimit, Item item,
			String command)
	{
		if (denyPay)
		{
			if (KarmicEcon.denyPay(field, player, pay, item, command))
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

				if (config.debugEvents)
				{
					plugin.getLogger().info(
							"Denied " + field + " for player "
									+ player.getName() + " for "
									+ DenyType.MONEY);
				}
				return true;
			}
		}
		if (denyLimit)
		{
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
				if (config.debugEvents)
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
					if (config.debugEvents)
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

	public static boolean hitLimit(Field field, Player player, int configLimit,
			Item item, String command)
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

	public static void sendLackMessage(Player player, DenyType type,
			String action, String extra)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(ChatColor.RED + Karmiconomy.TAG);
		switch (type)
		{
			case MONEY:
				sb.append(" Lack money ");
				break;
			case LIMIT:
				sb.append(" Hit limit ");
				break;
			default:
				sb.append(" Unknown DenyType ");
				break;
		}
		sb.append("for action: " + ChatColor.AQUA + action);
		if (extra != null)
		{
			sb.append(ChatColor.RED + " of " + ChatColor.GOLD + extra);
		}
		final String out = sb.toString();
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
