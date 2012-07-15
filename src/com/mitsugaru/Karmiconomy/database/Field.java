package com.mitsugaru.Karmiconomy.database;

import com.mitsugaru.Karmiconomy.permissions.PermissionNode;

public enum Field {
   // TODO eggs, painting break?, vehicle
   /**
    * Normal
    */
   BOW_SHOOT(Table.DATA, "bowshoot", "bow.shoot",
         PermissionNode.BYPASS_BOW_SHOOT),
   BED_ENTER(Table.DATA, "bedenter", "bed.enter", PermissionNode.BYPASS_BED_ENTER),
   BED_LEAVE(Table.DATA, "bedleave", "bed.leave", PermissionNode.BYPASS_BED_LEAVE),
   BLOCK_PLACE(Table.ITEMS, "place", "block.place", PermissionNode.BYPASS_BLOCK_PLACE),
   BLOCK_DESTROY(Table.ITEMS, "destroy", "block.destroy", PermissionNode.BYPASS_BLOCK_DESTROY),
   ITEM_CRAFT(Table.ITEMS, "craft", "item.craft", PermissionNode.BYPASS_ITEM_CRAFT),
   ITEM_ENCHANT(Table.ITEMS, "enchant", "item.enchant", PermissionNode.BYPASS_ITEM_ENCHANT),
   ITEM_DROP(Table.ITEMS, "playerDrop", "item.drop", PermissionNode.BYPASS_ITEM_DROP),
   ITEM_PICKUP(Table.ITEMS, "pickup", "item.pickup", PermissionNode.BYPASS_ITEM_PICKUP),
   EGG_THROW(Table.DATA, "eggThrow", "item.egg", PermissionNode.BYPASS_ITEM_EGG),
   CHAT(Table.DATA, "chat", "player.chat", PermissionNode.BYPASS_PLAYER_CHAT),
   COMMAND(Table.COMMAND, "command", "player.command", PermissionNode.BYPASS_PLAYER_COMMAND),
   DEATH(Table.DATA, "death", "player.death", PermissionNode.BYPASS_PLAYER_DEATH),
   CREATIVE(Table.DATA, "creative", "player.gamemode.creative", PermissionNode.BYPASS_PLAYER_GAMEMODE_CREATIVE),
   SURVIVAL(Table.DATA, "survival", "player.gamemode.survival", PermissionNode.BYPASS_PLAYER_GAMEMODE_SURVIVAL),
   JOIN(Table.DATA, "playerJoin", "player.join", PermissionNode.BYPASS_PLAYER_JOIN),
   KICK(Table.DATA, "kick", "player.kick", PermissionNode.BYPASS_PLAYER_KICK),
   QUIT(Table.DATA, "quit", "player.quit", PermissionNode.BYPASS_PLAYER_QUIT),
   RESPAWN(Table.DATA, "respawn", "player.respawn", PermissionNode.BYPASS_PLAYER_RESPAWN),
   SNEAK(Table.DATA, "sneak", "player.sneak", PermissionNode.BYPASS_PLAYER_SNEAK),
   SPRINT(Table.DATA, "sprint", "player.sprint", PermissionNode.BYPASS_PLAYER_SPRINT),
   PAINTING_PLACE(Table.DATA, "paintingplace", "painting", PermissionNode.BYPASS_PAINTING),
   PORTAL_CREATE_NETHER(Table.PORTAL, "pcreatenether", "portal.createNether", PermissionNode.BYPASS_PORTAL_NETHER),
   PORTAL_CREATE_END(Table.PORTAL, "pcreateend", "portal.createEnd", PermissionNode.BYPASS_PORTAL_END),
   PORTAL_CREATE_CUSTOM(Table.PORTAL, "pcreatecustom", "portal.createCustom", PermissionNode.BYPASS_PORTAL_CUSTOM),
   PORTAL_ENTER(Table.PORTAL, "portalenter", "portal.enter", PermissionNode.BYPASS_PORTAL_ENTER),
   TAME_OCELOT(Table.DATA, "tameocelot", "tame.ocelot", PermissionNode.BYPASS_TAME_OCELOT),
   TAME_WOLF(Table.DATA, "tamewolf", "tame.wolf", PermissionNode.BYPASS_TAME_WOLF),
   WORLD_CHANGE(Table.DATA, "worldchange", "world.change", PermissionNode.BYPASS_WORLD_CHANGE),
   BUCKET_EMPTY_LAVA(Table.BUCKET, "bemptylava", "bucket.empty.lava", PermissionNode.BYPASS_BUCKET_EMPTY_LAVA),
   BUCKET_EMPTY_WATER(Table.BUCKET, "bemptywater", "bucket.empty.water", PermissionNode.BYPASS_BUCKET_EMPTY_WATER),
   BUCKET_FILL_LAVA(Table.BUCKET, "bfilllava", "bucket.fill.lava", PermissionNode.BYPASS_BUCKET_FILL_LAVA),
   BUCKET_FILL_WATER(Table.BUCKET, "bfillwater", "bucket.fill.water", PermissionNode.BYPASS_BUCKET_FILL_WATER),
   /**
    * MCMMO
    */
   MCMMO_PARTY_TELEPORT(Table.MCMMO, "partyteleport", "party.teleport", PermissionNode.BYPASS_MCMMO_PARTY_TELEPORT),
   MCMMO_PARTY_JOIN(Table.MCMMO, "partyjoin", "party.join", PermissionNode.BYPASS_MCMMO_PARTY_JOIN),
   MCMMO_PARTY_LEAVE(Table.MCMMO, "partyleave", "party.leave", PermissionNode.BYPASS_MCMMO_PARTY_LEAVE),
   MCMMO_PARTY_KICK(Table.MCMMO, "partykick", "party.kick", PermissionNode.BYPASS_MCMMO_PARTY_KICK),
   MCMMO_PARTY_CHANGE(Table.MCMMO, "partychange", "party.change", PermissionNode.BYPASS_MCMMO_PARTY_CHANGE),
   MCMMO_LEVEL_ACROBATICS(Table.MCMMO, "acrobaticslevel", "acrobatics.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_ACROBATICS),
   MCMMO_LEVEL_ARCHERY(Table.MCMMO, "archerylevel", "archery.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_ARCHERY),
   MCMMO_LEVEL_AXES(Table.MCMMO, "axeslevel", "axes.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_AXES),
   MCMMO_LEVEL_EXCAVATION(Table.MCMMO, "excavationlevel", "excavation.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_EXCAVATION),
   MCMMO_LEVEL_FISHING(Table.MCMMO, "fishinglevel", "fishing.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_FISHING),
   MCMMO_LEVEL_HERBALISM(Table.MCMMO, "herbalismlevel", "herbalism.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_HERBALISM),
   MCMMO_LEVEL_MINING(Table.MCMMO, "mininglevel", "mining.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_MINING),
   MCMMO_LEVEL_REPAIR(Table.MCMMO, "repairlevel", "repair.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_REPAIR),
   MCMMO_LEVEL_SWORDS(Table.MCMMO, "swordslevel", "swords.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_SWORDS),
   MCMMO_LEVEL_TAMING(Table.MCMMO, "taminglevel", "taming.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_TAMING),
   MCMMO_LEVEL_UNARMED(Table.MCMMO, "unarmedlevel", "unarmed.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_UNARMED),
   MCMMO_LEVEL_WOODCUTTING(Table.MCMMO, "woodcuttinglevel",
         "woodcutting.levelup", PermissionNode.BYPASS_MCMMO_LEVEL_WOODCUTTING),
   MCMMO_GAIN_ACROBATICS(Table.MCMMO, "acrobaticsgain", "acrobatics.xpgain", PermissionNode.BYPASS_MCMMO_XP_ACROBATICS),
   MCMMO_GAIN_ARCHERY(Table.MCMMO, "archerygain", "archery.xpgain", PermissionNode.BYPASS_MCMMO_XP_ARCHERY),
   MCMMO_GAIN_AXES(Table.MCMMO, "axesgain", "axes.xpgain", PermissionNode.BYPASS_MCMMO_XP_AXES),
   MCMMO_GAIN_EXCAVATION(Table.MCMMO, "excavationgain", "excavation.xpgain", PermissionNode.BYPASS_MCMMO_XP_EXCAVATION),
   MCMMO_GAIN_FISHING(Table.MCMMO, "fishinggain", "fishing.xpgain", PermissionNode.BYPASS_MCMMO_XP_FISHING),
   MCMMO_GAIN_HERBALISM(Table.MCMMO, "herbalismgain", "herbalism.xpgain", PermissionNode.BYPASS_MCMMO_XP_HERBALISM),
   MCMMO_GAIN_MINING(Table.MCMMO, "mininggain", "mining.xpgain", PermissionNode.BYPASS_MCMMO_XP_MINING),
   MCMMO_GAIN_REPAIR(Table.MCMMO, "repairgain", "repair.xpgain", PermissionNode.BYPASS_MCMMO_XP_REPAIR),
   MCMMO_GAIN_SWORDS(Table.MCMMO, "swordsgain", "swords.xpgain", PermissionNode.BYPASS_MCMMO_XP_SWORDS),
   MCMMO_GAIN_TAMING(Table.MCMMO, "taminggain", "taming.xpgain", PermissionNode.BYPASS_MCMMO_XP_TAMING),
   MCMMO_GAIN_UNARMED(Table.MCMMO, "unarmedgain", "unarmed.xpgain", PermissionNode.BYPASS_MCMMO_XP_UNARMED),
   MCMMO_GAIN_WOODCUTTING(Table.MCMMO, "woodcuttinggain", "woodcutting.xpgain", PermissionNode.BYPASS_MCMMO_XP_WOODCUTTING),
   /**
    * Heroes
    */
   HEROES_CLASS_CHANGE(Table.HEROES, "classchange", "change.class", PermissionNode.BYPASS_HEROES_CHANGE_CLASS),
   HEROES_EXP_CHANGE(Table.HEROES, "expchange", "change.exp", PermissionNode.BYPASS_HEROES_CHANGE_EXP),
   HEROES_CHANGE_LEVEL(Table.HEROES, "changelevel", "change.level", PermissionNode.BYPASS_HEROES_CHANGE_LEVEL),
   HEROES_COMBAT_ENTER(Table.HEROES, "combatenter", "combat.enter", PermissionNode.BYPASS_HEROES_COMBAT_ENTER),
   HEROES_COMBAT_LEAVE(Table.HEROES, "combatleave", "combat.leave", PermissionNode.BYPASS_HEROES_COMBAT_LEAVE),
   HEROES_PARTY_JOIN(Table.HEROES, "partyjoin", "party.join", PermissionNode.BYPASS_HEROES_PARTY_JOIN),
   HEROES_PARTY_LEAVE(Table.HEROES, "partyleave", "party.leave", PermissionNode.BYPASS_HEROES_PARTY_LEAVE),
   HEROES_KILL_ATTACK_MOB(Table.HEROES, "killattackmob", "kill.attack.mob", PermissionNode.BYPASS_HEROES_KILL_ATTACK_MOB),
   HEROES_KILL_ATTACK_PLAYER(Table.HEROES, "killattackplayer",
         "kill.attack.player", PermissionNode.BYPASS_HEROES_KILL_ATTACK_PLAYER),
   HEROES_KILL_DEFEND_MOB(Table.HEROES, "killdefendmob", "kill.defend.mob", PermissionNode.BYPASS_HEROES_KILL_DEFEND_MOB),
   HEROES_KILL_DEFEND_PLAYER(Table.HEROES, "killdefendplayer",
         "kill.defend.player", PermissionNode.BYPASS_HEROES_KILL_DEFEND_PLAYER),
   HEROES_REGAIN_HEALTH(Table.HEROES, "regainhealth", "regain.health", PermissionNode.BYPASS_HEROES_REGAIN_HEALTH),
   HEROES_REGAIN_MANA(Table.HEROES, "regainmana", "regain.mana", PermissionNode.BYPASS_HEROES_REGAIN_MANA),
   HEROES_SKILL_COMPLETE(Table.HEROES, "skillcomplete", "skill.complete", PermissionNode.BYPASS_HEROES_SKILL_COMPLETE),
   HEROES_SKILL_DAMAGE(Table.HEROES, "skilldamage", "skill.damage", PermissionNode.BYPASS_HEROES_SKILL_DAMAGE),
   HEROES_SKILL_USE(Table.HEROES, "skilluse", "skill.use", PermissionNode.BYPASS_HEROES_SKILL_USE),
   HEROES_WEAPON_DAMAGE(Table.HEROES, "weapondamage", "weapon.damage", PermissionNode.BYPASS_HEROES_WEAPON_DAMAGE);
   
   private final Table table;
   private final String columnname;
   private final String config;
   private final PermissionNode bypass;

   private Field(Table table, String columnname, String config,
         PermissionNode node){
      this.table = table;
      this.columnname = columnname;
      this.config = config;
      this.bypass = node;
   }

   public Table getTable(){
      return table;
   }

   public String getColumnName(){
      return columnname;
   }

   public String getConfigPath(){
      return config;
   }

   public PermissionNode getBypassNode(){
      return bypass;
   }
}