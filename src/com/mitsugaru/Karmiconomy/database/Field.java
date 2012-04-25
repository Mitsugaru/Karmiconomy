package com.mitsugaru.Karmiconomy.database;

public enum Field
{
	// TODO eggs, painting break?, vehicle
	BOW_SHOOT(Table.DATA, "bowshoot"), BED_ENTER(Table.DATA, "bedenter"), BED_LEAVE(
			Table.DATA, "bedleave"), BLOCK_PLACE(Table.ITEMS, "place"), BLOCK_DESTROY(
			Table.ITEMS, "destroy"), ITEM_CRAFT(Table.ITEMS, "craft"), ITEM_ENCHANT(
			Table.ITEMS, "enchant"), ITEM_DROP(Table.ITEMS, "playerDrop"), ITEM_PICKUP(
			Table.ITEMS, "pickup"), EGG_THROW(Table.DATA, "eggThrow"), CHAT(
			Table.DATA, "chat"), COMMAND(Table.COMMAND, "command"), DEATH(
			Table.DATA, "death"), CREATIVE(Table.DATA, "creative"), SURVIVAL(
			Table.DATA, "survival"), JOIN(Table.DATA, "playerJoin"), KICK(
			Table.DATA, "kick"), QUIT(Table.DATA, "quit"), RESPAWN(Table.DATA,
			"respawn"), SNEAK(Table.DATA, "sneak"), SPRINT(Table.DATA, "sprint"), PAINTING_PLACE(
			Table.DATA, "paintingplace"), PORTAL_CREATE_NETHER(Table.PORTAL,
			"pcreatenether"), PORTAL_CREATE_END(Table.PORTAL, "pcreateend"), PORTAL_CREATE_CUSTOM(
			Table.PORTAL, "pcreatecustom"), PORTAL_ENTER(Table.PORTAL,
			"portalenter"), TAME_OCELOT(Table.DATA, "tameocelot"), TAME_WOLF(
			Table.DATA, "tamewolf"), WORLD_CHANGE(Table.DATA, "worldchange"), BUCKET_EMPTY_LAVA(
			Table.BUCKET, "bemptylava"), BUCKET_EMPTY_WATER(Table.BUCKET,
			"bemptywater"), BUCKET_FILL_LAVA(Table.BUCKET, "bfilllava"), BUCKET_FILL_WATER(
			Table.BUCKET, "bfillwater"), MCMMO_PARTY_TELEPORT(Table.MCMMO,
			"partyteleport"), MCMMO_PARTY_JOIN(Table.MCMMO, "partyjoin"), MCMMO_PARTY_LEAVE(
			Table.MCMMO, "partyleave"), MCMMO_PARTY_KICK(Table.MCMMO,
			"partykick"), MCMMO_PARTY_CHANGE(Table.MCMMO, "partychange"), MCMMO_LEVEL_ACROBATICS(
			Table.MCMMO, "acrobaticslevel"), MCMMO_LEVEL_ARCHERY(Table.MCMMO,
			"archerylevel"), MCMMO_LEVEL_AXES(Table.MCMMO, "axeslevel"), MCMMO_LEVEL_EXCAVATION(
			Table.MCMMO, "excavationlevel"), MCMMO_LEVEL_FISHING(Table.MCMMO,
			"fishinglevel"), MCMMO_LEVEL_HERBALISM(Table.MCMMO,
			"herbalismlevel"), MCMMO_LEVEL_MINING(Table.MCMMO, "mininglevel"), MCMMO_LEVEL_REPAIR(
			Table.MCMMO, "repairlevel"), MCMMO_LEVEL_SWORDS(Table.MCMMO,
			"swordslevel"), MCMMO_LEVEL_TAMING(Table.MCMMO, "taminglevel"), MCMMO_LEVEL_UNARMED(
			Table.MCMMO, "unarmedlevel"), MCMMO_LEVEL_WOODCUTTING(Table.MCMMO,
			"woodcuttinglevel"), MCMMO_GAIN_ACROBATICS(Table.MCMMO,
			"acrobaticsgain"), MCMMO_GAIN_ARCHERY(Table.MCMMO, "archerygain"), MCMMO_GAIN_AXES(
			Table.MCMMO, "axesgain"), MCMMO_GAIN_EXCAVATION(Table.MCMMO,
			"excavationgain"), MCMMO_GAIN_FISHING(Table.MCMMO, "fishinggain"), MCMMO_GAIN_HERBALISM(
			Table.MCMMO, "herbalismgain"), MCMMO_GAIN_MINING(Table.MCMMO,
			"mininggain"), MCMMO_GAIN_REPAIR(Table.MCMMO, "repairgain"), MCMMO_GAIN_SWORDS(
			Table.MCMMO, "swordsgain"), MCMMO_GAIN_TAMING(Table.MCMMO,
			"taminggain"), MCMMO_GAIN_UNARMED(Table.MCMMO, "unarmedgain"), MCMMO_GAIN_WOODCUTTING(
			Table.MCMMO, "woodcuttinggain"), HEROES_CLASS_CHANGE(Table.HEROES,
			"classchange"), HEROES_EXP_CHANGE(Table.HEROES, "expchange"), HEROES_CHANGE_LEVEL(
			Table.HEROES, "changelevel"), HEROES_COMBAT_ENTER(Table.HEROES,
			"combatenter"), HEROES_COMBAT_LEAVE(Table.HEROES, "combatleave"), HEROES_PARTY_JOIN(
			Table.HEROES, "partyjoin"), HEROES_PARTY_LEAVE(Table.HEROES,
			"partyleave"), HEROES_KILL_ATTACK_MOB(Table.HEROES, "killattackmob"), HEROES_KILL_ATTACK_PLAYER(
			Table.HEROES, "killattackplayer"), HEROES_KILL_DEFEND_MOB(
			Table.HEROES, "killdefendmob"), HEROES_KILL_DEFEND_PLAYER(
			Table.HEROES, "killdefendplayer"), HEROES_REGAIN_HEALTH(
			Table.HEROES, "regainhealth"), HEROES_REGAIN_MANA(Table.HEROES,
			"regainmana"), HEROES_SKILL_COMPLETE(Table.HEROES, "skillcomplete"), HEROES_SKILL_DAMAGE(
			Table.HEROES, "skilldamage"), HEROES_SKILL_USE(Table.HEROES,
			"skilluse"), HEROES_WEAPON_DAMAGE(Table.HEROES, "weapondamage");
	private final Table table;
	private final String columnname;

	private Field(Table table, String columnname)
	{
		this.table = table;
		this.columnname = columnname;
	}

	public Table getTable()
	{
		return table;
	}

	public String getColumnName()
	{
		return columnname;
	}
}