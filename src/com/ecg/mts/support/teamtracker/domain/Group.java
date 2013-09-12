package com.ecg.mts.support.teamtracker.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ecg.mts.support.teamtracker.dataaccess.UserPersistenceWrapper;
import com.google.appengine.api.datastore.Key;

/**
 * This class provides general information about a group. <br>
 * <br>
 * by Markus Arndt <br>
 * <a href="arndt.markus@googlemail.com">arndt.markus@googlemail.com</a><br>
 * created 26.04.2012
 */
@Entity
public class Group
{
	private int		supportType;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key		key;
	private String	name;
	private String	description;
	private boolean	isActive;

	public Group(String name, String description, SupportType supportType)
	{
		this.name = name;
		this.description = description;
		this.supportType = supportType.getId();
		this.isActive = true;
	}

	public SupportType getSupportType()
	{
		return SupportType.getSupportTypeForId(supportType);
	}

	public void setSupportType(SupportType supportType)
	{
		this.supportType = supportType.getId();
	}

	public Key getKey()
	{
		return key;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public List<User> getMembers()
	{
		return UserPersistenceWrapper.getGroupMembers(key.getId());
	}

	public enum SupportType
	{
		ST_24_7(1, "24/7"),

		ST_8_20(2, "08:00 - 20:00");

		private int		id;
		private String	description;

		SupportType(int id, String description)
		{
			this.id = id;
			this.description = description;
		}

		public int getId()
		{
			return id;
		}

		public String getDescription()
		{
			return description;
		}

		public static SupportType getSupportTypeForId(int id)
		{
			for (SupportType type : SupportType.values())
			{
				if (type.getId() == id)
				{
					return type;
				}
			}

			return null;
		}
	}
}
