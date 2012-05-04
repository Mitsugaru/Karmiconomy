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
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.config.McMMOConfig;
import com.mitsugaru.Karmiconomy.database.Field;

public class McMMOListener implements Listener
{
	private Karmiconomy plugin;
	private McMMOConfig config;

	public McMMOListener(Karmiconomy plugin)
	{
		this.plugin = plugin;
		config = new McMMOConfig(plugin);
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
					if (McMMOConfig.acrobaticsXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_ACROBATICS,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_ACROBATICS, player,
									config.getLimitValue(
											Field.MCMMO_GAIN_ACROBATICS, null,
											null), config.getPayValue(
											Field.MCMMO_GAIN_ACROBATICS, null,
											null), null, null);
						}
					}
					break;
				}
				case ARCHERY:
				{
					if (McMMOConfig.archeryXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_ARCHERY, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_ARCHERY, player, config
											.getLimitValue(
													Field.MCMMO_GAIN_ARCHERY,
													null, null), config
											.getPayValue(
													Field.MCMMO_GAIN_ARCHERY,
													null, null), null, null);
						}
					}
					break;
				}
				case AXES:
				{
					if (McMMOConfig.axesXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_AXES, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_GAIN_AXES,
									player, config.getLimitValue(
											Field.MCMMO_GAIN_AXES, null, null),
									config.getPayValue(Field.MCMMO_GAIN_AXES,
											null, null), null, null);
						}
					}
					break;
				}
				case EXCAVATION:
				{
					if (McMMOConfig.excavationXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_EXCAVATION,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_EXCAVATION, player,
									config.getLimitValue(
											Field.MCMMO_GAIN_EXCAVATION, null,
											null), config.getPayValue(
											Field.MCMMO_GAIN_EXCAVATION, null,
											null), null, null);
						}
					}
					break;
				}
				case FISHING:
				{
					if (McMMOConfig.fishingXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_FISHING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_FISHING, player, config
											.getLimitValue(
													Field.MCMMO_GAIN_FISHING,
													null, null), config
											.getPayValue(
													Field.MCMMO_GAIN_FISHING,
													null, null), null, null);
						}
					}
					break;
				}
				case HERBALISM:
				{
					if (McMMOConfig.herbalismXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_HERBALISM,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_HERBALISM, player, config
											.getLimitValue(
													Field.MCMMO_GAIN_HERBALISM,
													null, null), config
											.getPayValue(
													Field.MCMMO_GAIN_HERBALISM,
													null, null), null, null);
						}
					}
					break;
				}
				case MINING:
				{
					if (McMMOConfig.miningXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_MINING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_GAIN_MINING,
									player, config
											.getLimitValue(
													Field.MCMMO_GAIN_MINING,
													null, null), config
											.getPayValue(
													Field.MCMMO_GAIN_MINING,
													null, null), null, null);
						}
					}
					break;
				}
				case REPAIR:
				{
					if (McMMOConfig.repairXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_REPAIR, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_GAIN_REPAIR,
									player, config
											.getLimitValue(
													Field.MCMMO_GAIN_REPAIR,
													null, null), config
											.getPayValue(
													Field.MCMMO_GAIN_REPAIR,
													null, null), null, null);
						}
					}
					break;
				}
				case SWORDS:
				{
					if (McMMOConfig.swordsXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_SWORDS, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_GAIN_SWORDS,
									player, config
											.getLimitValue(
													Field.MCMMO_GAIN_SWORDS,
													null, null), config
											.getPayValue(
													Field.MCMMO_GAIN_SWORDS,
													null, null), null, null);
						}
					}
					break;
				}
				case TAMING:
				{
					if (McMMOConfig.tamingXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_TAMING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_GAIN_TAMING,
									player, config
											.getLimitValue(
													Field.MCMMO_GAIN_TAMING,
													null, null), config
											.getPayValue(
													Field.MCMMO_GAIN_TAMING,
													null, null), null, null);
						}
					}
					break;
				}
				case UNARMED:
				{
					if (McMMOConfig.unarmedXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_UNARMED, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_UNARMED, player, config
											.getLimitValue(
													Field.MCMMO_GAIN_UNARMED,
													null, null), config
											.getPayValue(
													Field.MCMMO_GAIN_UNARMED,
													null, null), null, null);
						}
					}
					break;
				}
				case WOODCUTTING:
				{
					if (McMMOConfig.woodcuttingXp)
					{
						if (config.checkWorld(Field.MCMMO_GAIN_WOODCUTTING,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_WOODCUTTING, player,
									config.getLimitValue(
											Field.MCMMO_GAIN_WOODCUTTING, null,
											null), config.getPayValue(
											Field.MCMMO_GAIN_WOODCUTTING, null,
											null), null, null);
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
				EventLogic.debugEvent(event, details);
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
					if (McMMOConfig.acrobaticsLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_ACROBATICS,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_ACROBATICS, player,
									config.getLimitValue(
											Field.MCMMO_LEVEL_ACROBATICS, null,
											null), config.getPayValue(
											Field.MCMMO_LEVEL_ACROBATICS, null,
											null), null, null);
						}
					}
					break;
				}
				case ARCHERY:
				{
					if (McMMOConfig.archeryLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_ARCHERY, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_ARCHERY, player, config
											.getLimitValue(
													Field.MCMMO_LEVEL_ARCHERY,
													null, null), config
											.getPayValue(
													Field.MCMMO_LEVEL_ARCHERY,
													null, null), null, null);
						}
					}
					break;
				}
				case AXES:
				{
					if (McMMOConfig.axesLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_AXES, player
								.getWorld().getName()))
						{
							EventLogic
									.hitPayIncrement(Field.MCMMO_LEVEL_AXES,
											player, config.getLimitValue(
													Field.MCMMO_LEVEL_AXES,
													null, null),
											config.getPayValue(
													Field.MCMMO_LEVEL_AXES,
													null, null), null, null);
						}
					}
					break;
				}
				case EXCAVATION:
				{
					if (McMMOConfig.excavationLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_EXCAVATION,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_EXCAVATION, player,
									config.getLimitValue(
											Field.MCMMO_LEVEL_EXCAVATION, null,
											null), config.getPayValue(
											Field.MCMMO_LEVEL_EXCAVATION, null,
											null), null, null);
						}
					}
					break;
				}
				case FISHING:
				{
					if (McMMOConfig.fishingLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_FISHING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_FISHING, player, config
											.getLimitValue(
													Field.MCMMO_LEVEL_FISHING,
													null, null), config
											.getPayValue(
													Field.MCMMO_LEVEL_FISHING,
													null, null), null, null);
						}
					}
					break;
				}
				case HERBALISM:
				{
					if (McMMOConfig.herbalismLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_HERBALISM,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_HERBALISM, player,
									config.getLimitValue(
											Field.MCMMO_LEVEL_HERBALISM, null,
											null), config.getPayValue(
											Field.MCMMO_LEVEL_HERBALISM, null,
											null), null, null);
						}
					}
					break;
				}
				case MINING:
				{
					if (McMMOConfig.miningLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_MINING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_MINING, player, config
											.getLimitValue(
													Field.MCMMO_LEVEL_MINING,
													null, null), config
											.getPayValue(
													Field.MCMMO_LEVEL_MINING,
													null, null), null, null);
						}
					}
					break;
				}
				case REPAIR:
				{
					if (McMMOConfig.repairLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_REPAIR, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_REPAIR, player, config
											.getLimitValue(
													Field.MCMMO_LEVEL_REPAIR,
													null, null), config
											.getPayValue(
													Field.MCMMO_LEVEL_REPAIR,
													null, null), null, null);
						}
					}
					break;
				}
				case SWORDS:
				{
					if (McMMOConfig.swordsLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_SWORDS, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_SWORDS, player, config
											.getLimitValue(
													Field.MCMMO_LEVEL_SWORDS,
													null, null), config
											.getPayValue(
													Field.MCMMO_LEVEL_SWORDS,
													null, null), null, null);
						}
					}
					break;
				}
				case TAMING:
				{
					if (McMMOConfig.tamingLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_TAMING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_TAMING, player, config
											.getLimitValue(
													Field.MCMMO_LEVEL_TAMING,
													null, null), config
											.getPayValue(
													Field.MCMMO_LEVEL_TAMING,
													null, null), null, null);
						}
					}
					break;
				}
				case UNARMED:
				{
					if (McMMOConfig.unarmedLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_UNARMED, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_UNARMED, player, config
											.getLimitValue(
													Field.MCMMO_LEVEL_UNARMED,
													null, null), config
											.getPayValue(
													Field.MCMMO_LEVEL_UNARMED,
													null, null), null, null);
						}
					}
					break;
				}
				case WOODCUTTING:
				{
					if (McMMOConfig.woodcuttingLevel)
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_WOODCUTTING,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_WOODCUTTING, player,
									config.getLimitValue(
											Field.MCMMO_LEVEL_WOODCUTTING,
											null, null), config.getPayValue(
											Field.MCMMO_LEVEL_WOODCUTTING,
											null, null), null, null);
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
				EventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void partyTeleportValid(McMMOPartyTeleportEvent event)
	{
		if (!event.isCancelled() && McMMOConfig.partyTeleport
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (config.checkWorld(Field.MCMMO_PARTY_TELEPORT, player.getWorld()
					.getName()))
			{
				if (EventLogic.deny(Field.MCMMO_PARTY_TELEPORT, player,
						McMMOConfig.partyTeleportDenyPay, config.getPayValue(
								Field.MCMMO_PARTY_TELEPORT, null, null),
						McMMOConfig.partyTeleportDenyLimit, config
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
						EventLogic.debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void partyTeleport(McMMOPartyTeleportEvent event)
	{
		if (!event.isCancelled() && McMMOConfig.partyTeleport
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (config.checkWorld(Field.MCMMO_PARTY_TELEPORT, player.getWorld()
					.getName()))
			{
				EventLogic.hitPayIncrement(Field.MCMMO_PARTY_TELEPORT, player,
						config.getLimitValue(Field.MCMMO_PARTY_TELEPORT, null,
								null), config.getPayValue(
								Field.MCMMO_PARTY_TELEPORT, null, null), null,
						null);
			}
			if (plugin.getPluginConfig().debugEvents)
			{
				final Map<String, String> details = new HashMap<String, String>();
				details.put("Player", player.getName());
				details.put("Party", event.getParty());
				details.put("To", event.getTo().toString());
				EventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void partyChangeValid(McMMOPartyChangeEvent event)
	{
		if (!event.isCancelled() && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			boolean cancelled = false;
			switch (event.getReason())
			{
				case JOINED_PARTY:
				{
					if (McMMOConfig.partyJoin)
					{
						if (config.checkWorld(Field.MCMMO_PARTY_JOIN, player
								.getWorld().getName()))
						{
							if (EventLogic.deny(Field.MCMMO_PARTY_JOIN, player,
									McMMOConfig.partyJoinDenyPay, config
											.getPayValue(
													Field.MCMMO_PARTY_JOIN,
													null, null),
									McMMOConfig.partyJoinDenyLimit, config
											.getLimitValue(
													Field.MCMMO_PARTY_JOIN,
													null, null), null, null))
							{
								cancelled = true;
							}
						}
					}
					break;
				}
				case LEFT_PARTY:
				{
					if (McMMOConfig.partyLeave)
					{
						if (config.checkWorld(Field.MCMMO_PARTY_LEAVE, player
								.getWorld().getName()))
						{
							if (EventLogic.deny(Field.MCMMO_PARTY_LEAVE,
									player, McMMOConfig.partyLeaveDenyPay,
									config.getPayValue(Field.MCMMO_PARTY_LEAVE,
											null, null),
									McMMOConfig.partyLeaveDenyLimit, config
											.getLimitValue(
													Field.MCMMO_PARTY_LEAVE,
													null, null), null, null))
							{
								cancelled = true;
							}
						}
					}
					break;
				}
				case KICKED_FROM_PARTY:
				{
					if (McMMOConfig.partyKick)
					{
						if (config.checkWorld(Field.MCMMO_PARTY_KICK, player
								.getWorld().getName()))
						{
							if (EventLogic.deny(Field.MCMMO_PARTY_KICK, player,
									McMMOConfig.partyKickDenyPay, config
											.getPayValue(
													Field.MCMMO_PARTY_KICK,
													null, null),
									McMMOConfig.partyKickDenyLimit, config
											.getLimitValue(
													Field.MCMMO_PARTY_KICK,
													null, null), null, null))
							{
								cancelled = true;
							}
						}
					}
					break;
				}
				case CHANGED_PARTIES:
				{
					if (McMMOConfig.partyChange)
					{
						if (config.checkWorld(Field.MCMMO_PARTY_CHANGE, player
								.getWorld().getName()))
						{
							if (EventLogic.deny(Field.MCMMO_PARTY_CHANGE,
									player, McMMOConfig.partyChangeDenyPay,
									config.getPayValue(
											Field.MCMMO_PARTY_CHANGE, null,
											null),
									McMMOConfig.partyChangeDenyLimit, config
											.getLimitValue(
													Field.MCMMO_PARTY_CHANGE,
													null, null), null, null))
							{
								cancelled = true;
							}
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
				EventLogic.debugEvent(event, details);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void partyChange(McMMOPartyChangeEvent event)
	{
		if (!event.isCancelled() && McMMOConfig.partyChange
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			switch (event.getReason())
			{
				case JOINED_PARTY:
				{
					if (McMMOConfig.partyJoin)
					{
						if (config.checkWorld(Field.MCMMO_PARTY_JOIN, player
								.getWorld().getName()))
						{
							EventLogic
									.hitPayIncrement(Field.MCMMO_PARTY_JOIN,
											player, config.getLimitValue(
													Field.MCMMO_PARTY_JOIN,
													null, null),
											config.getPayValue(
													Field.MCMMO_PARTY_JOIN,
													null, null), null, null);
						}
					}
					break;
				}
				case LEFT_PARTY:
				{
					if (McMMOConfig.partyLeave)
					{
						if (config.checkWorld(Field.MCMMO_PARTY_LEAVE, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_PARTY_LEAVE,
									player, config
											.getLimitValue(
													Field.MCMMO_PARTY_LEAVE,
													null, null), config
											.getPayValue(
													Field.MCMMO_PARTY_LEAVE,
													null, null), null, null);
						}
					}
					break;
				}
				case KICKED_FROM_PARTY:
				{
					if (McMMOConfig.partyKick)
					{
						if (config.checkWorld(Field.MCMMO_PARTY_KICK, player
								.getWorld().getName()))
						{
							EventLogic
									.hitPayIncrement(Field.MCMMO_PARTY_KICK,
											player, config.getLimitValue(
													Field.MCMMO_PARTY_KICK,
													null, null),
											config.getPayValue(
													Field.MCMMO_PARTY_KICK,
													null, null), null, null);
						}
					}
					break;
				}
				case CHANGED_PARTIES:
				{
					if (McMMOConfig.partyChange)
					{
						if (config.checkWorld(Field.MCMMO_PARTY_CHANGE, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_PARTY_CHANGE, player, config
											.getLimitValue(
													Field.MCMMO_PARTY_CHANGE,
													null, null), config
											.getPayValue(
													Field.MCMMO_PARTY_CHANGE,
													null, null), null, null);
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
				EventLogic.debugEvent(event, details);
			}
		}
	}

}
