package com.ecg.mts.support.teamtracker.android;

import java.util.List;

import com.ecg.mts.support.teamtracker.domain.Group;
import com.ecg.mts.support.teamtracker.domain.User;

/**
 * This converter convertes an android request in a parseble xml format. <br>
 * <br>
 * by Markus Arndt <br>
 * <a href="arndt.markus@googlemail.com">arndt.markus@googlemail.com</a><br>
 * created 25.05.2012
 */
public class ResponseToXmlConverter
{
	public static String convertToUserExistenceXml(boolean isUserExistent)
	{
		String content = "";
		content += addElement("existence", Boolean.toString(isUserExistent));

		String xml = addElement("team-tracker-response", content);
		return xml;
	}

	public static String convertToBooleanResultXml(boolean result)
	{
		String content = "";
		content += addElement("result", Boolean.toString(result));

		String xml = addElement("team-tracker-response", content);
		return xml;
	}

	public static String convertToStatusXml(List<User> userList,
			List<Group> groupList)
	{
		String userContent = "";

		for (User user : userList)
		{
			String attributes = "";

			attributes += addElement("first-name", user.getFirstName());
			attributes += addElement("last-name", user.getLastName());
			attributes += addElement("mobile-phone", user.getMobilePhone());
			attributes += addElement("second-mobile-phone",
					user.getSecondMobilePhone());
			attributes += addElement("landline-phone", user.getLandlinePhone());
			attributes += addElement("email", user.getEmail());
			attributes += addElement("status", user.getSupportStatus().getId()
					+ "");
			attributes += addElement("level", user.getSupportLevel().getId()
					+ "");
			attributes += addElement("level-change", user.getLevelChange()
					.getTime() + "");
			attributes += addElement("status-change", user.getStatusChange()
					.getTime() + "");
			attributes += addElement("group-id", user.getGroup().getKey()
					.getId()
					+ "");

			userContent += addElement("user", attributes);
		}

		String users = addElement("users", userContent);

		String groupContent = "";
		for (Group group : groupList)
		{
			String attributes = "";

			attributes += addElement("name", group.getName());
			attributes += addElement("description", group.getDescription());
			attributes += addElement("id", group.getKey().getId() + "");
			attributes += addElement("support-type", group.getSupportType()
					.getId() + "");

			groupContent += addElement("group", attributes);
		}

		String groups = addElement("groups", groupContent);

		String xml = addElement("team-tracker-response", users + groups);
		return xml;
	}

	public static String addElement(String tag, String value)
	{
		return ("<" + tag + ">" + value + "</" + tag + ">");
	}
}
