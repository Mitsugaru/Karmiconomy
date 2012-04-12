package com.mitsugaru.Karmiconomy;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.inventory.ItemStack;

import lib.Mitsugaru.SQLibrary.MySQL;
import lib.Mitsugaru.SQLibrary.SQLite;
import lib.Mitsugaru.SQLibrary.Database.Query;

public class DatabaseHandler {
	private Karmiconomy plugin;
	private static Config config;
	private SQLite sqlite;
	private MySQL mysql;
	private boolean useMySQL;
	private final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

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
			if (!mysql.checkTable(Table.MASTER.getName())) {
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created master table");
				// Create table
				mysql.createTable("CREATE TABLE "
						+ Table.MASTER.getName()
						+ " (id INT UNSIGNED NOT NULL AUTO_INCREMENT, playername varchar(32) NOT NULL, laston TEXT NOT NULL, UNIQUE (playername), PRIMARY KEY (id));");
			}
			if (!mysql.checkTable(Table.ITEMS.getName())) {
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created items table");
				mysql.createTable("CREATE TABLE "
						+ Table.ITEMS.getName()
						+ " (row INT UNSIGNED NOT NULL AUTO_INCREMENT, id INT UNSIGNED NOT NULL, itemid SMALLINT UNSIGNED NOT NULL, data TINYTEXT, durability TINYTEXT, place INT NOT NULL, destroy INT NOT NULL, craft INT NOT NULL, enchant INT NOT NULL, drop INT NOT NULL, PRIMARY KEY(row))");
			}
			if (!mysql.checkTable(Table.COMMAND.getName())) {
				plugin.getLogger().info(
						Karmiconomy.TAG + " Created command table");
				mysql.createTable("CREATE TABLE "
						+ Table.COMMAND.getName()
						+ " (row INT UNSIGNED NOT NULL AUTO_INCREMENT, id INT UNSIGNED NOT NULL, command TEXT NOT NULL, count INT NOT NULL, PRIMARY KEY(row));");
			}
			if (!mysql.checkTable(Table.DATA.getName())) {
				plugin.getLogger()
						.info(Karmiconomy.TAG + " Created data table");
				mysql.createTable("CREATE TABLE "
						+ Table.DATA.getName()
						+ " (id INT UNSIGNED NOT NULL, bedenter INT NOT NULL, bedleave INT NOT NULL, bowshoot INT NOT NULL, chat INT NOT NULL, death INT NOT NULL, creative INT NOT NULL, survival INT NOT NULL, join INT NOT NULL, kick INT NOT NULL, quit INT NOT NULL, respawn INT NOT NULL, worldchange INT NOT NULL, portalcreate INT NOT NULL, portalenter INT NOT NULL, tameocelot INT NOT NULL, tamewolf INT NOT NULL, PRIMARY KEY (id));");
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
			// TODO import other tables as well
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
			final Query query = select("SELECT * FROM "
					+ Table.MASTER.getName() + " WHERE playername='" + name
					+ "';");
			if (query.getResult().next()) {
				do {
					id = query.getResult().getInt("id");
				} while (query.getResult().next());
			}
			query.closeQuery();
		} catch (SQLException e) {
			plugin.getLogger().warning("SQL Exception on Import");
			e.printStackTrace();
		}
		return id;
	}

	public void addPlayer(String name) {
		if (!name.contains("'")) {
			final int id = getPlayerId(name);
			if (id == -1) {
				// Generate last on
				String laston = dateFormat.format(new Date());
				// Insert player to master database
				standardQuery("INSERT INTO " + Table.MASTER.getName()
						+ " (playername,laston) VALUES('" + name + "','"
						+ laston + "');");
				// Grab generated id
				final int generatedId = getPlayerId(name);
				if (generatedId == -1) {
					// Add player data table
					standardQuery("INSERT INTO "
							+ Table.DATA.getName()
							+ " (id, bedenter, bedleave, bowshoot, chat, death, creative, survival, join, kick, quit, respawn, worldchange, portalcreate, portalenter, tameocelot, tamewolf) VALUES('"
							+ generatedId
							+ "','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0');");
				} else {
					plugin.getLogger().warning(
							"Player '" + name
									+ "' NOT successfully added to database!");
				}
			}
		} else {
			plugin.getLogger().warning(
					"Illegal character for player: " + name
							+ " ... Not added to database.");
		}
	}

	public void resetAllValues(String name) {
		final int id = getPlayerId(name);
		if (id != -1) {
			// Reset player values in data table
			standardQuery("UPDATE "
					+ Table.DATA.getName()
					+ " SET bedenter='0', bedleave='0', bowshoot='0', chat='0', death='0', creative='0', survival='0', join='0', kick='0', quit='0', respawn='0', worldchange='0', portalcreate='0', portalenter='0', tameocelot='0', tamewolf='0' WHERE id='"
					+ id + "');");
			// TODO drop everything in items for player id
		} else {
			// Reset was called, but somehow player did not exist. Add them to
			// database
			addPlayer(name);
		}
	}

	public void resetValue(Field field, String name, ItemStack item,
			String command) {
		// TODO reset specfied field
	}

	public void incrementData(Field field, String name, ItemStack item,
			String command) {
		// TODO increment count of specified field for given player name and
		// optional item
	}

	public int getData(Field field, String name, ItemStack item, String command) {
		int data = -1;
		final int id = getPlayerId(name);
		if (id == -1) {
			plugin.getLogger().warning(
					"Player '" + name + "' not found in master database!");
			// TODO make entries?
		}
		try {
			Query query = null;
			if (field.getTable() == Table.DATA) {
				// Handle data specific stuff
				query = select("SELECT * FROM " + field.getTable().getName()
						+ " WHERE id='" + id + "';");
				if (query.getResult().next()) {
					data = query.getResult().getInt(field.getColumnName());
					if (query.getResult().wasNull()) {
						data = -1;
						plugin.getLogger().warning(
								"Null field '" + field + "' for player '"
										+ name + "'");
					}
				}
			} else if (field.getTable() == Table.ITEMS) {
				if (item != null) {
					// TODO handle items specific stuff
					// TODO check against potions / whatever
				} else {
					plugin.getLogger().warning(
							"ItemStack cannot be null for field: " + field);
				}
			} else if (field.getTable() == Table.COMMAND) {
				if (command != null) {
					// TODO handle command specific stuff
				} else {
					plugin.getLogger().warning(
							"Command cannot be null for field: " + field);
				}
			} else {
				plugin.getLogger().warning(
						"Unhandled table '" + field.getTable().getName()
								+ "' for field '" + field + "'");
			}
			if (query != null) {
				query.closeQuery();
			}
		} catch (SQLException e) {
			plugin.getLogger().warning("SQL Exception on Import");
			e.printStackTrace();
		}
		return data;
	}

	// TODO make method to get limit field for specified player

	public enum Field {
		// TODO eggs, paintings, vehicle
		BOW_SHOOT(Table.DATA, "bowshoot"), BED_ENTER(Table.DATA, "bedenter"), BED_LEAVE(
				Table.DATA, "bedleave"), BLOCK_PLACE(Table.ITEMS, "place"), BLOCK_DESTROY(
				Table.ITEMS, "destroy"), ITEM_CRAFT(Table.ITEMS, "craft"), ITEM_ENCHANT(
				Table.ITEMS, "enchant"), ITEM_DROP(Table.ITEMS, "drop"), CHAT(
				Table.DATA, "chat"), COMMAND(Table.COMMAND, "command"), DEATH(
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
				+ "items"), DATA(config.tablePrefix + "data"), COMMAND(
				config.tablePrefix + "command");
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
