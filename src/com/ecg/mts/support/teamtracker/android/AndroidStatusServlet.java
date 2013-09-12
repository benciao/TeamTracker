package com.ecg.mts.support.teamtracker.android;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecg.mts.support.teamtracker.dataaccess.GroupPersistenceWrapper;
import com.ecg.mts.support.teamtracker.dataaccess.UserPersistenceWrapper;
import com.ecg.mts.support.teamtracker.domain.Group;
import com.ecg.mts.support.teamtracker.domain.User;
import com.ecg.mts.support.teamtracker.domain.User.SupportStatus;

public class AndroidStatusServlet extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		String action = req.getParameter("action");

		if (action != null && action.equalsIgnoreCase("all"))
		{
			List<User> userList = UserPersistenceWrapper.getUserList(true);
			List<Group> groupList = GroupPersistenceWrapper.getGroupList(true);

			resp.setContentType("text/xml");
			resp.setHeader("Cache-Control", "no-cache");

			String xml = ResponseToXmlConverter.convertToStatusXml(userList,
					groupList);

			resp.getWriter().write(xml);
		}
		else if (action != null && action.equalsIgnoreCase("updateUserStatus"))
		{
			String userId = req.getParameter("user");
			String newStatus = req.getParameter("newStatus");

			Boolean result = UserPersistenceWrapper.updateUserStatus(userId,
					SupportStatus.getSupportStatusForId(Integer
							.parseInt(newStatus)));

			resp.setContentType("text/xml");
			resp.setHeader("Cache-Control", "no-cache");

			String xml = ResponseToXmlConverter
					.convertToBooleanResultXml(result);

			resp.getWriter().write(xml);
		}
		else if (action != null
				&& action.equalsIgnoreCase("giveSupportLevelToOtherUser"))
		{
			String userFromId = req.getParameter("fromUser");
			String userToId = req.getParameter("toUser");

			Boolean result = UserPersistenceWrapper
					.giveSupportLevelToOtherUser(userFromId, userToId);

			resp.setContentType("text/xml");
			resp.setHeader("Cache-Control", "no-cache");

			String xml = ResponseToXmlConverter
					.convertToBooleanResultXml(result);

			resp.getWriter().write(xml);
		}
		else if (action != null
				&& action.equalsIgnoreCase("switchSupportLevel"))
		{
			String requestUserid = req.getParameter("requestUser");

			Boolean result = UserPersistenceWrapper
					.switchSupportLevel(requestUserid);

			resp.setContentType("text/xml");
			resp.setHeader("Cache-Control", "no-cache");

			String xml = ResponseToXmlConverter
					.convertToBooleanResultXml(result);

			resp.getWriter().write(xml);
		}
	}
}
