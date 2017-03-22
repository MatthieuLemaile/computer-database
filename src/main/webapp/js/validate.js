//on load

$(function() {
	$( "#introduced" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$( "#discontinued" ).datepicker({ dateFormat: 'yy-mm-dd'});
	
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



function validateDate(){
	var disco=$('#discontinued').val();
	var intro=$('#introduced').val();
	if(disco>intro || !intro || !disco){
		$('#discontinued').parent().removeClass("alert alert-danger")
		return true;
	}else{
		$('#discontinued').parent().addClass("alert alert-danger");
		return false;
	}
}

function validateName(){
	var name=$('#computerName').val();
	if(typeof name === "undefined" || name.trim()==""){
		alert("You must suply a name");
		return false;
	}else{
		return true;
	}
}