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
			//Check if table exists
			if (!mysql.checkTable(config.tablePrefix + "data"))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created data table");
				// TODO Create table
				mysql.createTable("CREATE TABLE "
						+ config.tablePrefix
						+ "data (id INT UNSIGNED NOT NULL AUTO_INCREMENT, playername varchar(32) NOT NULL, laston TEXT NOT NULL, UNIQUE (playername), PRIMARY KEY (id));");
			}
		}
		else
		{
			// Connect to sql database
			sqlite = new SQLite(plugin.getLogger(), Karmiconomy.TAG, "data",
					plugin.getDataFolder().getAbsolutePath());
			// Check if table exists
			if (!sqlite.checkTable(config.tablePrefix + "data"))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created data table");
				// TODO create table
				sqlite.createTable("CREATE TABLE "
						+ config.tablePrefix
						+ "data (id INTEGER PRIMARY KEY, playername varchar(32) NOT NULL, laston TEXT NOT NULL, UNIQUE (playername));");
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
					+ "data;");
			if (rs.getResult().next())
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Importing jailed players...");
				do
				{
					//TODO import
					//PreparedStatement statement = mysql.prepare("INSERT INTO " + config.tablePrefix + ");
				}while (rs.getResult().next());
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
}
