<html>
<head>
<title>MTS.support TeamTracker [Manager]</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="../resources/teamtracker.css">

<script type='text/javascript'>
		function enterPressed(evn) {
			if (window.event && window.event.keyCode == 13) {
  				document.logonForm.submit();
			} 
			else if (evn && evn.keyCode == 13) {
  				document.logonForm.submit();
			}
		}
		
		document.onkeypress = enterPressed;
	</script>
</head>

<body onload="document.logonForm.managername.focus()">
	<div id="container">
		<div id="header">
			<div class="headerTitle">
				<p>MTS.support TeamTracker [Manager]</p>
			</div>
		</div>
		<div id="body">
			<table width="100%">
				<tr>
					<td class="welcomeColumn">
						<div class="welcomeArea">
							<p>
								<b>Melde Dich an und ...</b>
							</p>
							<ul>
								<li>Erstelle, bearbeite oder lösche <b>Bereitschaftsgruppen</b></li>
								<li>Erstelle, bearbeite oder lösche <b>Teammitglieder</b>
									und weise diese Gruppen zu
								</li>
								<li>Lege pro Teammitglied Informationen zur <b>Erreichbarkeit</b>
									und <b>speziellem Wissen</b> an
								</li>
								<li>Weise Teammitgliedern einen speziellen <b>Bereitschaftsstatus</b>
									zu
								</li>
								<li>Erstelle und pflege einen <b>Bereitschaftsplan</b> für
									jede Gruppe
								</li>
								<li>Und vieles mehr!</li>
							</ul>
						</div>
					</td>
					<td class="logonColumn">
						<div class="logonForm">
							<FORM action="/Logon" name="logonForm" method="post">
								<div id="popupHeader">
									<p class="inputHeaderMargin">Anmeldung
									<p>
								</div>
								<div class="inputHeader">
									<p class="inputHeaderMargin">Benutzer
									<p>
								</div>
								<div class="inputText">
									<input class="inputLogonWith" size="50" name="managername"
										type="text" />
								</div>
								<div class="inputHeader">
									<p class="inputHeaderMargin">Kennwort
									<p>
								</div>
								<div class="inputText">
									<input class="inputLogonWith" size="50" name="managerpassword"
										type="password" />
								</div>
								<div class="inputButtonMargin">
									<a class="styleButtonNormal"
										href="javascript:document.logonForm.submit()"> <span
										style="background-color: #3c80ee;">Login</span>
									</a>
								</div>
								<%
									String logonResponse = null;
									if (request != null)
									{
										Object attribute = request.getAttribute("logonResponse");

										if (attribute != null)
										{
											logonResponse = attribute.toString();
										}
									}

									if (logonResponse != null)
									{
								%>
								<div class="error">
									<p class="inputHeaderMargin"><%=logonResponse%></p>
								</div>
								<%
									}
								%>
							</FORM>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="footer"></div>
	</div>
</body>
</html>
