const jwtToken = $('input[name="_csrf"]').val();
console.log(jwtToken);

// Function to fetch active modules
function fetchActiveModules() {
    $.ajax({
        url: '/api/modules',
        type: 'GET',
        headers: {
            "Authorization": jwtToken // Ensure proper Bearer token format
        },
        success: function (modules) {
            const moduleDropdown = $("#moduleDropdown");

            if (modules && modules.length > 0) {
                modules.forEach(module => {
                    moduleDropdown.append(
                        `<option value="${module.moduleId}">${module.moduleName}</option>` // Adjusted to match DB column names
                    );
                    moduleDropdown.selectpicker('refresh');
                });
            } else {
                moduleDropdown.append('<option value="">No Modules Available</option>');
                moduleDropdown.selectpicker('refresh');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching modules:", error);
            alert('Failed to load modules. Please try again later.');
        }
    });
}


// Function to fetch characteristics by module
function fetchCharacteristicsByModule(moduleId) {
    $.ajax({
        url: `/api/modules/${moduleId}/characteristics`,
        type: 'GET',
        headers: {
            "Authorization": jwtToken // Ensure proper Bearer token format
        },
        success: function (characteristics) {

            const characteristicDropdown = $("#characteristicDropdown");
            characteristicDropdown.empty();
            characteristicDropdown.append(new Option("-- Select a Characteristic --", ""));

            if (characteristics && characteristics.length > 0) {
                characteristics.forEach(characteristic => {
                    characteristicDropdown.append(
                        `<option value="${characteristic.characteristicId}">${characteristic.characteristicName}</option>` // Adjusted to match DB column names
                    );
                });
                characteristicDropdown.selectpicker('refresh');
            } else {
                characteristicDropdown.append('<option value="">No Characteristics Available</option>');
                characteristicDropdown.selectpicker('refresh');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching characteristics:", error);
            alert('Failed to load characteristics. Please try again later.');
        }
    });
}

function loadSubCharacteristics(characteristicId) {
    const subCharacteristicDropdown = $("#subchar");
    const jwtToken = $('input[name="_csrf"]').val(); // Consistent token retrieval

    // Clear and reset the sub-characteristic dropdown
    subCharacteristicDropdown.empty();
    subCharacteristicDropdown.append(new Option("-- Select a Sub-Characteristic --", ""));

    if (characteristicId) {
        $.ajax({
            url: `/api/characteristics/${characteristicId}/subcharacteristics`,
            method: "GET",
            headers: {
                "Authorization": jwtToken
            },
            success: function (data) {
                data.forEach(subCharacteristic => {
                    subCharacteristicDropdown.append(
                        new Option(subCharacteristic.subCharacteristicName, subCharacteristic.subCharacteristicId)
                    );
                });
                subCharacteristicDropdown.selectpicker('refresh'); // Refresh if using Bootstrap Select
            },
            error: function (xhr, status, error) {
                console.error("Error fetching sub-characteristics for characteristicId " + characteristicId + ":", error);
                toastr.error('Failed to load sub-characteristics: ' + (xhr.responseJSON?.message || error));
            }
        });
    }
}


function fetchMonths() {
    $.ajax({
        url: '/api/months',
        type: 'GET',
        headers: {
            "Authorization": jwtToken // Ensure proper Bearer token format
        },
        success: function (months) {

            const monthDropdown = $("#startM");

            if (months && months.length > 0) {
                months.forEach(month => {
                    monthDropdown.append(
                        `<option value="${month.id}">${month.monthname}</option>` // Adjusted to match Month entity
                    );
                    monthDropdown.selectpicker('refresh');
                });
            } else {
                monthDropdown.append('<option value="">No Months Available</option>');
                monthDropdown.selectpicker('refresh');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching months:", error);
            alert('Failed to load months. Please try again later.');
        }
    });
}

// Function to fetch recurrences
function fetchRecurrences() {
    $.ajax({
        url: '/api/recurrences',
        type: 'GET',
        headers: {
            "Authorization": jwtToken // Ensure proper Bearer token format
        },
        success: function (recurrences) {

            const recurrenceDropdown = $("#recurence");


            if (recurrences && recurrences.length > 0) {
                recurrences.forEach(recurrence => {
                    recurrenceDropdown.append(
                        `<option value="${recurrence.recurrenceId}">${recurrence.name}</option>` // Adjusted to match Recurrence entity
                    );
                    recurrenceDropdown.selectpicker('refresh');
                });
            } else {
                recurrenceDropdown.append('<option value="">No Recurrences Available</option>');
                recurrenceDropdown.selectpicker('refresh');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching recurrences:", error);
            alert('Failed to load recurrences. Please try again later.');
        }
    });
}

// Fetch Answer Types from API and Populate Dropdown
function fetchAnswerTypes() {
    $.ajax({
        url: '/api/answer-types',
        type: 'GET',
        headers: {
            "Authorization": jwtToken // Ensure proper Bearer token format
        },
        success: function (answerTypes) {

            const answerTypeDropdown = $("#answaretype"); // Target by class, as per your HTML
            answerTypeDropdown.empty();

            if (answerTypes && answerTypes.length > 0) {
                answerTypes.forEach(answerType => {
                    answerTypeDropdown.append(
                        `<option value="${answerType.answertypeid}">${answerType.answertypename}</option>` // Adjusted to match AnswerType entity
                    );
                    answerTypeDropdown.selectpicker('refresh');
                });
            } else {
                answerTypeDropdown.append('<option value="">No Answer Types Available</option>');
                answerTypeDropdown.selectpicker('refresh');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching answer types:", error);
            alert('Failed to load answer types. Please try again later.');
        }
    });
}

function fetchNextFormId() {
    const jwtToken = $('input[name="_csrf"]').val(); // Get token from hidden input
    $.ajax({
        url: '/api/forms/next-id',
        method: 'GET',
        headers: {
            'Authorization': jwtToken
        },
        success: function (response) {
            $('#formIdInput').val(response);
        },
        error: function (xhr, status, error) {
            toastr.error('Failed to fetch next form ID: ' + (xhr.responseJSON?.message || error));
            $('#formIdInput').val('FORM-1');
        }
    });
}

// Event listener for module dropdown change
$("#moduleDropdown").on('change', function () {
    const selectedModuleId = $(this).val();
    if (selectedModuleId) {
        fetchCharacteristicsByModule(selectedModuleId);
    } else {
        const characteristicDropdown = $("#characteristicDropdown");
        characteristicDropdown.empty();
        characteristicDropdown.append('<option value="">Select a module first</option>');
        characteristicDropdown.selectpicker('refresh');
    }
});