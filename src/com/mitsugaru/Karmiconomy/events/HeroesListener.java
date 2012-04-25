package com.mitsugaru.Karmiconomy.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
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

import com.mitsugaru.Karmiconomy.KarmicEcon;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.config.HeroesConfig;
import com.mitsugaru.Karmiconomy.database.DatabaseHandler;
import com.mitsugaru.Karmiconomy.database.Field;

public class HeroesListener implements Listener
{
	private Karmiconomy plugin;
	private HeroesConfig config;
	private DatabaseHandler db;

	public HeroesListener(Karmiconomy plugin)
	{
		this.plugin = plugin;
		config = new HeroesConfig(plugin);
		db = plugin.getDatabaseHandler();
	}

	public void classChangeValid(ClassChangeEvent event)
	{
		// Cancellable
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

	public void classChange(ClassChangeEvent event)
	{
		// Cancellable
		if (!event.isCancelled() && HeroesConfig.classChange
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
			if (player != null)
			{
				// Check if hit limit
				if (!EventLogic.hitLimit(Field.HEROES_CLASS_CHANGE, player,
						config.getLimitValue(Field.HEROES_CLASS_CHANGE, null,
								null), null, null))
				{
					// Attempt to pay
					if (KarmicEcon.pay(Field.HEROES_CLASS_CHANGE, player,
							config.getPayValue(Field.HEROES_CLASS_CHANGE, null,
									null), null, null))
					{
						// Increment
						db.incrementData(Field.HEROES_CLASS_CHANGE,
								player.getName(), null, null);
					}
				}
			}
		}
	}

	public void expChangeValid(ExperienceChangeEvent event)
	{
		// Cancellable
		if (!event.isCancelled() && HeroesConfig.expChange
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void expChange(ExperienceChangeEvent event)
	{
		// Cancellable
		if (!event.isCancelled() && HeroesConfig.expChange
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void changeLevelEvent(HeroChangeLevelEvent event)
	{
		// non-cancel
		if (HeroesConfig.changeLevel && event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void combatEnter(HeroEnterCombatEvent event)
	{
		// non-cancel
		if (HeroesConfig.combatEnter && event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void combatLeave(HeroLeaveCombatEvent event)
	{
		// non-cancel
		if (HeroesConfig.combatLeave && event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void joinPartyValid(HeroJoinPartyEvent event)
	{
		// cancellable
		if (!event.isCancelled() && HeroesConfig.partyJoin
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void joinParty(HeroJoinPartyEvent event)
	{
		// cancellable
		if (!event.isCancelled() && HeroesConfig.partyJoin
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void leavePartyValid(HeroLeavePartyEvent event)
	{
		// cancellable
		if (!event.isCancelled() && HeroesConfig.partyLeave
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void leaveParty(HeroLeavePartyEvent event)
	{
		// cancellable
		if (!event.isCancelled() && HeroesConfig.partyLeave
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void killHero(HeroKillHeroEvent event)
	{
		// non-cancel
		if (event.getAttacker() != null)
		{
			final Player player = event.getAttacker().getPlayer();
			switch (event.getReason())
			{
				case ATTACKED_PLAYER:
				{
					if(HeroesConfig.killAttackPlayer)
					{
						
					}
					break;
				}
				case ATTACKED_MOB:
				{
					if(HeroesConfig.killAttackMob)
					{
						
					}
					break;
				}
				default:
				{
					if (plugin.getPluginConfig().debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled attack reason '"
										+ event.getReason().name() + " for "
										+ event.getEventName());
					}
					break;
				}
			}
		}
		if (event.getDefender() != null)
		{
			final Player player = event.getDefender().getPlayer();
			switch (event.getReason())
			{
				case DAMAGED_BY_PLAYER:
				{
					if(HeroesConfig.killDefendPlayer)
					{
						
					}
					break;
				}
				case DAMAGED_BY_MOB:
				{
					if(HeroesConfig.killDefendMob)
					{
						
					}
					break;
				}
				default:
				{
					if (plugin.getPluginConfig().debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled defend reason '"
										+ event.getReason().name() + " for "
										+ event.getEventName());
					}
					break;
				}
			}
		}
	}

	public void regainHealthValid(HeroRegainHealthEvent event)
	{
		// cancellable
		if (!event.isCancelled() && HeroesConfig.regainHealth
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void regainHealth(HeroRegainHealthEvent event)
	{
		// cancellable
		if (!event.isCancelled() && HeroesConfig.regainHealth
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void regainManaValid(HeroRegainManaEvent event)
	{
		// cancellable
		if (!event.isCancelled() && HeroesConfig.regainMana
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void regainMana(HeroRegainManaEvent event)
	{
		// cancellable
		if (!event.isCancelled() && HeroesConfig.regainMana
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void skillComplete(SkillCompleteEvent event)
	{
		// non-cancel
		// TODO check if successful?
		if (HeroesConfig.skillComplete && event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void skillUseValid(SkillUseEvent event)
	{
		// cancellable
		if (!event.isCancelled() && HeroesConfig.skillUse
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
		}
	}

	public void skillUse(SkillUseEvent event)
	{
		// cancellable
		if (!event.isCancelled() && HeroesConfig.skillUse
				&& event.getHero() != null)
		{
			final Player player = event.getHero().getPlayer();
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
