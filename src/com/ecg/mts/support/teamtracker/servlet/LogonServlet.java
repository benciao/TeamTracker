package com.ecg.mts.support.teamtracker.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecg.mts.support.teamtracker.dataaccess.AdminUserPersistenceWrapper;
import com.ecg.mts.support.teamtracker.dataaccess.UserPersistenceWrapper;
import com.ecg.mts.support.teamtracker.domain.AdminUser;
import com.ecg.mts.support.teamtracker.util.Util;

public class LogonServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{
		String managerName = req.getParameter("managername");
		String managerPassword = req.getParameter("managerpassword");

		AdminUser adminUser = AdminUserPersistenceWrapper
				.getAdminUserByUserName(managerName);

		if (adminUser != null)
		{
			if (adminUser.isActive())
			{
				if (adminUser.getPassword().equals(
						Util.getMd5Encoding(managerPassword)))
				{
					AdminUserPersistenceWrapper.setAdminUserLogonState(
							adminUser.getUsername(), true);
					req.setAttribute("userList",
							UserPersistenceWrapper.getUserList(true));
					req.setAttribute("currentAdminUser", adminUser);
					redirectWithSuccess(req, resp);
				}
				else
				{
					req.setAttribute("logonResponse", "Falsches Passwort.");
					redirectWithLogonError(req, resp);
				}
			}
			else
			{
				req.setAttribute("logonResponse",
						"Benutzer ist nicht aktiviert.");
				redirectWithLogonError(req, resp);
			}
		}
		else
		{
			req.setAttribute("logonResponse", "Benutzer ist nicht bekannt.");
			redirectWithLogonError(req, resp);
		}
	}

	private void redirectWithSuccess(HttpServletRequest req,
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

	private void redirectWithLogonError(HttpServletRequest req,
			HttpServletResponse resp) throws IOException
	{
		try
		{
			getServletConfig().getServletContext()
					.getRequestDispatcher("/jsp/Logon.jsp").forward(req, resp);
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
	}
}
