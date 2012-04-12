package com.mitsugaru.Karmiconomy;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import lib.Mitsugaru.SQLibrary.MySQL;
import lib.Mitsugaru.SQLibrary.SQLite;
import lib.Mitsugaru.SQLibrary.Database.Query;

public class DatabaseHandler
{
	private Karmiconomy plugin;
	private Config config;
	private SQLite sqlite;
	private MySQL mysql;
	private boolean useMySQL;

	public DatabaseHandler(Karmiconomy plugin, Config conf)
	{
		this.plugin = plugin;
		config = conf;
		useMySQL = config.useMySQL;
		checkTables();
		if (config.importSQL)
		{
			if (useMySQL)
			{
				importSQL();
			}
			config.set("mysql.import", false);
		}
	}

	private void checkTables()
	{
		if (useMySQL)
		{
			// Connect to mysql database
			mysql = new MySQL(plugin.getLogger(), Karmiconomy.TAG, config.host,
					config.port, config.database, config.user, config.password);
			// Check if table exists
			if (!mysql.checkTable(config.tablePrefix + "master"))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created master table");
				// Create table
				mysql.createTable("CREATE TABLE "
						+ config.tablePrefix
						+ "master (id INT UNSIGNED NOT NULL AUTO_INCREMENT, playername varchar(32) NOT NULL, laston TEXT NOT NULL, UNIQUE (playername), PRIMARY KEY (id));");
			}
			if (!mysql.checkTable(config.tablePrefix + "items"))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created items table");
				mysql.createTable("CREATE TABLE "
						+ config.tablePrefix
						+ "items (id INT UNSIGNED NOT NULL, itemid SMALLINT UNSIGNED NOT NULL, data TINYTEXT, durability TINYTEXT, place INT NOT NULL, destroy INT NOT NULL, craft INT NOT NULL, enchant INT NOT NULL, drop INT NOT NULL)");
			}
			if (!mysql.checkTable(config.tablePrefix + "data"))
			{
				mysql.createTable("CREATE TABLE "
						+ config.tablePrefix
						+ "data (id INT UNSIGNED NOT NULL, bedenter INT NOT NULL, bedleave INT NOT NULL, bowhoot INT NOT NULL, chat INT NOT NULL, command INT NOT NULL, death INT NOT NULL, creative INT NOT NULL, survival INT NOT NULL, join INT NOT NULL, kick INT NOT NULL, quit INT NOT NULL, respawn INT NOT NULL, worldchange INT NOT NULL, portalcreate INT NOT NULL, portalenter INT NOT NULL, tameocelot INT NOT NULL, tamewolf INT NOT NULL, PRIMARY KEY (id));");
			}
		}
		else
		{
			// Connect to sql database
			sqlite = new SQLite(plugin.getLogger(), Karmiconomy.TAG, "data",
					plugin.getDataFolder().getAbsolutePath());
			// Check if table exists
			if (!sqlite.checkTable(config.tablePrefix + "master"))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created master table");
				// Create table
				sqlite.createTable("CREATE TABLE "
						+ config.tablePrefix
						+ "master (id INTEGER PRIMARY KEY, playername varchar(32) NOT NULL, laston TEXT NOT NULL, UNIQUE (playername));");
			}
			if (!sqlite.checkTable(config.tablePrefix + "items"))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created items table");
			}
		}
	}

	private void importSQL()
	{
		// Connect to sql database
		try
		{
			// Grab local SQLite database
			sqlite = new SQLite(plugin.getLogger(), Karmiconomy.TAG, "data",
					plugin.getDataFolder().getAbsolutePath());
			// Copy items
			Query rs = sqlite.select("SELECT * FROM " + config.tablePrefix
					+ "master;");
			if (rs.getResult().next())
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Importing master table...");
				do
				{
					// TODO import
					// PreparedStatement statement =
					// mysql.prepare("INSERT INTO " + config.tablePrefix + ");
				} while (rs.getResult().next());
			}
			rs.closeQuery();
			plugin.getLogger().info(
					Karmiconomy.TAG + " Done importing SQLite into MySQL");
		}
		catch (SQLException e)
		{
			plugin.getLogger().warning(
					Karmiconomy.TAG + " SQL Exception on Import");
			e.printStackTrace();
		}
	}

	// TODO make method to get limit field for specified player

	private enum Field
	{
		//TODO eggs, paintings, vehicle
		BOW_SHOOT, BED_ENTER, BED_LEAVE, BLOCK_PLACE, BLOCK_DESTROY, ITEM_CRAFT, ITEM_ENCHANT, ITEM_DROP, CHAT, COMMAND, DEATH, CREATIVE, SURVIVAL, JOIN, KICK, QUIT, RESPAWN, PORTAL_CREATE, PORTAL_ENTER, TAME_OCELOT, TAME_WOLF, WORLD_CHANGE;
	}
}
