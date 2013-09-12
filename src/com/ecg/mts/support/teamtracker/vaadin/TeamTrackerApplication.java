package com.ecg.mts.support.teamtracker.vaadin;

import com.ecg.mts.support.teamtracker.dataaccess.AdminUserPersistenceWrapper;
import com.ecg.mts.support.teamtracker.domain.AdminUser;
import com.ecg.mts.support.teamtracker.util.Util;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TeamTrackerApplication extends Application
{
	private static final long	serialVersionUID	= -729015274819198483L;

	private LoginForm			loginForm			= new LoginForm();

	@Override
	public void init()
	{
		// setTheme("benciao.teamtracker");
		Window main = new Window("MTS.support TeamTracker [Manager]");
		setMainWindow(main);
		main.getContent().setSizeFull();
		Label header = new Label("MTS.support TeamTracker [Manager]");
		header.setContentMode(Label.CONTENT_TEXT);

		main.addComponent(header);

		loginForm.setCaption("Anmeldung");
		loginForm.setPasswordCaption("Kennwort");
		loginForm.setUsernameCaption("Benutzer");
		main.addComponent(loginForm);
		loginForm.addListener(new LoginListener()
		{
			public void onLogin(LoginEvent event)
			{
				String managerName = event.getLoginParameter("username");
				String managerPassword = event.getLoginParameter("password");

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
							// req.setAttribute("userList",
							// UserPersistenceWrapper.getUserList(true));
							// req.setAttribute("currentAdminUser", adminUser);
							loggedIn();
						}
						else
						{
							// req.setAttribute("logonResponse",
							// "Falsches Passwort.");
							// redirectWithLogonError(req, resp);
						}
					}
					else
					{
						// req.setAttribute("logonResponse",
						// "Benutzer ist nicht aktiviert.");
						// redirectWithLogonError(req, resp);
					}
				}
				else
				{
					// loginForm.set.setAttribute("logonResponse",
					// "Benutzer ist nicht bekannt.");
				}

			}
		});
	}

	private void loggedIn()
	{
		VerticalLayout appLayout = new VerticalLayout();
		getMainWindow().setContent(appLayout);
		appLayout.addComponent(new Label("My app goes here"));
		appLayout.addComponent(new Button("Logout", new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				close();
			}
		}));
	}
}
