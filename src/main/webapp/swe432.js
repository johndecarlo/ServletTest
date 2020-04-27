function checkGym (form) {
	if(!(document.getElementById('afc').checked) && !(document.getElementById('rac').checked) && !(document.getElementById('skyline').checked)) {
		return false;
	}
	return true;
}

function checkRating (form) {
	if(!(document.getElementById('very_bad').checked) && !(document.getElementById('bad').checked) && !(document.getElementById('okay').checked)
		&& !(document.getElementById('good').checked) && !(document.getElementById('very_good').checked)) {
		return false;
	}
	return true;
}

function checkExercise (form) {
	if(!(form.cardio.checked) && !(form.chest.checked) && !(form.back.checked) && !(form.arms.checked) && !(form.legs.checked) && !(form.core.checked) && !(form.basketball.checked) && !(form.swimming.checked) && !(form.other.checked)) {
		return false;
	}
	return true;
}
		
function checkOptions (form) {
	if(!checkGym(form))
		alert ("Please select a gym.");
	else if(!checkExercise(form))
		alert ("Please select at least one exercise you did.");
	else if(!checkRating(form))
		alert ("Please select a rating.");
	else
		document.getElementById("theForm").submit();
}