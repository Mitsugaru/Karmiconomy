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
					if (config.isEnabled(Field.MCMMO_GAIN_ACROBATICS))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_ACROBATICS,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_ACROBATICS, player,
									config, null, null);
						}
					}
					break;
				}
				case ARCHERY:
				{
					if (config.isEnabled(Field.MCMMO_GAIN_ARCHERY))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_ARCHERY, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_ARCHERY, player, config,
									null, null);
						}
					}
					break;
				}
				case AXES:
				{
					if (config.isEnabled(Field.MCMMO_GAIN_AXES))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_AXES, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_GAIN_AXES,
									player, config, null, null);
						}
					}
					break;
				}
				case EXCAVATION:
				{
					if (config.isEnabled(Field.MCMMO_GAIN_EXCAVATION))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_EXCAVATION,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_EXCAVATION, player,
									config, null, null);
						}
					}
					break;
				}
				case FISHING:
				{
					if (config.isEnabled(Field.MCMMO_GAIN_FISHING))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_FISHING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_FISHING, player, config,
									null, null);
						}
					}
					break;
				}
				case HERBALISM:
				{
					if (config.isEnabled(Field.MCMMO_GAIN_HERBALISM))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_HERBALISM,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_HERBALISM, player, config,
									null, null);
						}
					}
					break;
				}
				case MINING:
				{
					if (config.isEnabled(Field.MCMMO_GAIN_MINING))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_MINING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_GAIN_MINING,
									player, config, null, null);
						}
					}
					break;
				}
				case REPAIR:
				{
					if (config.isEnabled(Field.MCMMO_GAIN_REPAIR))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_REPAIR, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_GAIN_REPAIR,
									player, config, null, null);
						}
					}
					break;
				}
				case SWORDS:
				{
					if (config.isEnabled(Field.MCMMO_GAIN_SWORDS))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_SWORDS, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_GAIN_SWORDS,
									player, config, null, null);
						}
					}
					break;
				}
				case TAMING:
				{
					if (config.isEnabled(Field.MCMMO_GAIN_SWORDS))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_TAMING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_GAIN_TAMING,
									player, config, null, null);
						}
					}
					break;
				}
				case UNARMED:
				{
					if (config.isEnabled(Field.MCMMO_GAIN_UNARMED))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_UNARMED, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_UNARMED, player, config,
									null, null);
						}
					}
					break;
				}
				case WOODCUTTING:
				{
					if (config.isEnabled(Field.MCMMO_GAIN_WOODCUTTING))
					{
						if (config.checkWorld(Field.MCMMO_GAIN_WOODCUTTING,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_GAIN_WOODCUTTING, player,
									config, null, null);
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
					if (config.isEnabled(Field.MCMMO_LEVEL_ACROBATICS))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_ACROBATICS,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_ACROBATICS, player,
									config, null, null);
						}
					}
					break;
				}
				case ARCHERY:
				{
					if (config.isEnabled(Field.MCMMO_LEVEL_ARCHERY))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_ARCHERY, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_ARCHERY, player, config,
									null, null);
						}
					}
					break;
				}
				case AXES:
				{
					if (config.isEnabled(Field.MCMMO_LEVEL_AXES))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_AXES, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_LEVEL_AXES,
									player, config, null, null);
						}
					}
					break;
				}
				case EXCAVATION:
				{
					if (config.isEnabled(Field.MCMMO_LEVEL_EXCAVATION))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_EXCAVATION,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_EXCAVATION, player,
									config, null, null);
						}
					}
					break;
				}
				case FISHING:
				{
					if (config.isEnabled(Field.MCMMO_LEVEL_FISHING))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_FISHING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_FISHING, player, config,
									null, null);
						}
					}
					break;
				}
				case HERBALISM:
				{
					if (config.isEnabled(Field.MCMMO_LEVEL_HERBALISM))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_HERBALISM,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_HERBALISM, player,
									config, null, null);
						}
					}
					break;
				}
				case MINING:
				{
					if (config.isEnabled(Field.MCMMO_LEVEL_MINING))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_MINING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_MINING, player, config,
									null, null);
						}
					}
					break;
				}
				case REPAIR:
				{
					if (config.isEnabled(Field.MCMMO_LEVEL_REPAIR))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_REPAIR, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_REPAIR, player, config,
									null, null);
						}
					}
					break;
				}
				case SWORDS:
				{
					if (config.isEnabled(Field.MCMMO_LEVEL_SWORDS))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_SWORDS, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_SWORDS, player, config,
									null, null);
						}
					}
					break;
				}
				case TAMING:
				{
					if (config.isEnabled(Field.MCMMO_LEVEL_TAMING))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_TAMING, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_TAMING, player, config,
									null, null);
						}
					}
					break;
				}
				case UNARMED:
				{
					if (config.isEnabled(Field.MCMMO_LEVEL_UNARMED))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_UNARMED, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_UNARMED, player, config,
									null, null);
						}
					}
					break;
				}
				case WOODCUTTING:
				{
					if (config.isEnabled(Field.MCMMO_LEVEL_WOODCUTTING))
					{
						if (config.checkWorld(Field.MCMMO_LEVEL_WOODCUTTING,
								player.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_LEVEL_WOODCUTTING, player,
									config, null, null);
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
		if (!event.isCancelled()
				&& config.isEnabled(Field.MCMMO_PARTY_TELEPORT)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (config.checkWorld(Field.MCMMO_PARTY_TELEPORT, player.getWorld()
					.getName()))
			{
				if (EventLogic.deny(Field.MCMMO_PARTY_TELEPORT, player, config,
						null, null))
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
		if (!event.isCancelled()
				&& config.isEnabled(Field.MCMMO_PARTY_TELEPORT)
				&& event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			if (config.checkWorld(Field.MCMMO_PARTY_TELEPORT, player.getWorld()
					.getName()))
			{
				EventLogic.hitPayIncrement(Field.MCMMO_PARTY_TELEPORT, player,
						config, null, null);
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
					if (config.isEnabled(Field.MCMMO_PARTY_JOIN))
					{
						if (config.checkWorld(Field.MCMMO_PARTY_JOIN, player
								.getWorld().getName()))
						{
							if (EventLogic.deny(Field.MCMMO_PARTY_JOIN, player,
									config, null, null))
							{
								cancelled = true;
							}
						}
					}
					break;
				}
				case LEFT_PARTY:
				{
					if (config.isEnabled(Field.MCMMO_PARTY_LEAVE))
					{
						if (config.checkWorld(Field.MCMMO_PARTY_LEAVE, player
								.getWorld().getName()))
						{
							if (EventLogic.deny(Field.MCMMO_PARTY_LEAVE,
									player, config, null, null))
							{
								cancelled = true;
							}
						}
					}
					break;
				}
				case KICKED_FROM_PARTY:
				{
					if (config.isEnabled(Field.MCMMO_PARTY_KICK))
					{
						if (config.checkWorld(Field.MCMMO_PARTY_KICK, player
								.getWorld().getName()))
						{
							if (EventLogic.deny(Field.MCMMO_PARTY_KICK, player,
									config, null, null))
							{
								cancelled = true;
							}
						}
					}
					break;
				}
				case CHANGED_PARTIES:
				{
					if (config.isEnabled(Field.MCMMO_PARTY_CHANGE))
					{
						if (config.checkWorld(Field.MCMMO_PARTY_CHANGE, player
								.getWorld().getName()))
						{
							if (EventLogic.deny(Field.MCMMO_PARTY_CHANGE,
									player, config, null, null))
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
		if (!event.isCancelled() && event.getPlayer() != null)
		{
			final Player player = event.getPlayer();
			switch (event.getReason())
			{
				case JOINED_PARTY:
				{
					if (config.isEnabled(Field.MCMMO_PARTY_JOIN))
					{
						if (config.checkWorld(Field.MCMMO_PARTY_JOIN, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_PARTY_JOIN,
									player, config, null, null);
						}
					}
					break;
				}
				case LEFT_PARTY:
				{
					if (config.isEnabled(Field.MCMMO_PARTY_LEAVE))
					{
						if (config.checkWorld(Field.MCMMO_PARTY_LEAVE, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_PARTY_LEAVE,
									player, config, null, null);
						}
					}
					break;
				}
				case KICKED_FROM_PARTY:
				{
					if (config.isEnabled(Field.MCMMO_PARTY_KICK))
					{
						if (config.checkWorld(Field.MCMMO_PARTY_KICK, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(Field.MCMMO_PARTY_KICK,
									player, config, null, null);
						}
					}
					break;
				}
				case CHANGED_PARTIES:
				{
					if (config.isEnabled(Field.MCMMO_PARTY_CHANGE))
					{
						if (config.checkWorld(Field.MCMMO_PARTY_CHANGE, player
								.getWorld().getName()))
						{
							EventLogic.hitPayIncrement(
									Field.MCMMO_PARTY_CHANGE, player, config,
									null, null);
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
