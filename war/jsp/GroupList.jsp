<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.ecg.mts.support.teamtracker.domain.Group"%>
<%@ page import="com.ecg.mts.support.teamtracker.domain.AdminUser"%>
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
		List<Group> groupList = null;
		Group groupToUpdate = null;
		if (request != null)
		{
			Object attribute = request.getAttribute("groupList");
			Object updateAttribute = request
					.getAttribute("groupToUpdate");

			if (attribute != null)
			{
				groupList = (List<Group>) attribute;
			}
			if (updateAttribute != null)
			{
				groupToUpdate = (Group) updateAttribute;
			}
		}
		if (groupToUpdate != null)
		{
%>
<body onLoad="openPopupUpdate()">
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
									<FORM action="/UserList" name="categoryForm" method="post">
										<select class="menuSelect" name="selectCategory"
											onChange="javascript:document.categoryForm.submit()">
											<option class="selectOption" value="user">Benutzer</option>
											<option class="selectOption" value="group" selected>Gruppen</option>
										</select>
									</FORM>
								</div>
							</div>
						</th>
						<th scope="col"><a class="styleButtonInactive" id="aDelete"
							title="Löschen"> <span style="display: none;" id="spanDelete"><img
									src="../resources/images/trash.png"></span>
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
									href="javascript:openPopupCreate()"> <span
									style="background-color: #3c80ee;">Neue Gruppe</span>
								</a> <br> <br>
								<FORM action="/GroupList" name="refreshForm" method="post">
									<a class="styleButtonContentAction"
										href="javascript:document.refreshForm.submit()"> <span
										style="background-color: #3c80ee;">Aktualisieren</span>
									</a>
								</FORM>
							</div>
						</td>
						<td class="centerColumn">
							<div class="centerContent">
								<FORM action="/GroupList" name="listForm" method="post">
									<input name="useraction" type="hidden" /> <input
										name="groupId" type="hidden" />
									<table class="listTable">
										<thead>
											<tr>
												<th scope="col"><input type="checkbox"
													name="checkAllBox" value="dummy"
													onClick="check(document.listForm.groupCheckBox, document.listForm.checkAllBox)"></th>
												<th scope="col">Gruppenname</th>
												<th scope="col">Beschreibung</th>
												<th scope="col">Bereitschaftstyp</th>
												<th scope="col">Anzahl Mitglieder</th>
											</tr>
										</thead>
										<tbody>

											<%
												if (groupList != null && !groupList.isEmpty())
													{
														for (Group group : groupList)
														{
											%>
											<tr>
												<td><input type="checkbox" name="groupCheckBox"
													onClick="setCheckAllState(document.listForm.groupCheckBox, document.listForm.checkAllBox)"
													value="<%=group.getKey().getId()%>"></td>
												<td onClick="getGroup(<%=group.getKey().getId()%>)"><%=group.getName()%></td>
												<td onClick="getGroup(<%=group.getKey().getId()%>)"><%=group.getDescription()%></td>
												<td onClick="getGroup(<%=group.getKey().getId()%>)"><%=group.getSupportType().getDescription()%></td>
												<td onClick="getGroup(<%=group.getKey().getId()%>)"><%=group.getMembers().size()%></td>
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
			<div id="popupAddGroup">
				<div id="popupHeader">
					<p class="inputHeaderMargin">Neue Gruppe
					<p>
				</div>
				<FORM action="/GroupList" name="addForm" method="post">
					<input name="useraction" type="hidden" />
					<div class="inputHeader">
						<p class="inputHeaderMargin">Name
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="groupName"
							type="text" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Beschreibung
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="groupDescription"
							type="text" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Bereitschaftstyp
						<p>
					</div>
					<div class="inputText">
						<select class="inputSelect" name="selectSupportType">
							<%
								for (SupportType supportType : SupportType.values())
									{
							%>
							<option value="<%=supportType.getId()%>"><%=supportType.getDescription()%></option>
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
									href="javascript:validateGroupForm(document.addForm, 'addGroup', 'submitErrorAddGroup')">
										<span style="background-color: #3c80ee;"><img
											src="../resources/images/save.png"></span>
								</a></td>
								<td><a id="popupCancelAddGroup" title="Abbrechen"
									class="styleButtonNormal"> <span
										style="background-color: #d74635;"><img
											src="../resources/images/cancel.png"></span>
								</a></td>
							</tr>
						</table>
					</div>
					<div class="error">
						<p class="inputHeaderMargin" id="submitErrorAddGroup"></p>
					</div>
				</FORM>
			</div>
			<div id="popupUpdateGroup">
				<%
					if (groupToUpdate != null)
						{
				%>
				<div id="popupHeader">
					<p class="inputHeaderMargin">Gruppe bearbeiten
					<p>
				</div>
				<FORM action="/GroupList" name="updateForm" method="post">
					<input name="useraction" type="hidden" /> <input name="groupId"
						type="hidden" value="<%=groupToUpdate.getKey().getId()%>" />
					<div class="inputHeader">
						<p class="inputHeaderMargin">Name
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="groupName"
							type="text" value="<%=groupToUpdate.getName()%>" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Beschreibung
						<p>
					</div>
					<div class="inputText">
						<input class="inputLogonWith" size="50" name="groupDescription"
							type="text" value="<%=groupToUpdate.getDescription()%>" />
					</div>
					<div class="inputHeader">
						<p class="inputHeaderMargin">Bereitschaftstyp
						<p>
					</div>
					<div class="inputText">
						<select class="inputSelect" name="selectSupportType">
							<%
								for (SupportType supportType : SupportType.values())
										{
											if (supportType.getId() == groupToUpdate
													.getSupportType().getId())
											{
							%>
							<option selected value="<%=supportType.getId()%>"><%=supportType.getDescription()%></option>
							<%
								}
											else
											{
							%>
							<option value="<%=supportType.getId()%>"><%=supportType.getDescription()%></option>
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
									href="javascript:validateGroupForm(document.updateForm, 'updateGroup', 'submitErrorUpdateGroup')">
										<span style="background-color: #3c80ee;"><img
											src="../resources/images/save.png"></span>
								</a></td>
								<td><a id="popupCancelUpdateGroup" title="Abbrechen"
									class="styleButtonNormal"> <span
										style="background-color: #d74635;"><img
											src="../resources/images/cancel.png"></span>
								</a></td>
							</tr>
						</table>
					</div>
					<div class="error">
						<p class="inputHeaderMargin" id="submitErrorUpdateGroup"></p>
					</div>
				</FORM>
				<%
					}
				%>
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
