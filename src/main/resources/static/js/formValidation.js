toastr.options = {
	closeButton: true,
	progressBar: true,
	positionClass: "toast-top-right",
	timeOut: "5000",
};
$(document).ready(function () {
	fetchAllFormsWithDetails();
	fetchNextFormId();
	fetchAnswerTypes();
	fetchRecurrences();
	fetchActiveModules();
	fetchMonths();

	const priceInput = $("#comperiod");
	priceInput.on("input", function () {
		const invalidChars = /[^0-9]/g; // Allow only numbers (0-9)
		$(this).val($(this).val().replace(invalidChars, ""));

		if ($(this).val().length >= 10) {
			$(this).val($(this).val().substring(0, 10));
		}
	});
	const nameInput = $("#aliasname");
	nameInput.on("input", function () {
		const invalidChars = /[^a-zA-Z\s]/g;
		$(this).val($(this).val().replace(invalidChars, ""));
		if ($(this).val().length >= 30) {
			$(this).val($(this).val().substring(0, 30));
		}
	});
	const descInput = $("#textenglish");
	descInput.on("input", function () {
		const invalidChars = /[^a-zA-Z\s]/g;
		$(this).val($(this).val().replace(invalidChars, ""));
		if ($(this).val().length >= 50) {
			$(this).val($(this).val().substring(0, 50));
		}
	});
	const queInput = $("#description,#questionN,#questionL,#titletext");
	queInput.on("input", function () {
		const validChars = /[^a-zA-Z0-9\s]/g; // Allow only letters, numbers, and spaces
		$(this).val($(this).val().replace(validChars, "")); // Remove invalid characters

		if ($(this).val().length >= 50) {
			$(this).val($(this).val().substring(0, 50)); // Limit to 50 characters
		}
	});
	toastr.options = {
		closeButton: true,
		timeOut: 3000,
		positionClass: "toast-top-right"
	};

	$("#characteristicDropdown").on('change', function () {
		const selectedCharacteristicId = $(this).val();
		if (selectedCharacteristicId) {
			loadSubCharacteristics(selectedCharacteristicId);
		} else {
			const subCharacteristicDropdown = $("#subchar"); // Adjusted to match your HTML ID
			subCharacteristicDropdown.empty();
			subCharacteristicDropdown.append('<option value="">Select a characteristic first</option>');
			subCharacteristicDropdown.selectpicker('refresh');
		}
	});
});
// Validation function
function validateFormData(data) {
	const {
		titleText, aliasName, module, characteristic, subCharacteristic,
		recurrence, startMonth, compliancePeriod, effectiveDate, textEnglish
	} = data;

	// Validation checks
	if (!titleText) {
		toastr.error("Title Text (English) is required.");
		return false;
	}

	if (!aliasName) {
		toastr.error("Alias Name is required.");
		return false;
	}

	if (!module) {
		toastr.error("Module is required.");
		return false;
	}

	if (!characteristic) {
		toastr.error("Characteristic is required.");
		return false;
	}

	if (!subCharacteristic) {
		toastr.error("Sub-Characteristic is required.");
		return false;
	}

	if (!recurrence) {
		toastr.error("Recurrence is required.");
		return false;
	}

	if (!startMonth) {
		toastr.error("Start Month is required.");
		return false;
	}

	if (!compliancePeriod) {
		toastr.error("Compliance Period is required.");
		return false;
	} else if (!/^\d+$/.test(compliancePeriod)) {
		toastr.error("Compliance Period must be a valid number.");
		return false;
	}

	if (!effectiveDate) {
		toastr.error("Effective Date is required.");
		return false;
	} else if (!/^\d{2}\/\d{2}\/\d{4}$/.test(effectiveDate)) {
		toastr.error("Effective Date must be in DD/MM/YYYY format.");
		return false;
	}

	if (!textEnglish) {
		toastr.error("Text (English) is required.");
		return false;
	}

	return true; // All validations passed
}


