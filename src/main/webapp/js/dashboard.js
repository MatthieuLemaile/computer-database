//On load
$(function() {
	// Default: hide edit mode
	$(".editMode").hide();

	// $(".pageNumber").click(
	// function() {
	// window.location = window.location.pathname
	// + replaceQueryParam('page', this.text,
	// window.location.search);
	// });
	//	
	// $(".numberPerPage").click(
	// function() {
	// window.location = window.location.pathname
	// + replaceQueryParam('numberPerPage', this.innerText,
	// window.location.search);
	// });
	
	$(".nb-per-page").click(function(){
		$(this).children()[0].click();
	});

	// Click on "selectall" box
	$("#selectall").click(function() {
		$('.cb').prop('checked', this.checked);
	});

	// Click on a checkbox
	$(".cb").click(function() {
		if ($(".cb").length == $(".cb:checked").length) {
			$("#selectall").prop("checked", true);
		} else {
			$("#selectall").prop("checked", false);
		}
		if ($(".cb:checked").length != 0) {
			$("#deleteSelected").enable();
		} else {
			$("#deleteSelected").disable();
		}
	});

});

function replaceQueryParam(param, newval, search) {
	var regex = new RegExp("([?;&])" + param + "[^&;]*[;&]?");
	var query = search.replace(regex, "$1").replace(/&$/, '');

	return (query.length > 2 ? query + "&" : "?")
			+ (newval ? param + "=" + newval : '');
}

// Function setCheckboxValues
(function($) {

	$.fn.setCheckboxValues = function(formFieldName, checkboxFieldName) {

		var str = $('.' + checkboxFieldName + ':checked').map(function() {
			return this.value;
		}).get().join();

		$(this).attr('value', str);

		return this;
	};

}(jQuery));

// Function toggleEditMode
(function($) {

	$.fn.toggleEditMode = function() {
		if ($(".editMode").is(":visible")) {
			$(".editMode").hide();
			$("#editComputer").text("Edit");
		} else {
			$(".editMode").show();
			$("#editComputer").text("View");
		}
		return this;
	};

}(jQuery));

// Function delete selected: Asks for confirmation to delete selected computers,
// then submits it to the deleteForm
(function($) {
	$.fn.deleteSelected = function() {
		//if (confirm("Are you sure you want to delete the selected computers?")) {
			$('#deleteForm input[name=selection]').setCheckboxValues(
					'selection', 'cb');
			$('#deleteForm').submit();
		//}
	};
}(jQuery));

// Event handling
// Onkeydown
$(document).keydown(function(e) {

	switch (e.keyCode) {
	// DEL key
	case 46:
		if ($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
			$.fn.deleteSelected();
		}
		break;
	// E key (CTRL+E will switch to edit mode)
	case 69:
		if (e.ctrlKey) {
			$.fn.toggleEditMode();
		}
		break;
	}
});
