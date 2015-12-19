function isPrefix(text, prefix) {
	return text.indexOf(prefix) === 0;
}
function isSuffix(text, suffix) {
	return text.match(suffix + "$") == suffix;
}

function normalize(attribute, object, parameters) {
	parameters = JSON.parse(parameters);
	var prefix = parameters["prefix"];
	if (prefix != null) {
		prefix = prefix.toLowerCase();
	}
	var suffix = parameters["suffix"];
	if (suffix != null) {
		suffix = suffix.toLowerCase();
	}
	if(object == null) {
		return object;
	}
	var objectLowerCase = object.toLowerCase();

	if (prefix != null && prefix != "" && isPrefix(objectLowerCase, prefix)) {
		var indexPrefix = prefix.length;
		if(indexPrefix > -1) {
			object = object.substr(indexPrefix);
		}
	}
	if (suffix != null && suffix != "" && isSuffix(objectLowerCase, suffix)) {
		var indexSuffix = objectLowerCase.indexOf(suffix);
		if(indexSuffix > -1) {
			object = object.substr(0, indexSuffix);
		}
	}

	return object.replace(/^\s+|\s+$/g, '');
}
