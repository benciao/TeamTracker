package com.ecg.mts.support.teamtracker.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecg.mts.support.teamtracker.dataaccess.GroupPersistenceWrapper;
import com.ecg.mts.support.teamtracker.domain.AdminUser;
import com.ecg.mts.support.teamtracker.domain.Group;
import com.ecg.mts.support.teamtracker.domain.Group.SupportType;
import com.ecg.mts.support.teamtracker.util.Util;

/**
 * This servlet handles requests that perform an action in the context of a
 * group. <br>
 * <br>
 * by Markus Arndt <br>
 * <a href="arndt.markus@googlemail.com">arndt.markus@googlemail.com</a><br>
 * created 06.05.2012
 */
public class GroupListServlet extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		AdminUser currentAdminUser = Util.getCurrentAdminUser();

		if (currentAdminUser != null)
		{
			String action = req.getParameter("useraction");
			req.setAttribute("groupToUpdate", null);

			/*
			 * adds a group
			 */
			if (action != null && action.equalsIgnoreCase("addGroup"))
			{
				String groupName = req.getParameter("groupName");
				String groupDescription = req.getParameter("groupDescription");
				String selectedValue = req.getParameter("selectSupportType");

				Group group = new Group(groupName, groupDescription,
						SupportType.getSupportTypeForId(Integer
								.parseInt(selectedValue)));

				GroupPersistenceWrapper.createGroup(group);
			}
			/*
			 * deletes a group
			 */
			else if (action != null && action.equalsIgnoreCase("delete"))
			{
				String[] checkBoxes = req.getParameterValues("groupCheckBox");
				if (checkBoxes != null)
				{
					List<Long> groupsToDelete = new ArrayList<Long>();
					for (String checkValue : checkBoxes)
					{
						groupsToDelete.add(Long.parseLong(checkValue));
					}

					if (!groupsToDelete.isEmpty())
					{
						GroupPersistenceWrapper.deleteGroup(groupsToDelete);
					}
				}
			}
			/*
			 * loads a group, to which a certain action will be performed
			 */
			else if (action != null && action.equalsIgnoreCase("getGroup"))
			{
				Long groupId = Long.parseLong(req.getParameter("groupId"));

				req.setAttribute("groupToUpdate",
						GroupPersistenceWrapper.getGroupForId(groupId));
			}
			/*
			 * updates a group
			 */
			else if (action != null && action.equalsIgnoreCase("updateGroup"))
			{
				String groupName = req.getParameter("groupName");
				String groupDescription = req.getParameter("groupDescription");
				String selectedValue = req.getParameter("selectSupportType");

				Long groupId = Long.parseLong(req.getParameter("groupId"));
				GroupPersistenceWrapper.updateGroup(groupId, groupName,
						groupDescription, SupportType
								.getSupportTypeForId(Integer
										.parseInt(selectedValue)));
			}
		}

		req.setAttribute("groupList",
				GroupPersistenceWrapper.getGroupList(true));
		req.setAttribute("currentAdminUser", currentAdminUser);
		redirectToGroupList(req, resp);
	}

	private void redirectToGroupList(HttpServletRequest req,
			HttpServletResponse resp) throws IOException
	{
		try
		{
			getServletConfig().getServletContext()
					.getRequestDispatcher("/jsp/GroupList.jsp")
					.forward(req, resp);
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
	}
}
