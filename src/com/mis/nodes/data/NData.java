package com.mis.nodes.data;

import java.io.Serializable;
import java.util.UUID;

public abstract class NData implements Serializable
{
	private static final long serialVersionUID = -7737385329626495183L;

	public UUID id;

	@Override
	public int hashCode()
	{
		return id.hashCode();
	}

	@Override
	public boolean equals( Object obj )
	{
		return id.hashCode() == obj.hashCode();
	}

	@Override
	public String toString()
	{
		return id.toString();
	}
}
