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
		if (!event.isCancelled() && HeroesConfig.classChange
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (EventLogic
					.deny(Field.HEROES_CLASS_CHANGE, player,
							HeroesConfig.classChangeDenyPay, config
									.getPayValue(Field.HEROES_CLASS_CHANGE,
											null, null),
							HeroesConfig.classChangeDenyLimit, config
									.getLimitValue(Field.HEROES_CLASS_CHANGE,
											null, null), null, null))
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void classChange(ClassChangeEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.classChange
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				EventLogic.hitPayIncrement(
						Field.HEROES_CLASS_CHANGE, player,
						config.getLimitValue(
								Field.HEROES_CLASS_CHANGE,
								null, null), config.getPayValue(
								Field.HEROES_CLASS_CHANGE,
								null, null), null, null);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void expChangeValid(ExperienceChangeEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.expChange
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (EventLogic
						.deny(Field.HEROES_EXP_CHANGE, player,
								HeroesConfig.expChangeDenyPay, config
										.getPayValue(Field.HEROES_EXP_CHANGE,
												null, null),
								HeroesConfig.expChangeDenyLimit, config
										.getLimitValue(Field.HEROES_EXP_CHANGE,
												null, null), null, null))
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void expChange(ExperienceChangeEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.expChange
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				EventLogic.hitPayIncrement(
						Field.HEROES_EXP_CHANGE, player,
						config.getLimitValue(
								Field.HEROES_EXP_CHANGE,
								null, null), config.getPayValue(
								Field.HEROES_EXP_CHANGE,
								null, null), null, null);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void changeLevelEvent(HeroChangeLevelEvent event)
	{
		if (HeroesConfig.changeLevel && event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				EventLogic.hitPayIncrement(
						Field.HEROES_CHANGE_LEVEL, player,
						config.getLimitValue(
								Field.HEROES_CHANGE_LEVEL,
								null, null), config.getPayValue(
								Field.HEROES_CHANGE_LEVEL,
								null, null), null, null);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void combatEnter(HeroEnterCombatEvent event)
	{
		if (HeroesConfig.combatEnter && event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				EventLogic.hitPayIncrement(
						Field.HEROES_COMBAT_ENTER, player,
						config.getLimitValue(
								Field.HEROES_COMBAT_ENTER,
								null, null), config.getPayValue(
								Field.HEROES_COMBAT_ENTER,
								null, null), null, null);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void combatLeave(HeroLeaveCombatEvent event)
	{
		if (HeroesConfig.combatLeave && event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				EventLogic.hitPayIncrement(
						Field.HEROES_COMBAT_LEAVE, player,
						config.getLimitValue(
								Field.HEROES_COMBAT_LEAVE,
								null, null), config.getPayValue(
								Field.HEROES_COMBAT_LEAVE,
								null, null), null, null);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void joinPartyValid(HeroJoinPartyEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.partyJoin
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (EventLogic
						.deny(Field.HEROES_PARTY_JOIN, player,
								HeroesConfig.regainHealthDenyPay, config
										.getPayValue(Field.HEROES_PARTY_JOIN,
												null, null),
								HeroesConfig.regainHealthDenyLimit, config
										.getLimitValue(Field.HEROES_PARTY_JOIN,
												null, null), null, null))
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void joinParty(HeroJoinPartyEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.partyJoin
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				EventLogic.hitPayIncrement(
						Field.HEROES_PARTY_JOIN, player,
						config.getLimitValue(
								Field.HEROES_PARTY_JOIN,
								null, null), config.getPayValue(
								Field.HEROES_PARTY_JOIN,
								null, null), null, null);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void leavePartyValid(HeroLeavePartyEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.partyLeave
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (EventLogic.deny(Field.HEROES_PARTY_LEAVE, player,
						HeroesConfig.regainHealthDenyPay, config.getPayValue(
								Field.HEROES_PARTY_LEAVE, null, null),
						HeroesConfig.regainHealthDenyLimit, config
								.getLimitValue(Field.HEROES_PARTY_LEAVE, null,
										null), null, null))
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void leaveParty(HeroLeavePartyEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.partyLeave
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				EventLogic.hitPayIncrement(
						Field.HEROES_PARTY_LEAVE, player,
						config.getLimitValue(
								Field.HEROES_PARTY_LEAVE,
								null, null), config.getPayValue(
								Field.HEROES_PARTY_LEAVE,
								null, null), null, null);
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
						if (HeroesConfig.killAttackPlayer)
						{
							EventLogic.hitPayIncrement(
									Field.HEROES_KILL_ATTACK_PLAYER, player,
									config.getLimitValue(
											Field.HEROES_KILL_ATTACK_PLAYER,
											null, null), config.getPayValue(
											Field.HEROES_KILL_ATTACK_PLAYER,
											null, null), null, null);
						}
						break;
					}
					case ATTACKED_MOB:
					{
						if (HeroesConfig.killAttackMob)
						{
							EventLogic.hitPayIncrement(
									Field.HEROES_KILL_DEFEND_PLAYER, player,
									config.getLimitValue(
											Field.HEROES_KILL_DEFEND_PLAYER, null,
											null), config.getPayValue(
											Field.HEROES_KILL_DEFEND_PLAYER, null,
											null), null, null);
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
						if (HeroesConfig.killDefendPlayer)
						{
							EventLogic.hitPayIncrement(
									Field.HEROES_KILL_DEFEND_PLAYER, player,
									config.getLimitValue(
											Field.HEROES_KILL_DEFEND_PLAYER,
											null, null), config.getPayValue(
											Field.HEROES_KILL_DEFEND_PLAYER,
											null, null), null, null);
						}
						break;
					}
					case DAMAGED_BY_MOB:
					{
						if (HeroesConfig.killDefendMob)
						{
							EventLogic.hitPayIncrement(
									Field.HEROES_KILL_DEFEND_MOB, player,
									config.getLimitValue(
											Field.HEROES_KILL_DEFEND_MOB, null,
											null), config.getPayValue(
											Field.HEROES_KILL_DEFEND_MOB, null,
											null), null, null);
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
		if (!event.isCancelled() && HeroesConfig.regainHealth
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (EventLogic.deny(Field.HEROES_REGAIN_HEALTH, player,
						HeroesConfig.regainHealthDenyPay, config.getPayValue(
								Field.HEROES_REGAIN_HEALTH, null, null),
						HeroesConfig.regainHealthDenyLimit, config
								.getLimitValue(Field.HEROES_REGAIN_HEALTH,
										null, null), null, null))
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void regainHealth(HeroRegainHealthEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.regainHealth
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				EventLogic.hitPayIncrement(
						Field.HEROES_REGAIN_HEALTH, player,
						config.getLimitValue(
								Field.HEROES_REGAIN_HEALTH,
								null, null), config.getPayValue(
								Field.HEROES_REGAIN_HEALTH,
								null, null), null, null);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void regainManaValid(HeroRegainManaEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.regainMana
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (EventLogic.deny(Field.HEROES_REGAIN_MANA, player,
						HeroesConfig.regainManaDenyPay, config.getPayValue(
								Field.HEROES_REGAIN_MANA, null, null),
						HeroesConfig.regainManaDenyLimit, config.getLimitValue(
								Field.HEROES_REGAIN_MANA, null, null), null,
						null))
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void regainMana(HeroRegainManaEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.regainMana
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				EventLogic.hitPayIncrement(
						Field.HEROES_REGAIN_MANA, player,
						config.getLimitValue(
								Field.HEROES_REGAIN_MANA,
								null, null), config.getPayValue(
								Field.HEROES_REGAIN_MANA,
								null, null), null, null);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void skillComplete(SkillCompleteEvent event)
	{
		// TODO check if successful?
		if (HeroesConfig.skillComplete && event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				EventLogic.hitPayIncrement(
						Field.HEROES_SKILL_COMPLETE, player,
						config.getLimitValue(
								Field.HEROES_SKILL_COMPLETE,
								null, null), config.getPayValue(
								Field.HEROES_SKILL_COMPLETE,
								null, null), null, null);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void skillUseValid(SkillUseEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.skillUse
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				if (EventLogic
						.deny(Field.HEROES_SKILL_USE, player,
								HeroesConfig.skillUseDenyPay, config
										.getPayValue(Field.HEROES_SKILL_USE,
												null, null),
								HeroesConfig.skillUseDenyLimit, config
										.getLimitValue(Field.HEROES_SKILL_USE,
												null, null), null, null))
				{
					// Deny
					event.setCancelled(true);
					if (plugin.getPluginConfig().debugEvents)
					{
						final Map<String, String> details = new HashMap<String, String>();
						details.put("Player", player.getName());
						details.put("Skill", "" + event.getSkill().getName());
						details.put("Cancelled", "true");
						EventLogic.debugEvent(event, details);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void skillUse(SkillUseEvent event)
	{
		if (!event.isCancelled() && HeroesConfig.skillUse
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				EventLogic.hitPayIncrement(
						Field.HEROES_SKILL_USE, player,
						config.getLimitValue(
								Field.HEROES_SKILL_USE,
								null, null), config.getPayValue(
								Field.HEROES_SKILL_USE,
								null, null), null, null);
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
