package com.ecg.mts.support.teamtracker.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

/**
 * This class represents one knownledge entry for one certain user of this
 * application. <br>
 * <br>
 * by Markus Arndt <br>
 * <a href="arndt.markus@googlemail.com">arndt.markus@googlemail.com</a><br>
 * created 14.05.2012
 */
@Entity
public class Knownledge
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key		key;
	private String	header;
	private Text	content;
	private String	userId;

	public Knownledge(String header, Text content, String userId)
	{
		this.userId = userId;
		this.header = header;
		this.content = content;
	}

	public String getHeader()
	{
		return header;
	}

	public void setHeader(String header)
	{
		this.header = header;
	}

	public Text getContent()
	{
		return content;
	}

	public void setContent(Text content)
	{
		this.content = content;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Key getKey()
	{
		return key;
	}
}
