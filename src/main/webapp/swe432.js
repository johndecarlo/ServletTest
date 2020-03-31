function checkGym (form) {
	if(!(document.getElementById('afc').checked) && !(document.getElementById('rac').checked) && !(document.getElementById('skyline').checked)) {
		alert ("Please select a gym.");
	}
}

function checkRating (form) {
	if(!(document.getElementById('very_bad').checked) && !(document.getElementById('bad').checked) && !(document.getElementById('okay').checked)
		&& !(document.getElementById('good').checked) && !(document.getElementById('very_good').checked)) {
		alert ("Please give a rating of your experience.");
	}
}

function checkExercise (form) {
	if(!(form.cardio.checked) && !(form.chest.checked) && !(form.back.checked) && !(form.arms.checked) && !(form.legs.checked) && !(form.core.checked) && !(form.basketball.checked) && !(form.swimming.checked) && !(form.other.checked)) {
		alert ("Please select at least one exercise you did.");
	}
}
		
function checkOptions (form) {
	checkGym(form);
	checkExercise(form);
	checkExercise(form);
}