package com.ecg.mts.support.teamtracker.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecg.mts.support.teamtracker.dataaccess.AdminUserPersistenceWrapper;
import com.ecg.mts.support.teamtracker.domain.AdminUser;
import com.ecg.mts.support.teamtracker.util.Util;

public class LogoutServlet extends HttpServlet
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
			AdminUserPersistenceWrapper.setAdminUserLogonState(
					currentAdminUser.getUsername(), false);
		}

		redirect(req, resp);
	}

	private void redirect(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
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
