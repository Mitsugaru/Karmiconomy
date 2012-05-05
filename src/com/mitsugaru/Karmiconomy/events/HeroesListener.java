package com.mitsugaru.Karmiconomy.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.herocraftonline.heroes.api.events.ClassChangeEvent;
import com.herocraftonline.heroes.api.events.ExperienceChangeEvent;
import com.herocraftonline.heroes.api.events.HeroChangeLevelEvent;
import com.herocraftonline.heroes.api.events.HeroEnterCombatEvent;
import com.herocraftonline.heroes.api.events.HeroJoinPartyEvent;
import com.herocraftonline.heroes.api.events.HeroKillHeroEvent;
import com.herocraftonline.heroes.api.events.HeroLeaveCombatEvent;
import com.herocraftonline.heroes.api.events.HeroLeavePartyEvent;
import com.herocraftonline.heroes.api.events.HeroRegainHealthEvent;
import com.herocraftonline.heroes.api.events.HeroRegainManaEvent;
import com.herocraftonline.heroes.api.events.SkillCompleteEvent;
import com.herocraftonline.heroes.api.events.SkillDamageEvent;
import com.herocraftonline.heroes.api.events.SkillUseEvent;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.config.HeroesConfig;
import com.mitsugaru.Karmiconomy.database.Field;

public class HeroesListener implements Listener
{
	private Karmiconomy plugin;
	private HeroesConfig config;

	public HeroesListener(Karmiconomy plugin)
	{
		this.plugin = plugin;
		config = new HeroesConfig(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void classChangeValid(ClassChangeEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_CLASS_CHANGE)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (config.checkWorld(Field.HEROES_CLASS_CHANGE, player.getWorld()
					.getName()))
			{
				if (EventLogic.deny(Field.HEROES_CLASS_CHANGE, player, config,
						null, null))
				{
					// Deny
					event.setCancelled(true);
					if (plugin.getPluginConfig().debugEvents)
					{
						final Map<String, String> details = new HashMap<String, String>();
						details.put("Player", player.getName());
						if (event.getFrom() != null)
						{
							details.put("Class from", event.getFrom().getName());
						}
						if (event.getTo() != null)
						{
							details.put("To", event.getTo().getName());
						}
						details.put("Cancelled", "true");
						EventLogic.debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void classChange(ClassChangeEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_CLASS_CHANGE)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_CLASS_CHANGE, player
						.getWorld().getName()))
				{
					EventLogic.hitPayIncrement(Field.HEROES_CLASS_CHANGE,
							player, config, null, null);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void expChangeValid(ExperienceChangeEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_EXP_CHANGE)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_EXP_CHANGE, player
						.getWorld().getName()))
				{
					if (EventLogic.deny(Field.HEROES_EXP_CHANGE, player,
							config, null, null))
					{
						// Deny
						event.setCancelled(true);
						if (plugin.getPluginConfig().debugEvents)
						{
							final Map<String, String> details = new HashMap<String, String>();
							details.put("Player", player.getName());
							details.put("Exp", "" + event.getExpChange());
							details.put("Cancelled", "true");
							EventLogic.debugEvent(event, details);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void expChange(ExperienceChangeEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_EXP_CHANGE)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_EXP_CHANGE, player
						.getWorld().getName()))
				{
					EventLogic.hitPayIncrement(Field.HEROES_EXP_CHANGE, player,
							config, null, null);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void changeLevelEvent(HeroChangeLevelEvent event)
	{
		if (config.isEnabled(Field.HEROES_CHANGE_LEVEL)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_CHANGE_LEVEL, player
						.getWorld().getName()))
				{
					EventLogic.hitPayIncrement(Field.HEROES_CHANGE_LEVEL,
							player, config, null, null);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void combatEnter(HeroEnterCombatEvent event)
	{
		if (config.isEnabled(Field.HEROES_COMBAT_ENTER)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_COMBAT_ENTER, player
						.getWorld().getName()))
				{
					EventLogic.hitPayIncrement(Field.HEROES_COMBAT_ENTER,
							player, config, null, null);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void combatLeave(HeroLeaveCombatEvent event)
	{
		if (config.isEnabled(Field.HEROES_COMBAT_LEAVE)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_COMBAT_LEAVE, player
						.getWorld().getName()))
				{
					EventLogic.hitPayIncrement(Field.HEROES_COMBAT_LEAVE,
							player, config, null, null);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void joinPartyValid(HeroJoinPartyEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_PARTY_JOIN)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_PARTY_JOIN, player
						.getWorld().getName()))
				{
					if (EventLogic.deny(Field.HEROES_PARTY_JOIN, player,
							config, null, null))
					{
						// Deny
						event.setCancelled(true);
						if (plugin.getPluginConfig().debugEvents)
						{
							final Map<String, String> details = new HashMap<String, String>();
							details.put("Player", player.getName());
							details.put("Cancelled", "true");
							EventLogic.debugEvent(event, details);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void joinParty(HeroJoinPartyEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_PARTY_JOIN)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_PARTY_JOIN, player
						.getWorld().getName()))
				{
					EventLogic.hitPayIncrement(Field.HEROES_PARTY_JOIN, player,
							config, null, null);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void leavePartyValid(HeroLeavePartyEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_PARTY_LEAVE)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_PARTY_LEAVE, player
						.getWorld().getName()))
				{
					if (EventLogic.deny(Field.HEROES_PARTY_LEAVE, player,
							config, null, null))
					{
						// Deny
						event.setCancelled(true);
						if (plugin.getPluginConfig().debugEvents)
						{
							final Map<String, String> details = new HashMap<String, String>();
							details.put("Player", player.getName());
							details.put("Cancelled", "true");
							EventLogic.debugEvent(event, details);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void leaveParty(HeroLeavePartyEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_PARTY_LEAVE)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_PARTY_LEAVE, player
						.getWorld().getName()))
				{
					EventLogic.hitPayIncrement(Field.HEROES_PARTY_LEAVE,
							player, config, null, null);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void killHero(HeroKillHeroEvent event)
	{
		if (event.getAttacker() != null)
		{
			final Player player = event.getAttacker().getPlayer();
			if (player != null)
			{
				switch (event.getReason())
				{
					case ATTACKED_PLAYER:
					{
						if (config.isEnabled(Field.HEROES_KILL_ATTACK_PLAYER))
						{
							if (config.checkWorld(
									Field.HEROES_KILL_ATTACK_PLAYER, player
											.getWorld().getName()))
							{
								EventLogic.hitPayIncrement(
										Field.HEROES_KILL_ATTACK_PLAYER,
										player, config, null, null);
							}
						}
						break;
					}
					case ATTACKED_MOB:
					{
						if (config.isEnabled(Field.HEROES_KILL_ATTACK_MOB))
						{
							if (config.checkWorld(Field.HEROES_KILL_ATTACK_MOB,
									player.getWorld().getName()))
							{
								EventLogic.hitPayIncrement(
										Field.HEROES_KILL_ATTACK_MOB, player,
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
									"Unhandled attack reason '"
											+ event.getReason().name()
											+ " for " + event.getEventName());
						}
						break;
					}
				}
			}
		}
		if (event.getDefender() != null)
		{
			final Player player = event.getDefender().getPlayer();
			if (player != null)
			{

				switch (event.getReason())
				{
					case DAMAGED_BY_PLAYER:
					{
						if (config.isEnabled(Field.HEROES_KILL_DEFEND_PLAYER))
						{
							if (config.checkWorld(
									Field.HEROES_KILL_DEFEND_PLAYER, player
											.getWorld().getName()))
							{
								EventLogic.hitPayIncrement(
										Field.HEROES_KILL_DEFEND_PLAYER,
										player, config, null, null);
							}
						}
						break;
					}
					case DAMAGED_BY_MOB:
					{
						if (config.isEnabled(Field.HEROES_KILL_DEFEND_MOB))
						{
							if (config.checkWorld(Field.HEROES_KILL_DEFEND_MOB,
									player.getWorld().getName()))
							{
								EventLogic.hitPayIncrement(
										Field.HEROES_KILL_DEFEND_MOB, player,
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
									"Unhandled defend reason '"
											+ event.getReason().name()
											+ " for " + event.getEventName());
						}
						break;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void regainHealthValid(HeroRegainHealthEvent event)
	{
		if (!event.isCancelled()
				&& config.isEnabled(Field.HEROES_REGAIN_HEALTH)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_REGAIN_HEALTH, player
						.getWorld().getName()))
				{
					if (EventLogic.deny(Field.HEROES_REGAIN_HEALTH, player,
							config, null, null))
					{
						// Deny
						event.setCancelled(true);
						if (plugin.getPluginConfig().debugEvents)
						{
							final Map<String, String> details = new HashMap<String, String>();
							details.put("Player", player.getName());
							details.put("Health regain", "" + event.getAmount());
							details.put("Cancelled", "true");
							EventLogic.debugEvent(event, details);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void regainHealth(HeroRegainHealthEvent event)
	{
		if (!event.isCancelled()
				&& config.isEnabled(Field.HEROES_REGAIN_HEALTH)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_REGAIN_HEALTH, player
						.getWorld().getName()))
				{
					EventLogic.hitPayIncrement(Field.HEROES_REGAIN_HEALTH,
							player, config, null, null);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void regainManaValid(HeroRegainManaEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_REGAIN_MANA)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_REGAIN_MANA, player
						.getWorld().getName()))
				{
					if (EventLogic.deny(Field.HEROES_REGAIN_MANA, player,
							config, null, null))
					{
						// Deny
						event.setCancelled(true);
						if (plugin.getPluginConfig().debugEvents)
						{
							final Map<String, String> details = new HashMap<String, String>();
							details.put("Player", player.getName());
							details.put("Mana regain", "" + event.getAmount());
							details.put("Cancelled", "true");
							EventLogic.debugEvent(event, details);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void regainMana(HeroRegainManaEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_REGAIN_MANA)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_REGAIN_MANA, player
						.getWorld().getName()))
				{
					EventLogic.hitPayIncrement(Field.HEROES_REGAIN_MANA,
							player, config, null, null);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void skillComplete(SkillCompleteEvent event)
	{
		// TODO check if successful?
		if (config.isEnabled(Field.HEROES_SKILL_COMPLETE)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_SKILL_COMPLETE, player
						.getWorld().getName()))
				{
					EventLogic.hitPayIncrement(Field.HEROES_SKILL_COMPLETE,
							player, config, null, null);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void skillUseValid(SkillUseEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_SKILL_USE)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_SKILL_USE, player.getWorld()
						.getName()))
				{
					if (EventLogic.deny(Field.HEROES_SKILL_USE, player, config,
							null, null))
					{
						// Deny
						event.setCancelled(true);
						if (plugin.getPluginConfig().debugEvents)
						{
							final Map<String, String> details = new HashMap<String, String>();
							details.put("Player", player.getName());
							details.put("Skill", ""
									+ event.getSkill().getName());
							details.put("Cancelled", "true");
							EventLogic.debugEvent(event, details);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void skillUse(SkillUseEvent event)
	{
		if (!event.isCancelled() && config.isEnabled(Field.HEROES_SKILL_USE)
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (config.checkWorld(Field.HEROES_SKILL_USE, player.getWorld()
						.getName()))
				{
					EventLogic.hitPayIncrement(Field.HEROES_SKILL_USE, player,
							config, null, null);
				}
			}
		}
	}

	public void skillDamageValid(SkillDamageEvent event)
	{
		// cancellable
		// TODO implement
	}

	public void skillDamage(SkillDamageEvent event)
	{
		// cancellable
		// TODO implement
	}

	public void weaponDamageValid(WeaponDamageEvent event)
	{
		// cancellable
		// TODO implement
	}

	public void weaponDamage(WeaponDamageEvent event)
	{
		// cancellable
		// TODO implement
	}
}
