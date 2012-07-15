package com.mitsugaru.Karmiconomy.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.comparable.ComparableCommand;
import com.mitsugaru.Karmiconomy.database.Field;

public class CommandsConfig implements KConfig{

   private Karmiconomy plugin;
   private final Map<ComparableCommand, KCCommandInfo> commands = new HashMap<ComparableCommand, KCCommandInfo>();

   public CommandsConfig(Karmiconomy plugin){
      this.plugin = plugin;
   }

   @Override
   public boolean isEnabled(Field field){
      return true;
   }

   @Override
   public boolean getDenyPay(Field field, Item item, String command){
      boolean deny = false;
      if(command == null){
         return deny;
      }
      final ComparableCommand com = new ComparableCommand(command);
      for(Map.Entry<ComparableCommand, KCCommandInfo> entry : commands.entrySet())
      {
         if(entry.getKey().compareTo(com) == 0)
         {
            deny = entry.getValue().denyPay;
            break;
         }
      }
      return deny;
   }

   @Override
   public boolean getDenyLimit(Field field, Item item, String command){
      boolean deny = false;
      if(command == null){
         return deny;
      }
      final ComparableCommand com = new ComparableCommand(command);
      for(Map.Entry<ComparableCommand, KCCommandInfo> entry : commands.entrySet())
      {
         if(entry.getKey().compareTo(com) == 0)
         {
            deny = entry.getValue().denyLimit;
            break;
         }
      }
      return deny;
   }

   @Override
   public int getLimitValue(Field field, Item item, String command){
      int limit = 0;
      if(command == null){
         return limit;
      }
      final ComparableCommand com = new ComparableCommand(command);
      for(Map.Entry<ComparableCommand, KCCommandInfo> entry : commands.entrySet())
      {
         if(entry.getKey().compareTo(com) == 0)
         {
            limit = entry.getValue().limit;
            break;
         }
      }
      return limit;
   }

   @Override
   public double getPayValue(Field field, Item item, String command){
      double pay = 0.0;
      if(command == null){
         return pay;
      }
      final ComparableCommand com = new ComparableCommand(command);
      for(Map.Entry<ComparableCommand, KCCommandInfo> entry : commands.entrySet())
      {
         if(entry.getKey().compareTo(com) == 0)
         {
            pay = entry.getValue().pay;
            break;
         }
      }
      return pay;
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
      if(command == null){
         return valid;
      }
      final ComparableCommand com = new ComparableCommand(command);
      if(commands.containsKey(com)){
         final YamlConfiguration valueFile = this.commandsValueFile();
         final List<String> list = valueFile
               .getStringList(commands.get(com).path + ".worlds");
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
         valid = true;
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

   public Map<ComparableCommand, KCCommandInfo> getCommandMap(){
      return commands;
   }

   public boolean containsCommand(String command){
      final ComparableCommand com = new ComparableCommand(command);
      if(commands.containsKey(com)){
         return true;
      }
      return false;
   }
   
   public ComparableCommand getComparableCommand(String command)
   {
      final ComparableCommand com = new ComparableCommand(command);
      for(ComparableCommand com2 : commands.keySet())
      {
         if(com2.compareTo(com) == 0)
         {
            return com2;
         }
      }
      return null;
   }

   public String getBypass(String command){
      return commands.get(new ComparableCommand(command)).bypass;
   }

   private YamlConfiguration commandsValueFile(){
      final File file = new File(plugin.getDataFolder().getAbsolutePath()
            + "/commands.yml");
      final YamlConfiguration comFile = YamlConfiguration
            .loadConfiguration(file);
      try{
         comFile.save(file);
      }catch(IOException e){
         plugin.getLogger().severe("Could not save commands.yml file!");
      }
      return comFile;
   }

   void loadCommandMap(){
      commands.clear();
      final YamlConfiguration comFile = this.commandsValueFile();

      for(final String entry : comFile.getKeys(false)){
         if(comFile.isConfigurationSection(entry)){
            // Attempt to grab information
            parseInfo(comFile, entry);
         }
      }
   }

   private void parseInfo(YamlConfiguration config, String entry){
      final String command = config.getString(entry + ".command");
      if(command == null || command.equals("")){
         plugin.getLogger().warning("Missing command for entry: " + entry);
         return;
      }
      final String bypass = config.getString(entry + ".bypass", "");
      final double pay = config.getDouble(entry + ".pay", plugin
            .getPluginConfig().getPayValue(Field.COMMAND, null, null));
      final int limit = config.getInt(entry + ".limit", plugin
            .getPluginConfig().getLimitValue(Field.COMMAND, null, null));
      final boolean denyPay = config.getBoolean(entry + ".denyPay", plugin
            .getPluginConfig().getDenyPay(Field.COMMAND, null, null));
      final boolean denyLimit = config.getBoolean(entry + ".denyLimit", plugin
            .getPluginConfig().getDenyLimit(Field.COMMAND, null, null));
      commands.put(new ComparableCommand(command), new KCCommandInfo(entry,
            bypass, pay, limit, denyPay, denyLimit));
   }

   public class KCCommandInfo{
      public String path, bypass;
      public double pay;
      public int limit;
      public boolean denyPay, denyLimit;

      public KCCommandInfo(String path, String bypass, double pay, int limit,
            boolean denyPay, boolean denyLimit){
         this.path = path;
         this.bypass = bypass;
         this.pay = pay;
         this.limit = limit;
         this.denyPay = denyPay;
         this.denyLimit = denyLimit;
      }
   }

}
