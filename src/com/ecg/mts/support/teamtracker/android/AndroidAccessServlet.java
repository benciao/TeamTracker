package com.ecg.mts.support.teamtracker.android;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecg.mts.support.teamtracker.dataaccess.UserPersistenceWrapper;
import com.ecg.mts.support.teamtracker.domain.User;

/**
 * This servlet is responsible for the decision of given access to a certain
 * android device. <br>
 * <br>
 * by Markus Arndt <br>
 * <a href="arndt.markus@googlemail.com">arndt.markus@googlemail.com</a><br>
 * created 25.05.2012
 */
public class AndroidAccessServlet extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		String mobilePhone = req.getParameter("mobilePhone");

		User user = UserPersistenceWrapper.getUserForId(mobilePhone);

		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");

		String xml = ResponseToXmlConverter
				.convertToUserExistenceXml(user != null);

		resp.getWriter().write(xml);

	}
}
