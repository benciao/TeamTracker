<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.ecg.mts.support.teamtracker.domain.User"%>
<%@ page import="com.ecg.mts.support.teamtracker.domain.Group"%>
<%@ page import="com.ecg.mts.support.teamtracker.domain.AdminUser"%>
<%@ page
	import="com.ecg.mts.support.teamtracker.dataaccess.GroupPersistenceWrapper"%>
<%@ page import="java.util.List"%>
<%@ page
	import="com.ecg.mts.support.teamtracker.domain.Group.SupportType"%>

<html>
<head>
<title>MTS.support TeamTracker [Manager]</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="../resources/teamtracker.css">
<link rel="stylesheet" type="text/css" href="../resources/popup.css">
<script src="http://jqueryjs.googlecode.com/files/jquery-1.2.6.min.js"
	type="text/javascript"></script>
<script type="text/javascript" src="../resources/functions.js"></script>
<script type="text/javascript" src="../resources/popup.js"></script>
</head>

<%
	AdminUser currentAdminUser = (AdminUser) request
			.getAttribute("currentAdminUser");

	if (currentAdminUser != null)
	{
		List<User> userList = null;
		User userToUpdate = null;

		if (request != null)
		{
			Object attribute = request.getAttribute("userList");
			Object updateAttribute = request
					.getAttribute("userToUpdate");

			if (attribute != null)
			{
				userList = (List<User>) attribute;
			}
			if (updateAttribute != null)
			{
				userToUpdate = (User) updateAttribute;
			}
		}
		if (userToUpdate != null)
		{
%>
<body onLoad="openPopupUpdateUser()">
	<%
		}
			else
			{
	%>

<body>
	<%
		}
	%>

	<div id="container">
		<div id="header">
			<div class="headerTitle">
				<p>MTS.support TeamTracker [Manager]</p>
			</div>
		</div>
		<div id="body">
			<table class="mainTable">
				<thead>
					<tr>
						<th scope="col">
							<div class="leftThContent">
								<div id="menuSelection">
									<FORM action="/GroupList" name="categoryForm" method="post">
										<select class="menuSelect" name="selectCategory"
											onChange="javascript:document.categoryForm.submit()">
											<option class="selectOption" value="user" selected>Benutzer</option>
											<option class="selectOption" value="group">Gruppen</option>
										</select>
									</FORM>
								</div>
							</div>
						</th>
						<th scope="col"><a class="styleButtonInactive" id="aDelete"
							title="Löschen"> <span style="display: none;" id="spanDelete"><img
									src="../resources/images/trash.png"></span>
						</a> <a class="styleButtonInactive" id="aKnownledge"
							title="Spezialwissen"> <span style="display: none;"
								id="spanKnownledge"><img
									src="../resources/images/knownledge.png"></span>
						</a></th>
						<th scope="col">
							<div class="rightThContent">
								<FORM action="/Logout" name="logoutForm" method="post">
									<a class="styleButtonNormal"
										href="javascript:document.logoutForm.submit()"> <span
										style="background-color: #d74635;">Logout</span>
									</a>
								</FORM>
							</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="leftColumn">
							<div class="leftContent">
								<br> <br> <a class="styleButtonContentAction"
									href="javascript:openPopupCreateUser()"> <span
									style="background-color: #3c80ee;">Neuer Benutzer</span>
								</a> <br> <br>
								<FORM action="/UserList" name="refreshForm" method="post">
									<a class="styleButtonContentAction"
										href="javascript:document.refreshForm.submit()"> <span
										style="background-color: #3c80ee;">Aktualisieren</span>
									</a>
								</FORM>
							</div>
						</td>
						<td class="centerColumn">
							<div class="centerContent">
								<FORM action="/UserList" name="listForm" method="post">
									<input name="useraction" type="hidden" /> <input name="userId"
										type="hidden" />
									<table class="listTable">
										<thead>
											<tr>
												<th scope="col"><input type="checkbox"
													name="checkAllBox" value="dummy"
													onClick="check(document.listForm.userCheckBox, document.listForm.checkAllBox)"></th>
												<th scope="col">Status</th>
												<th scope="col">Bereitschaftstyp</th>
												<th scope="col">Name</th>
												<th scope="col">Vorname</th>
												<th scope="col">Email</th>
												<th scope="col">Bereitschaftsnummer</th>
												<th scope="col">Gruppe</th>
											</tr>
										</thead>
										<tbody>

											<%
												if (userList != null && !userList.isEmpty())
													{
														for (User user : userList)
														{
											%>
											<tr>
												<td><input type="checkbox" name="userCheckBox"
													onClick="setCheckAllState(document.listForm.userCheckBox, document.listForm.checkAllBox)"
													value="<%=user.getMobilePhone()%>" ></td>
												<td onClick="getUser(<%=user.getMobilePhone()%>)"><%=user.getSupportStatus().getDescription()%></td>
												<td onClick="getUser(<%=user.getMobilePhone()%>)"><%=user.getSupportLevel().getDescription()%></td>
												<td onClick="getUser(<%=user.getMobilePhone()%>)"><%=user.getLastName()%></td>
												<td onClick="getUser(<%=user.getMobilePhone()%>)"><%=user.getFirstName()%></td>
												<td onClick="getUser(<%=user.getMobilePhone()%>)"><%=user.getEmail()%></td>
												<td onClick="getUser(<%=user.getMobilePhone()%>)"><%=user.getMobilePhone()%></td>
												<td onClick="getUser(<%=user.getMobilePhone()%>)"><%=user.getGroup().getName()%></td>
											</tr>
											<%
												}
													}
											%>
										</tbody>
									</table>
								</FORM>
							</div>
						</td>
						<td class="rightColumn">
							<div class="rightContent"></div>
						</td>
					</tr>
				</tbody>
			</table>
			<div id="popupAddUser">
				<div id="popupHeader">
					<p class="inputHeaderMargin">Neuer Benutzer
					<p>
				</div>
				<FORM action="/UserList" name="addForm" method="post">
					<input name="useraction" type="hidden" />
					<div class="inputHeader">
						<p class="inputHeaderMargin">Nachname
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="lastName"
							type="text" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Vorname
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="firstName"
							type="text" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Email
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="email" type="text" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Bereitschaftsnummer
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="mobilePhone"
							type="text" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Private Nummer
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="secondMobilePhone"
							type="text" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Festnetznummer
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="landlinePhone"
							type="text" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Bereitschaftstyp
						<p>
					</div>
					<div class="inputText">
						<select class="inputSelect" name="selectLevel">
							<%
								for (User.SupportLevel level : User.SupportLevel.values())
									{
							%>
							<option value="<%=level.getId()%>"><%=level.getDescription()%></option>
							<%
								}
							%>
						</select>
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Gruppe
						<p>
					</div>
					<div class="inputText">
						<select class="inputSelect" name="selectGroup">
							<%
								for (Group group : GroupPersistenceWrapper.getGroupList(true))
									{
							%>
							<option value="<%=group.getKey().getId()%>"><%=group.getName()%></option>
							<%
								}
							%>
						</select>
					</div>
					<br>
					<div class="inputButtonMargin">
						<table class="buttonTable">
							<tr>
								<td><a class="styleButtonNormal" title="Speichern"
									href="javascript:validateUserForm(document.addForm, 'addUser', 'submitErrorAddUser')">
										<span style="background-color: #3c80ee;"><img
											src="../resources/images/save.png"></span>
								</a></td>
								<td><a id="popupCancelAddUser" title="Abbrechen"
									class="styleButtonNormal"> <span
										style="background-color: #d74635;"><img
											src="../resources/images/cancel.png"></span>
								</a></td>
							</tr>
						</table>
					</div>
					<div class="error">
						<p class="inputHeaderMargin" id="submitErrorAddUser"></p>
					</div>
				</FORM>
			</div>
			<div id="popupUpdateUser">
				<%
					if (userToUpdate != null)
						{
				%>
				<div id="popupHeader">
					<p class="inputHeaderMargin">Benutzer bearbeiten
					<p>
				</div>
				<FORM action="/UserList" name="updateForm" method="post">
					<input name="useraction" type="hidden" /> <input name="userId"
						type="hidden" value="<%=userToUpdate.getMobilePhone()%>" />
					<div class="inputHeader">
						<p class="inputHeaderMargin">Nachname
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="lastName"
							type="text" value="<%=userToUpdate.getLastName()%>" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Vorname
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="firstName"
							type="text" value="<%=userToUpdate.getFirstName()%>" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Email
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="email" type="text"
							value="<%=userToUpdate.getEmail()%>" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Bereitschaftsnummer
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="mobilePhone"
							type="text" value="<%=userToUpdate.getMobilePhone()%>"
							disabled="disabled" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Private Nummer
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="secondMobilePhone"
							type="text" value="<%=userToUpdate.getSecondMobilePhone()%>" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Festnetznummer
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="landlinePhone"
							type="text" value="<%=userToUpdate.getLandlinePhone()%>" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Bereitschaftstyp
						<p>
					</div>
					<div class="inputText">
						<select class="inputSelect" name="selectLevel">
							<%
								for (User.SupportLevel level : User.SupportLevel.values())
										{
											if (level.getId() == userToUpdate.getSupportLevel().getId())
											{
							%>
							<option selected value="<%=level.getId()%>"><%=level.getDescription()%></option>
							<%
								}
											else
											{
							%>
							<option value="<%=level.getId()%>"><%=level.getDescription()%></option>
							<%
								}
										}
							%>
						</select>
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Gruppe
						<p>
					</div>
					<div class="inputText">
						<select class="inputSelect" name="selectGroup">
							<%
								for (Group group : GroupPersistenceWrapper
												.getGroupList(true))
										{
											if (group.getKey().getId() == userToUpdate.getGroup()
													.getKey().getId())
											{
							%>
							<option selected value="<%=group.getKey().getId()%>"><%=group.getName()%></option>
							<%
								}
											else
											{
							%>
							<option value="<%=group.getKey().getId()%>"><%=group.getName()%></option>
							<%
								}
										}
							%>
						</select>
					</div>
					<br>
					<div class="inputButtonMargin">
						<table class="buttonTable">
							<tr>
								<td><a class="styleButtonNormal" title="Speichern"
									href="javascript:validateUserForm(document.updateForm, 'updateUser', 'submitErrorUpdateUser')">
										<span style="background-color: #3c80ee;"><img
											src="../resources/images/save.png"></span>
								</a></td>
								<td><a id="popupCancelUpdateUser" title="Abbrechen"
									class="styleButtonNormal"> <span
										style="background-color: #d74635;"><img
											src="../resources/images/cancel.png"></span>
								</a></td>
							</tr>
						</table>
					</div>
					<div class="error">
						<p class="inputHeaderMargin" id="submitErrorUpdateUser"></p>
					</div>
				</FORM>
				<%
					}
				%>
			</div>
			<div id="popupBackground"></div>
		</div>
		<div id="popupShowKnownledge">
			<div id="popupHeader">
				<p class="inputHeaderMargin">Spezialwissen hinzufügen
				<p>
			</div>
			<FORM action="/UserList" name="knownledgeForm" method="post">
				<input name="useraction" type="hidden" /> <input name="userId"
					type="hidden" />

				<br>
				<div class="inputButtonMargin">
					<table class="buttonTable">
						<tr>
							<td><a class="styleButtonNormal" title="Speichern"
								href="javascript:validateUserForm(document.updateForm, 'updateUser', 'submitErrorUpdateUser')">
									<span style="background-color: #3c80ee;"><img
										src="../resources/images/save.png"></span>
							</a></td>
							<td><a id="popupCancelShowKnownledge" title="Abbrechen"
								class="styleButtonNormal"> <span
									style="background-color: #d74635;"><img
										src="../resources/images/cancel.png"></span>
							</a></td>
						</tr>
					</table>
				</div>
				<div class="error">
					<p class="inputHeaderMargin" id="submitErrorShowKnownledge"></p>
				</div>
			</FORM>
		</div>
		<div id="popupBackground"></div>
	</div>



	<div id="footer">
		<div class="currentUser">
			angemeldet als [<b><%=currentAdminUser.getUsername()%></b>]
		</div>
	</div>
	</div>
	<%
		}
		else
		{
	%>
	Keine Berechtigung!
	<%
		}
	%>
</body>
</html>