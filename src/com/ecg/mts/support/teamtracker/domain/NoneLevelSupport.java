package com.ecg.mts.support.teamtracker.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class NoneLevelSupport implements ISupportLevel
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key		key;
	private String	userId;
	private Long	begin;
	private Long	end;
	private Long	period;

	public NoneLevelSupport(String userId, Long begin, Long end, Long period)
	{
		this.userId = userId;
		this.begin = begin;
		this.end = end;
		this.period = period;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Long getBegin()
	{
		return begin;
	}

	public void setBegin(Long begin)
	{
		this.begin = begin;
	}

	public Long getEnd()
	{
		return end;
	}

	public void setEnd(Long end)
	{
		this.end = end;
	}

	public Long getPeriod()
	{
		return period;
	}

	public void setPeriod(Long period)
	{
		this.period = period;
	}

	public Key getKey()
	{
		return key;
	}
}
