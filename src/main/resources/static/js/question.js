// Function to convert question type number to readable label
function getQuestionTypeLabel(type) {
    const questionTypes = {
        0: "No Answer Required",
        1: "Single Choice",
        2: "Multiple Choice",
        3: "Single Textbox",
        4: "Multiline Textbox",
        5: "Single Select Dropdown",
        6: "Multi Select Dropdown",
        7: "Date"
    };
    return questionTypes[type] || "N/A"; // Returns "N/A" if type is not found
}

// Function to populate the questions table
function populateTable() {
    let tbody = $('#formquestion_datatable tbody'); // Get table body
    tbody.empty(); // Clear existing rows

    // Loop through questionsList to create table rows
    questionsList.forEach((question, index) => {
        let row = `
            <tr>
                <td>${question.questionLabel || 'N/A'}</td> <!-- Question label -->
                <td>${question.questionText || 'N/A'}</td> <!-- Question text -->
                <td>${getQuestionTypeLabel(question.questionType)}</td> <!-- Question type label -->
                <td>${question.required ? 'Yes' : 'No'}</td> <!-- Required status -->
                <td class="text-center">
                    <!-- Edit button -->
                    <span data-toggle="modal" data-target=".addformquestion">
                        <a href="javascript:void(0)" 
                           data-toggle="tooltip" 
                           data-placement="bottom" 
                           data-index="${index}"
                           data-original-title="Edit"
                           id="queEditbtn"
                           class="text-success fa-size">
                            <i class="fa fa-pencil"></i>
                        </a>
                    </span>
                    <!-- Delete button -->
                    <span class="delete-user-alert">
                        <a href="javascript:void(0)" 
                           class="text-danger fa-size"
                           id="questionDelete" 
                           data-toggle="tooltip" 
                           data-index="${index}"
                           data-placement="bottom" 
                           data-original-title="Delete">
                            <i class="fa fa-trash"></i>
                        </a>
                    </span>
                </td>
            </tr>
        `;
        tbody.append(row); // Add row to table
    });
}

// Event handler for question type dropdown change
$("#answaretype").change(function () {
    const selectedValue = this.value; // Get selected value
    // console.log(selectedValue);
    // Hide all conditional sections initially
    $('.multiselectdata, .multichoicedata, .singlechoicedata, .singleselectdata, .hidetextvalidation, .showanswershouldbe, .hidedatevalidation, .noansdisplaynone').hide();

    if (!selectedValue) { // If no value selected, exit
        return;
    }

    // Show/hide sections based on selected question type
    if (this.value === '0') {
        $('.multiselectdata').hide();
        $('.multichoicedata').hide();
        $('.singlechoicedata').hide();
        $('.singleselectdata').hide();
        $('.hidetextvalidation').hide();
        $('.showanswershouldbe').hide();
        $('.hidedatevalidation').hide();
        $('.noansdisplaynone').hide();
    } else if (this.value === '1') {
        $('.singlechoicedata').show();
        $('.multichoicedata').hide();
        $('.singleselectdata').hide();
        $('.multiselectdata').hide();
        $('.hidetextvalidation').hide();
        $('.showanswershouldbe').hide();
        $('.hidedatevalidation').hide();
        $('.noansdisplaynone').show();
    } else if (this.value === '2') {
        $('.multichoicedata').show();
        $('.singlechoicedata').hide();
        $('.singleselectdata').hide();
        $('.multiselectdata').hide();
        $('.hidetextvalidation').hide();
        $('.showanswershouldbe').hide();
        $('.hidedatevalidation').hide();
        $('.noansdisplaynone').show();
    } else if (this.value === '3') {
        $('.multichoicedata').hide();
        $('.singlechoicedata').hide();
        $('.singleselectdata').hide();
        $('.multiselectdata').hide();
        $('.hidetextvalidation').show();
        $('.showanswershouldbe').hide();
   //     $('.&pageLengthhidedatevalidation').hide();
        $('.noansdisplaynone').show();
    } else if (this.value === '4') {
        $('.multichoicedata').hide();
        $('.singlechoicedata').hide();
        $('.singleselectdata').hide();
        $('.multiselectdata').hide();
        $('.hidetextvalidation').show();
        $('.showanswershouldbe').hide();
        $('.hidedatevalidation').hide();
        $('.noansdisplaynone').show();
    } else if (this.value === '5') {
        $('.singleselectdata').show();
        $('.multichoicedata').hide();
        $('.singlechoicedata').hide();
        $('.multiselectdata').hide();
        $('.hidetextvalidation').hide();
        $('.showanswershouldbe').hide();
        $('.hidedatevalidation').hide();
        $('.noansdisplaynone').show();
    } else if (this.value === '6') {
        $('.multiselectdata').show();
        $('.multichoicedata').hide();
        $('.singlechoicedata').hide();
        $('.singleselectdata').hide();
        $('.hidetextvalidation').hide();
        $('.showanswershouldbe').hide();
        $('.hidedatevalidation').hide();
        $('.noansdisplaynone').show();
    } else if (this.value === '7') {
        $('.multiselectdata').hide();
        $('.multichoicedata').hide();
        $('.singlechoicedata').hide();
        $('.singleselectdata').hide();
        $('.hidetextvalidation').hide();
        $('.showanswershouldbe').hide();
        $('.hidedatevalidation').show();
        $('.noansdisplaynone').show();
    }

    // Added to handle table visibility and initial row
    const optionTables = {
        '1': { selector: '.singlechoicetable', addClass: 'singlechoiceadd', removeClass: 'singlechoiceremove' },
        '2': { selector: '.multichoicetable', addClass: 'multichoiceadd', removeClass: 'multichoiceremove' },
        '5': { selector: '.singleselecttable', addClass: 'singleselectadd', removeClass: 'singleselectremove' },
        '6': { selector: '.multiselecttable', addClass: 'multiselectadd', removeClass: 'multiselectremove' }
    };

    // Hide and clear all option tables initially
    Object.values(optionTables).forEach(table => {
        $(table.selector).hide();
        $(table.selector).empty();
    });

    // Add initial row if applicable
    if (optionTables[selectedValue]) {
        addInitialRow(optionTables[selectedValue]);
    }
});

// Second change handler for charformat visibility
$("#answaretype").change(function () {
    if (this.value === '1') {
        $('.charformat').css("visibility", "visible"); // Show charformat for single choice
    } else if (this.value === '2') {
        $('.charformat').css("visibility", "hidden");
    } else if (this.value === '3') {
        $('.charformat').css("visibility", "hidden");
    } else if (this.value === '4') {
        $('.charformat').css("visibility", "hidden");
    }
});

// Validation checkbox change handler
$('#validatans').change(function () {
    if (this.checked) {
        $(".showanswershouldbe").show(); // Show validation options
    } else {
        $(".showanswershouldbe").hide(); // Hide validation options
    }
});

// Global array to store questions
let questionsList = [];

// Initialize DataTable
$('#formquestion_datatable').DataTable({
    paging: true,
    "bLengthChange": false,
    "columnDefs": [{
        "targets": 4,
        "orderable": false // Disable sorting on action column
    }],
    "pageLength": 10,
    language: {
        paginate: {
            next: '<i class="fa fa-angle-double-right">',
            previous: '<i class="fa fa-angle-double-left">'
        }
    },
    dom: // Custom layout for DataTable
        "<'row'<'col-xl-6 col-lg-6 col-sm-5'pi><'col-xl-5 col-lg-4 col-sm-5'f><'col-xl-1 col-lg-2 col-sm-2 colmspadding text-left'<'toolbar1'>>>" +
        "<'row'<'col-md-12'tr>>",
    fnInitComplete: function () { // Add buttons after table initialization
        $('div.toolbar1').html('<a href="javascript:void(0)" data-toggle="modal" data-target=".formsorting" class="btn btn-warning btn-padding mb-1 mr-1"><i class="fa fa-sort"></i></a><a href="javascript:void(0)" data-toggle="modal" data-target=".addformquestion" class="btn btn-warning btn-padding mb-1"><i class="fa fa-plus"></i> Add</a>');
    },
});

let index;

// Function to save edited question
function saveEditedQuestion(index) {
    const questionLabel = $('input[placeholder="Enter Your Question Label in English"]').val().trim();
    const questionName = $('input[placeholder="Enter Your Question in English"]').val().trim();
    const answerTypeId = $('#answaretype').val();

    if (!questionLabel || !questionName || !answerTypeId) {
        toastr.error('Please fill all required fields Update');
        return false;
    }

    let tableSelector;
    switch (answerTypeId) {
        case '1': tableSelector = '.singlechoicetable'; break;
        case '2': tableSelector = '.multichoicetable'; break;
        case '5': tableSelector = '.singleselecttable'; break;
        case '6': tableSelector = '.multiselecttable'; break;
        default: tableSelector = null;
    }

    let options = [];
    if (tableSelector) {
        options = collectOptions(tableSelector);
        if (options.length < 2) {
            toastr.error('Please add at least two options.');
            return false;
        }
    }

    // Create updated question object
    const updatedQuestion = {
        questionLabel: questionLabel,
        questionText: questionName,
        description: $('#description').val().trim(),
        questionType: answerTypeId,
        required: $('#reqans').is(':checked'),
        validation: $('#validatans').is(':checked') ? $('#write').val() : null,
        options: options
    };
	
	function clearForm() {
        $('input[placeholder="Enter Your Question Label in English"]').val('');
        $('input[placeholder="Enter Your Question in English"]').val('');
        $('#answaretype').val('');
        $('#reqans').prop('checked', false);
        $('#description').val('');
        $('#validatans').prop('checked', false);
        $('.singlechoicetable').empty();
        $('.multichoicetable').empty();
        $('.singleselecttable').empty();
        $('.multiselecttable').empty();
        $('#answaretype').trigger('change');
    }

    questionsList[index] = updatedQuestion; // Update question in list
    populateTable(); // Refresh table
    $('.addformquestion').modal('hide'); // Hide modal
	index = undefined;
    clearForm(); // Clear form
    toastr.success('Question updated successfully');
	return;
}

$(document).on('click', '#queEditbtn', function () {
    index = $(this).data('index');
    console.log(index);
    editQuestion(index);
});

// Save button handler
$('#QueSave').on('click', function (e) {
    e.preventDefault();
	 console.log(index)
	if(index!==undefined){
		saveEditedQuestion(index);
		return;
	}else{
		const questionLabel = $('input[placeholder="Enter Your Question Label in English"]').val().trim();
		const questionName = $('input[placeholder="Enter Your Question in English"]').val().trim();
		const answerType = $('#answaretype option:selected').text();
		const answerTypeId = $('#answaretype').val();

		// Validate required fields
		if (!questionLabel || !questionName || !answerType) {
		    toastr.error('Please fill all required fields ADD');
		    return false;
		}

		// Determine table selector based on question type
		let tableSelector;
		switch (answerTypeId) {
		    case '1': tableSelector = '.singlechoicetable'; break;
		    case '2': tableSelector = '.multichoicetable'; break;
		    case '5': tableSelector = '.singleselecttable'; break;
		    case '6': tableSelector = '.multiselecttable'; break;
		    default: tableSelector = null;
		}

		let options = [];
		if (tableSelector) {
		    options = collectOptions(tableSelector); // Collect options from table
		    if (options.length < 2) { // Validate minimum options
		        toastr.error('Please add at least two options.');
		        return false;
		    }
		}

		// Function to collect options from table
		function collectOptions(tableSelector) {
		    let options = [];
		    $(tableSelector)
		        .find("tr")
		        .each(function (index) {
		            let optionValue = $(this).find("input").val().trim();
		            if (optionValue) {
		                options.push({
		                    optionText: optionValue,
		                    position: index + 1
		                });
		            }
		        });
		    return options;
		}

		// Create question object
		let questionData = {
		    questionLabel: questionLabel,
		    questionText: questionName,
		    description: $('#description').val().trim(),
		    questionType: answerTypeId,
		    required: $('#reqans').is(':checked'),
		    validation: $('#validatans').is(':checked') ? $('#write').val() : null,
		    options: options
		};

		questionsList.push(questionData); // Add to list
		populateTable(); // Update table
		toastr.success('Question saved successfully');
		console.log('Saved Question Data:', questionData);
		return true;
	}
    // Get form values

});


	// Single choice table row removal
$(".singlechoicetable").on("click", ".singlechoiceremove", function () {
    if ($('.singlechoicetable tr').length > 1) {
        $(this).closest("tr").remove();
        if ($('.singlechoicetable tr').length === 1) {
            $('.singlechoicetable .permanent-row .remove-cell a').css('display', 'none');
        }
    }
});

// Add new row to single choice table
$(document).on("click", ".singlechoiceadd", function () {
    var addNewRow = `
        <tr>
            <td class='text-center border-0' width='5%'><i class='fa fa-arrow-right' aria-hidden='true'></i></td>
            <td class="border-0 p-1">
                <div class="form-group mb-0">
                    <input type="text" class="form-control" id="answerInput" maxlength="15" 
                           pattern="[^A-Za-z]*" placeholder="Enter an answer choice in English" 
                           title="Maximum 15 characters and no alphabets allowed">
                    <small id="errorText" style="color: red;"></small>
                </div>
            </td>
            <td class='text-center border-0 p-0' width='3%'><a href='javascript:void(0)' class='singlechoiceadd'><i class='fa fa-plus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i></a></td>
            <td class='text-center border-0 p-0 remove-cell' width='3%'><a href='javascript:void(0)' class='singlechoiceremove'><i class='fa fa-minus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i></a></td>
        </tr>`;
    $('table.singlechoicetable').append(addNewRow);
    $('.singlechoicetable .permanent-row .remove-cell a').css('display', 'none'); // Hide remove button on permanent row
    if ($('.singlechoicetable tr').length > 1) {
        $('.singlechoicetable .permanent-row .remove-cell a').css('display', 'none');
    }
});

// Multiple choice table row removal
$(".multichoicetable").on("click", ".multichoiceremove", function () {
    if ($('.multichoicetable tr').length > 1) {
        $(this).closest("tr").remove();
        if ($('.multichoicetable tr').length === 1) {
            $('.multichoicetable .permanent-row .remove-cell a').css('display', 'none');
        }
    }
});

// Add new row to multiple choice table
$(document).on("click", ".multichoiceadd", function () {
    var addNewRow = `
        <tr>
            <td class='text-center border-0' width='5%'><i class='fa fa-arrow-right' aria-hidden='true'></i></td>
            <td class='border-0 p-1'><div class='form-group mb-0'><input type='text' maxlength="15" pattern="[^A-Za-z]*" class='form-control' placeholder='Enter an answer choice in English'></div></td>
            <td class='text-center border-0 p-0' width='3%'><a href='javascript:void(0)' class='multichoiceadd'><i class='fa fa-plus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i></a></td>
            <td class='text-center border-0 p-0 remove-cell' width='3%'><a href='javascript:void(0)' class='multichoiceremove'><i class='fa fa-minus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i></a></td>
        </tr>`;
    $('table.multichoicetable').append(addNewRow);
    $('.multichoicetable .permanent-row .remove-cell a').css('display', 'none'); // Hide remove button on permanent row
    if ($('.multichoicetable tr').length > 1) {
        $('.multichoicetable .permanent-row .remove-cell a').css('display', 'none');
    }
});

// Single select table row removal
$(".singleselecttable").on("click", ".singleselectremove", function () {
    if ($('.singleselecttable tr').length > 1) {
        $(this).closest("tr").remove();
        if ($('.singleselecttable tr').length === 1) {
            $('.singleselecttable .permanent-row .remove-cell a').css('display', 'none');
        }
    }
});

// Add new row to single select table
$(document).on("click", ".singleselectadd", function () {
    var addNewRow = `
        <tr>
            <td class='text-center border-0' width='5%'><i class='fa fa-arrow-right' aria-hidden='true'></i></td>
            <td class='border-0 p-1'><div class='form-group mb-0'><input type='text' maxlength="15" pattern="[^A-Za-z]*" class='form-control' placeholder='Enter an answer choice in English'></div></td>
            <td class='text-center border-0 p-0' width='3%'><a href='javascript:void(0)' class='singleselectadd'><i class='fa fa-plus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i></a></td>
            <td class='text-center border-0 p-0 remove-cell' width='3%'><a href='javascript:void(0)' class='singleselectremove'><i class='fa fa-minus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i></a></td>
        </tr>`;
    $('table.singleselecttable').append(addNewRow);
    $('.singleselecttable .permanent-row .remove-cell a').css('display', 'none'); // Hide remove button on permanent row
    if ($('.singleselecttable tr').length > 1) {
        $('.singleselecttable .permanent-row .remove-cell a').css('display', 'none');
    }
});

// Multi select table row removal
$(".multiselecttable").on("click", ".multiselectremove", function () {
    if ($('.multiselecttable tr').length > 1) {
        $(this).closest("tr").remove();
        if ($('.multiselecttable tr').length === 1) {
            $('.multiselecttable .permanent-row .remove-cell a').css('display', 'none');
        }
    }
});

// Add new row to multi select table
$(document).on("click", ".multiselectadd", function () {
    var addNewRow = `
        <tr>
            <td class='text-center border-0' width='5%'><i class='fa fa-arrow-right' aria-hidden='true'></i></td>
            <td class='border-0 p-1'><div class='form-group mb-0'><input type='text' maxlength="15" pattern="[^A-Za-z]*" class='form-control' placeholder='Enter an answer choice in English'></div></td>
            <td class='text-center border-0 p-0' width='3%'><a href='javascript:void(0)' class='multiselectadd'><i class='fa fa-plus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i></a></td>
            <td class='text-center border-0 p-0 remove-cell' width='3%'><a href='javascript:void(0)' class='multiselectremove'><i class='fa fa-minus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i></a></td>
        </tr>`;
    $('table.multiselecttable').append(addNewRow);
    $('.multiselecttable .permanent-row .remove-cell a').css('display', 'none'); // Hide remove button on permanent row
    if ($('.multiselecttable tr').length > 1) {
        $('.multiselecttable .permanent-row .remove-cell a').css('display', 'none');
    }
});

// Delete question handler
$(document).on('click', '#questionDelete', function () {
    let index = $(this).data('index');
    console.log("Index on click:", index);

    // Show confirmation dialog
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
                    console.log("Index inside delete action:", index);
                    if (index !== undefined) {
                        questionsList.splice(index, 1); // Remove question
                        populateTable(); // Update table
                    } else {
                        console.error("Index is undefined in delete action!");
                    }
                }
            },
            close: function () {}
        }
    });
});

function editQuestion(index) {
    const question = questionsList[index];
    console.log(question);

    $('input[placeholder="Enter Your Question Label in English"]').val(question.questionLabel || '');
    $('input[placeholder="Enter Your Question in English"]').val(question.questionText || '');
    $('#answaretype').val(question.questionType);
    $('#reqans').prop('checked', question.required);
    $('#description').val(question.description || '');

   const validationValue = question.validation;
   if(validationValue){
	$('.showanswershouldbe').show();
   }
   // Check or uncheck the checkbox based on validationValue
   $('#validatans').prop('checked', validationValue !== null && validationValue !== '');
	
   // If the checkbox is checked, set the value in #write and trigger change
   if ($('#validatans').is(':checked')) {
	$('.showanswershouldbe').show();
	refreshValidationDropdown();
    $("#write").val(validationValue).trigger("change");
   } else {
       $("#write").val('').trigger("change"); // Clear if unchecked
   }

    // Function to refresh validation dropdown
    function refreshValidationDropdown() {
        if ($('#validatans').is(':checked') && validationValue !== null && validationValue !== '') {
            setTimeout(function () {
                $('.answercombo').val(validationValue);
                $(".answercombo").selectpicker('refresh');
            }, 500);
            $('.showanswershouldbe').show();
        } else {
            $('.answercombo').val('');
            $(".answercombo").selectpicker('refresh');
            $('.showanswershouldbe').hide();
        }
    }

    refreshValidationDropdown();

    // Update validation dropdown on checkbox change
    $('#validatans').off('change').on('change', function() {
        refreshValidationDropdown();
    });

    $('#answaretype').trigger('change'); // Update UI based on question type

    const optionTables = {
        '1': '.singlechoicetable',
        '2': '.multichoicetable',
        '5': '.singleselecttable',
        '6': '.multiselecttable'
    };

    // Added to handle table visibility and initial row when no options exist
    if (optionTables[question.questionType]) {
        const tableSelector = optionTables[question.questionType];
        $(tableSelector).empty();

        if (question.options && question.options.length > 0) {
            // Populate existing options
            question.options.forEach((option, idx) => {
                const isPermanent = idx === 0 ? 'permanent-row' : '';
                const removeDisplay = idx === 0 ? 'display: none;' : '';
                const row = `
                    <tr class="${isPermanent}">
                        <td class='text-center border-0' width='5%'><i class='fa fa-arrow-right' aria-hidden='true'></i></td>
                        <td class='border-0 p-1'><div class='form-group mb-0'><input type='text' class='form-control' value='${option.optionText}' placeholder='Enter an answer choice in English'></div></td>
                        <td class='text-center border-0 p-0' width='3%'><a href='javascript:void(0)' class='${tableSelector.slice(1, -5)}add'><i class='fa fa-plus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i></a></td>
                        <td class='text-center border-0 p-0 remove-cell' width='3%'><a href='javascript:void(0)' class='${tableSelector.slice(1, -5)}remove' style='${removeDisplay}'><i class='fa fa-minus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i></a></td>
                    </tr>`;
                $(tableSelector).append(row);
            });
        } else {
            // Added to add initial row when no options exist
            addInitialRow({
                selector: tableSelector,
                addClass: tableSelector.slice(1, -5) + 'add',
                removeClass: tableSelector.slice(1, -5) + 'remove'
            });
        }
    }

    $('.addformquestion').modal('show');
	index = undefined;
	return true; // Show edit modal
}



// Function to collect options from table (used in save)
function collectOptions(tableSelector) {
    let options = [];
    $(tableSelector).find("tr").each(function (index) {
        let optionValue = $(this).find("input").val().trim();
        if (optionValue) {
            options.push({
                optionText: optionValue,
                position: index + 1
            });
        }
    });
    return options;
}

// Handler for add new question button
$(document).on('click', '#addque', function() {
    clearQuestion();
});

// Handler to clear form after save
$(document).on('click', '#QueSave', function() {
    clearQuestion();
});
$(document).on('click', '#clearque', function() {
    clearQuestion();
});

// Function to clear question form
function clearQuestion() {
    // Clear all form fields
	index = undefined;
	$(this).removeAttr('data-index'); // OR $(this).data('index', '');
    $('input[placeholder="Enter Your Question Label in English"]').val('');
    $('input[placeholder="Enter Your Question in English"]').val('');
    $('#description').val('');
    $('#answaretype').val('');
    $('#answaretype').trigger('change');
    $('#reqans').prop('checked', false);
    $('#validatans').prop('checked', false);
    $('.answercombo').val('');
    $(".answercombo").selectpicker('refresh');
    $('.showanswershouldbe').hide();
    
    const optionTables = [
        '.singlechoicetable',
        '.multichoicetable',
        '.singleselecttable',
        '.multiselecttable'
    ];
    
    // Clear all option tables
    optionTables.forEach(tableSelector => {
        $(tableSelector).empty();
    });

    // Added to hide tables and show modal
    const optionTablesObj = {
        '1': '.singlechoicetable',
        '2': '.multichoicetable',
        '5': '.singleselecttable',
        '6': '.multiselecttable'
    };
    Object.values(optionTablesObj).forEach(tableSelector => {
        $(tableSelector).hide(); // Hide all option tables
    });
    $('.addformquestion').modal('show'); // Show modal for new question
}

// Function to handle initial row creation with permanent row
function addInitialRow(tableInfo) {
    const { selector, addClass, removeClass } = tableInfo;
    $(selector).show(); // Show the table
    $(selector).empty(); // Clear existing rows
    
    // Create new row template with permanent first row
    const newRow = `
        <tr class="permanent-row">
            <td class='text-center border-0' width='5%'><i class='fa fa-arrow-right' aria-hidden='true'></i></td>
            <td class='border-0 p-1'>
                <div class='form-group mb-0'>
                    <input type='text' class='form-control' maxlength="15" pattern="[^A-Za-z]*" 
                           placeholder='Enter an answer choice in English'>
                </div>
            </td>
            <td class='text-center border-0 p-0' width='3%'>
                <a href='javascript:void(0)' class='${addClass}'>
                    <i class='fa fa-plus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i>
                </a>
            </td>
            <td class='text-center border-0 p-0 remove-cell' width='3%'>
                <a href='javascript:void(0)' class='${removeClass}' style='display: none;'>
                    <i class='fa fa-minus-square-o font_20 m-t-5 text-default' aria-hidden='true'></i>
                </a>
            </td>
        </tr>`;
    $(selector).append(newRow); // Add row to table
}