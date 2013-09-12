package com.ecg.mts.support.teamtracker.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecg.mts.support.teamtracker.dataaccess.UserPersistenceWrapper;
import com.ecg.mts.support.teamtracker.domain.AdminUser;
import com.ecg.mts.support.teamtracker.domain.User;
import com.ecg.mts.support.teamtracker.domain.User.SupportLevel;
import com.ecg.mts.support.teamtracker.domain.User.SupportStatus;
import com.ecg.mts.support.teamtracker.util.Util;

public class UserListServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		AdminUser currentAdminUser = Util.getCurrentAdminUser();

		if (currentAdminUser != null)
		{
			String action = req.getParameter("useraction");
			req.setAttribute("userToUpdate", null);

			/*
			 * adds a user
			 */
			if (action != null && action.equalsIgnoreCase("addUser"))
			{
				String firstName = req.getParameter("firstName");
				String lastName = req.getParameter("lastName");
				String email = req.getParameter("email");
				String mobilePhone = req.getParameter("mobilePhone");
				String groupId = req.getParameter("selectGroup");
				String secondMobilePhone = req
						.getParameter("secondMobilePhone");
				String landlinePhone = req.getParameter("landlinePhone");
				String supportLevelId = req.getParameter("selectLevel");

				User user = new User(firstName, lastName, email, mobilePhone,
						SupportStatus.NOT_AVAILABLE,
						SupportLevel.getSupportLevelForId(Integer
								.parseInt(supportLevelId)), secondMobilePhone,
						landlinePhone, Long.parseLong(groupId));

				UserPersistenceWrapper.createUser(user);
			}
			/*
			 * deletes a user
			 */
			else if (action != null && action.equalsIgnoreCase("delete"))
			{
				String[] checkBoxes = req.getParameterValues("userCheckBox");
				if (checkBoxes != null)
				{
					List<String> usersToDelete = new ArrayList<String>();
					for (String checkValue : checkBoxes)
					{
						usersToDelete.add(checkValue);
					}

					if (!usersToDelete.isEmpty())
					{
						UserPersistenceWrapper.deleteUser(usersToDelete);
					}
				}
			}
			else if (action != null
					&& action.equalsIgnoreCase("saveKnownledge"))
			{
				String userId = req.getParameter("userCheckBox");
				req.setAttribute("showKnownledgeForUser",
						UserPersistenceWrapper.getUserForId(userId));
			}
			/*
			 * loads a user, to which a certain action will be performed
			 */
			else if (action != null && action.equalsIgnoreCase("getUser"))
			{
				String userId = req.getParameter("userId");

				req.setAttribute("userToUpdate",
						UserPersistenceWrapper.getUserForId(userId));
			}
			/*
			 * updates a user
			 */
			else if (action != null && action.equalsIgnoreCase("updateUser"))
			{
				String firstName = req.getParameter("firstName");
				String lastName = req.getParameter("lastName");
				String email = req.getParameter("email");
				String userId = req.getParameter("userId");
				String groupId = req.getParameter("selectGroup");
				String secondMobilePhone = req
						.getParameter("secondMobilePhone");
				String landlinePhone = req.getParameter("landlinePhone");
				String supportLevelId = req.getParameter("selectLevel");

				UserPersistenceWrapper.updateUser(userId, firstName, lastName,
						email, secondMobilePhone, landlinePhone, Long
								.parseLong(groupId), SupportLevel
								.getSupportLevelForId(Integer
										.parseInt(supportLevelId)));
			}
		}

		req.setAttribute("userList", UserPersistenceWrapper.getUserList(true));
		req.setAttribute("currentAdminUser", currentAdminUser);
		redirectToUserList(req, resp);
	}

	private void redirectToUserList(HttpServletRequest req,
			HttpServletResponse resp) throws IOException
	{
		try
		{
			getServletConfig().getServletContext()
					.getRequestDispatcher("/jsp/UserList.jsp")
					.forward(req, resp);
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
	}
}
