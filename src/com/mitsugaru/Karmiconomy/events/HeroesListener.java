package com.mitsugaru.Karmiconomy.events;

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

import com.mitsugaru.Karmiconomy.DatabaseHandler;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.config.HeroesConfig;

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

	public void classChange(ClassChangeEvent event)
	{
		// Cancellable
	}

	public void expChange(ExperienceChangeEvent event)
	{
		// Cancellable
	}

	public void changeLevelEvent(HeroChangeLevelEvent event)
	{
		// non-cancel
	}

	public void enterCombat(HeroEnterCombatEvent event)
	{
		// non-cancel
		switch (event.getReason())
		{

		}
	}

	public void leaveCombat(HeroLeaveCombatEvent event)
	{
		// non-cancel
		switch (event.getReason())
		{

		}
	}

	public void joinParty(HeroJoinPartyEvent event)
	{
		// cancellable
	}

	public void leaveParty(HeroLeavePartyEvent event)
	{
		// cancellable
	}

	public void killHero(HeroKillHeroEvent event)
	{
		// non-cancel
	}

	public void regainHealth(HeroRegainHealthEvent event)
	{
		// cancellable
	}

	public void regainMana(HeroRegainManaEvent event)
	{
		// cancellable
	}

	public void skillComplete(SkillCompleteEvent event)
	{
		// non-cancel
	}

	public void skillDamage(SkillDamageEvent event)
	{
		// cancellable
	}

	public void skillUse(SkillUseEvent event)
	{
		// cancellable
	}

	public void weaponDamage(WeaponDamageEvent event)
	{
		// cancellable
	}
}
