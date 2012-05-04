package com.mitsugaru.Karmiconomy.database;

public enum Field
{
	// TODO eggs, painting break?, vehicle
	BOW_SHOOT(Table.DATA, "bowshoot", "bow.shoot"), BED_ENTER(Table.DATA,
			"bedenter", "bed.enter"), BED_LEAVE(Table.DATA, "bedleave",
			"bed.leave"), BLOCK_PLACE(Table.ITEMS, "place", "block.place"), BLOCK_DESTROY(
			Table.ITEMS, "destroy", "block.destroy"), ITEM_CRAFT(Table.ITEMS,
			"craft", "item.craft"), ITEM_ENCHANT(Table.ITEMS, "enchant",
			"item.enchant"), ITEM_DROP(Table.ITEMS, "playerDrop", "item.drop"), ITEM_PICKUP(
			Table.ITEMS, "pickup", "item.pickup"), EGG_THROW(Table.DATA,
			"eggThrow", "item.egg"), CHAT(Table.DATA, "chat", "player.chat"), COMMAND(
			Table.COMMAND, "command", "player.command"), DEATH(Table.DATA,
			"death", "player.death"), CREATIVE(Table.DATA, "creative",
			"player.gamemode.creative"), SURVIVAL(Table.DATA, "survival",
			"player.gamemode.survival"), JOIN(Table.DATA, "playerJoin",
			"player.join"), KICK(Table.DATA, "kick", "player.kick"), QUIT(
			Table.DATA, "quit", "player.quit"), RESPAWN(Table.DATA, "respawn",
			"player.respawn"), SNEAK(Table.DATA, "sneak", "player.sneak"), SPRINT(
			Table.DATA, "sprint", "player.sprint"), PAINTING_PLACE(Table.DATA,
			"paintingplace", "painting"), PORTAL_CREATE_NETHER(Table.PORTAL,
			"pcreatenether", "portal.createNether"), PORTAL_CREATE_END(
			Table.PORTAL, "pcreateend", "portal.createEnd"), PORTAL_CREATE_CUSTOM(
			Table.PORTAL, "pcreatecustom", "portal.createCustom"), PORTAL_ENTER(
			Table.PORTAL, "portalenter", "portal.enter"), TAME_OCELOT(
			Table.DATA, "tameocelot", "tame.ocelot"), TAME_WOLF(Table.DATA,
			"tamewolf", "tame.wolf"), WORLD_CHANGE(Table.DATA, "worldchange",
			"world.change"), BUCKET_EMPTY_LAVA(Table.BUCKET, "bemptylava",
			"bucket.empty.lava"), BUCKET_EMPTY_WATER(Table.BUCKET,
			"bemptywater", "bucket.empty.water"), BUCKET_FILL_LAVA(
			Table.BUCKET, "bfilllava", "bucket.fill.lava"), BUCKET_FILL_WATER(
			Table.BUCKET, "bfillwater", "bucket.fill.water"), MCMMO_PARTY_TELEPORT(
			Table.MCMMO, "partyteleport", "party.teleport"), MCMMO_PARTY_JOIN(
			Table.MCMMO, "partyjoin", "party.join"), MCMMO_PARTY_LEAVE(
			Table.MCMMO, "partyleave", "party.leave"), MCMMO_PARTY_KICK(
			Table.MCMMO, "partykick", "party.kick"), MCMMO_PARTY_CHANGE(
			Table.MCMMO, "partychange", "party.change"), MCMMO_LEVEL_ACROBATICS(
			Table.MCMMO, "acrobaticslevel", "acrobatics.levelup"), MCMMO_LEVEL_ARCHERY(
			Table.MCMMO, "archerylevel", "archery.levelup"), MCMMO_LEVEL_AXES(
			Table.MCMMO, "axeslevel", "axes.levelup"), MCMMO_LEVEL_EXCAVATION(
			Table.MCMMO, "excavationlevel", "excavation.levelup"), MCMMO_LEVEL_FISHING(
			Table.MCMMO, "fishinglevel", "fishing.levelup"), MCMMO_LEVEL_HERBALISM(
			Table.MCMMO, "herbalismlevel", "herbalism.levelup"), MCMMO_LEVEL_MINING(
			Table.MCMMO, "mininglevel", "mining.levelup"), MCMMO_LEVEL_REPAIR(
			Table.MCMMO, "repairlevel", "repair.levelup"), MCMMO_LEVEL_SWORDS(
			Table.MCMMO, "swordslevel", "swords.levelup"), MCMMO_LEVEL_TAMING(
			Table.MCMMO, "taminglevel", "taming.levelup"), MCMMO_LEVEL_UNARMED(
			Table.MCMMO, "unarmedlevel", "unarmed.levelup"), MCMMO_LEVEL_WOODCUTTING(
			Table.MCMMO, "woodcuttinglevel", "woodcutting.levelup"), MCMMO_GAIN_ACROBATICS(
			Table.MCMMO, "acrobaticsgain", "acrobatics.xpgain"), MCMMO_GAIN_ARCHERY(
			Table.MCMMO, "archerygain", "archery.xpgain"), MCMMO_GAIN_AXES(
			Table.MCMMO, "axesgain", "axes.xpgain"), MCMMO_GAIN_EXCAVATION(
			Table.MCMMO, "excavationgain", "excavation.xpgain"), MCMMO_GAIN_FISHING(
			Table.MCMMO, "fishinggain", "fishing.xpgain"), MCMMO_GAIN_HERBALISM(
			Table.MCMMO, "herbalismgain", "herbalism.xpgain"), MCMMO_GAIN_MINING(
			Table.MCMMO, "mininggain", "mining.xpgain"), MCMMO_GAIN_REPAIR(
			Table.MCMMO, "repairgain", "repair.xpgain"), MCMMO_GAIN_SWORDS(
			Table.MCMMO, "swordsgain", "swords.xpgain"), MCMMO_GAIN_TAMING(
			Table.MCMMO, "taminggain", "taming.xpgain"), MCMMO_GAIN_UNARMED(
			Table.MCMMO, "unarmedgain", "unarmed.xpgain"), MCMMO_GAIN_WOODCUTTING(
			Table.MCMMO, "woodcuttinggain", "woodcutting.xpgain"), HEROES_CLASS_CHANGE(
			Table.HEROES, "classchange", "change.class"), HEROES_EXP_CHANGE(
			Table.HEROES, "expchange", "change.exp"), HEROES_CHANGE_LEVEL(
			Table.HEROES, "changelevel", "change.level"), HEROES_COMBAT_ENTER(
			Table.HEROES, "combatenter", "combat.enter"), HEROES_COMBAT_LEAVE(
			Table.HEROES, "combatleave", "combat.leave"), HEROES_PARTY_JOIN(
			Table.HEROES, "partyjoin", "party.join"), HEROES_PARTY_LEAVE(
			Table.HEROES, "partyleave", "party.leave"), HEROES_KILL_ATTACK_MOB(
			Table.HEROES, "killattackmob", "kill.attack.mob"), HEROES_KILL_ATTACK_PLAYER(
			Table.HEROES, "killattackplayer", "kill.attack.player"), HEROES_KILL_DEFEND_MOB(
			Table.HEROES, "killdefendmob", "kill.defend.mob"), HEROES_KILL_DEFEND_PLAYER(
			Table.HEROES, "killdefendplayer", "kill.defend.player"), HEROES_REGAIN_HEALTH(
			Table.HEROES, "regainhealth", "regain.health"), HEROES_REGAIN_MANA(
			Table.HEROES, "regainmana", "regain.mana"), HEROES_SKILL_COMPLETE(
			Table.HEROES, "skillcomplete", "skill.complete"), HEROES_SKILL_DAMAGE(
			Table.HEROES, "skilldamage", "skill.damage"), HEROES_SKILL_USE(
			Table.HEROES, "skilluse", "skill.use"), HEROES_WEAPON_DAMAGE(
			Table.HEROES, "weapondamage", "weapon.damage");
	private final Table table;
	private final String columnname;
	private final String config;

	private Field(Table table, String columnname, String config)
	{
		this.table = table;
		this.columnname = columnname;
		this.config = config;
	}

	public Table getTable()
	{
		return table;
	}

	public String getColumnName()
	{
		return columnname;
	}

	public String getConfigPath()
	{
		return config;
	}
}