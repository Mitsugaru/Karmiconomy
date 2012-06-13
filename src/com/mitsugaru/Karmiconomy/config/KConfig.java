package com.mitsugaru.Karmiconomy.config;

import com.mitsugaru.Karmiconomy.Item;
import com.mitsugaru.Karmiconomy.database.Field;

public interface KConfig
{
	public abstract boolean isEnabled(Field field);

	public abstract boolean getDenyPay(Field field, Item item, String command);

	public abstract boolean getDenyLimit(Field field, Item item, String command);

	public abstract int getLimitValue(Field field, Item item, String command);

	public abstract double getPayValue(Field field, Item item, String command);

	public abstract boolean sendBroadcast(Field field);
	
	public abstract boolean checkWorld(Field field, String worldName);

	public abstract boolean checkWorld(Field field, Item item, String command, String worldName);
}
