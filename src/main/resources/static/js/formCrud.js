function clearForm() {
    // Clear text inputs
    $("#formIdInput").val("");
    $("#editformId").val("");
    $("#titletext").val("");
    $("#textenglish").val("");
    $("#aliasname").val("");
    $("#comperiod").val("");
    $("#date_from").val("");

    // Reset dropdowns to default (empty or first option)
    $("#moduleDropdown").val("").selectpicker('refresh');
    $("#characteristicDropdown").val("").selectpicker('refresh');
    $("#subchar").val("").selectpicker('refresh');
    $("#recurence").val("").selectpicker('refresh');
    $("#startM").val("").selectpicker('refresh');
    let tbody = $('#formquestion_datatable tbody');
    tbody.empty();
    // Optionally clear any other dynamic UI elements related to questions
    // For example, if you have a table or list displaying questions:
    $("#questionsTable tbody").empty(); // Adjust selector based on your HTML
}
$(document).on('click', '#moveback', function () {
    clearForm();
    questionsList = []; // Clear questions list only on create


})


function fetchAllFormsWithDetails() {
    const jwtToken = $('input[name="_csrf"]').val();
    $.ajax({
        url: '/api/forms/all-details',
        method: 'GET',
        headers: {
            'Authorization': jwtToken
        },
        success: function (forms) {
            const tableBody = $('#form_datatable tbody');

            forms.forEach(form => {
                let row = `
                    <tr>
                        <td>FORM-${form.formId}</td>
                        <td>${form.formTitle}</td>
                        <td>${form.isActive ? 'Yes' : 'No'}</td>
                        <td class="text-center">
                            <a href="javascript:void(0)" data-toggle="tooltip" data-placement="bottom" 
                               id="editbtn" data-id="${form.formId}" title="Edit" 
                               class="text-success fa-size client_add_btn">
                                <i class="fa fa-pencil"></i>
                            </a>
                            <span data-toggle="modal" data-target="#all_question_preview">
                                <a href="javascript:void(0)" data-toggle="tooltip" data-placement="bottom" 
                                   title="Preview" id="viewform" data-id="${form.formId}" class="text-info fa-size">
                                    <i class="fa fa-eye"></i>
                                </a>
                            </span>
                            <span class="delete-user-alert">
                                <a href="javascript:void(0)" class="text-danger fa-size" data-toggle="tooltip" 
                                   data-placement="bottom" id="formdel" data-id="${form.formId}" title="Delete">
                                    <i class="fa fa-trash"></i>
                                </a>
                            </span>
                        </td>
                    </tr>`;
                tableBody.append(row);
            });

            // Reinitialize DataTable
            $('#form_datatable').DataTable({
                destroy: true,
                scrollX: true,
                "bAutoWidth": true,
                paging: true,
                "bLengthChange": false,
                "columnDefs": [{
                    "targets": 3,
                    "orderable": false
                }],
                "pageLength": 10,
                fixedColumns: {
                    rightColumns: 1,
                    leftColumns: 0
                },
                language: {
                    paginate: {
                        next: '<i class="fa fa-angle-double-right">',
                        previous: '<i class="fa fa-angle-double-left">'
                    }
                },
                "dom": '<"top"pif>rt<"clear">'
            });


        },
        error: function (xhr, status, error) {
            toastr.error('Failed to fetch forms: ' + (xhr.responseJSON?.message || error));
        }
    });
}
$(document).on('click', '#addbtn', function () {
    fetchNextFormId();
});

$("#saveForm").on('click', function (e) {
    e.preventDefault(); // Prevent default form submission if inside a form

    // Collect form metadata
    var formId = $("#editformId").val() ? $("#editformId").val().trim() : ""; // Check if formId exists
    console.log(formId);
    var titleText = $("#titletext").val() ? $("#titletext").val().trim() : "";
    var aliasName = $("#aliasname").val() ? $("#aliasname").val().trim() : "";
    var module = $("#moduleDropdown").val() || "";
    var characteristic = $("#characteristicDropdown").val() || "";
    var subCharacteristic = $("#subchar").val() || "";
    var recurrence = $("#recurence").val() || "";
    var startMonth = $("#startM").val() || "";
    var compliancePeriod = $("#comperiod").val() ? $("#comperiod").val().trim() : "";
    var effectiveDate = $("#date_from").val() ? $("#date_from").val().trim() : "";
    var textEnglish = $("#textenglish").val() ? $("#textenglish").val().trim() : "";
    var isActive = $('#active').is(':checked');

    // Validate form data
    if (!validateFormData({
        titleText, aliasName, module, characteristic, subCharacteristic,
        recurrence, startMonth, compliancePeriod, effectiveDate, textEnglish
    })) {
        return false; // Exit if validation fails
    }

    if (!questionsList || questionsList.length === 0) {
        toastr.error("Please add at least one question.");
        return false; // Stop execution
    }

    // Prepare payload
    let payload = {
        formId: formId ? parseInt(formId) : null, // Include formId if present, null otherwise
        formTitle: titleText,
        formDescription: textEnglish,
        aliasName: aliasName,
        module: module,
        characteristic: characteristic,
        subCharacteristic: subCharacteristic,
        recurrence: recurrence,
        startMonth: startMonth,
        compliancePeriod: compliancePeriod,
        effectiveDate: effectiveDate,
        isActive: isActive,
        questions: questionsList // Ensure questionsList is defined globally
    };

    const jwtToken = $('input[name="_csrf"]').val(); // Get token from hidden input
    console.log("Payload:", payload);

    // Determine URL and method based on whether formId exists
    const isUpdate = !!formId; // True if formId exists, false otherwise
    const url = isUpdate ? '/api/forms/update' : '/api/forms/create';
    const method = isUpdate ? 'PUT' : 'POST';

    // AJAX call with JWT header
    $.ajax({
        url: url,
        method: method,
        contentType: 'application/json',
        data: JSON.stringify(payload),
        headers: {
            'Authorization': jwtToken
        },
        beforeSend: function () {
            $('.preloader').show(); // Show loader before request starts
        },
        success: function (response) {
            $('.preloader').hide(); // Hide loader on success

            const successMessage = isUpdate ? 'Form updated successfully' : 'Form saved successfully';
            toastr.success(successMessage);
            clearForm();

            // Modified: Only clear questionsList on create, not update
            if (!isUpdate) {
                questionsList = []; // Clear questions list only on create
                fetchNextFormId(); // Update form ID only on create
            }

            // Modified: Ensure DataTable is updated correctly
            const dataTable = $('#formquestion_datatable').DataTable(); // Get DataTable instance
            dataTable.clear(); // Clear existing rows
            populateTable(); // Repopulate with updated data
            dataTable.draw(); // Redraw the table

            $('#editFormModal').removeClass('show'); // Close modal if open
            fetchAllFormsWithDetails(); // Refresh the forms list

            // Modified: Remove location.reload() and setTimeout as they're redundant with DataTable update
            // location.reload();
            // setTimeout(function () {}, 500);
        },
        error: function (xhr, status, error) {
            $('.preloader').hide(); // Hide loader on error

            const errorMessage = isUpdate ? 'Failed to update form' : 'Failed to save form';
            toastr.error(errorMessage + ': ' + (xhr.responseJSON?.message || error));
        }
    });
});
// Edit button click handler
$(document).on('click', '#editbtn', function () {
    const formId = $(this).data('id'); // Fix: Use 'id' instead of 'data-id'
    const jwtToken = $('input[name="_csrf"]').val();

    console.log("Form ID:", formId); // Debug: Check if formId is correctly retrieved

    if (!formId) {
        toastr.error('Form ID is undefined. Please check the button configuration.');
        return;
    }

    $.ajax({
        url: `/api/forms/get/${formId}`, // Ensure this matches your backend endpoint
        method: 'GET',
        headers: {
            'Authorization': jwtToken
        },
        success: function (data) {
            console.log(data)
            // Prefill form fields
            $('#formIdInput').val('FORM-' + data.formId);
            $('#editformId').val(data.formId);
            $('#titletext').val(data.formTitle);
            $('#textenglish').val(data.formDescription);
            $('#aliasname').val(data.aliasName);
            $('#comperiod').val(data.compliancePeriod);
            $('#date_from').val(data.effectiveDate);
            $('#active').prop('checked', data.isActive === true);
            setTimeout(function () {
                $('#moduleDropdown').val(data.module)
                $("#moduleDropdown").selectpicker('refresh');
            }, 500)
            fetchCharacteristicsByModule(data.module);

            setTimeout(function () {
                $('#characteristicDropdown').val(data.characteristic);
                $("#characteristicDropdown").selectpicker('refresh');
            }, 500)
            loadSubCharacteristics(data.characteristic);

            setTimeout(function () {
                $('#subchar').val(data.subCharacteristic).selectpicker('refresh');
                $("#subchar").selectpicker('refresh');
            }, 500)
            $('#recurence').val(data.recurrence).selectpicker('refresh');
            $('#startM').val(data.startMonth).selectpicker('refresh');

            // Populate questions table
            questionsList = data.questions || []; // Update global questionsList
            populateTable();

            // Show the modal
            $('#editFormModal').addClass('show');
            toastr.success('Form data loaded successfully');
        },
        error: function (xhr, status, error) {
            if (xhr.status === 404) {
                toastr.error('Form not found with ID: ' + formId);
            } else {
                toastr.error('Failed to fetch form data: ' + (xhr.responseJSON?.message || error));
            }
        }
    });
});

// Close modal
$('#closeEditModal').on('click', function () {
    $('#editFormModal').removeClass('show');
});
// Delete confirmation and AJAX call
$(document).on('click', '#formdel', function () {

    const formId = $(this).data('id'); // Fix: Use 'id' instead of 'data-id'
    console.log(formId);
    const jwtToken = $('input[name="_csrf"]').val();

    $.confirm({
        title: 'Delete Form..!',
        content: 'Please be sure before deleting the form',
        theme: 'material',
        icon: 'fa fa-warning',
        type: 'red',
        buttons: {
            delete: {
                text: 'Delete',
                btnClass: 'btn-red',
                action: function () {
                    $.ajax({
                        url: 'api/forms/delete/' + formId,
                        method: 'DELETE',
                        headers: {
                            'Authorization': jwtToken
                        },
                        success: function (response) {
                            toastr.success('Form deleted successfully');
                            fetchAllFormsWithDetails();
                            location.reload();
                        },
                        error: function (xhr, status, error) {
                            let errorMessage = 'Failed to delete form: ';
                            if (xhr.status === 400) {
                                errorMessage += 'User not authenticated.';
                            } else if (xhr.status === 404) {
                                errorMessage += xhr.responseJSON?.message || 'Form not found.';
                            } else {
                                errorMessage += xhr.responseJSON?.message || error;
                            }
                            toastr.error(errorMessage);
                        }
                    });
                }
            },
            close: function () {
                // Do nothing on cancel
            }
        }
    });
});