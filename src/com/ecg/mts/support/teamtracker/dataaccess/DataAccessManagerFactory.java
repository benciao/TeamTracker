package com.ecg.mts.support.teamtracker.dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This class represents a factory that returns an implementation of a
 * persistence manager. <br>
 * <br>
 * by Markus Arndt <br>
 * <a href="arndt.markus@googlemail.com">arndt.markus@googlemail.com</a><br>
 * created 06.04.2012
 */
public final class DataAccessManagerFactory
{
	private static final EntityManagerFactory	emfInstance	= Persistence
																	.createEntityManagerFactory("transactions-optional");

	private DataAccessManagerFactory()
	{
	}

	public static EntityManagerFactory get()
	{
		return emfInstance;
	}
}
