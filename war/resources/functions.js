function check(boxes, checkAllBox) {
	checkedBoxesCounter = 0;
	
	if (checkAllBox.checked) {
		if (boxes.length == undefined) {
			boxes.checked = true;
			++checkedBoxesCounter;
		} else {
			for (index = 0; index < boxes.length; index++) {
				boxes[index].checked = true;
				++checkedBoxesCounter;
			}
		}
	} else {
		if (boxes.length == undefined) {
			boxes.checked = false;
		} else {
			for (index = 0; index < boxes.length; index++) {
				boxes[index].checked = false;
			}
		}
	}
	if (document.getElementById("popupAddUser") != null) {
		toggleKnownledgeActionButton(checkedBoxesCounter)
	}
	
	toggleDeleteActionButton(checkAllBox.checked);
}

function setCheckAllState(boxes, checkAllBox) {
	atLeastOneBoxNotChecked = false;
	atLeastOneBoxChecked = false;
	checkedBoxesCounter = 0;

	if (boxes.length == undefined) {
		if (boxes.checked) {
			atLeastOneBoxChecked = true;
		} else {
			atLeastOneBoxNotChecked = true;
		}

		++checkedBoxesCounter;
	} else {
		for (index = 0; index < boxes.length; index++) {
			if (!boxes[index].checked) {
				atLeastOneBoxNotChecked = true;
			}
		}

		for (index = 0; index < boxes.length; index++) {
			if (boxes[index].checked) {
				atLeastOneBoxChecked = true;
				++checkedBoxesCounter;
			}
		}
	}

	if (document.getElementById("popupAddUser") != null) {
		toggleKnownledgeActionButton(checkedBoxesCounter)
	}

	toggleDeleteActionButton(atLeastOneBoxChecked);

	checkAllBox.checked = !atLeastOneBoxNotChecked;
}

function toggleDeleteActionButton(atLeastOneBoxChecked) {
	if (atLeastOneBoxChecked) {
		document.getElementById("aDelete").href = "javascript:submitListForm('delete')";
		document.getElementById("aDelete").className = "styleButtonListAction";
		document.getElementById("spanDelete").style.backgroundColor = "#ffbb1c";
		document.getElementById("spanDelete").style.display = "block";
	} else {
		document.getElementById("aDelete").removeAttribute("href");
		document.getElementById("aDelete").className = "styleButtonInactive";
		document.getElementById("spanDelete").style.display = "none";
	}
}

function toggleKnownledgeActionButton(checkedBoxesCounter) {
	if (checkedBoxesCounter == 1) {
		document.getElementById("aKnownledge").href = "javascript:openPopupShowKnownledge()";
		document.getElementById("aKnownledge").className = "styleButtonListAction";
		document.getElementById("spanKnownledge").style.backgroundColor = "#ffbb1c";
		document.getElementById("spanKnownledge").style.display = "block";
	} else {
		document.getElementById("aKnownledge").removeAttribute("href");
		document.getElementById("aKnownledge").className = "styleButtonInactive";
		document.getElementById("spanKnownledge").style.display = "none";
	}
}

function submitListForm(newAction) {
	document.listForm.useraction.value = newAction;
	document.listForm.submit();
}

function submitAction(form, newAction) {
	form.useraction.value = newAction;
	form.submit();
}

function validateGroupForm(form, newAction, errorElement) {
	var name = form.groupName.value;
	var desc = form.groupDescription.value;
	if (name == null || name == "") {
		document.getElementById(errorElement).innerHTML = "Der Gruppenname darf nicht leer sein.";
	} else if ((name.indexOf(".") > -1) || (name.indexOf(";") > -1)) {
		document.getElementById(errorElement).innerHTML = "Der Gruppenname darf keines der folgenden Zeichen enthalten: .;{}";
	} else if ((desc.indexOf("{") > -1) || (desc.indexOf(";") > -1)
			|| (desc.indexOf("}") > -1)) {
		document.getElementById(errorElement).innerHTML = "Die Gruppenbeschreibung darf keines der folgenden Zeichen enthalten: ;{}";
	} else {
		document.getElementById(errorElement).innerHTML = "";
		submitAction(form, newAction);
	}
}

function validateUserForm(form, newAction, errorElement) {
	var firstName = form.firstName.value;
	var lastName = form.lastName.value;
	var email = form.email.value;
	var mobilePhone = form.mobilePhone.value;
	var secondMobilePhone = form.secondMobilePhone.value;
	var landlinePhone = form.landlinePhone.value;

	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

	if (firstName == null || firstName == "") {
		document.getElementById(errorElement).innerHTML = "Der Vorname darf nicht leer sein.";
	} else if (lastName == null || lastName == "") {
		document.getElementById(errorElement).innerHTML = "Der Nachname darf nicht leer sein.";
	} else if (email == null || email == "") {
		document.getElementById(errorElement).innerHTML = "Die Emailadresse darf nicht leer sein.";
	} else if (mobilePhone == null || mobilePhone == "") {
		document.getElementById(errorElement).innerHTML = "Die Bereitschaftsnummer darf nicht leer sein.";
	}

	else if ((firstName.indexOf(".") > -1) || (firstName.indexOf(";") > -1)
			|| (firstName.indexOf("{") > -1) || (firstName.indexOf("}") > -1)) {
		document.getElementById(errorElement).innerHTML = "Der Vorname darf keines der folgenden Zeichen enthalten: .;{}";
	} else if ((lastName.indexOf(".") > -1) || (lastName.indexOf(";") > -1)
			|| (lastName.indexOf("{") > -1) || (lastName.indexOf("}") > -1)) {
		document.getElementById(errorElement).innerHTML = "Der Nachname darf keines der folgenden Zeichen enthalten: .;{}";
	} else if ((email.indexOf(";") > -1) || (email.indexOf("{") > -1)
			|| (email.indexOf("}") > -1)) {
		document.getElementById(errorElement).innerHTML = "Die Emailadresse darf keines der folgenden Zeichen enthalten: ;{}";
	} else if ((mobilePhone.indexOf(".") > -1)
			|| (mobilePhone.indexOf(";") > -1)
			|| (mobilePhone.indexOf("{") > -1)
			|| (mobilePhone.indexOf("}") > -1)) {
		document.getElementById(errorElement).innerHTML = "Die Bereitschaftsnummer darf keines der folgenden Zeichen enthalten: .;{}";
	} else if ((secondMobilePhone.indexOf(".") > -1)
			|| (secondMobilePhone.indexOf(";") > -1)
			|| (secondMobilePhone.indexOf("{") > -1)
			|| (secondMobilePhone.indexOf("}") > -1)) {
		document.getElementById(errorElement).innerHTML = "Die private Nummer darf keines der folgenden Zeichen enthalten: .;{}";
	} else if ((landlinePhone.indexOf(".") > -1)
			|| (landlinePhone.indexOf(";") > -1)
			|| (landlinePhone.indexOf("{") > -1)
			|| (landlinePhone.indexOf("}") > -1)) {
		document.getElementById(errorElement).innerHTML = "Die Festnetznummer darf keines der folgenden Zeichen enthalten: .;{}";
	}

	else if (!re.test(email)) {
		document.getElementById(errorElement).innerHTML = "Die Emailadresse ist ungültig.";
	} else if (isNaN(mobilePhone)) {
		document.getElementById(errorElement).innerHTML = "Die Bereitschaftsnummer darf nur aus Zahlen bestehen.";
	} else if (secondMobilePhone != "" && isNaN(secondMobilePhone)) {
		document.getElementById(errorElement).innerHTML = "Die private Nummer darf nur aus Zahlen bestehen.";
	} else if (landlinePhone != "" && isNaN(landlinePhone)) {
		document.getElementById(errorElement).innerHTML = "Die Festnetznummer darf nur aus Zahlen bestehen.";
	} else {
		document.getElementById(errorElement).innerHTML = "";
		submitAction(form, newAction);
	}
}

function getGroup(groupId) {
	document.listForm.groupId.value = groupId;
	submitAction(document.listForm, 'getGroup');
}

function getUser(userId) {
	document.listForm.userId.value = userId;
	submitAction(document.listForm, 'getUser');
}