package com.ecg.mts.support.teamtracker.dataaccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ecg.mts.support.teamtracker.domain.AvailableStatus;
import com.ecg.mts.support.teamtracker.domain.ISupportLevel;
import com.ecg.mts.support.teamtracker.domain.LimitedAvailableStatus;
import com.ecg.mts.support.teamtracker.domain.NotAvailableStatus;
import com.ecg.mts.support.teamtracker.domain.User;
import com.ecg.mts.support.teamtracker.domain.User.SupportLevel;
import com.ecg.mts.support.teamtracker.domain.User.SupportStatus;
import com.ecg.mts.support.teamtracker.util.Util;

/**
 * This class acts as a wrapper to the data access layer. It provides crud
 * operations for the domain objects of type {@link User} of this application. <br>
 * <br>
 * by Markus Arndt <br>
 * <a href="arndt.markus@googlemail.com">arndt.markus@googlemail.com</a><br>
 * created 06.04.2012
 */
public class UserPersistenceWrapper
{
	private static final Logger	log	= Logger.getLogger(UserPersistenceWrapper.class
											.getName());

	/**
	 * This methode inserts a {@link User}-Object into the database.
	 * 
	 * @param user
	 *            User
	 * @return true, if insert was successful. Otherwise false.
	 */
	@SuppressWarnings("unchecked")
	public static boolean createUser(User user)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();
		em.getTransaction().begin();

		try
		{
			boolean exists = checkUserExistence(user, em);
			if (!exists)
			{
				Date creationDate = new Date();
				user.setStatusChange(creationDate);
				user.setLevelChange(creationDate);

				if (user.getSupportLevel() == SupportLevel.FIRST_LEVEL)
				{
					Query query = em
							.createQuery("SELECT user FROM "
									+ User.class.getName()
									+ " user WHERE user.supportLevel = :param1 AND user.groupId = :param2");

					query.setParameter("param1",
							SupportLevel.FIRST_LEVEL.getId());
					query.setParameter("param2", user.getGroup().getKey()
							.getId());

					List<User> queryResult = query.getResultList();

					for (User firstLevelUser : queryResult)
					{
						firstLevelUser.setSupportLevel(SupportLevel.NONE);
					}
				}
				else if (user.getSupportLevel() == SupportLevel.SECOND_LEVEL)
				{
					Query query = em
							.createQuery("SELECT user FROM "
									+ User.class.getName()
									+ " user WHERE user.supportLevel = :param1 AND user.groupId = :param2");

					query.setParameter("param1",
							SupportLevel.SECOND_LEVEL.getId());
					query.setParameter("param2", user.getGroup().getKey()
							.getId());

					List<User> queryResult = query.getResultList();

					for (User secondLevelUser : queryResult)
					{
						secondLevelUser.setSupportLevel(SupportLevel.NONE);
					}
				}

				em.persist(user);
				em.getTransaction().commit();
				return true;
			}
			else
			{
				log.severe("User with folowing mobilePhoneNumber already exists in the database: "
						+ user.getMobilePhone());
				return false;
			}
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
	 * This methode deletes the given {@link User}-Object from the database.
	 * 
	 * @param ids
	 *            Ids of user to delete.
	 * @return true, if delete was successful. Otherwise false.
	 */
	public static boolean deleteUser(List<String> ids)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();

		try
		{
			for (String id : ids)
			{
				em.getTransaction().begin();
				User userToDelete = em.find(User.class, id);
				em.remove(userToDelete);
				em.getTransaction().commit();
			}

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

	@SuppressWarnings("rawtypes")
	private static boolean checkUserExistence(User user, EntityManager em)
	{
		Query query = em
				.createQuery("SELECT user FROM User user WHERE user.mobilePhone = :param");

		List queryResult = query.setParameter("param", user.getMobilePhone())
				.getResultList();

		return !queryResult.isEmpty();
	}

	/**
	 * This methode returns a list of {@link User}-Objects, where the property
	 * value of property <code>isActive</code> meets the value of the passed
	 * parameter.
	 * 
	 * @param isActive
	 *            User status
	 * @return List of {@link User}-Objects if found. Otherwise empty list.
	 */
	@SuppressWarnings("unchecked")
	public static List<User> getUserList(boolean isActive)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();
		List<User> result = new ArrayList<User>();

		try
		{
			Query query = em.createQuery("SELECT user FROM "
					+ User.class.getName() + " user");
			List<User> queryResult = (List<User>) query.getResultList();

			for (User user : queryResult)
			{
				if (user.isActive() == isActive)
				{
					result.add(user);
				}
			}
		}
		catch (Exception ex)
		{
			log.severe(ex.getMessage());
		}
		finally
		{
			em.close();
		}

		return result;
	}

	/**
	 * This methode loads a {@link User}-Object that is assoziated with the
	 * given id.
	 * 
	 * @param id
	 *            Id of user to load.
	 * @return User, if it exist. Otherwise null.
	 */
	public static User getUserForId(String id)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();

		try
		{
			return em.find(User.class, id);
		}
		catch (Exception ex)
		{
			log.severe(ex.getMessage());
		}
		finally
		{
			em.close();
		}

		return null;
	}

	/**
	 * This methode returns a list of {@link User}-Objects, where the property
	 * value of property <code>groupId</code> meets the value of the passed
	 * parameter.
	 * 
	 * @param groupId
	 *            Group id.
	 * @return List of {@link User}-Objects if found. Otherwise empty list.
	 */
	@SuppressWarnings("unchecked")
	public static List<User> getGroupMembers(Long groupId)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();
		List<User> result = new ArrayList<User>();

		try
		{
			Query query = em.createQuery("SELECT user FROM "
					+ User.class.getName()
					+ " user WHERE user.groupId = :param");

			List<User> queryResult = query.setParameter("param", groupId)
					.getResultList();

			for (User user : queryResult)
			{
				if (user.isActive())
				{
					result.add(user);
				}
			}
		}
		catch (Exception ex)
		{
			log.severe(ex.getMessage());
		}
		finally
		{
			em.close();
		}

		return result;
	}

	/**
	 * This methode updates a {@link User}-Object that is assoziated with the
	 * given id.
	 * 
	 * @param id
	 *            Id of user to load.
	 * @param firstName
	 *            New first name of user.
	 * @param lastName
	 *            New last name of user.
	 * @param email
	 *            New email of user.
	 * @param seconMobilePhone
	 *            New second mobile phone of user.
	 * @param landlinePhone
	 *            New landline phone of user.
	 * @param groupId
	 *            New groupId of user.
	 */
	@SuppressWarnings("unchecked")
	public static void updateUser(String id, String firstName, String lastName,
			String email, String secondMobilePhone, String landlinePhone,
			Long groupId, SupportLevel supportLevel)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();
		em.getTransaction().begin();

		try
		{
			User user = em.find(User.class, id);
			if (user != null)
			{
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
				user.setSecondMobilePhone(secondMobilePhone);
				user.setLandlinePhone(landlinePhone);
				user.setGroupId(groupId);
				user.setSupportLevel(supportLevel);

				if (supportLevel == SupportLevel.FIRST_LEVEL)
				{
					Query query = em
							.createQuery("SELECT user FROM "
									+ User.class.getName()
									+ " user WHERE user.supportLevel = :param1 AND user.groupId = :param2");

					query.setParameter("param1",
							SupportLevel.FIRST_LEVEL.getId());
					query.setParameter("param2", user.getGroup().getKey()
							.getId());

					List<User> queryResult = query.getResultList();

					for (User firstLevelUser : queryResult)
					{
						firstLevelUser.setSupportLevel(SupportLevel.NONE);
					}
				}
				else if (supportLevel == SupportLevel.SECOND_LEVEL)
				{
					Query query = em
							.createQuery("SELECT user FROM "
									+ User.class.getName()
									+ " user WHERE user.supportLevel = :param1 AND user.groupId = :param2");

					query.setParameter("param1",
							SupportLevel.SECOND_LEVEL.getId());
					query.setParameter("param2", user.getGroup().getKey()
							.getId());

					List<User> queryResult = query.getResultList();

					for (User secondLevelUser : queryResult)
					{
						secondLevelUser.setSupportLevel(SupportLevel.NONE);
					}
				}

				ISupportLevel supportType = Util.updateSupportLevel(user,
						supportLevel);
				em.persist(supportType);
				em.getTransaction().commit();
			}
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
	}

	public static boolean updateUserStatus(String userId,
			SupportStatus supportStatus)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();
		em.getTransaction().begin();

		try
		{
			User user = em.find(User.class, userId);
			if (user != null)
			{
				Object statusObject = Util.updateStatus(user, supportStatus);
				user.setSupportStatus(supportStatus);
				em.getTransaction().commit();

				if (statusObject instanceof AvailableStatus)
				{
					AvailableStatus avStatus = (AvailableStatus) statusObject;
					em.persist(avStatus);
				}
				else if (statusObject instanceof NotAvailableStatus)
				{
					NotAvailableStatus navStatus = (NotAvailableStatus) statusObject;
					em.persist(navStatus);
				}
				else if (statusObject instanceof LimitedAvailableStatus)
				{
					LimitedAvailableStatus lavStatus = (LimitedAvailableStatus) statusObject;
					em.persist(lavStatus);
				}

				return true;
			}
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

	public static boolean giveSupportLevelToOtherUser(String userFromId,
			String userToId)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();
		em.getTransaction().begin();

		try
		{
			User userFrom = em.find(User.class, userFromId);
			User userTo = em.find(User.class, userToId);
			if (userFrom != null && userTo != null
					&& userTo.getSupportLevel() == SupportLevel.NONE)
			{
				SupportLevel newLevel = userFrom.getSupportLevel();

				ISupportLevel supportType = Util.updateSupportLevel(userTo,
						newLevel);
				em.persist(supportType);
				userTo.setSupportLevel(newLevel);

				supportType = Util.updateSupportLevel(userFrom, newLevel);
				em.persist(supportType);
				userFrom.setSupportLevel(SupportLevel.NONE);

				em.getTransaction().commit();
				return true;
			}
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

	@SuppressWarnings("unchecked")
	public static boolean switchSupportLevel(String requestUserid)
	{
		EntityManager em = DataAccessManagerFactory.get().createEntityManager();
		em.getTransaction().begin();

		try
		{
			User requestUser = em.find(User.class, requestUserid);
			SupportLevel requestUserLevel = requestUser.getSupportLevel();

			SupportLevel partnerUserLevel;

			if (SupportLevel.FIRST_LEVEL == requestUserLevel)
			{
				partnerUserLevel = SupportLevel.SECOND_LEVEL;
			}
			else
			{
				partnerUserLevel = SupportLevel.FIRST_LEVEL;
			}

			Query query = em
					.createQuery("SELECT user FROM "
							+ User.class.getName()
							+ " user WHERE user.supportLevel = :param1 AND user.groupId = :param2");

			query.setParameter("param1", partnerUserLevel.getId());
			query.setParameter("param2", requestUser.getGroup().getKey()
					.getId());

			List<User> queryResult = query.getResultList();

			if (!queryResult.isEmpty())
			{
				User partnerUser = queryResult.get(0);

				if (requestUser != null && partnerUser != null)
				{
					ISupportLevel supportType = Util.updateSupportLevel(
							partnerUser, requestUserLevel);
					em.persist(supportType);
					partnerUser.setSupportLevel(requestUserLevel);

					supportType = Util.updateSupportLevel(requestUser,
							partnerUser.getSupportLevel());
					em.persist(supportType);
					requestUser.setSupportLevel(partnerUser.getSupportLevel());

					em.getTransaction().commit();
					return true;
				}
			}
			return false;
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
