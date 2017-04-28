//on load

$(function() {	
	$( "#introduced" ).datepicker({
		dateFormat: 'yy-mm-dd',
		onSelect:function(){validateDate();}
	});
	$( "#discontinued" ).datepicker({
		dateFormat: 'yy-mm-dd',
		onSelect:function(){validateDate();}
	});
	
	$('#discontinued').on('input', function() {
		validateDate();
	});
	
	$('#introduced').on('input', function() {
		validateDate();
	});
	
	$('#submit').click(function(event) {
		if(!validateDate() || !validateName()){
			event.preventDefault();
		}
	});
	
	
});

$.urlParam = function(name){
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	return results[1] || 0;
}

function getParameterByName(name, url) {
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if (!results) return null
	return results[1] || 0;
//    if (!url) url = window.location.href;
//    name = name.replace(/[\[\]]/g, "\\$&");
//    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
//        results = regex.exec(url);
//    ;
//    if (!results[2]) return '';
//    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function validateDate(){
	var disco=$('#discontinued').val();
	var intro=$('#introduced').val();
	if(!intro || !disco || disco>intro){
		$('#discontinued').parent().removeClass("alert alert-danger")
		return true;
	}else{
		$('#discontinued').parent().addClass("alert alert-danger");
		return false;
	}
}

function validateName(){
	var name=$('#name').val();
	if(typeof name === "undefined" || name.trim()==""){
		alert("You must suply a name");
		return false;
	}else{
		return true;
	}
}