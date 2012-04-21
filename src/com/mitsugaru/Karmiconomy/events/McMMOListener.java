package com.mitsugaru.Karmiconomy.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.events.party.McMMOPartyChangeEvent;
import com.gmail.nossr50.events.party.McMMOPartyTeleportEvent;
import com.mitsugaru.Karmiconomy.DatabaseHandler;
import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.KarmicEcon;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.KconMcMMOConfig;
import com.mitsugaru.Karmiconomy.DatabaseHandler.Field;
import com.mitsugaru.Karmiconomy.events.KconEventLogic.DenyType;

public class McMMOListener implements Listener
{
	private Karmiconomy plugin;
	private KconMcMMOConfig config;
	private DatabaseHandler db;

	public McMMOListener(Karmiconomy plugin)
	{
		this.plugin = plugin;
		config = new KconMcMMOConfig(plugin);
		db = plugin.getDatabaseHandler();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void playerLevelUp(McMMOPlayerLevelUpEvent event)
	{
		if (event.getPlayer() != null && event.getSkill() != null)
		{
			switch (event.getSkill())
			{
				case ACROBATICS:
				{
					break;
				}
				case ARCHERY:
				{
					break;
				}
				case AXES:
				{
					break;
				}
				case EXCAVATION:
				{
					break;
				}
				case FISHING:
				{
					break;
				}
				case HERBALISM:
				{
					break;
				}
				case MINING:
				{
					break;
				}
				case REPAIR:
				{
					break;
				}
				case SWORDS:
				{
					break;
				}
				case TAMING:
				{
					break;
				}
				case UNARMED:
				{
					break;
				}
				case WOODCUTTING:
				{
					break;
				}
				default:
				{
					if (plugin.getPluginConfig().debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled skill '" + event.getSkill().name()
										+ " for " + event.getEventName());
					}
					break;
				}
			}
			if (plugin.getPluginConfig().debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", event.getPlayer().getName());
				details.put("Skill", event.getSkill().name());
				details.put("SkillLevel", "" + event.getSkillLevel());
				details.put("LvlsGained", "" + event.getLevelsGained());
				details.put("Cancelled", "true");
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void partyTeleportValid(McMMOPartyTeleportEvent event)
	{
		if (!event.isCancelled() && config.partyTeleport
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (KconEventLogic
					.deny(Field.MCMMO_PARTY_TELEPORT, player,
							config.partyTeleportDenyPay, config.getPayValue(
									Field.MCMMO_PARTY_TELEPORT, null, null),
							config.partyTeleportDenyLimit, config
									.getLimitValue(Field.MCMMO_PARTY_TELEPORT,
											null, null), null, null))
			{
				// Cancel event
				event.setCancelled(true);
				if (plugin.getPluginConfig().debugEvents)
				{
					final Map<String, String> details = new HashMap<String, String>();
					details.put("Player", player.getName());
					details.put("Party", event.getParty());
					details.put("To", event.getTo().toString());
					details.put("Cancelled", "true");
					KconEventLogic.debugEvent(event, details);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void partyTeleport(McMMOPartyTeleportEvent event)
	{
		if (!event.isCancelled() && config.partyTeleport
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			// Check if hit limit
			if (!KconEventLogic
					.hitLimit(Field.MCMMO_PARTY_TELEPORT, player, config
							.getLimitValue(Field.MCMMO_PARTY_TELEPORT, null,
									null), null, null))
			{
				// Try to pay
				if (KarmicEcon.pay(Field.MCMMO_PARTY_TELEPORT, player, null,
						null))
				{
					// Increment
					db.incrementData(Field.MCMMO_PARTY_TELEPORT,
							player.getName(), null, null);
				}
			}
			if (plugin.getPluginConfig().debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				details.put("Party", event.getParty());
				details.put("To", event.getTo().toString());
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void partyChangeValid(McMMOPartyChangeEvent event)
	{
		if (!event.isCancelled() && config.partyTeleport
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			boolean cancelled = false;
			switch (event.getReason())
			{
				case JOINED_PARTY:
				{
					if (KconEventLogic.deny(Field.MCMMO_PARTY_JOIN, player,
							config.partyJoinDenyPay, config.getPayValue(
									Field.MCMMO_PARTY_JOIN, null, null),
							config.partyJoinDenyLimit, config.getLimitValue(
									Field.MCMMO_PARTY_JOIN, null, null), null,
							null))
					{
						cancelled = true;
					}
					break;
				}
				case LEFT_PARTY:
				{
					if (KconEventLogic.deny(Field.MCMMO_PARTY_LEAVE, player,
							config.partyLeaveDenyPay, config.getPayValue(
									Field.MCMMO_PARTY_LEAVE, null, null),
							config.partyLeaveDenyLimit, config.getLimitValue(
									Field.MCMMO_PARTY_LEAVE, null, null), null,
							null))
					{
						cancelled = true;
					}
					break;
				}
				case KICKED_FROM_PARTY:
				{
					if (KconEventLogic.deny(Field.MCMMO_PARTY_KICK, player,
							config.partyKickDenyPay, config.getPayValue(
									Field.MCMMO_PARTY_KICK, null, null),
							config.partyKickDenyLimit, config.getLimitValue(
									Field.MCMMO_PARTY_KICK, null, null), null,
							null))
					{
						cancelled = true;
					}
					break;
				}
				case CHANGED_PARTIES:
				{
					if (KconEventLogic.deny(Field.MCMMO_PARTY_CHANGE, player,
							config.partyChangeDenyPay, config.getPayValue(
									Field.MCMMO_PARTY_CHANGE, null, null),
							config.partyChangeDenyLimit, config.getLimitValue(
									Field.MCMMO_PARTY_CHANGE, null, null),
							null, null))
					{
						cancelled = true;
					}
					break;
				}
				default:
				{
					if (plugin.getPluginConfig().debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled reason '" + event.getReason().name()
										+ " for " + event.getEventName());
					}
					break;
				}
			}
			if (plugin.getPluginConfig().debugEvents && cancelled)
			{
				event.setCancelled(true);
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getOldParty() != null)
				{
					details.put("Party", event.getOldParty());
				}
				if (event.getNewParty() != null)
				{
					details.put("NewParty", event.getNewParty());
				}
				details.put("Reason", event.getReason().name());
				details.put("Cancelled", "true");
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void partyChange(McMMOPartyChangeEvent event)
	{
		if (!event.isCancelled() && config.partyTeleport
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			switch (event.getReason())
			{
				case JOINED_PARTY:
				{
					// Check if hit limit
					if (!KconEventLogic.hitLimit(Field.MCMMO_PARTY_JOIN,
							player, config.getLimitValue(
									Field.MCMMO_PARTY_CHANGE, null, null),
							null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_PARTY_JOIN, player,
								null, null))
						{
							// Increment
							db.incrementData(Field.MCMMO_PARTY_JOIN,
									player.getName(), null, null);
						}
					}
					break;
				}
				case LEFT_PARTY:
				{
					// Check if hit limit
					if (!KconEventLogic.hitLimit(Field.MCMMO_PARTY_LEAVE,
							player, config.getLimitValue(
									Field.MCMMO_PARTY_LEAVE, null, null), null,
							null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_PARTY_LEAVE, player,
								null, null))
						{
							// Increment
							db.incrementData(Field.MCMMO_PARTY_LEAVE,
									player.getName(), null, null);
						}
					}
					break;
				}
				case KICKED_FROM_PARTY:
				{
					// Check if hit limit
					if (!KconEventLogic.hitLimit(Field.MCMMO_PARTY_KICK,
							player, config.getLimitValue(
									Field.MCMMO_PARTY_KICK, null, null), null,
							null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_PARTY_KICK, player,
								null, null))
						{
							// Increment
							db.incrementData(Field.MCMMO_PARTY_KICK,
									player.getName(), null, null);
						}
					}
					break;
				}
				case CHANGED_PARTIES:
				{
					// Check if hit limit
					if (!KconEventLogic.hitLimit(Field.MCMMO_PARTY_CHANGE,
							player, config.getLimitValue(
									Field.MCMMO_PARTY_CHANGE, null, null),
							null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_PARTY_CHANGE, player,
								null, null))
						{
							// Increment
							db.incrementData(Field.MCMMO_PARTY_CHANGE,
									player.getName(), null, null);
						}
					}
					break;
				}
				default:
				{
					if (plugin.getPluginConfig().debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled reason '" + event.getReason().name()
										+ " for " + event.getEventName());
					}
					break;
				}
			}
			if (plugin.getPluginConfig().debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				if (event.getOldParty() != null)
				{
					details.put("Party", event.getOldParty());
				}
				if (event.getNewParty() != null)
				{
					details.put("NewParty", event.getNewParty());
				}
				details.put("Reason", event.getReason().name());
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

}
