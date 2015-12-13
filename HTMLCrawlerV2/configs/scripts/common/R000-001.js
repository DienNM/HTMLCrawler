function isPrefix(text, prefix) {
	return text.indexOf(prefix) === 0;
}
function isSuffix(text, suffix) {
	return text.match(suffix+"$") == suffix;
}

function getPrefix(attribute) {
	var map = {
		"jobType" : "- Hình thức làm việc:",
		"salary" : "- Mức lương:",
		"education" : "- Trình độ:",
		"experience" : "- Kinh nghiệm:",
		"workplace" : "Việc làm",
		"companyPhone" : "Điện thoại:",
		"companyAddress" : "Địa chỉ:",
		"genderRequired" : "- Giới tính:"
	};
	return map[attribute];
}

function getSuffix(attribute) {
	var map = {
		"companySize" : "người"
	};
	return map[attribute];
}

function normalize(attribute, object) {
	var prefix = getPrefix(attribute);
	var suffix = getSuffix(attribute);
	var objectLowerCase = object.toLowerCase();
	if(prefix != null && isPrefix(objectLowerCase, prefix.toLowerCase())) {
		var indexPrefix = prefix.length;
		object = object.substr(indexPrefix);
	}
	if(suffix != null && isSuffix(objectLowerCase, suffix.toLowerCase())) {
		var indexSuffix = objectLowerCase.indexOf(suffix.toLowerCase());
		object = object.substr(0, indexSuffix);
	}
	return object.replace(/^\s+|\s+$/g,'');
}
