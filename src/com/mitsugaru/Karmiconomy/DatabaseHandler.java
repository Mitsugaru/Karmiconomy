package com.mitsugaru.Karmiconomy;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import lib.Mitsugaru.SQLibrary.MySQL;
import lib.Mitsugaru.SQLibrary.SQLite;
import lib.Mitsugaru.SQLibrary.Database.Query;

public class DatabaseHandler {
	private Karmiconomy plugin;
	private static Config config;
	private SQLite sqlite;
	private MySQL mysql;
	private boolean useMySQL;

	public DatabaseHandler(Karmiconomy plugin, Config conf) {
		this.plugin = plugin;
		config = conf;
		useMySQL = config.useMySQL;
		checkTables();
		if (config.importSQL) {
			if (useMySQL) {
				importSQL();
			}
			config.set("mysql.import", false);
		}
	}

	private void checkTables() {
		if (useMySQL) {
			// Connect to mysql database
			mysql = new MySQL(plugin.getLogger(), Karmiconomy.TAG, config.host,
					config.port, config.database, config.user, config.password);
			// Check if table exists
			if (!mysql.checkTable(config.tablePrefix + "master")) {
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created master table");
				// Create table
				mysql.createTable("CREATE TABLE "
						+ Table.MASTER.getName()
						+ " (id INT UNSIGNED NOT NULL AUTO_INCREMENT, playername varchar(32) NOT NULL, laston TEXT NOT NULL, UNIQUE (playername), PRIMARY KEY (id));");
			}
			if (!mysql.checkTable(config.tablePrefix + "items")) {
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created items table");
				mysql.createTable("CREATE TABLE "
						+ Table.ITEMS.getName()
						+ " (id INT UNSIGNED NOT NULL, itemid SMALLINT UNSIGNED NOT NULL, data TINYTEXT, durability TINYTEXT, place INT NOT NULL, destroy INT NOT NULL, craft INT NOT NULL, enchant INT NOT NULL, drop INT NOT NULL)");
			}
			if (!mysql.checkTable(config.tablePrefix + "data")) {
				mysql.createTable("CREATE TABLE "
						+ Table.DATA.getName()
						+ " (id INT UNSIGNED NOT NULL, bedenter INT NOT NULL, bedleave INT NOT NULL, bowshoot INT NOT NULL, chat INT NOT NULL, command INT NOT NULL, death INT NOT NULL, creative INT NOT NULL, survival INT NOT NULL, join INT NOT NULL, kick INT NOT NULL, quit INT NOT NULL, respawn INT NOT NULL, worldchange INT NOT NULL, portalcreate INT NOT NULL, portalenter INT NOT NULL, tameocelot INT NOT NULL, tamewolf INT NOT NULL, PRIMARY KEY (id));");
			}
		} else {
			// Connect to sql database
			sqlite = new SQLite(plugin.getLogger(), Karmiconomy.TAG, "data",
					plugin.getDataFolder().getAbsolutePath());
			// Check if table exists
			if (!sqlite.checkTable(config.tablePrefix + "master")) {
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created master table");
				// Create table
				sqlite.createTable("CREATE TABLE "
						+ config.tablePrefix
						+ "master (id INTEGER PRIMARY KEY, playername varchar(32) NOT NULL, laston TEXT NOT NULL, UNIQUE (playername));");
			}
			if (!sqlite.checkTable(config.tablePrefix + "items")) {
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created items table");
			}
		}
	}

	private void importSQL() {
		// Connect to sql database
		try {
			// Grab local SQLite database
			sqlite = new SQLite(plugin.getLogger(), Karmiconomy.TAG, "data",
					plugin.getDataFolder().getAbsolutePath());
			// Copy items
			Query rs = sqlite.select("SELECT * FROM " + config.tablePrefix
					+ "master;");
			if (rs.getResult().next()) {
				plugin.getLogger().info(
						Karmiconomy.TAG + " Importing master table...");
				do {
					// TODO import
					// PreparedStatement statement =
					// mysql.prepare("INSERT INTO " + config.tablePrefix + ");
				} while (rs.getResult().next());
			}
			rs.closeQuery();
			plugin.getLogger().info(
					Karmiconomy.TAG + " Done importing SQLite into MySQL");
		} catch (SQLException e) {
			plugin.getLogger().warning(
					Karmiconomy.TAG + " SQL Exception on Import");
			e.printStackTrace();
		}
	}

	public boolean checkConnection() {
		boolean connected = false;
		if (useMySQL) {
			connected = mysql.checkConnection();
		} else {
			connected = sqlite.checkConnection();
		}
		return connected;
	}

	public void close() {
		if (useMySQL) {
			mysql.close();
		} else {
			sqlite.close();
		}
	}

	public Query select(String query) {
		if (useMySQL) {
			return mysql.select(query);
		} else {
			return sqlite.select(query);
		}
	}

	public void standardQuery(String query) {
		if (useMySQL) {
			mysql.standardQuery(query);
		} else {
			sqlite.standardQuery(query);
		}
	}

	private int getPlayerId(String name) {
		int id = -1;
		try {
			final Query query = select("SELECT * FROM " + Table.MASTER.getName()
					+ " WHERE playername='" + name + "';");
			if (query.getResult().next()) {
				do
				{
					id = query.getResult().getInt("id");
				}while(query.getResult().next());
			}
			query.closeQuery();
		} catch (SQLException e) {
			plugin.getLogger().warning("SQL Exception on Import");
			e.printStackTrace();
		}
		return id;
	}

	// TODO make method to get limit field for specified player

	public enum Field {
		// TODO eggs, paintings, vehicle
		BOW_SHOOT(Table.DATA, "bowshoot"), BED_ENTER(Table.DATA, "bedenter"), BED_LEAVE(
				Table.DATA, "bedleave"), BLOCK_PLACE(Table.ITEMS, "place"), BLOCK_DESTROY(
				Table.ITEMS, "destroy"), ITEM_CRAFT(Table.ITEMS, "craft"), ITEM_ENCHANT(
				Table.ITEMS, "enchant"), ITEM_DROP(Table.ITEMS, "drop"), CHAT(
				Table.DATA, "chat"), COMMAND(Table.DATA, "command"), DEATH(
				Table.DATA, "death"), CREATIVE(Table.DATA, "creative"), SURVIVAL(
				Table.DATA, "survival"), JOIN(Table.DATA, "join"), KICK(
				Table.DATA, "kick"), QUIT(Table.DATA, "quit"), RESPAWN(
				Table.DATA, "respawn"), PORTAL_CREATE(Table.DATA,
				"portalcreate"), PORTAL_ENTER(Table.DATA, "portalenter"), TAME_OCELOT(
				Table.DATA, "tameocelot"), TAME_WOLF(Table.DATA, "tamewolf"), WORLD_CHANGE(
				Table.DATA, "worldchange");
		private final Table table;
		private final String columnname;

		private Field(Table table, String columnname) {
			this.table = table;
			this.columnname = columnname;
		}

		public Table getTable() {
			return table;
		}

		public String getColumnName() {
			return columnname;
		}
	}

	private enum Table {
		MASTER(config.tablePrefix + "master"), ITEMS(config.tablePrefix
				+ "items"), DATA(config.tablePrefix + ITEMS);
		private final String table;

		private Table(String table) {
			this.table = table;
		}

		public String getName() {
			return table;
		}

		@Override
		public String toString() {
			return table;
		}
	}
}
