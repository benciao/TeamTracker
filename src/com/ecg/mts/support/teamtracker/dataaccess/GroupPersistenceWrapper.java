package com.ecg.mts.support.teamtracker.dataaccess;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ecg.mts.support.teamtracker.domain.Group;
import com.ecg.mts.support.teamtracker.domain.Group.SupportType;

public class GroupPersistenceWrapper
{
    private static final Logger log = Logger.getLogger(GroupPersistenceWrapper.class.getName());

    /**
     * This methode inserts a {@link Group}-Object into the database.
     * 
     * @param group
     *            Group
     * @return true, if insert was successful. Otherwise false.
     */
    public static boolean createGroup(Group group)
    {
        EntityManager em = DataAccessManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        try
        {
            em.persist(group);
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
     * This methode returns a list of {@link Group}-Objects, where the property
     * value of property <code>isActive</code> meets the value of the passed
     * parameter.
     * 
     * @param isActive
     *            Group status
     * @return List of {@link Group}-Objects if found. Otherwise empty list.
     */
    @SuppressWarnings("unchecked")
    public static List<Group> getGroupList(boolean isActive)
    {
        EntityManager em = DataAccessManagerFactory.get().createEntityManager();
        List<Group> result = new ArrayList<Group>();

        try
        {
            Query query = em.createQuery("SELECT group FROM " + Group.class.getName() + " group");
            List<Group> queryResult = (List<Group>) query.getResultList();

            for (Group group : queryResult)
            {
                if (group.isActive() == isActive)
                {
                    result.add(group);
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
     * This methode deletes the given {@link Group}-Object from the database.
     * 
     * @param ids
     *            Ids of groups to delete.
     * @return true, if delete was successful. Otherwise false.
     */
    public static boolean deleteGroup(List<Long> ids)
    {
        EntityManager em = DataAccessManagerFactory.get().createEntityManager();

        try
        {
            for (Long id : ids)
            {
                em.getTransaction().begin();
                Group groupToDelete = em.find(Group.class, id);
                em.remove(groupToDelete);
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

    /**
     * This methode loads a {@link Group}-Object that is assoziated with the
     * given id.
     * 
     * @param id
     *            Id of group to load.
     * @return Group, if it exist. Otherwise null.
     */
    public static Group getGroupForId(Long id)
    {
        EntityManager em = DataAccessManagerFactory.get().createEntityManager();

        try
        {
            Group group = em.find(Group.class, id);

            return group;
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
     * This methode updates a {@link Group}-Object that is assoziated with the
     * given id.
     * 
     * @param id
     *            Id of group to load.
     * @param name
     *            New name of group.
     * @param description
     *            New description of group.
     * @param supportType
     *            New support type of group.
     */
    public static void updateGroup(Long id, String name, String description, SupportType supportType)
    {
        EntityManager em = DataAccessManagerFactory.get().createEntityManager();
        em.getTransaction().begin();

        try
        {
            Group group = em.find(Group.class, id);
            if (group != null)
            {
                group.setName(name);
                group.setDescription(description);
                group.setSupportType(supportType);
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
}
