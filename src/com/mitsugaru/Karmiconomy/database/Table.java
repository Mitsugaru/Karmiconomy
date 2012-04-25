package com.mitsugaru.Karmiconomy.database;

import com.mitsugaru.Karmiconomy.config.Config;

public enum Table
{
	MASTER(Config.tablePrefix + "master"), ITEMS(Config.tablePrefix
			+ "items"), DATA(Config.tablePrefix + "data"), COMMAND(
			Config.tablePrefix + "command"), PORTAL(Config.tablePrefix
			+ "portal"), BUCKET(Config.tablePrefix + "bucket"), MCMMO(
			Config.tablePrefix + "mcmmo"), HEROES(Config.tablePrefix + "heroes");
	private final String table;

	private Table(String table)
	{
		this.table = table;
	}

	public String getName()
	{
		return table;
	}

	@Override
	public String toString()
	{
		return table;
	}
}