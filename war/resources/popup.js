//SETTING UP OUR POPUP
//0 means disabled; 1 means enabled;
var popupStatus = 0;

//loading popup with jQuery magic!
function loadPopup(popup){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#popupBackground").css({
			"opacity": "0.7"
		});
		$("#popupBackground").fadeIn("slow");
		$(popup).fadeIn("slow");
		popupStatus = 1;
		document.addForm.groupName.focus();
	}
}

//disabling popup with jQuery magic!
function disablePopup(popup){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#popupBackground").fadeOut("slow");
		$(popup).fadeOut("slow");
		popupStatus = 0;
	}
}

//centering popup
function centerPopup(popup){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $(popup).height();
	var popupWidth = $(popup).width();
	//centering
	$(popup).css({
		"position": "absolute",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	//only need force for IE6
	
	$("#popupBackground").css({
		"height": windowHeight
	});
	
}

function openPopupCreate()
{
	//centering with css
	centerPopup("#popupAddGroup");
	//load popup
	loadPopup("#popupAddGroup");
}

function openPopupCreateUser()
{
	//centering with css
	centerPopup("#popupAddUser");
	//load popup
	loadPopup("#popupAddUser");
}

function openPopupUpdate()
{
	//centering with css
	centerPopup("#popupUpdateGroup");
	//load popup
	loadPopup("#popupUpdateGroup");
}
function openPopupUpdateUser()
{
	//centering with css
	centerPopup("#popupUpdateUser");
	//load popup
	loadPopup("#popupUpdateUser");
}
function openPopupShowKnownledge()
{
	//centering with css
	centerPopup("#popupShowKnownledge");
	//load popup
	loadPopup("#popupShowKnownledge");
}


//CONTROLLING EVENTS IN jQuery
$(document).ready(function(){				
	//CLOSING POPUP
	$("#popupCancelAddGroup").click(function(){
		disablePopup("#popupAddGroup");
		document.getElementById('submitErrorAddGroup').innerHTML = "";
	});
	
	$("#popupCancelUpdateGroup").click(function(){
		disablePopup("#popupUpdateGroup");
		document.getElementById('submitErrorUpdateGroup').innerHTML = "";
	});
	
	$("#popupCancelAddUser").click(function(){
		disablePopup("#popupAddUser");
		document.getElementById('submitErrorAddUser').innerHTML = "";
	});
	
	$("#popupCancelUpdateUser").click(function(){
		disablePopup("#popupUpdateUser");
		document.getElementById('submitErrorUpdateUser').innerHTML = "";
	});
	
	$("#popupShowKnownledge").click(function(){
		disablePopup("#popupShowKnownledge");
		document.getElementById('submitErrorShowKnownledge').innerHTML = "";
	});
});