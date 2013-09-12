package com.ecg.mts.support.teamtracker.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.ecg.mts.support.teamtracker.dataaccess.AdminUserPersistenceWrapper;
import com.ecg.mts.support.teamtracker.domain.AdminUser;
import com.ecg.mts.support.teamtracker.domain.AvailableStatus;
import com.ecg.mts.support.teamtracker.domain.FirstLevelSupport;
import com.ecg.mts.support.teamtracker.domain.ISupportLevel;
import com.ecg.mts.support.teamtracker.domain.LimitedAvailableStatus;
import com.ecg.mts.support.teamtracker.domain.NoneLevelSupport;
import com.ecg.mts.support.teamtracker.domain.NotAvailableStatus;
import com.ecg.mts.support.teamtracker.domain.SecondLevelSupport;
import com.ecg.mts.support.teamtracker.domain.User;
import com.ecg.mts.support.teamtracker.domain.User.SupportLevel;
import com.ecg.mts.support.teamtracker.domain.User.SupportStatus;

public class Util
{
	public static String getMd5Encoding(String input)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			return number.toString(16);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static AdminUser getCurrentAdminUser()
	{
		return AdminUserPersistenceWrapper.getLogedOnAdminUser();
	}

	public static Object updateStatus(User user, SupportStatus newStatus)
	{
		Object result = null;

		if (user.getSupportStatus() != newStatus)
		{
			Date changeDate = new Date();

			switch (newStatus)
			{
			case AVAILABLE:
				result = new AvailableStatus(user.getMobilePhone(), user
						.getStatusChange().getTime(), changeDate.getTime(),
						changeDate.getTime() - user.getStatusChange().getTime());
				break;
			case LIMITED_AVAILABLE:
				result = new LimitedAvailableStatus(user.getMobilePhone(), user
						.getStatusChange().getTime(), changeDate.getTime(),
						changeDate.getTime() - user.getStatusChange().getTime());
				break;
			default:
				result = new NotAvailableStatus(user.getMobilePhone(), user
						.getStatusChange().getTime(), changeDate.getTime(),
						changeDate.getTime() - user.getStatusChange().getTime());
			}

			user.setStatusChange(changeDate);
		}

		return result;
	}

	public static ISupportLevel updateSupportLevel(User user,
			SupportLevel newLevel)
	{
		ISupportLevel result = null;

		if (user.getSupportLevel() != newLevel)
		{
			Date changeDate = new Date();

			switch (newLevel)
			{
			case FIRST_LEVEL:
				result = new FirstLevelSupport(user.getMobilePhone(), user
						.getLevelChange().getTime(), changeDate.getTime(),
						changeDate.getTime() - user.getLevelChange().getTime());
				break;
			case SECOND_LEVEL:
				result = new SecondLevelSupport(user.getMobilePhone(), user
						.getLevelChange().getTime(), changeDate.getTime(),
						changeDate.getTime() - user.getLevelChange().getTime());
				break;
			default:
				result = new NoneLevelSupport(user.getMobilePhone(), user
						.getLevelChange().getTime(), changeDate.getTime(),
						changeDate.getTime() - user.getLevelChange().getTime());
			}

			user.setLevelChange(changeDate);
		}

		return result;
	}
}
