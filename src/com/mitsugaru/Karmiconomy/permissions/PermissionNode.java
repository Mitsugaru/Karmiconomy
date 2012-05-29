package com.mitsugaru.Karmiconomy.permissions;

public enum PermissionNode
{
	ADMIN(".admin");
	private static final String prefix = "Karmiconomy";
	private String node;

	private PermissionNode(String node)
	{
		this.node = prefix + node;
	}
	
	public String getNode()
	{
		return node;
	}

}
