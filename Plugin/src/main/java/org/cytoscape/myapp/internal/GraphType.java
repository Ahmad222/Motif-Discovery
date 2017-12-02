package org.cytoscape.myapp.internal;



public enum GraphType
{
	DIRECTED,
	UNDIRECTED;

	public int getValue()
	{
		return this.ordinal();
	}

	public static GraphType forValue(int value)
	{
		return values()[value];
	}
}