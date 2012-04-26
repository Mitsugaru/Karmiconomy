package com.mitsugaru.Karmiconomy.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.Karmiconomy;
import com.mitsugaru.Karmiconomy.config.Config;

import lib.Mitsugaru.SQLibrary.MySQL;
import lib.Mitsugaru.SQLibrary.SQLite;
import lib.Mitsugaru.SQLibrary.Database.Query;

public class DatabaseHandler
{
	private Karmiconomy plugin;
	private static Config config;
	private SQLite sqlite;
	private MySQL mysql;
	private boolean useMySQL;
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"MM-dd-yyyy");

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
			if (!mysql.checkTable(Table.MASTER.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created master table");
				// Create table
				mysql.createTable("CREATE TABLE "
						+ Table.MASTER.getName()
						+ " (id INT UNSIGNED NOT NULL AUTO_INCREMENT, playername varchar(32) NOT NULL, laston TEXT NOT NULL, UNIQUE (playername), PRIMARY KEY (id));");
			}
			if (!mysql.checkTable(Table.ITEMS.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created items table");
				mysql.createTable("CREATE TABLE "
						+ Table.ITEMS.getName()
						+ " (row INT UNSIGNED NOT NULL AUTO_INCREMENT, id INT UNSIGNED NOT NULL, itemid SMALLINT UNSIGNED NOT NULL, data TINYTEXT, durability TINYTEXT, place INT NOT NULL, destroy INT NOT NULL, craft INT NOT NULL, enchant INT NOT NULL, playerDrop INT NOT NULL, pickup INT NOT NULL, PRIMARY KEY(row))");
			}
			if (!mysql.checkTable(Table.COMMAND.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created command table");
				mysql.createTable("CREATE TABLE "
						+ Table.COMMAND.getName()
						+ " (row INT UNSIGNED NOT NULL AUTO_INCREMENT, id INT UNSIGNED NOT NULL, command TEXT NOT NULL, count INT NOT NULL, PRIMARY KEY(row));");
			}
			if (!mysql.checkTable(Table.DATA.getName()))
			{
				plugin.getLogger()
						.info(Karmiconomy.TAG + " Created data table");
				mysql.createTable("CREATE TABLE "
						+ Table.DATA.getName()
						+ " (id INT UNSIGNED NOT NULL, bedenter INT NOT NULL, bedleave INT NOT NULL, bowshoot INT NOT NULL, chat INT NOT NULL, death INT NOT NULL, creative INT NOT NULL, survival INT NOT NULL, playerJoin INT NOT NULL, kick INT NOT NULL, quit INT NOT NULL, respawn INT NOT NULL, worldchange INT NOT NULL, tameocelot INT NOT NULL, tamewolf INT NOT NULL, paintingplace INT NOT NULL, eggThrow INT NOT NULL, sneak INT NOT NULL, sprint INT NOT NULL, PRIMARY KEY (id));");
			}
			if (!mysql.checkTable(Table.PORTAL.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created portal table");
				mysql.createTable("CREATE TABLE "
						+ Table.PORTAL.getName()
						+ " (id INT UNSIGNED NOT NULL, pcreatenether INT NOT NULL, pcreateend INT NOT NULL, pcreatecustom INT NOT NULL, portalenter INT NOT NULL, PRIMARY KEY(id));");
			}
			if (!mysql.checkTable(Table.BUCKET.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created bucket table");
				mysql.createTable("CREATE TABLE "
						+ Table.BUCKET.getName()
						+ " (id INT UNSIGNED NOT NULL, bemptylava INT NOT NULL, bemptywater INT NOT NULL, bfilllava INT NOT NULL, bfillwater INT NOT NULL, PRIMARY KEY(id));");
			}
			if (!mysql.checkTable(Table.MCMMO.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created mcMMO table");
				mysql.createTable("CREATE TABLE "
						+ Table.MCMMO.getName()
						+ " (id INT UNSIGNED NOT NULL, partyteleport INT NOT NULL, partyjoin INT NOT NULL, partyleave INT NOT NULL, partykick INT NOT NULL, partychange INT NOT NULL, acrobaticslevel INT NOT NULL, archerylevel INT NOT NULL, axeslevel INT NOT NULL, excavationlevel INT NOT NULL, fishinglevel INT NOT NULL, herbalismlevel INT NOT NULL, mininglevel INT NOT NULL, repairlevel INT NOT NULL, swordslevel INT NOT NULL, taminglevel INT NOT NULL, unarmedlevel INT NOT NULL, woodcuttinglevel INT NOT NULL, acrobaticsgain INT NOT NULL, archerygain INT NOT NULL, axesgain INT NOT NULL, excavationgain INT NOT NULL, fishinggain INT NOT NULL, herbalismgain INT NOT NULL, mininggain INT NOT NULL, repairgain INT NOT NULL, swordsgain INT NOT NULL, taminggain INT NOT NULL, unarmedgain INT NOT NULL, woodcuttinggain INT NOT NULL, PRIMARY KEY(id));");
			}
			if (!mysql.checkTable(Table.HEROES.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created Heroes table");
				mysql.createTable("CREATE TABLE "
						+ Table.HEROES.getName()
						+ " (id INT UNSIGNED NOT NULL, classchange INT NOT NULL, expchange INT NOT NULL, changelevel INT NOT NULL, combatenter INT NOT NULL, combatleave INT NOT NULL, partyjoin INT NOT NULL, partyleave INT NOT NULL, killattackmob INT NOT NULL, killattackplayer INT NOT NULL, killdefendmob INT NOT NULL, killdefendplayer INT NOT NULL, regainhealth INT NOT NULL, regainmana INT NOT NULL, skillcomplete INT NOT NULL, skilldamage INT NOT NULL, skilluse INT NOT NULL, weapondamage INT NOT NULL, PRIMARY KEY(id));");
			}
		}
		else
		{
			// Connect to sql database
			sqlite = new SQLite(plugin.getLogger(), Karmiconomy.TAG, "data",
					plugin.getDataFolder().getAbsolutePath());
			// Check if table exists
			if (!sqlite.checkTable(Table.MASTER.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created master table");
				// Create table
				sqlite.createTable("CREATE TABLE "
						+ Table.MASTER.getName()
						+ " (id INTEGER PRIMARY KEY, playername varchar(32) NOT NULL, laston TEXT NOT NULL, UNIQUE (playername));");
			}
			if (!sqlite.checkTable(Table.ITEMS.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created items table");
				sqlite.createTable("CREATE TABLE "
						+ Table.ITEMS.getName()
						+ " (row INTEGER PRIMARY KEY, id INTEGER NOT NULL, itemid INTEGER NOT NULL, data TEXT NOT NULL, durability TEXT NOT NULL, place INTEGER NOT NULL, destroy INTEGER NOT NULL, craft INTEGER NOT NULL, enchant INTEGER NOT NULL, playerDrop INTEGER NOT NULL, pickup INT NOT NULL)");
			}
			if (!sqlite.checkTable(Table.COMMAND.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created command table");
				sqlite.createTable("CREATE TABLE "
						+ Table.COMMAND.getName()
						+ " (row INTEGER PRIMARY KEY, id INTEGER NOT NULL, command TEXT NOT NULL, count INTEGER NOT NULL);");
			}
			if (!sqlite.checkTable(Table.DATA.getName()))
			{
				plugin.getLogger()
						.info(Karmiconomy.TAG + " Created data table");
				sqlite.createTable("CREATE TABLE "
						+ Table.DATA.getName()
						+ " (id INTEGER PRIMARY KEY, bedenter INTEGER NOT NULL, bedleave INTEGER NOT NULL, bowshoot INTEGER NOT NULL, chat INTEGER NOT NULL, death INTEGER NOT NULL, creative INTEGER NOT NULL, survival INTEGER NOT NULL, playerJoin INTEGER NOT NULL, kick INTEGER NOT NULL, quit INTEGER NOT NULL, respawn INTEGER NOT NULL, worldchange INTEGER NOT NULL, tameocelot INTEGER NOT NULL, tamewolf INTEGER NOT NULL, paintingplace INTEGER NOT NULL, eggThrow INTEGER NOT NULL, sneak INTEGER NOT NULL, sprint INTEGER NOT NULL);");
			}
			if (!sqlite.checkTable(Table.PORTAL.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created portal table");
				sqlite.createTable("CREATE TABLE "
						+ Table.PORTAL.getName()
						+ " (id INTEGER PRIMARY KEY, pcreatenether INTEGER NOT NULL, pcreateend INTEGER NOT NULL, pcreatecustom INTEGER NOT NULL, portalenter INTEGER NOT NULL);");
			}
			if (!sqlite.checkTable(Table.BUCKET.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created bucket table");
				sqlite.createTable("CREATE TABLE "
						+ Table.BUCKET.getName()
						+ " (id INTEGER PRIMARY KEY, bemptylava INTEGER NOT NULL, bemptywater INTEGER NOT NULL, bfilllava INTEGER NOT NULL, bfillwater INTEGER NOT NULL);");
			}
			if (!sqlite.checkTable(Table.MCMMO.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created mcMMO table");
				sqlite.createTable("CREATE TABLE "
						+ Table.MCMMO.getName()
						+ " (id INTEGER PRIMARY KEY, partyteleport INTEGER NOT NULL, partyjoin INTEGER NOT NULL, partyleave INTEGER NOT NULL, partykick INTEGER NOT NULL, partychange INTEGER NOT NULL, acrobaticslevel INTEGER NOT NULL, archerylevel INTEGER NOT NULL, axeslevel INTEGER NOT NULL, excavationlevel INTEGER NOT NULL, fishinglevel INTEGER NOT NULL, herbalismlevel INTEGER NOT NULL, mininglevel INTEGER NOT NULL, repairlevel INTEGER NOT NULL, swordslevel INTEGER NOT NULL, taminglevel INTEGER NOT NULL, unarmedlevel INTEGER NOT NULL, woodcuttinglevel INTEGER NOT NULL, acrobaticsgain INTEGER NOT NULL, archerygain INTEGER NOT NULL, axesgain INTEGER NOT NULL, excavationgain INTEGER NOT NULL, fishinggain INTEGER NOT NULL, herbalismgain INTEGER NOT NULL, mininggain INTEGER NOT NULL, repairgain INTEGER NOT NULL, swordsgain INTEGER NOT NULL, taminggain INTEGER NOT NULL, unarmedgain INTEGER NOT NULL, woodcuttinggain INTEGER NOT NULL);");
			}
			if (!sqlite.checkTable(Table.HEROES.getName()))
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created Heroes table");
				sqlite.createTable("CREATE TABLE "
						+ Table.HEROES.getName()
						+ " (id INTEGER PRIMARY KEY, classchange INTEGER NOT NULL, expchange INTEGER NOT NULL, changelevel INTEGER NOT NULL, combatenter INTEGER NOT NULL, combatleave INTEGER NOT NULL, partyjoin INTEGER NOT NULL, partyleave INTEGER NOT NULL, killattackmob INTEGER NOT NULL, killattackplayer INTEGER NOT NULL, killdefendmob INTEGER NOT NULL, killdefendplayer INTEGER NOT NULL, regainhealth INTEGER NOT NULL, regainmana INTEGER NOT NULL, skillcomplete INTEGER NOT NULL, skilldamage INTEGER NOT NULL, skilluse INTEGER NOT NULL, weapondamage INTEGER NOT NULL);");
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
			// Copy tables
			Query rs = sqlite.select("SELECT * FROM " + Table.MASTER.getName()
					+ ";");
			if (rs.getResult().next())
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Importing master table...");
				PreparedStatement statement = mysql.prepare("INSERT INTO "
						+ Table.MASTER.getName()
						+ " (id, playername, laston) VALUES('0','0','0');");
				do
				{
					// import master
					statement.setInt(1, rs.getResult().getInt("id"));
					statement.setString(2,
							rs.getResult().getString("playername"));
					statement.setString(3, rs.getResult().getString("laston"));
					statement.executeUpdate();

				} while (rs.getResult().next());
				statement.close();
			}
			rs.closeQuery();
			rs = sqlite.select("SELECT * FROM " + Table.DATA.getName() + ";");
			if (rs.getResult().next())
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Importing data table...");
				PreparedStatement statement = mysql
						.prepare("INSERT INTO "
								+ Table.DATA.getName()
								+ " (id, bedenter, bedleave, bowshoot, chat, death, creative, survival, playerJoin, kick, quit, respawn, worldchange, tameocelot, tamewolf, paintingplace, eggThrow, sneak, sprint) VALUES('0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");
				do
				{
					statement.setInt(1, rs.getResult().getInt("id"));
					statement.setInt(2, rs.getResult().getInt("bedenter"));
					statement.setInt(3, rs.getResult().getInt("bedleave"));
					statement.setInt(4, rs.getResult().getInt("bowshoot"));
					statement.setInt(5, rs.getResult().getInt("chat"));
					statement.setInt(6, rs.getResult().getInt("death"));
					statement.setInt(7, rs.getResult().getInt("creative"));
					statement.setInt(8, rs.getResult().getInt("survival"));
					statement.setInt(9, rs.getResult().getInt("playerJoin"));
					statement.setInt(10, rs.getResult().getInt("kick"));
					statement.setInt(11, rs.getResult().getInt("quit"));
					statement.setInt(12, rs.getResult().getInt("respawn"));
					statement.setInt(13, rs.getResult().getInt("worldchange"));
					statement.setInt(14, rs.getResult().getInt("tameocelot"));
					statement.setInt(15, rs.getResult().getInt("tamewolf"));
					statement
							.setInt(16, rs.getResult().getInt("paintingplace"));
					statement.setInt(17, rs.getResult().getInt("eggThrow"));
					statement.setInt(18, rs.getResult().getInt("sneak"));
					statement.setInt(19, rs.getResult().getInt("sprint"));
					statement.executeUpdate();
				} while (rs.getResult().next());
				statement.close();
			}
			// Import items
			rs = sqlite.select("SELECT * FROM " + Table.ITEMS.getName() + ";");
			if (rs.getResult().next())
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Importing items table...");
				PreparedStatement statement = mysql
						.prepare("INSERT INTO "
								+ Table.ITEMS.getName()
								+ "(id, itemid, data, durability, place, destroy, craft, enchant, playerDrop, pickup) VALUES ('0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");
				do
				{
					statement.setInt(1, rs.getResult().getInt("id"));
					statement.setInt(2, rs.getResult().getInt("itemid"));
					statement.setString(3, rs.getResult().getString("data"));
					statement.setString(4,
							rs.getResult().getString("durability"));
					statement.setInt(5, rs.getResult().getInt("place"));
					statement.setInt(6, rs.getResult().getInt("destroy"));
					statement.setInt(7, rs.getResult().getInt("craft"));
					statement.setInt(8, rs.getResult().getInt("enchant"));
					statement.setInt(9, rs.getResult().getInt("playerDrop"));
					statement.setInt(10, rs.getResult().getInt("pickup"));
					statement.execute();
				} while (rs.getResult().next());
				statement.close();
			}
			rs.closeQuery();
			// TODO import command
			rs = sqlite.select("SELECT * FROM " + Table.MCMMO.getName() + ";");
			if (rs.getResult().next())
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Importing mcmmo table...");
				PreparedStatement statement = mysql
						.prepare("INSERT INTO "
								+ Table.MCMMO.getName()
								+ " (id, partyteleport , partyjoin , partyleave , partykick , partychange , acrobaticslevel , archerylevel , axeslevel , excavationlevel , fishinglevel , herbalismlevel , mininglevel , repairlevel , swordslevel , taminglevel , unarmedlevel , woodcuttinglevel , acrobaticsgain , archerygain , axesgain, excavationgain , fishinggain, herbalismgain, mininggain, repairgain, swordsgain, taminggain, unarmedgain, woodcuttinggain) VALUES('0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0');");
				do
				{
					statement.setInt(1, rs.getResult().getInt("id"));
					statement.setInt(2, rs.getResult().getInt("partyteleport"));
					statement.setString(3, rs.getResult()
							.getString("partyjoin"));
					statement.setString(4,
							rs.getResult().getString("partyleave"));
					statement.setInt(5, rs.getResult().getInt("partykick"));
					statement.setInt(6, rs.getResult().getInt("partychange"));
					statement.setInt(7, rs.getResult()
							.getInt("acrobaticslevel"));
					statement.setInt(8, rs.getResult().getInt("archerylevel"));
					statement.setInt(9, rs.getResult().getInt("axeslevel"));
					statement.setInt(10,
							rs.getResult().getInt("excavationlevel"));
					statement.setInt(11, rs.getResult().getInt("fishinglevel"));
					statement.setInt(12, rs.getResult()
							.getInt("herbalismlevel"));
					statement.setInt(13, rs.getResult().getInt("mininglevel"));
					statement.setInt(14, rs.getResult().getInt("repairlevel"));
					statement.setInt(15, rs.getResult().getInt("swordslevel"));
					statement.setInt(16, rs.getResult().getInt("taminglevel"));
					statement.setInt(17, rs.getResult().getInt("unarmedlevel"));
					statement.setInt(18,
							rs.getResult().getInt("woodcuttinglevel"));
					statement.setInt(19, rs.getResult()
							.getInt("acrobaticsgain"));
					statement.setInt(20, rs.getResult().getInt("archerygain"));
					statement.setInt(21, rs.getResult().getInt("axesgain"));
					statement.setInt(22, rs.getResult()
							.getInt("excavationgain"));
					statement.setInt(23, rs.getResult().getInt("fishinggain"));
					statement
							.setInt(24, rs.getResult().getInt("herbalismgain"));
					statement.setInt(25, rs.getResult().getInt("mininggain"));
					statement.setInt(26, rs.getResult().getInt("repairgain"));
					statement.setInt(27, rs.getResult().getInt("swordsgain"));
					statement.setInt(28, rs.getResult().getInt("taminggain"));
					statement.setInt(29, rs.getResult().getInt("unarmedgain"));
					statement.setInt(30,
							rs.getResult().getInt("woodcuttinggain"));
				} while (rs.getResult().next());
				statement.close();
			}
			rs.closeQuery();
			rs = sqlite.select("SELECT * FROM " + Table.PORTAL.getName() + ";");
			if (rs.getResult().next())
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Importing portal table...");
				PreparedStatement statement = mysql
						.prepare("INSERT INTO "
								+ Table.PORTAL.getName()
								+ " (id,pcreatenether, pcreateend, pcreatecustom, portalenter) VALUES ('0','0','0','0','0');");
				do
				{
					statement.setInt(1, rs.getResult().getInt("id"));
					statement.setInt(2, rs.getResult().getInt("pcreatenether"));
					statement.setInt(3, rs.getResult().getInt("pcreateend"));
					statement.setInt(4, rs.getResult().getInt("pcreatecustom"));
					statement.setInt(5, rs.getResult().getInt("portalenter"));
					statement.executeUpdate();
				} while (rs.getResult().next());
				statement.close();
			}
			rs.closeQuery();
			rs = sqlite.select("SELECT * FROM " + Table.BUCKET.getName() + ";");
			if (rs.getResult().next())
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Importing bucket table...");
				PreparedStatement statement = mysql
						.prepare("INSERT INTO "
								+ Table.BUCKET.getName()
								+ " (id,bemptylava, bemptywater, bfilllava, bfillwater) VALUES ('0','0','0','0','0');");
				do
				{
					statement.setInt(1, rs.getResult().getInt("id"));
					statement.setInt(2, rs.getResult().getInt("bemptylava"));
					statement.setInt(3, rs.getResult().getInt("bemptywater"));
					statement.setInt(4, rs.getResult().getInt("bfilllava"));
					statement.setInt(5, rs.getResult().getInt("bfillwater"));
					statement.executeUpdate();

				} while (rs.getResult().next());
				statement.close();
			}
			rs.closeQuery();
			// import mcmmo
			rs = sqlite.select("SELECT * FROM " + Table.MCMMO.getName() + ";");
			if (rs.getResult().next())
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Importing mcmmo table...");
				PreparedStatement statement = mysql
						.prepare("INSERT INTO "
								+ Table.MCMMO.getName()
								+ " (id, partyteleport, partyjoin, partyleave, partykick, partychange, acrobaticslevel, archerylevel, axeslevel, excavationlevel, fishinglevel, herbalismlevel, mininglevel, repairlevel, swordslevel, taminglevel, unarmedlevel, woodcuttinglevel, acrobaticsgain, archerygain, axesgain, excavationgain, fishinggain, herbalismgain, mininggain, repairgain, swordsgain, taminggain, unarmedgain, woodcuttinggain) VALUES ('0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");
				do
				{
					statement.setInt(1, rs.getResult().getInt("id"));
					statement.setInt(2, rs.getResult().getInt("partyteleport"));
					statement.setInt(3, rs.getResult().getInt("partyjoin"));
					statement.setInt(4, rs.getResult().getInt("partyleave"));
					statement.setInt(5, rs.getResult().getInt("partykick"));
					statement.setInt(6, rs.getResult().getInt("partychange"));
					statement.setInt(7, rs.getResult()
							.getInt("acrobaticslevel"));
					statement.setInt(8, rs.getResult().getInt("archerylevel"));
					statement.setInt(9, rs.getResult().getInt("axeslevel"));
					statement.setInt(10,
							rs.getResult().getInt("excavationlevel"));
					statement.setInt(11, rs.getResult().getInt("fishinglevel"));
					statement.setInt(12, rs.getResult()
							.getInt("herbalismlevel"));
					statement.setInt(13, rs.getResult().getInt("mininglevel"));
					statement.setInt(14, rs.getResult().getInt("repairlevel"));
					statement.setInt(15, rs.getResult().getInt("swordslevel"));
					statement.setInt(16, rs.getResult().getInt("taminglevel"));
					statement.setInt(17, rs.getResult().getInt("unarmedlevel"));
					statement.setInt(18,
							rs.getResult().getInt("woodcuttinglevel"));
					statement.setInt(19, rs.getResult()
							.getInt("acrobaticsgain"));
					statement.setInt(20, rs.getResult().getInt("archerygain"));
					statement.setInt(21, rs.getResult().getInt("axesgain"));
					statement.setInt(22, rs.getResult()
							.getInt("excavationgain"));
					statement.setInt(23, rs.getResult().getInt("fishinggain"));
					statement
							.setInt(24, rs.getResult().getInt("herbalismgain"));
					statement.setInt(25, rs.getResult().getInt("mininggain"));
					statement.setInt(26, rs.getResult().getInt("repairgain"));
					statement.setInt(27, rs.getResult().getInt("swordsgain"));
					statement.setInt(28, rs.getResult().getInt("taminggain"));
					statement.setInt(29, rs.getResult().getInt("unarmedgain"));
					statement.setInt(30,
							rs.getResult().getInt("woodcuttinggain"));
					statement.executeUpdate();

				} while (rs.getResult().next());
				statement.close();
			}
			rs.closeQuery();
			// import heroes
			rs = sqlite.select("SELECT * FROM " + Table.HEROES.getName() + ";");
			if (rs.getResult().next())
			{
				plugin.getLogger().info(
						Karmiconomy.TAG + " Importing mcmmo table...");
				PreparedStatement statement = mysql
						.prepare("INSERT INTO "
								+ Table.HEROES.getName()
								+ " (id, classchange, expchange, changelevel, combatenter, combatleave, partyjoin, partyleave, killattackmob, killattackplayer, killdefendmob, killdefendplayer, regainhealth, regainmana, skillcomplete, skilldamage, skilluse, weapondamage) VALUES ('0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");
				do
				{
					statement.setInt(1, rs.getResult().getInt("id"));
					statement.setInt(2, rs.getResult().getInt("classchange"));
					statement.setInt(3, rs.getResult().getInt("expchange"));
					statement.setInt(4, rs.getResult().getInt("changelevel"));
					statement.setInt(5, rs.getResult().getInt("combatenter"));
					statement.setInt(6, rs.getResult().getInt("combatleave"));
					statement.setInt(7, rs.getResult().getInt("partyjoin"));
					statement.setInt(8, rs.getResult().getInt("partyleave"));
					statement.setInt(9, rs.getResult().getInt("killattackmob"));
					statement.setInt(10,
							rs.getResult().getInt("killattackplayer"));
					statement
							.setInt(11, rs.getResult().getInt("killdefendmob"));
					statement.setInt(12,
							rs.getResult().getInt("killdefendplayer"));
					statement.setInt(13, rs.getResult().getInt("regainhealth"));
					statement.setInt(14, rs.getResult().getInt("regainmana"));
					statement
							.setInt(15, rs.getResult().getInt("skillcomplete"));
					statement.setInt(16, rs.getResult().getInt("skilldamage"));
					statement.setInt(17, rs.getResult().getInt("skilluse"));
					statement.setInt(18, rs.getResult().getInt("weapondamage"));
					statement.executeUpdate();
				} while (rs.getResult().next());
				statement.close();
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

	public boolean checkConnection()
	{
		boolean connected = false;
		if (useMySQL)
		{
			connected = mysql.checkConnection();
		}
		else
		{
			connected = sqlite.checkConnection();
		}
		return connected;
	}

	public void close()
	{
		if (useMySQL)
		{
			mysql.close();
		}
		else
		{
			sqlite.close();
		}
	}

	public Query select(String query)
	{
		if (useMySQL)
		{
			return mysql.select(query);
		}
		else
		{
			return sqlite.select(query);
		}
	}

	public void standardQuery(String query)
	{
		if (useMySQL)
		{
			mysql.standardQuery(query);
		}
		else
		{
			sqlite.standardQuery(query);
		}
	}

	private int getPlayerId(String name)
	{
		int id = -1;
		try
		{
			final Query query = select("SELECT * FROM "
					+ Table.MASTER.getName() + " WHERE playername='" + name
					+ "';");
			if (query.getResult().next())
			{
				id = query.getResult().getInt("id");
			}
			query.closeQuery();
		}
		catch (SQLException e)
		{
			plugin.getLogger().warning("SQL Exception on grabbing player ID");
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Attempts to add player. Does not allow for players that contain the
	 * special character of single quotes.
	 * 
	 * @param Name
	 *            of player to be added.
	 * @return True if attempted to be added. Else, false.
	 */
	public boolean addPlayer(String name)
	{
		if (!name.contains("'"))
		{
			int id = getPlayerId(name);
			if (id == -1)
			{
				// Generate last on
				final String laston = dateFormat.format(new Date());
				// Insert player to master database
				final String query = "INSERT INTO " + Table.MASTER.getName()
						+ " (playername,laston) VALUES('" + name + "','"
						+ laston + "');";
				standardQuery(query);
				// Grab generated id
				id = getPlayerId(name);
				if (id != -1)
				{
					// Add player data table
					standardQuery("INSERT INTO "
							+ Table.DATA.getName()
							+ " (id, bedenter, bedleave, bowshoot, chat, death, creative, survival, playerJoin, kick, quit, respawn, worldchange, tameocelot, tamewolf, paintingplace, eggThrow, sneak, sprint) VALUES('"
							+ id
							+ "','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0');");
					// Add player portal table
					standardQuery("INSERT INTO "
							+ Table.PORTAL.getName()
							+ " (id, pcreatenether, pcreateend, pcreatecustom, portalenter) VALUES('"
							+ id + "','0','0','0','0');");
					// Add player bucket table
					standardQuery("INSERT INTO "
							+ Table.BUCKET.getName()
							+ " (id, bemptylava, bemptywater, bfilllava, bfillwater) VALUES('"
							+ id + "','0','0','0','0');");
					// Add mcmmo
					standardQuery("INSERT INTO "
							+ Table.MCMMO.getName()
							+ " (id, partyteleport , partyjoin , partyleave , partykick , partychange , acrobaticslevel , archerylevel , axeslevel , excavationlevel , fishinglevel , herbalismlevel , mininglevel , repairlevel , swordslevel , taminglevel , unarmedlevel , woodcuttinglevel , acrobaticsgain , archerygain , axesgain, excavationgain , fishinggain, herbalismgain, mininggain, repairgain, swordsgain, taminggain, unarmedgain, woodcuttinggain) VALUES('"
							+ id
							+ "','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0');");
					// add Heroes
					standardQuery("INSERT INTO "
							+ Table.HEROES.getName()
							+ " (id, classchange, expchange, changelevel, combatenter, combatleave, partyjoin, partyleave, killattackmob, killattackplayer, killdefendmob, killdefendplayer, regainhealth, regainmana, skillcomplete, skilldamage, skilluse, weapondamage) VALUES ('"
							+ id
							+ "', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");
					return true;
				}
				else
				{
					plugin.getLogger().warning(
							"Player '" + name
									+ "' NOT successfully added to database!");
				}
			}
		}
		else
		{
			plugin.getLogger().warning(
					"Illegal character for player: " + name
							+ " ... Not added to database.");
		}
		return false;
	}

	/**
	 * Check if player data needs to be reset due to new day based off of server
	 * time and their last on field.
	 * 
	 * @param Name
	 *            of player.
	 * @return True if the current date and the players last on date is
	 *         different.
	 */
	public boolean checkDateReset(String name)
	{
		boolean valid = false;
		boolean same = false;
		int id = getPlayerId(name);
		if (id != -1)
		{
			valid = true;
		}
		else
		{
			// Reset was called, but somehow player did not exist. Add them to
			// the database
			addPlayer(name);
		}
		if (valid)
		{
			try
			{
				// Grab date
				String date = "";
				final Query query = select("SELECT * FROM "
						+ Table.MASTER.getName() + " WHERE id='" + id + "'");
				if (query.getResult().next())
				{
					date = query.getResult().getString("laston");
				}
				query.closeQuery();
				final String current = dateFormat.format(new Date());
				// Compare
				final String[] dArray = date.split("-");
				final String[] cArray = current.split("-");
				if (cArray[0].equals(dArray[0]) && cArray[1].equals(dArray[1])
						&& cArray[2].equals(dArray[2]))
				{
					same = true;
				}
				else
				{
					// Update date
					standardQuery("UPDATE " + Table.MASTER.getName()
							+ " SET laston='" + current + "' WHERE id='" + id
							+ "';");
				}
			}
			catch (SQLException e)
			{
				plugin.getLogger().warning(
						"SQL Exception on grabbing player date");
				e.printStackTrace();
			}
			catch (NullPointerException n)
			{
				plugin.getLogger().warning("NPE on grabbing player date");
				n.printStackTrace();
			}
			catch (ArrayIndexOutOfBoundsException a)
			{
				plugin.getLogger()
						.warning(
								"ArrayIndexOutOfBoundsExceptoin on grabbing player date");
				a.printStackTrace();
			}
		}
		return same;
	}

	/**
	 * Reset all values for a player
	 * 
	 * @param Name
	 *            of player
	 * @return True if tables were attempted to be dropped. Else, false.
	 */
	public boolean resetAllValues(String name)
	{
		boolean drop = false;
		int id = getPlayerId(name);
		if (id != -1)
		{
			drop = true;
		}
		else
		{
			// Reset was called, but somehow player did not exist. Add them to
			// the
			// database
			return addPlayer(name);
		}
		if (drop)
		{
			// Reset player values in data table
			standardQuery("UPDATE "
					+ Table.DATA.getName()
					+ " SET bedenter='0', bedleave='0', bowshoot='0', chat='0', death='0', creative='0', survival='0', playerJoin='0', kick='0', quit='0', respawn='0', worldchange='0', tameocelot='0', tamewolf='0', paintingplace='0', eggThrow='0', sneak='0', sprint='0' WHERE id='"
					+ id + "';");
			// Reset player values in portal table
			standardQuery("UPDATE "
					+ Table.PORTAL.getName()
					+ " SET pcreatenether='0', pcreateend='0', pcreatecustom='0', portalenter='0' WHERE id='"
					+ id + "';");
			// Reset player values in bucket table
			standardQuery("UPDATE "
					+ Table.BUCKET.getName()
					+ " SET bemptylava='0', bemptywater='0', bfilllava='0', bfillwater='0' WHERE id='"
					+ id + "';");
			// Reset mcmmo
			standardQuery("UPDATE "
					+ Table.MCMMO.getName()
					+ " SET partyteleport='0', partyjoin='0', partyleave='0', partykick='0', partychange='0', acrobaticslevel='0', archerylevel='0', axeslevel='0', excavationlevel='0', fishinglevel='0', herbalismlevel='0', mininglevel='0', repairlevel='0', swordslevel='0', taminglevel='0', unarmedlevel='0', woodcuttinglevel='0', acrobaticsgain='0', archerygain='0', axesgain='0', excavationgain='0', fishinggain='0', herbalismgain='0', mininggain='0', repairgain='0', swordsgain='0', taminggain='0', unarmedgain='0', woodcuttinggain='0' WHERE id='"
					+ id + "';");
			// Reset heroes
			standardQuery("UPDATE "
					+ Table.HEROES.getName()
					+ " SET classchange='0', expchange='0', changelevel='0', combatenter='0', combatleave='0', partyjoin='0', partyleave='0', killattackmob='0', killattackplayer='0', killdefendmob='0', killdefendplayer='0', regainhealth='0', regainmana='0', skillcomplete='0', skilldamage='0', skilluse='0', weapondamage='0' WHERE id='"
					+ id + "';");
			// Drop everything in items for player id
			standardQuery("DELETE FROM " + Table.ITEMS.getName()
					+ " WHERE id='" + id + "';");
			// Drop everything in commands for player id
			standardQuery("DELETE FROM " + Table.COMMAND.getName()
					+ " WHERE id='" + id + "';");
		}
		else
		{
			plugin.getLogger().warning("Could not reset values for: " + name);
		}
		return drop;
	}

	public void resetValue(Field field, String name, Item item, String command)
	{
		// TODO reset specified field
	}

	public void incrementData(Field field, String name, Item item,
			String command)
	{
		boolean inc = false;
		int id = getPlayerId(name);
		if (id != -1)
		{
			inc = true;
		}
		else
		{
			// Increment was called, but somehow player did not exist. Add them
			// to
			// database
			addPlayer(name);
			id = getPlayerId(name);
			if (id != -1)
			{
				inc = true;
			}
		}
		if (inc)
		{
			// Grab previous value
			int value = getData(field, name, item, command);
			// Increment count of specified field for given player name and
			// optional item / command
			value++;
			switch (field.getTable())
			{
				case DATA:
				{
					// Update
					standardQuery("UPDATE " + field.getTable().getName()
							+ " SET " + field.getColumnName() + "='" + value
							+ "' WHERE id='" + id + "';");
					break;
				}
				case ITEMS:
				{
					// Handle items
					if (item != null)
					{
						if (item.isTool())
						{
							standardQuery("UPDATE "
									+ field.getTable().getName() + " SET "
									+ field.getColumnName() + "='" + value
									+ "' WHERE id='" + id + "' AND itemid='"
									+ item.itemId() + "';");
						}
						else if (item.isPotion())
						{
							// See if entry exists
							standardQuery("UPDATE "
									+ field.getTable().getName() + " SET "
									+ field.getColumnName() + "='" + value
									+ "' WHERE id='" + id + "' AND itemid='"
									+ item.itemId() + "' AND data='"
									+ item.itemDurability()
									+ "' AND durability='"
									+ item.itemDurability() + "';");
						}
						else
						{
							// See if entry exists
							standardQuery("UPDATE "
									+ field.getTable().getName() + " SET "
									+ field.getColumnName() + "='" + value
									+ "' WHERE id='" + id + "' AND itemid='"
									+ item.itemId() + "' AND data='"
									+ item.itemData() + "' AND durability='"
									+ item.itemDurability() + "';");
						}
					}
					else
					{
						plugin.getLogger().warning(
								"Item cannot be null for field: " + field);
					}
					break;
				}
				case COMMAND:
				{
					// TODO check if insert or update
					break;
				}
				case PORTAL:
				{
					// Update
					standardQuery("UPDATE " + field.getTable().getName()
							+ " SET " + field.getColumnName() + "='" + value
							+ "' WHERE id='" + id + "';");
					break;
				}
				case BUCKET:
				{
					// Update
					standardQuery("UPDATE " + field.getTable().getName()
							+ " SET " + field.getColumnName() + "='" + value
							+ "' WHERE id='" + id + "';");
					break;
				}
				case MCMMO:
				{
					standardQuery("UPDATE " + field.getTable().getName()
							+ " SET " + field.getColumnName() + "='" + value
							+ "' WHERE id='" + id + "';");
					break;
				}
				case HEROES:
				{
					standardQuery("UPDATE " + field.getTable().getName()
							+ " SET " + field.getColumnName() + "='" + value
							+ "' WHERE id='" + id + "';");
					break;
				}
				default:
				{
					if (config.debugUnhandled)
					{
						plugin.getLogger().warning(
								"Unhandled table " + field.getTable().getName()
										+ " for field " + field);
					}
					break;
				}
			}

		}
		else
		{
			plugin.getLogger().warning(
					"Could not increment value '" + field + "' for: " + name);
		}

	}

	/**
	 * Get the data for a given field of a given player. If necessary, include
	 * itemm and command for appropriate fields.
	 * 
	 * @param Field
	 *            to retrieve
	 * @param Name
	 *            of player
	 * @param Item
	 *            to get data of
	 * @param Command
	 *            to get data of
	 * @return the integer value for the associated field and parameters
	 */
	public int getData(Field field, String name, Item item, String command)
	{
		boolean validId = false;
		int data = -1;
		int id = getPlayerId(name);
		if (id == -1)
		{
			plugin.getLogger().warning(
					"Player '" + name + "' not found in master database!");
			addPlayer(name);
			id = getPlayerId(name);
			if (id != -1)
			{
				validId = true;
			}
		}
		else
		{
			validId = true;
		}
		if (validId)
		{
			try
			{
				Query query = null;
				switch (field.getTable())
				{
					case DATA:
					{
						// Handle data specific stuff
						query = select("SELECT * FROM "
								+ field.getTable().getName() + " WHERE id='"
								+ id + "';");
						if (query.getResult().next())
						{
							data = query.getResult().getInt(
									field.getColumnName());
						}
						break;
					}
					case ITEMS:
					{
						boolean found = false;
						if (item != null)
						{
							// Handle items
							if (item.isTool())
							{
								// See if entry exists
								query = select("SELECT * FROM "
										+ field.getTable().getName()
										+ " WHERE id='" + id + "' AND itemid='"
										+ item.itemId() + "';");
								if (query.getResult().next())
								{
									found = true;
									data = query.getResult().getInt(
											field.getColumnName());
								}
								else
								{
									// No entry, create one
									data = 0;
								}
								query.closeQuery();
								if (!found)
								{
									// Add entry for tool item
									standardQuery("INSERT INTO "
											+ field.getTable().getName()
											+ " (id,itemid,data,durability,place,destroy,craft,enchant,playerDrop,pickup) VALUES('"
											+ id
											+ "','"
											+ item.itemId()
											+ "','0','0','0','0','0','0','0','0');");
								}
							}
							else if (item.isPotion())
							{
								// See if entry exists
								query = select("SELECT * FROM "
										+ field.getTable().getName()
										+ " WHERE id='" + id + "' AND itemid='"
										+ item.itemId() + "' AND data='"
										+ item.itemDurability()
										+ "' AND durability='"
										+ item.itemDurability() + "';");
								if (query.getResult().next())
								{
									found = true;
									data = query.getResult().getInt(
											field.getColumnName());
								}
								else
								{
									// No entry, create one
									data = 0;
								}
								query.closeQuery();
								// Only record durability
								if (!found)
								{
									// Add entry for tool item
									standardQuery("INSERT INTO "
											+ field.getTable().getName()
											+ " (id,itemid,data,durability,place,destroy,craft,enchant,playerDrop,pickup) VALUES('"
											+ id + "','" + item.itemId()
											+ "','" + item.itemDurability()
											+ "','" + item.itemDurability()
											+ "','0','0','0','0','0','0');");
								}
							}
							else
							{
								// See if entry exists
								query = select("SELECT * FROM "
										+ field.getTable().getName()
										+ " WHERE id='" + id + "' AND itemid='"
										+ item.itemId() + "' AND data='"
										+ item.itemData()
										+ "' AND durability='"
										+ item.itemDurability() + "';");
								if (query.getResult().next())
								{
									found = true;
									data = query.getResult().getInt(
											field.getColumnName());
								}
								else
								{
									// No entry, create one
									data = 0;
								}
								query.closeQuery();
								// Normal, so set everything
								if (!found)
								{
									// Add entry for tool item
									standardQuery("INSERT INTO "
											+ field.getTable().getName()
											+ " (id,itemid,data,durability,place,destroy,craft,enchant,playerDrop,pickup) VALUES('"
											+ id + "','" + item.itemId()
											+ "','" + item.itemData() + "','"
											+ item.itemDurability()
											+ "','0','0','0','0','0','0');");
								}
							}
						}
						else
						{
							plugin.getLogger().warning(
									"Item cannot be null for field: " + field);
						}
						break;
					}
					case COMMAND:
					{
						if (command != null)
						{
							// TODO handle command specific stuff
						}
						else
						{
							plugin.getLogger().warning(
									"Command cannot be null for field: "
											+ field);
						}
						break;
					}
					case PORTAL:
					{
						query = select("SELECT * FROM "
								+ field.getTable().getName() + " WHERE id='"
								+ id + "';");
						if (query.getResult().next())
						{
							data = query.getResult().getInt(
									field.getColumnName());
						}
						break;
					}
					case BUCKET:
					{
						query = select("SELECT * FROM "
								+ field.getTable().getName() + " WHERE id='"
								+ id + "';");
						if (query.getResult().next())
						{
							data = query.getResult().getInt(
									field.getColumnName());
						}
						break;
					}
					case MCMMO:
					{
						query = select("SELECT * FROM "
								+ field.getTable().getName() + " WHERE id='"
								+ id + "';");
						if (query.getResult().next())
						{
							data = query.getResult().getInt(
									field.getColumnName());
						}
						break;
					}
					case HEROES:
					{
						query = select("SELECT * FROM "
								+ field.getTable().getName() + " WHERE id='"
								+ id + "';");
						if (query.getResult().next())
						{
							data = query.getResult().getInt(
									field.getColumnName());
						}
						break;
					}
					default:
					{
						if (config.debugUnhandled)
						{
							plugin.getLogger().warning(
									"Unhandled table '"
											+ field.getTable().getName()
											+ "' for field '" + field + "'");
						}
						break;
					}
				}
				if (query != null)
				{
					query.closeQuery();
				}
			}
			catch (SQLException e)
			{
				plugin.getLogger().warning("SQL Exception on Import");
				e.printStackTrace();
			}
		}
		return data;
	}
}
