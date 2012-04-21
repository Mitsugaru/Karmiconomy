package com.mitsugaru.Karmiconomy.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.gmail.nossr50.events.party.McMMOPartyChangeEvent;
import com.gmail.nossr50.events.party.McMMOPartyTeleportEvent;
import com.mitsugaru.Karmiconomy.DatabaseHandler;
import com.mitsugaru.Karmiconomy.KarmicEcon;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.KconMcMMOConfig;
import com.mitsugaru.Karmiconomy.DatabaseHandler.Field;

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
	public void playerXpGain(McMMOPlayerXpGainEvent event)
	{
		if (event.getPlayer() != null && event.getSkill() != null)
		{
			final Player player = event.getPlayer();
			switch (event.getSkill())
			{
				case ACROBATICS:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_ACROBATICS, player, config
									.getLimitValue(Field.MCMMO_GAIN_ACROBATICS, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_ACROBATICS, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_ACROBATICS,
									player.getName(), null, null);
						}
					}
					break;
				}
				case ARCHERY:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_ARCHERY, player, config
									.getLimitValue(Field.MCMMO_GAIN_ARCHERY, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_ARCHERY, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_ARCHERY,
									player.getName(), null, null);
						}
					}
					break;
				}
				case AXES:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_AXES, player, config
									.getLimitValue(Field.MCMMO_GAIN_AXES, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_AXES, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_AXES,
									player.getName(), null, null);
						}
					}
					break;
				}
				case EXCAVATION:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_EXCAVATION, player, config
									.getLimitValue(Field.MCMMO_GAIN_EXCAVATION, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_EXCAVATION, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_EXCAVATION,
									player.getName(), null, null);
						}
					}
					break;
				}
				case FISHING:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_FISHING, player, config
									.getLimitValue(Field.MCMMO_GAIN_FISHING, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_FISHING, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_FISHING,
									player.getName(), null, null);
						}
					}
					break;
				}
				case HERBALISM:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_HERBALISM, player, config
									.getLimitValue(Field.MCMMO_GAIN_HERBALISM, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_HERBALISM, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_HERBALISM,
									player.getName(), null, null);
						}
					}
					break;
				}
				case MINING:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_MINING, player, config
									.getLimitValue(Field.MCMMO_GAIN_MINING, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_MINING, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_MINING,
									player.getName(), null, null);
						}
					}
					break;
				}
				case REPAIR:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_REPAIR, player, config
									.getLimitValue(Field.MCMMO_GAIN_REPAIR, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_REPAIR, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_REPAIR,
									player.getName(), null, null);
						}
					}
					break;
				}
				case SWORDS:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_SWORDS, player, config
									.getLimitValue(Field.MCMMO_GAIN_SWORDS, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_SWORDS, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_SWORDS,
									player.getName(), null, null);
						}
					}
					break;
				}
				case TAMING:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_TAMING, player, config
									.getLimitValue(Field.MCMMO_GAIN_TAMING, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_TAMING, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_TAMING,
									player.getName(), null, null);
						}
					}
					break;
				}
				case UNARMED:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_UNARMED, player, config
									.getLimitValue(Field.MCMMO_GAIN_UNARMED, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_UNARMED, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_UNARMED,
									player.getName(), null, null);
						}
					}
					break;
				}
				case WOODCUTTING:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_GAIN_WOODCUTTING, player, config
									.getLimitValue(Field.MCMMO_GAIN_WOODCUTTING, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_GAIN_WOODCUTTING, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_GAIN_WOODCUTTING,
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
				details.put("XPGained", "" + event.getXpGained());
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void playerLevelUp(McMMOPlayerLevelUpEvent event)
	{
		if (event.getPlayer() != null && event.getSkill() != null)
		{
			final Player player = event.getPlayer();
			switch (event.getSkill())
			{
				case ACROBATICS:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_ACROBATICS, player, config
									.getLimitValue(Field.MCMMO_LEVEL_ACROBATICS, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_ACROBATICS, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_ACROBATICS,
									player.getName(), null, null);
						}
					}
					break;
				}
				case ARCHERY:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_ARCHERY, player, config
									.getLimitValue(Field.MCMMO_LEVEL_ARCHERY, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_ARCHERY, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_ARCHERY,
									player.getName(), null, null);
						}
					}
					break;
				}
				case AXES:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_AXES, player, config
									.getLimitValue(Field.MCMMO_LEVEL_AXES, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_AXES, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_AXES,
									player.getName(), null, null);
						}
					}
					break;
				}
				case EXCAVATION:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_EXCAVATION, player, config
									.getLimitValue(Field.MCMMO_LEVEL_EXCAVATION, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_EXCAVATION, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_EXCAVATION,
									player.getName(), null, null);
						}
					}
					break;
				}
				case FISHING:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_FISHING, player, config
									.getLimitValue(Field.MCMMO_LEVEL_FISHING, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_FISHING, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_FISHING,
									player.getName(), null, null);
						}
					}
					break;
				}
				case HERBALISM:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_HERBALISM, player, config
									.getLimitValue(Field.MCMMO_LEVEL_HERBALISM, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_HERBALISM, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_HERBALISM,
									player.getName(), null, null);
						}
					}
					break;
				}
				case MINING:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_MINING, player, config
									.getLimitValue(Field.MCMMO_LEVEL_MINING, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_MINING, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_MINING,
									player.getName(), null, null);
						}
					}
					break;
				}
				case REPAIR:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_REPAIR, player, config
									.getLimitValue(Field.MCMMO_LEVEL_REPAIR, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_REPAIR, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_REPAIR,
									player.getName(), null, null);
						}
					}
					break;
				}
				case SWORDS:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_SWORDS, player, config
									.getLimitValue(Field.MCMMO_LEVEL_SWORDS, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_SWORDS, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_SWORDS,
									player.getName(), null, null);
						}
					}
					break;
				}
				case TAMING:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_TAMING, player, config
									.getLimitValue(Field.MCMMO_LEVEL_TAMING, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_TAMING, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_TAMING,
									player.getName(), null, null);
						}
					}
					break;
				}
				case UNARMED:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_UNARMED, player, config
									.getLimitValue(Field.MCMMO_LEVEL_UNARMED, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_UNARMED, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_UNARMED,
									player.getName(), null, null);
						}
					}
					break;
				}
				case WOODCUTTING:
				{
					if (!KconEventLogic
							.hitLimit(Field.MCMMO_LEVEL_WOODCUTTING, player, config
									.getLimitValue(Field.MCMMO_LEVEL_WOODCUTTING, null,
											null), null, null))
					{
						// Try to pay
						if (KarmicEcon.pay(Field.MCMMO_LEVEL_WOODCUTTING, player, null,
								null))
						{
							// Increment
							db.incrementData(Field.MCMMO_LEVEL_WOODCUTTING,
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
				KconEventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void partyTeleportValid(McMMOPartyTeleportEvent event)
	{
		if (!event.isCancelled() && KconMcMMOConfig.partyTeleport
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (KconEventLogic
					.deny(Field.MCMMO_PARTY_TELEPORT, player,
							KconMcMMOConfig.partyTeleportDenyPay, config.getPayValue(
									Field.MCMMO_PARTY_TELEPORT, null, null),
									KconMcMMOConfig.partyTeleportDenyLimit, config
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
		if (!event.isCancelled() && KconMcMMOConfig.partyTeleport
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
		if (!event.isCancelled() && KconMcMMOConfig.partyTeleport
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			boolean cancelled = false;
			switch (event.getReason())
			{
				case JOINED_PARTY:
				{
					if (KconEventLogic.deny(Field.MCMMO_PARTY_JOIN, player,
							KconMcMMOConfig.partyJoinDenyPay, config.getPayValue(
									Field.MCMMO_PARTY_JOIN, null, null),
									KconMcMMOConfig.partyJoinDenyLimit, config.getLimitValue(
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
							KconMcMMOConfig.partyLeaveDenyPay, config.getPayValue(
									Field.MCMMO_PARTY_LEAVE, null, null),
									KconMcMMOConfig.partyLeaveDenyLimit, config.getLimitValue(
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
							KconMcMMOConfig.partyKickDenyPay, config.getPayValue(
									Field.MCMMO_PARTY_KICK, null, null),
									KconMcMMOConfig.partyKickDenyLimit, config.getLimitValue(
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
							KconMcMMOConfig.partyChangeDenyPay, config.getPayValue(
									Field.MCMMO_PARTY_CHANGE, null, null),
									KconMcMMOConfig.partyChangeDenyLimit, config.getLimitValue(
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
		if (!event.isCancelled() && KconMcMMOConfig.partyTeleport
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
