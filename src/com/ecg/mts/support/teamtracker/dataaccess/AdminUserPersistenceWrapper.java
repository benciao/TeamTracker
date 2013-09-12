package com.ecg.mts.support.teamtracker.dataaccess;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ecg.mts.support.teamtracker.domain.AdminUser;

/**
 * This class acts as a wrapper to the data access layer. It provides crud
 * operations for the domain objects of type {@link AdminUser} of this
 * application. <br>
 * <br>
 * by Markus Arndt <br>
 * <a href="arndt.markus@googlemail.com">arndt.markus@googlemail.com</a><br>
 * created 06.04.2012
 */
public class AdminUserPersistenceWrapper
{
    private static final Logger log = Logger.getLogger(AdminUserPersistenceWrapper.class.getName());

    /**
     * This methode inserts a {@link AdminUser}-Object into the database.
     * 
     * @param adminUser
     *            AdminUser
     * @return true, if insert was successful. Otherwise false.
     */
    public static boolean createAdminUser(AdminUser adminUser)
    {
        EntityManager em = DataAccessManagerFactory.get().createEntityManager();
        em.getTransaction().begin();
        try
        {
            boolean exists = checkAdminUserExistence(adminUser, em);
            if (!exists)
            {
                em.persist(adminUser);
                em.getTransaction().commit();
                return true;
            }
            else
            {
                log.severe("AdminUser with folowing username already exists in the database: "
                        + adminUser.getUsername());
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
     * This methode tries to find an {@link AdminUser}-Object with a certain
     * name.
     * 
     * @param username
     *            Admin user name
     * @return {@link AdminUser}, if admin user was found. Otherwise null.
     */
    public static AdminUser getAdminUserByUserName(String username)
    {
        EntityManager em = DataAccessManagerFactory.get().createEntityManager();
        AdminUser result = null;

        try
        {
            result = em.find(AdminUser.class, username);
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

        return result;
    }

    /**
     * This methode sets the loged on state of an {@link AdminUser}-Object which
     * is associated with the parameter value <code>username</code>.
     * 
     * @param username
     *            Admin user name
     * @param state
     *            true, if {@link AdminUser} is loged on. Otherwise false;
     */
    @SuppressWarnings("unchecked")
    public static void setAdminUserLogonState(String username, boolean state)
    {
        EntityManager em = DataAccessManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        try
        {
            Query query = em.createQuery("SELECT adminuser FROM " + AdminUser.class.getName() + " adminuser");

            List<AdminUser> queryResult = (List<AdminUser>) query.getResultList();

            if (!queryResult.isEmpty())
            {
                for (AdminUser adminUser : queryResult)
                {
                    if (adminUser.getUsername().equals(username))
                    {
                        adminUser.setLogedOn(state);
                    }
                    else
                    {
                        adminUser.setLogedOn(false);
                    }
                }
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

    /**
     * This methode looks for an {@link AdminUser} that is loged on.
     * 
     * @return {@link AdminUser}, if one is loged on. Otherwise null.
     */
    @SuppressWarnings("unchecked")
    public static AdminUser getLogedOnAdminUser()
    {
        EntityManager em = DataAccessManagerFactory.get().createEntityManager();

        try
        {
            Query query = em.createQuery("SELECT adminuser FROM " + AdminUser.class.getName() + " adminuser");

            List<AdminUser> queryResult = (List<AdminUser>) query.getResultList();

            if (!queryResult.isEmpty())
            {
                for (AdminUser adminUser : queryResult)
                {
                    if (adminUser.isLogedOn())
                    {
                        return adminUser;
                    }
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
        return null;
    }

    private static boolean checkAdminUserExistence(AdminUser adminUser, EntityManager em)
    {
        AdminUser result = em.find(AdminUser.class, adminUser.getUsername());
        return result != null;
    }
}
