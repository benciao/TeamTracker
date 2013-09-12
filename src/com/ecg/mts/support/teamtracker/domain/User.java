package com.ecg.mts.support.teamtracker.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.ecg.mts.support.teamtracker.dataaccess.GroupPersistenceWrapper;

/**
 * This class provides general information about a user of this application. <br>
 * <br>
 * by Markus Arndt <br>
 * <a href="arndt.markus@googlemail.com">arndt.markus@googlemail.com</a><br>
 * created 06.04.2012
 */
@Entity
public class User
{
	@Id
	private String	mobilePhone;
	private String	firstName;
	private String	lastName;
	private String	email;
	private int		supportStatus;
	private int		supportLevel;
	private String	secondMobilePhone;
	private String	landlinePhone;
	private boolean	isActive;
	private Long	groupId;
	private Date	statusChange;
	private Date	levelChange;

	public User(String firstName, String lastName, String email,
			String mobilePhone, SupportStatus supportStatus,
			SupportLevel supportLevel, String secondMobilePhone,
			String landlinePhone, Long groupId)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobilePhone = mobilePhone;
		this.secondMobilePhone = secondMobilePhone;
		this.landlinePhone = landlinePhone;
		this.supportStatus = supportStatus.getId();
		this.supportLevel = supportLevel.getId();
		this.isActive = true;
		this.groupId = groupId;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public SupportStatus getSupportStatus()
	{
		return SupportStatus.getSupportStatusForId(supportStatus);
	}

	public void setSupportStatus(SupportStatus status)
	{
		this.supportStatus = status.getId();
	}

	public SupportLevel getSupportLevel()
	{
		return SupportLevel.getSupportLevelForId(supportLevel);
	}

	public void setSupportLevel(SupportLevel supportLevel)
	{
		this.supportLevel = supportLevel.getId();
	}

	public String getSecondMobilePhone()
	{
		return secondMobilePhone;
	}

	public void setSecondMobilePhone(String secondMobilePhone)
	{
		this.secondMobilePhone = secondMobilePhone;
	}

	public String getLandlinePhone()
	{
		return landlinePhone;
	}

	public void setLandlinePhone(String landlinePhone)
	{
		this.landlinePhone = landlinePhone;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public Group getGroup()
	{
		return GroupPersistenceWrapper.getGroupForId(groupId);
	}

	public void setGroupId(Long groupId)
	{
		this.groupId = groupId;
	}

	public Date getStatusChange()
	{
		return statusChange;
	}

	public void setStatusChange(Date statusChange)
	{
		this.statusChange = statusChange;
	}

	public Date getLevelChange()
	{
		return levelChange;
	}

	public void setLevelChange(Date levelChange)
	{
		this.levelChange = levelChange;
	}

	public enum SupportStatus
	{

		AVAILABLE(1, "voll verfügbar"),
		LIMITED_AVAILABLE(2, "eingeschränkt verfügbar"),
		NOT_AVAILABLE(3, "nicht verfügbar");

		private int		id;
		private String	description;

		SupportStatus(int id, String description)
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

		public static SupportStatus getSupportStatusForId(int id)
		{
			for (SupportStatus status : SupportStatus.values())
			{
				if (status.getId() == id)
				{
					return status;
				}
			}

			return null;
		}
	}

	public enum SupportLevel
	{

		FIRST_LEVEL(1, "1. Bereitschaft"),
		SECOND_LEVEL(2, "2. Bereitschaft"),
		NONE(3, "keine Bereitschaft");

		private int		id;
		private String	description;

		SupportLevel(int id, String description)
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

		public static SupportLevel getSupportLevelForId(int id)
		{
			for (SupportLevel level : SupportLevel.values())
			{
				if (level.getId() == id)
				{
					return level;
				}
			}

			return null;
		}
	}
}
