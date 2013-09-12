package com.ecg.mts.support.teamtracker.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecg.mts.support.teamtracker.dataaccess.AdminUserPersistenceWrapper;
import com.ecg.mts.support.teamtracker.domain.AdminUser;

public class TestServlet extends HttpServlet
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        AdminUser adminUser = new AdminUser("benciao", "aaa", true);

        AdminUserPersistenceWrapper.createAdminUser(adminUser);
    }
}
