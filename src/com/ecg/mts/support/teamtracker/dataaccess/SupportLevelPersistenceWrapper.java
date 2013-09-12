package com.ecg.mts.support.teamtracker.dataaccess;

import java.util.logging.Logger;

import javax.persistence.EntityManager;

import com.ecg.mts.support.teamtracker.domain.FirstLevelSupport;
import com.ecg.mts.support.teamtracker.domain.NoneLevelSupport;
import com.ecg.mts.support.teamtracker.domain.SecondLevelSupport;

public class SupportLevelPersistenceWrapper
{
	private static final Logger	log	= Logger.getLogger(SupportLevelPersistenceWrapper.class
											.getName());

	/**
	 * This methode inserts a {@link FirstLevelSupport}-Object into the
	 * database.
	 * 
	 * @param supportObject
	 *            FirstLevelSupport
	 * @return true, if insert was successful. Otherwise false.
	 */
	public static boolean createFirstLevelSupportObject(
			FirstLevelSupport supportObject)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();
		em.getTransaction().begin();

		try
		{
			em.persist(supportObject);
			em.getTransaction().commit();
			return true;
		}
		catch (Exception ex)
		{
			log.severe(ex.getMessage());
		}
		finally
		{
			if (em.getTransaction().isActive())
			{
				em.getTransaction().rollback();
			}
			em.close();
		}

		return false;
	}

	/**
	 * This methode inserts a {@link SecondLevelSupport}-Object into the
	 * database.
	 * 
	 * @param supportObject
	 *            SecondLevelSupport
	 * @return true, if insert was successful. Otherwise false.
	 */
	public static boolean createSecondLevelSupportObject(
			SecondLevelSupport supportObject)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();
		em.getTransaction().begin();

		try
		{
			em.persist(supportObject);
			em.getTransaction().commit();
			return true;
		}
		catch (Exception ex)
		{
			log.severe(ex.getMessage());
		}
		finally
		{
			if (em.getTransaction().isActive())
			{
				em.getTransaction().rollback();
			}
			em.close();
		}

		return false;
	}

	/**
	 * This methode inserts a {@link NoneLevelSupport}-Object into the database.
	 * 
	 * @param supportObject
	 *            NoneLevelSupport
	 * @return true, if insert was successful. Otherwise false.
	 */
	public static boolean createNoneLevelSupportObject(
			NoneLevelSupport supportObject)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();
		em.getTransaction().begin();

		try
		{
			em.persist(supportObject);
			em.getTransaction().commit();
			return true;
		}
		catch (Exception ex)
		{
			log.severe(ex.getMessage());
		}
		finally
		{
			if (em.getTransaction().isActive())
			{
				em.getTransaction().rollback();
			}
			em.close();
		}

		return false;
	}
}
