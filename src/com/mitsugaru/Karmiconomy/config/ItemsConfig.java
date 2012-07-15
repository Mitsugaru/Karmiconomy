package com.mitsugaru.Karmiconomy.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.database.Field;

public class ItemsConfig implements KConfig{
   private Karmiconomy plugin;
   private final Map<Item, KCItemInfo> items = new HashMap<Item, KCItemInfo>();

   public ItemsConfig(Karmiconomy plugin){
      this.plugin = plugin;
   }

   @Override
   public boolean isEnabled(Field field){
      return true;
   }

   @Override
   public boolean getDenyPay(Field field, Item item, String command){
      boolean deny = false;
      if(item == null){
         return deny;
      }
      switch(field){
      case BLOCK_PLACE:{
         if(items.containsKey(item)){
            deny = items.get(item).placeDenyPay;
         }
         break;
      }
      case BLOCK_DESTROY:{
         if(items.containsKey(item)){
            deny = items.get(item).destroyDenyPay;
         }
         break;
      }
      case ITEM_CRAFT:{
         if(items.containsKey(item)){
            deny = items.get(item).craftDenyPay;
         }
         break;
      }
      case ITEM_DROP:{
         if(items.containsKey(item)){
            deny = items.get(item).dropDenyPay;
         }
         break;
      }
      case ITEM_ENCHANT:{
         if(items.containsKey(item)){
            deny = items.get(item).enchantDenyPay;
         }
         break;
      }
      case ITEM_PICKUP:{
         if(items.containsKey(item)){
            deny = items.get(item).pickupDenyPay;
         }
         break;
      }
      default:{
         if(plugin.getPluginConfig().debugUnhandled){
            plugin.getLogger().warning(
                  "Unhandled pay for field " + field.name());
         }
         break;
      }
      }
      return deny;
   }

   @Override
   public boolean getDenyLimit(Field field, Item item, String command){
      boolean deny = false;
      if(item == null){
         return deny;
      }
      switch(field){
      case BLOCK_PLACE:{
         if(items.containsKey(item)){
            deny = items.get(item).placeDenyLimit;
         }
         break;
      }
      case BLOCK_DESTROY:{
         if(items.containsKey(item)){
            deny = items.get(item).destroyDenyLimit;
         }
         break;
      }
      case ITEM_CRAFT:{
         if(items.containsKey(item)){
            deny = items.get(item).craftDenyLimit;
         }
         break;
      }
      case ITEM_DROP:{
         if(items.containsKey(item)){
            deny = items.get(item).dropDenyLimit;
         }
         break;
      }
      case ITEM_ENCHANT:{
         if(items.containsKey(item)){
            deny = items.get(item).enchantDenyLimit;
         }
         break;
      }
      case ITEM_PICKUP:{
         if(items.containsKey(item)){
            deny = items.get(item).pickupDenyLimit;
         }
         break;
      }
      default:{
         if(plugin.getPluginConfig().debugUnhandled){
            plugin.getLogger().warning(
                  "Unhandled pay for field " + field.name());
         }
         break;
      }
      }
      return deny;
   }

   @Override
   public int getLimitValue(Field field, Item item, String command){
      int limit = 0;
      if(item == null){
         return limit;
      }
      switch(field){
      case BLOCK_PLACE:{
         if(items.containsKey(item)){
            limit = items.get(item).placeLimit;
         }
         break;
      }
      case BLOCK_DESTROY:{
         if(items.containsKey(item)){
            limit = items.get(item).destroyLimit;
         }
         break;
      }
      case ITEM_CRAFT:{
         if(items.containsKey(item)){
            limit = items.get(item).craftLimit;
         }
         break;
      }
      case ITEM_DROP:{
         if(items.containsKey(item)){
            limit = items.get(item).dropLimit;
         }
         break;
      }
      case ITEM_ENCHANT:{
         if(items.containsKey(item)){
            limit = items.get(item).enchantLimit;
         }
         break;
      }
      case ITEM_PICKUP:{
         if(items.containsKey(item)){
            limit = items.get(item).pickupLimit;
         }
         break;
      }
      default:{
         if(plugin.getPluginConfig().debugUnhandled){
            plugin.getLogger().warning(
                  "Unhandled pay for field " + field.name());
         }
         break;
      }
      }
      return limit;
   }

   @Override
   public double getPayValue(Field field, Item item, String command){
      // TODO Auto-generated method stub
      double pay = 0.0;
      if(item == null){
         return pay;
      }
      switch(field){
      case BLOCK_PLACE:{
         if(items.containsKey(item)){
            pay = items.get(item).placePay;
         }
         break;
      }
      case BLOCK_DESTROY:{
         if(items.containsKey(item)){
            pay = items.get(item).destroyPay;
         }
         break;
      }
      case ITEM_CRAFT:{
         if(items.containsKey(item)){
            pay = items.get(item).craftPay;
         }
         break;
      }
      case ITEM_DROP:{
         if(items.containsKey(item)){
            pay = items.get(item).dropPay;
         }
         break;
      }
      case ITEM_ENCHANT:{
         if(items.containsKey(item)){
            pay = items.get(item).enchantPay;
         }
         break;
      }
      case ITEM_PICKUP:{
         if(items.containsKey(item)){
            pay = items.get(item).pickupPay;
         }
         break;
      }
      default:{
         if(plugin.getPluginConfig().debugUnhandled){
            plugin.getLogger().warning(
                  "Unhandled pay for field " + field.name());
         }
         break;
      }
      }
      return pay;
   }

   public String getBypass(Item item){
      return items.get(item).bypass;
   }

   @Override
   public boolean sendBroadcast(Field field){
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean checkWorld(Field field, String worldName){
      return checkWorld(field, null, null, worldName);
   }

   @Override
   public boolean checkWorld(Field field, Item item, String command,
         String worldName){
      boolean valid = false, noList = false;
      ;
      if(item == null){
         return valid;
      }
      if(items.containsKey(item)){
         final YamlConfiguration valueFile = this.itemValuesFile();
         final List<String> list = valueFile.getStringList(items.get(item).path
               + ".worlds");
         if(list == null){
            // Check base
            noList = true;
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
      if(noList){
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

   public boolean containsItem(Item item){
      if(item == null){
         return false;
      }
      return items.containsKey(item);
   }

   public Map<Item, KCItemInfo> getItemValueMap(){
      return items;
   }

   /**
    * Loads the per-item karma values into a hashmap for later usage
    */
   void loadItemValueMap(){
      //Flush old config
      items.clear();
      // Load karma file
      final YamlConfiguration valueFile = this.itemValuesFile();
      // Load custom karma file into map
      for(final String entry : valueFile.getKeys(false)){
         try{
            // Attempt to parse non data value nodes
            int key = Integer.parseInt(entry);
            if(key <= 0){
               plugin.getLogger().warning(
                     Karmiconomy.TAG + " Zero or negative item id for entry: "
                           + entry);
            }else{
               // If it has child nodes, parse those as well
               if(valueFile.isConfigurationSection(entry)){
                  items.put(new Item(key, Byte.parseByte("" + 0), (short) 0),
                        parseInfo(valueFile, entry));
               }else{
                  plugin.getLogger().warning("No section for " + entry);
               }
            }
         }catch(final NumberFormatException ex){
            // Potential data value entry
            if(entry.contains("&")){
               try{
                  final String[] split = entry.split("&");
                  final int item = Integer.parseInt(split[0]);
                  final int data = Integer.parseInt(split[1]);
                  if(item <= 0){
                     plugin.getLogger().warning(
                           Karmiconomy.TAG
                                 + " Zero or negative item id for entry: "
                                 + entry);
                  }else{
                     if(valueFile.isConfigurationSection(entry)){
                        if(item != 373){
                           items.put(new Item(item, Byte.parseByte("" + data),
                                 (short) data), parseInfo(valueFile, entry));
                        }else{
                           items.put(new Item(item, Byte.parseByte("" + 0),
                                 (short) data), parseInfo(valueFile, entry));
                        }
                     }else{
                        plugin.getLogger().warning("No section for " + entry);
                     }
                  }
               }catch(ArrayIndexOutOfBoundsException a){
                  plugin.getLogger().warning(
                        "Wrong format for " + entry
                              + ". Must follow '<itemid>&<datavalue>:' entry.");
               }catch(NumberFormatException exa){
                  plugin.getLogger().warning("Non-integer number for " + entry);
               }
            }else{
               plugin.getLogger().warning("Invalid entry for " + entry);
            }
         }
      }
      plugin.getLogger().info("Loaded custom values");
   }

   private KCItemInfo parseInfo(YamlConfiguration config, String path){
      final String bypass = config.getString(path + ".bypass", "");
      final double iCraftPay = config.getDouble(path + ".craftPay",
            this.getPayValue(Field.ITEM_CRAFT, null, null));
      final double iEnchantPay = config.getDouble(path + ".enchantPay",
            this.getPayValue(Field.ITEM_ENCHANT, null, null));
      final double iPlacePay = config.getDouble(path + ".placePay",
            this.getPayValue(Field.BLOCK_PLACE, null, null));
      final double iDestroyPay = config.getDouble(path + ".destroyPay",
            this.getPayValue(Field.BLOCK_DESTROY, null, null));
      final double iDropPay = config.getDouble(path + ".dropPay",
            this.getPayValue(Field.ITEM_DROP, null, null));
      final int iCraftLimit = config.getInt(path + ".craftLimit",
            this.getLimitValue(Field.ITEM_CRAFT, null, null));
      final int iEnchantLimit = config.getInt(path + ".enchantLimit",
            this.getLimitValue(Field.ITEM_ENCHANT, null, null));
      final int iPlaceLimit = config.getInt(path + ".placeLimit",
            this.getLimitValue(Field.BLOCK_PLACE, null, null));
      final int iDestroyLimit = config.getInt(path + ".destroyLimit",
            this.getLimitValue(Field.BLOCK_DESTROY, null, null));
      final int iDropLimit = config.getInt(path + ".dropLimit",
            this.getLimitValue(Field.ITEM_DROP, null, null));
      final boolean iCraftDenyPay = config.getBoolean(
            path + ".craftDenyOnPay",
            plugin.getConfig().getBoolean(
                  Field.ITEM_CRAFT.getConfigPath() + ".denyOnLackPay", false));
      final boolean iCraftDenyLimit = config.getBoolean(
            path + ".craftDenyOnLimit",
            plugin.getConfig().getBoolean(
                  Field.ITEM_CRAFT.getConfigPath() + ".denyOnLimit", false));
      final boolean iEnchantDenyPay = config
            .getBoolean(
                  path + ".enchantDenyOnPay",
                  plugin.getConfig().getBoolean(
                        Field.ITEM_ENCHANT.getConfigPath() + ".denyOnLackPay",
                        false));
      final boolean iEnchantDenyLimit = config.getBoolean(
            path + ".enchantDenyOnLimit",
            plugin.getConfig().getBoolean(
                  Field.ITEM_ENCHANT.getConfigPath() + ".denyOnLimit", false));
      final boolean iPlaceDenyPay = config.getBoolean(
            path + ".placeDenyOnPay",
            plugin.getConfig().getBoolean(
                  Field.BLOCK_PLACE.getConfigPath() + ".denyOnLackPay", false));
      final boolean iPlaceDenyLimit = config.getBoolean(
            path + ".placeDenyOnLimit",
            plugin.getConfig().getBoolean(
                  Field.BLOCK_PLACE.getConfigPath() + ".denyOnLimit", false));
      final boolean iDestroyDenyPay = config.getBoolean(
            path + ".destroyDenyOnPay",
            plugin.getConfig()
                  .getBoolean(
                        Field.BLOCK_DESTROY.getConfigPath() + ".denyOnLackPay",
                        false));
      final boolean iDestroyDenyLimit = config.getBoolean(
            path + ".destroyDenyOnLimit",
            plugin.getConfig().getBoolean(
                  Field.BLOCK_DESTROY.getConfigPath() + ".denyOnLimit", false));
      final boolean iDropDenyPay = config.getBoolean(
            path + ".dropDenyOnPay",
            plugin.getConfig().getBoolean(
                  Field.ITEM_DROP.getConfigPath() + ".denyOnLackPay", false));
      final boolean iDropDenyLimit = config.getBoolean(
            path + ".dropDenyOnLimit",
            plugin.getConfig().getBoolean(
                  Field.ITEM_PICKUP.getConfigPath() + ".denyOnLimit", false));
      final int iPickupLimit = config.getInt(path + ".pickupLimit",
            this.getLimitValue(Field.ITEM_PICKUP, null, null));
      final double iPickupPay = config.getDouble(path + ".pickupPay",
            this.getPayValue(Field.ITEM_PICKUP, null, null));
      final boolean iPickupDenyPay = config.getBoolean(
            path + ".pickupDenyOnPay",
            plugin.getConfig().getBoolean(
                  Field.ITEM_PICKUP.getConfigPath() + ".denyOnLackPay", false));
      final boolean iPickupDenyLimit = config.getBoolean(
            path + ".pickupDenyOnLimit",
            plugin.getConfig().getBoolean(
                  Field.ITEM_PICKUP.getConfigPath() + ".denyOnLimit", false));
      KCItemInfo info = new KCItemInfo(path, bypass, iCraftLimit, iCraftPay,
            iCraftDenyPay, iCraftDenyLimit, iEnchantLimit, iEnchantPay,
            iEnchantDenyPay, iEnchantDenyLimit, iPlaceLimit, iPlacePay,
            iPlaceDenyPay, iPlaceDenyLimit, iDestroyLimit, iDestroyPay,
            iDestroyDenyPay, iDestroyDenyLimit, iDropLimit, iDropPay,
            iDropDenyPay, iDropDenyLimit, iPickupLimit, iPickupPay,
            iPickupDenyPay, iPickupDenyLimit);
      return info;
   }

   /**
    * Loads the value file. Contains default values If the value file isn't
    * there, or if its empty, then load defaults.
    * 
    * @return YamlConfiguration file
    */
   private YamlConfiguration itemValuesFile(){
      final File file = new File(plugin.getDataFolder().getAbsolutePath()
            + "/values.yml");
      final YamlConfiguration valueFile = YamlConfiguration
            .loadConfiguration(file);
      if(valueFile.getKeys(false).isEmpty()){
         // Defaults
         valueFile.set("14.dropPay", 0.0);
         valueFile.set("14.dropDenyOnPay", false);
         valueFile.set("14.dropDenyOnLimit", false);
         valueFile.set("14.dropLimit", 0);
         valueFile.set("14.placePay", 0.0);
         valueFile.set("14.placeDenyOnPay", false);
         valueFile.set("14.placeDenyOnLimit", false);
         valueFile.set("14.placeLimit", 0);
         valueFile.set("14.pickupPay", 0.0);
         valueFile.set("14.pickupDenyOnPay", false);
         valueFile.set("14.pickupDenyOnLimit", false);
         valueFile.set("14.pickupLimit", 0);
         valueFile.set("35&7.destroyPay", 5.0);
         valueFile.set("35&7.destroyDenyOnPay", false);
         valueFile.set("35&7.destroyDenyOnLimit", false);
         valueFile.set("35&7.destroyLimit", 0);
         valueFile.set("290.craftPay", 0);
         valueFile.set("290.craftDenyOnPay", false);
         valueFile.set("290.craftDenyOnLimit", false);
         valueFile.set("290.craftLimit", 0);
         valueFile.set("290.enchantPay", 0.0);
         valueFile.set("290.enchantDenyOnPay", false);
         valueFile.set("290.enchantDenyOnLimit", false);
         valueFile.set("290.enchantLimit", 0);
         // Insert defaults into file if they're not present
         try{
            // Save the file
            valueFile.save(file);
         }catch(IOException e1){
            plugin.getLogger().warning(
                  "File I/O Exception on saving karma list");
            e1.printStackTrace();
         }
      }
      return valueFile;
   }

   // Private class to hold item specific information
   public class KCItemInfo{
      public String path, bypass;
      public double craftPay, enchantPay, placePay, destroyPay, dropPay,
            pickupPay;
      public int craftLimit, enchantLimit, placeLimit, destroyLimit, dropLimit,
            pickupLimit;
      public boolean craftDenyPay, craftDenyLimit, enchantDenyPay,
            enchantDenyLimit, destroyDenyPay, destroyDenyLimit, placeDenyPay,
            placeDenyLimit, dropDenyPay, dropDenyLimit, pickupDenyPay,
            pickupDenyLimit;

      public KCItemInfo(String path, String bypass, int craftLimit,
            double craftPay, boolean craftDenyPay, boolean craftDenyLimit,
            int enchantLimit, double enchantPay, boolean enchantDenyPay,
            boolean enchantDenyLimit, int placeLimit, double placePay,
            boolean placeDenyPay, boolean placeDenyLimit, int destroyLimit,
            double destroyPay, boolean destroyDenyPay,
            boolean destroyDenyLimit, int dropLimit, double dropPay,
            boolean dropDenyPay, boolean dropDenyLimit, int pickupLimit,
            double pickupPay, boolean pickupDenyPay, boolean pickupDenyLimit){
         this.path = path;
         this.bypass = bypass;
         this.craftPay = craftPay;
         this.enchantPay = enchantPay;
         this.placePay = placePay;
         this.destroyPay = destroyPay;
         this.dropPay = dropPay;
         this.craftLimit = craftLimit;
         this.enchantLimit = enchantLimit;
         this.placeLimit = placeLimit;
         this.destroyLimit = destroyLimit;
         this.dropLimit = dropLimit;
         this.craftDenyPay = craftDenyPay;
         this.craftDenyLimit = craftDenyLimit;
         this.enchantDenyPay = enchantDenyPay;
         this.enchantDenyLimit = enchantDenyLimit;
         this.destroyDenyPay = destroyDenyPay;
         this.destroyDenyLimit = destroyDenyLimit;
         this.placeDenyPay = placeDenyPay;
         this.placeDenyLimit = placeDenyLimit;
         this.dropDenyPay = dropDenyPay;
         this.dropDenyLimit = dropDenyLimit;
         this.pickupLimit = pickupLimit;
         this.pickupPay = pickupPay;
         this.pickupDenyLimit = pickupDenyLimit;
         this.pickupDenyPay = pickupDenyPay;
      }
   }

}
