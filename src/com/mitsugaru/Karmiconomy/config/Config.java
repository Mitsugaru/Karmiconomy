/**
 * Config file mimicking DiddiZ's Config class file in LB. Tailored for this
 * plugin.
 * 
 * @author Mitsugaru
 */
package com.mitsugaru.Karmiconomy.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.database.Field;

public class Config implements KConfig{
   // Class variables
   private Karmiconomy plugin;
   private ItemsConfig items;
   private CommandsConfig commands;
   public String host, port, database, user, password;
   public static String tablePrefix;
   public boolean debugTime, debugEvents, debugEconomy, debugUnhandled,
         useMySQL, importSQL, portalCreateNether, portalCreateEnd,
         portalCreateCustom, blockPlaceStatic, blockDestroyStatic,
         craftItemStatic, enchantItemStatic, itemDropStatic, commandStatic,
         pickupStatic, shootBowDenyForce;
   public int listlimit;
   public double shootBowForce;

   // TODO ability to change config in-game
   /**
    * Constructor and initializer
    * 
    * @param Karmiconomy
    *           plugin
    */
   public Config(Karmiconomy plugin){
      this.plugin = plugin;
      // Grab config
      final ConfigurationSection config = plugin.getConfig();
      // LinkedHashmap of defaults
      final Map<String, Object> defaults = new LinkedHashMap<String, Object>();
      defaults.put("listlimit", 10);
      defaults.put("bed.enter.enabled", false);
      defaults.put("bed.enter.denyOnLackPay", false);
      defaults.put("bed.enter.denyOnLimit", false);
      defaults.put("bed.enter.limit", 10);
      defaults.put("bed.enter.pay", 0.1);
      defaults.put("bed.enter.localMessage", false);
      defaults.put("bed.leave.enabled", false);
      defaults.put("bed.leave.limit", 10);
      defaults.put("bed.leave.pay", 0.1);
      defaults.put("bed.leave.localMessage", false);
      defaults.put("block.destroy.enabled", false);
      defaults.put("block.destroy.denyOnLackPay", false);
      defaults.put("block.destroy.denyOnLimit", false);
      defaults.put("block.destroy.static", true);
      defaults.put("block.destroy.limit", 100);
      defaults.put("block.destroy.pay", 0.1);
      defaults.put("block.destroy.localMessage", false);
      defaults.put("block.place.enabled", false);
      defaults.put("block.place.denyOnLackPay", false);
      defaults.put("block.place.denyOnLimit", false);
      defaults.put("block.place.static", true);
      defaults.put("block.place.limit", 100);
      defaults.put("block.place.pay", 0.1);
      defaults.put("block.place.localMessage", false);
      defaults.put("bow.shoot.enabled", false);
      defaults.put("bow.shoot.denyOnLackPay", false);
      defaults.put("bow.shoot.denyOnLimit", false);
      defaults.put("bow.shoot.denyOnLowForce", false);
      defaults.put("bow.shoot.minimumforce", 0.0);
      defaults.put("bow.shoot.limit", 100);
      defaults.put("bow.shoot.pay", 0.1);
      defaults.put("bow.shoot.localMessage", false);
      defaults.put("bucket.empty.lava.enabled", false);
      defaults.put("bucket.empty.lava.denyOnLackPay", false);
      defaults.put("bucket.empty.lava.denyOnLimit", false);
      defaults.put("bucket.empty.lava.limit", 100);
      defaults.put("bucket.empty.lava.pay", -10.0);
      defaults.put("bucket.empty.lava.localMessage", false);
      defaults.put("bucket.empty.water.enabled", false);
      defaults.put("bucket.empty.water.denyOnLackPay", false);
      defaults.put("bucket.empty.water.denyOnLimit", false);
      defaults.put("bucket.empty.water.limit", 100);
      defaults.put("bucket.empty.water.pay", 0.1);
      defaults.put("bucket.empty.water.localMessage", false);
      defaults.put("bucket.fill.lava.enabled", false);
      defaults.put("bucket.fill.lava.denyOnLackPay", false);
      defaults.put("bucket.fill.lava.denyOnLimit", false);
      defaults.put("bucket.fill.lava.limit", 100);
      defaults.put("bucket.fill.lava.pay", 0.1);
      defaults.put("bucket.fill.lava.localMessage", false);
      defaults.put("bucket.fill.water.enabled", false);
      defaults.put("bucket.fill.water.denyOnLackPay", false);
      defaults.put("bucket.fill.water.denyOnLimit", false);
      defaults.put("bucket.fill.water.limit", 100);
      defaults.put("bucket.fill.water.pay", 0.1);
      defaults.put("bucket.fill.water.localMessage", false);
      defaults.put("item.craft.enabled", false);
      defaults.put("item.craft.static", true);
      defaults.put("item.craft.denyOnLackPay", false);
      defaults.put("item.craft.denyOnLimit", false);
      defaults.put("item.craft.limit", 100);
      defaults.put("item.craft.pay", 0.1);
      defaults.put("item.craft.localMessage", false);
      defaults.put("item.enchant.enabled", false);
      defaults.put("item.enchant.denyOnLackPay", false);
      defaults.put("item.enchant.denyOnLimit", false);
      defaults.put("item.enchant.static", true);
      defaults.put("item.enchant.limit", 100);
      defaults.put("item.enchant.pay", 0.1);
      defaults.put("item.enchant.localMessage", false);
      defaults.put("item.drop.enabled", false);
      defaults.put("item.drop.denyOnLackPay", false);
      defaults.put("item.drop.denyOnLimit", false);
      defaults.put("item.drop.static", true);
      defaults.put("item.drop.limit", 100);
      defaults.put("item.drop.pay", 0.1);
      defaults.put("item.drop.localMessage", false);
      defaults.put("item.pickup.enabled", false);
      defaults.put("item.pickup.denyOnLackPay", false);
      defaults.put("item.pickup.denyOnLimit", false);
      defaults.put("item.pickup.static", true);
      defaults.put("item.pickup.limit", 100);
      defaults.put("item.pickup.pay", 0.1);
      defaults.put("item.pickup.localMessage", false);
      defaults.put("item.egg.enabled", false);
      defaults.put("item.egg.limit", 100);
      defaults.put("item.egg.pay", 0.1);
      defaults.put("item.egg.localMessage", false);
      defaults.put("painting.enabled", false);
      defaults.put("painting.denyOnLackPay", true);
      defaults.put("painting.denyOnLimit", true);
      defaults.put("painting.limit", 100);
      defaults.put("painting.pay", 0.1);
      defaults.put("painting.localMessage", false);
      defaults.put("player.chat.enabled", false);
      defaults.put("player.chat.denyOnLackPay", false);
      defaults.put("player.chat.denyOnLimit", false);
      defaults.put("player.chat.limit", 10);
      defaults.put("player.chat.pay", 0.1);
      defaults.put("player.chat.localMessage", false);
      defaults.put("player.command.enabled", false);
      defaults.put("player.command.denyOnLackPay", false);
      defaults.put("player.command.denyOnLimit", false);
      defaults.put("player.command.static", true);
      defaults.put("player.command.limit", 10);
      defaults.put("player.command.pay", 0.1);
      defaults.put("player.command.localMessage", false);
      defaults.put("player.death.enabled", false);
      defaults.put("player.death.limit", 100);
      defaults.put("player.death.pay", -1);
      defaults.put("player.death.localMessage", false);
      defaults.put("player.gamemode.creative.enabled", false);
      defaults.put("player.gamemode.creative.denyOnLackPay", false);
      defaults.put("player.gamemode.creative.denyOnLimit", false);
      defaults.put("player.gamemode.creative.limit", 10);
      defaults.put("player.gamemode.creative.pay", -10);
      defaults.put("player.gamemode.creative.localMessage", false);
      defaults.put("player.gamemode.survival.enabled", false);
      defaults.put("player.gamemode.survival.denyOnLackPay", false);
      defaults.put("player.gamemode.survival.denyOnLimit", false);
      defaults.put("player.gamemode.survival.limit", 1);
      defaults.put("player.gamemode.survival.pay", 0.1);
      defaults.put("player.gamemode.survival.localMessage", false);
      defaults.put("player.join.enabled", false);
      defaults.put("player.join.limit", 1);
      defaults.put("player.join.pay", 10);
      defaults.put("player.join.localMessage", false);
      defaults.put("player.kick.enabled", false);
      defaults.put("player.kick.limit", 10);
      defaults.put("player.kick.pay", -10);
      defaults.put("player.kick.localMessage", false);
      defaults.put("player.quit.enabled", false);
      defaults.put("player.quit.limit", 1);
      defaults.put("player.quit.pay", 0.1);
      defaults.put("player.quit.localMessage", false);
      defaults.put("player.respawn.enabled", false);
      defaults.put("player.respawn.limit", 100);
      defaults.put("player.respawn.pay", -0.1);
      defaults.put("player.respawn.localMessage", false);
      defaults.put("player.sneak.enabled", false);
      defaults.put("player.sneak.denyOnLackPay", false);
      defaults.put("player.sneak.denyOnLimit", false);
      defaults.put("player.sneak.limit", 10);
      defaults.put("player.sneak.pay", 0.1);
      defaults.put("player.sneak.localMessage", false);
      defaults.put("player.sprint.enabled", false);
      defaults.put("player.sprint.denyOnLackPay", false);
      defaults.put("player.sprint.denyOnLimit", false);
      defaults.put("player.sprint.limit", 10);
      defaults.put("player.sprint.pay", 0.1);
      defaults.put("player.sprint.localMessage", false);
      defaults.put("portal.createNether.enabled", false);
      defaults.put("portal.createNether.denyOnLackPay", false);
      defaults.put("portal.createNether.denyOnLimit", false);
      defaults.put("portal.createNether.limit", 10);
      // TODO fix so that it is no longer redundant
      defaults.put("portal.createNether.pay.nether", 0.1);
      defaults.put("portal.createNether.localMessage", false);
      defaults.put("portal.createEnd.enabled", false);
      defaults.put("portal.createEnd.denyOnLackPay", false);
      defaults.put("portal.createEnd.denyOnLimit", false);
      defaults.put("portal.createEnd.limit", 10);
      defaults.put("portal.createEnd.pay.ender", 0.1);
      defaults.put("portal.createEnd.localMessage", false);
      defaults.put("portal.createCustom.enabled", false);
      defaults.put("portal.createCustom.denyOnLackPay", false);
      defaults.put("portal.createCustom.denyOnLimit", false);
      defaults.put("portal.createCustom.limit", 10);
      defaults.put("portal.createCustom.pay.custom", 0.1);
      defaults.put("portal.createCustom.localMessage", false);
      defaults.put("portal.enter.enabled", false);
      defaults.put("portal.enter.limit", 10);
      defaults.put("portal.enter.pay", 0.1);
      defaults.put("portal.enter.localMessage", false);
      defaults.put("tame.ocelot.enabled", false);
      defaults.put("tame.ocelot.limit", 10);
      defaults.put("tame.ocelot.pay", 10);
      defaults.put("tame.ocelot.localMessage", false);
      defaults.put("tame.wolf.enabled", false);
      defaults.put("tame.wolf.limit", 10);
      defaults.put("tame.wolf.pay", 10);
      defaults.put("tame.wolf.localMessage", false);
      // TODO implement
      /*
       * defaults.put("vehicle.enter.enabled", false);
       * defaults.put("vehicle.enter.denyOnLackPay", false);
       * defaults.put("vehicle.enter.denyOnLimit", false);
       * defaults.put("vehicle.enter.limit", 100);
       * defaults.put("vehicle.enter.pay", 0.1);
       * defaults.put("vehicle.exit.enabled", false);
       * defaults.put("vehicle.exit.denyOnLackPay", false);
       * defaults.put("vehicle.exit.denyOnLimit", false);
       * defaults.put("vehicle.exit.limit", 100);
       * defaults.put("vehicle.exit.pay", 0.1);
       */
      defaults.put("world.change.enabled", false);
      defaults.put("world.change.limit", 15);
      defaults.put("world.change.pay", 1.0);
      defaults.put("world.change.localMessage", false);
      defaults.put("mysql.use", false);
      defaults.put("mysql.host", "localhost");
      defaults.put("mysql.port", 3306);
      defaults.put("mysql.database", "minecraft");
      defaults.put("mysql.user", "username");
      defaults.put("mysql.password", "pass");
      defaults.put("mysql.tablePrefix", "kcon_");
      defaults.put("mysql.import", false);
      defaults.put("debug.events", false);
      defaults.put("debug.time", false);
      defaults.put("debug.economy", false);
      defaults.put("debug.unhandled", false);
      defaults.put("version", plugin.getDescription().getVersion());
      // Insert defaults into config file if they're not present
      for(final Entry<String, Object> e : defaults.entrySet()){
         if(!config.contains(e.getKey())){
            config.set(e.getKey(), e.getValue());
         }
      }
      // Save config
      plugin.saveConfig();
      // Load variables from config
      /**
       * SQL info
       */
      useMySQL = config.getBoolean("mysql.use", false);
      host = config.getString("mysql.host", "localhost");
      port = config.getString("mysql.port", "3306");
      database = config.getString("mysql.database", "minecraft");
      user = config.getString("mysql.user", "user");
      password = config.getString("mysql.password", "password");
      tablePrefix = config.getString("mysql.prefix", "kcon_");
      importSQL = config.getBoolean("mysql.import", false);
      // Load all other settings
      this.loadSettings(config);
      // Load config for item specific value
      items = new ItemsConfig(plugin);
      items.loadItemValueMap();
      // Load config for command specific values
      commands = new CommandsConfig(plugin);
      commands.loadCommandMap();
      // Finally, do a bounds check on parameters to make sure they are legal
      this.boundsCheck();
   }

   public void set(String path, Object o){
      final ConfigurationSection config = plugin.getConfig();
      config.set(path, o);
      plugin.saveConfig();
   }

   /**
    * Check if updates are necessary
    */
   public void checkUpdate(){
      // Check if need to update
      ConfigurationSection config = plugin.getConfig();
      if(Double.parseDouble(plugin.getDescription().getVersion()) > Double
            .parseDouble(config.getString("version"))){
         // Update to latest version
         plugin.getLogger().info(
               "Updating to v" + plugin.getDescription().getVersion());
         this.update();
      }
   }

   /**
    * This method is called to make the appropriate changes, most likely only
    * necessary for database schema modification, for a proper update.
    */
   @SuppressWarnings("unused")
   private void update(){
      // Grab current version
      final double ver = Double.parseDouble(plugin.getConfig().getString(
            "version"));
      // can remove old config options using the following:
      // plugin.getConfig().set("path.to.remove", null);
      // Update version number in config.yml
      plugin.getConfig().set("version", plugin.getDescription().getVersion());
      plugin.saveConfig();
      plugin.getLogger().info("Upgrade complete");
   }

   /**
    * Reloads info from yaml file(s)
    */
   public void reloadConfig(){
      // Initial relaod
      plugin.reloadConfig();
      // Grab config
      final ConfigurationSection config = plugin.getConfig();
      // Load settings
      this.loadSettings(config);
      // Load config for item specific values
      items.loadItemValueMap();
      // Load config for command specific values
      commands.loadCommandMap();
      // Check bounds
      this.boundsCheck();
      plugin.getLogger().info("Config reloaded");
   }

   private void loadSettings(ConfigurationSection config){
      /**
       * General Settings
       */
      listlimit = config.getInt("listlimit", 10);
      debugTime = config.getBoolean("debug.time", false);
      debugEvents = config.getBoolean("debug.events", false);
      debugEconomy = config.getBoolean("debug.economy", false);
      debugUnhandled = config.getBoolean("debug.unhandled", false);
      /**
       * Event Settings
       */
      // destroy
      blockDestroyStatic = config.getBoolean("block.destroy.static", true);
      // place
      blockPlaceStatic = config.getBoolean("block.place.static", true);
      /**
       * Item
       */
      // craft
      craftItemStatic = config.getBoolean("item.craft.static", true);
      // enchant
      enchantItemStatic = config.getBoolean("item.enchant.static", true);
      // drop
      itemDropStatic = config.getBoolean("item.drop.static", true);
      // pickup
      pickupStatic = config.getBoolean("item.drop.static", true);
      /**
       * Bow
       */
      // shoot
      shootBowDenyForce = config.getBoolean("bow.shoot.denyOnLowForce", false);
      shootBowForce = config.getDouble("bow.shoot.minimumforce", 0.0);
      /**
       * Player section
       */
      // command
      commandStatic = config.getBoolean("player.command.static", true);
      /**
       * Portal
       */
      // create nether
      portalCreateNether = config.getBoolean("portal.createNether.enabled",
            false);
      // create end
      portalCreateEnd = config.getBoolean("portal.createEnd.enabled", false);
      // create custom
      portalCreateCustom = config.getBoolean("portal.createCustom.enabled",
            false);
      /**
       * Vehicle
       * 
       * TODO implement
       */
      // enter
      /*
       * vehicleEnter = config.getBoolean("vehicle.enter.enabled", false);
       * vehicleEnterDenyPay = config.getBoolean("vehicle.enter.denyOnLackPay",
       * false); vehicleEnterDenyLimit =
       * config.getBoolean("vehicle.enter.denyOnLimit", false);
       * vehicleEnterLimit = config.getInt("vehicle.enter.limit", 100);
       * vehicleEnterPay = config.getDouble("vehicle.enter.pay", 0.1);
       */
      // exit
      /*
       * vehicleExit = config.getBoolean("vehicle.exit.enabled", false);
       * vehicleExitLimit = config.getInt("vehicle.exit.limit", 100);
       * vehicleExitPay = config.getDouble("vehicle.exit.pay", 0.1);
       */
   }

   /**
    * Check the bounds on the parameters to make sure that all config variables
    * are legal and usable by the plugin
    */
   private void boundsCheck(){
      // TODO format all doubles to 2 decimal places
   }

   @Override
   public double getPayValue(Field type, Item item, String command){
      double pay = 0.0;
      boolean found = false;
      if(item != null){
         switch(type){
         case BLOCK_PLACE:{
            if(!blockPlaceStatic){
               if(items.containsItem(item)){
                  pay = items.getPayValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         case BLOCK_DESTROY:{
            if(!blockDestroyStatic){
               if(items.containsItem(item)){
                  pay = items.getPayValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         case ITEM_CRAFT:{
            if(!craftItemStatic){
               if(items.containsItem(item)){
                  pay = items.getPayValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         case ITEM_DROP:{
            if(!itemDropStatic){
               if(items.containsItem(item)){
                  pay = items.getPayValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         case ITEM_ENCHANT:{
            if(!enchantItemStatic){
               if(items.containsItem(item)){
                  pay = items.getPayValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         case ITEM_PICKUP:{
            if(!pickupStatic){
               if(items.containsItem(item)){
                  pay = items.getPayValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         default:{
            if(debugUnhandled){
               plugin.getLogger().warning(
                     "Unhandled pay for field " + type.name());
            }
            break;
         }
         }
         if(!found){
            pay = plugin.getConfig().getDouble(type.getConfigPath() + ".pay",
                  0.0);
         }
      }else if(command != null){
         if(!commandStatic){
            pay = commands.getPayValue(Field.COMMAND, null, command);
         }else{
            pay = plugin.getConfig().getDouble(type.getConfigPath() + ".pay",
                  0.0);
         }
      }else{
         pay = plugin.getConfig().getDouble(type.getConfigPath() + ".pay", 0.0);
      }
      return pay;
   }

   @Override
   public int getLimitValue(Field type, Item item, String command){
      int limit = -1;
      boolean found = false;
      if(item != null){
         switch(type){
         case BLOCK_PLACE:{
            if(!blockPlaceStatic){
               if(items.containsItem(item)){
                  limit = items.getLimitValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         case BLOCK_DESTROY:{
            if(!blockDestroyStatic){
               if(items.containsItem(item)){
                  limit = items.getLimitValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         case ITEM_CRAFT:{
            if(!craftItemStatic){
               if(items.containsItem(item)){
                  limit = items.getLimitValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         case ITEM_DROP:{
            if(!itemDropStatic){
               if(items.containsItem(item)){
                  limit = items.getLimitValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         case ITEM_ENCHANT:{
            if(!enchantItemStatic){
               if(items.containsItem(item)){
                  limit = items.getLimitValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         case ITEM_PICKUP:{
            if(!pickupStatic){
               if(items.containsItem(item)){
                  limit = items.getLimitValue(type, item, null);
                  found = true;
               }
            }
            break;
         }
         default:{
            if(debugUnhandled){
               plugin.getLogger().warning(
                     "Unhandled limit for field " + type.name());
            }
            break;
         }
         }
         if(!found){
            limit = plugin.getConfig().getInt(type.getConfigPath() + ".limit",
                  -1);
         }
      }else if(command != null){
         if(!commandStatic){
            limit = commands.getLimitValue(Field.COMMAND, null, command);
         }else{
            limit = plugin.getConfig().getInt(type.getConfigPath() + ".limit",
                  -1);
         }
      }else{
         limit = plugin.getConfig().getInt(type.getConfigPath() + ".limit", -1);
      }
      return limit;
   }

   @Override
   public boolean getDenyPay(Field field, Item item, String command){
      if(item != null){
         return getItemDenyPay(field, item);
      }else if(command != null){
         if(!commandStatic){
            return commands.getDenyPay(Field.COMMAND, null, command);
         }else{
            return plugin.getConfig().getBoolean(
                  field.getConfigPath() + ".denyOnLackPay", false);
         }
      }else{
         return plugin.getConfig().getBoolean(
               field.getConfigPath() + ".denyOnLackPay", false);
      }
   }

   public String getItemBypass(Item item){
      if(items.containsItem(item)){
         return items.getBypass(item);
      }
      return "";
   }

   public String getCommandBypass(String command){
      if(commands.containsCommand(command)){
         return commands.getBypass(command);
      }
      return "";
   }

   private boolean getItemDenyPay(Field type, Item item){
      switch(type){
      case BLOCK_PLACE:{
         if(!blockPlaceStatic){
            if(items.containsItem(item)){
               return items.getDenyPay(type, item, null);
            }
         }
         break;
      }
      case BLOCK_DESTROY:{
         if(!blockDestroyStatic){
            if(items.containsItem(item)){
               return items.getDenyPay(type, item, null);
            }
         }
         break;
      }
      case ITEM_CRAFT:{
         if(!craftItemStatic){
            if(items.containsItem(item)){
               return items.getDenyPay(type, item, null);
            }
         }
         break;
      }
      case ITEM_DROP:{
         if(!itemDropStatic){
            if(items.containsItem(item)){
               return items.getDenyPay(type, item, null);
            }
         }
         break;
      }
      case ITEM_ENCHANT:{
         if(!enchantItemStatic){
            if(items.containsItem(item)){
               return items.getDenyPay(type, item, null);
            }
         }
         break;
      }
      case ITEM_PICKUP:{
         if(!pickupStatic){
            if(items.containsItem(item)){
               return items.getDenyPay(type, item, null);
            }
         }
         break;
      }
      default:{
         if(debugUnhandled){
            plugin.getLogger().warning("Unhandled Deny Pay for " + type.name());
         }
         break;
      }
      }
      // Non-specifc, so return default
      return plugin.getConfig().getBoolean(
            type.getConfigPath() + ".denyOnLackPay", false);
   }

   @Override
   public boolean getDenyLimit(Field field, Item item, String command){
      if(item != null){
         return getItemDenyLimit(field, item);
      }else if(command != null){
         if(!commandStatic){
            return commands.getDenyLimit(Field.COMMAND, null, command);
         }else{
            return plugin.getConfig().getBoolean(
                  field.getConfigPath() + ".denyOnLimit", false);
         }
      }else{
         return plugin.getConfig().getBoolean(
               field.getConfigPath() + ".denyOnLimit", false);
      }
   }

   private boolean getItemDenyLimit(Field type, Item item){
      switch(type){
      case BLOCK_PLACE:{
         if(!blockPlaceStatic){
            if(items.containsItem(item)){
               return items.getDenyLimit(type, item, null);
            }
         }
         break;
      }
      case BLOCK_DESTROY:{
         if(!blockDestroyStatic){
            if(items.containsItem(item)){
               return items.getDenyLimit(type, item, null);
            }
         }
         break;
      }
      case ITEM_CRAFT:{
         if(!craftItemStatic){
            if(items.containsItem(item)){
               return items.getDenyLimit(type, item, null);
            }
         }
         break;
      }
      case ITEM_DROP:{
         if(!itemDropStatic){
            if(items.containsItem(item)){
               return items.getDenyLimit(type, item, null);
            }
         }
         break;
      }
      case ITEM_ENCHANT:{
         if(!enchantItemStatic){
            if(items.containsItem(item)){
               return items.getDenyLimit(type, item, null);
            }
         }
         break;
      }
      case ITEM_PICKUP:{
         if(!pickupStatic){
            if(items.containsItem(item)){
               return items.getDenyLimit(type, item, null);
            }
         }
         break;
      }
      default:{
         if(debugUnhandled){
            plugin.getLogger().warning(
                  "Unhandled Deny Limit for " + type.name());
         }
         break;
      }
      }
      return plugin.getConfig().getBoolean(
            type.getConfigPath() + ".denyOnLimit", false);
   }

   @Override
   public boolean sendBroadcast(Field field){
      return plugin.getConfig().getBoolean(
            field.getConfigPath() + ".localMessage", false);
   }

   @Override
   public boolean checkWorld(Field field, String worldName){
      return checkWorld(field, null, null, worldName);
   }

   @Override
   public boolean checkWorld(Field field, Item item, String command,
         String worldName){
      boolean valid = false;
      if(item != null){
         valid = items.checkWorld(field, item, null, worldName);
      }else if(command != null){
         valid = commands.checkWorld(field, null, command, worldName);
      }else{
         final List<String> list = plugin.getConfig().getStringList(
               field.getConfigPath() + ".worlds");
         if(list == null){
            // No worlds specified, so allow all
            valid = true;
         }else if(list.isEmpty()){
            valid = true;
         }else{
            for(String world : list){
               if(world.equalsIgnoreCase(worldName)){
                  valid = true;
                  break;
               }
            }
         }
      }
      return valid;
   }

   public ItemsConfig getItemsConfig(){
      return items;
   }

   public CommandsConfig getCommandsConfig(){
      return commands;
   }

   @Override
   public boolean isEnabled(Field field){
      return plugin.getConfig().getBoolean(field.getConfigPath() + ".enabled",
            false);
   }
}
