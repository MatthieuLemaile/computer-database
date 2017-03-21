//on load

$(function() {
	$('#discontinued').on('input', function() {
		validateDate();
	});
	
	$('#introduced').on('input', function() {
		validateDate();
	});
	
	$('#submit').click(function(event) {
		if(!validateDate()){
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