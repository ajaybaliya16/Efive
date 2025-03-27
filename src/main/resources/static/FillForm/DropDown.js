$(document).ready(function () {
    fetchAllFormsWithDetails();


    $(document).on('click', '#searchbtn', function () {
        const formId = $("#FormDrop").val();
        if (!formId || formId === "") {
            toastr.error("Please select a valid form.");
            return;
        }
        $('.showformfill').slideDown();
        formtable.draw();

        const jwtToken = $('input[name="_csrf"]').val();

        $.ajax({
            url: `/api/forms/get/${formId}`,
            method: 'GET',
            headers: {
                'Authorization': jwtToken
            },
            success: function (data) {
                $('#ansTitle').text(data.formTitle || 'N/A');
                $('#ansDesc').text(data.formDescription || 'N/A');

                const questionsContainer = $('#questionsContainer');
                questionsContainer.empty();

                (data.questions || []).forEach((question, index) => {
                    const questionHtml = generateQuestionCard(question, index + 1);
                    questionsContainer.append(questionHtml);
                });

                // Refresh selectpickers and initialize datepickers after appending content
                $('.selectpicker').selectpicker('refresh');
                initializeDatepickers(); // Moved here to ensure datepickers work on new content
                toastr.success('Form is Ready, Fill the Questions');
            },
            error: function (xhr, status, error) {
                if (xhr.status === 404) {
                    toastr.error('Form not found with ID: ' + formId);
                } else {
                    toastr.error('Failed to fetch form: ' + (xhr.responseJSON?.message || error));
                }
            }
        });
    });

    function generateQuestionCard(question, questionNumber) {
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

        const type = parseInt(question.questionType, 10);
        const isRequired = question.required ? '<span class="text-danger">*</span>' : '';
        let answerHtml = '';

        switch (type) {
            case 1: // Single Choice (Radio Buttons)
                answerHtml = `
                    <div class="row pl-2 pr-2">
                        ${(question.options || []).map((option, idx) => `
                            <div class="col-xl-3 col-lg-3 col-sm-3 col-xs-12 colmspadding">
                                <div class="custom-control custom-radio" data-option-id="${option.optionId}">
                                    <input type="radio" id="choice${question.questionId}_${idx}" 
                                           name="choice${question.questionId}" 
                                           class="custom-control-input" value="${option.optionId}">
                                    <label class="custom-control-label font-weight-300 m-t-5" 
                                           for="choice${question.questionId}_${idx}">
                                        ${option.optionText}
                                    </label>
                                </div>
                            </div>
                        `).join('')}
                    </div>`;
                break;

            case 2: // Multiple Choice (Checkboxes)
                answerHtml = `
                    <div class="row pl-2 pr-2">
                        ${(question.options || []).map((option, idx) => `
                            <div class="col-xl-3 col-lg-3 col-sm-3 col-xs-12 colmspadding">
                                <div class="custom-control custom-checkbox" data-option-id="${option.optionId}">
                                    <input type="checkbox" class="custom-control-input" 
                                           id="choiceckbox${question.questionId}_${idx}" 
                                           value="${option.optionId}">
                                    <label class="custom-control-label font-weight-300 m-t-5" 
                                           for="choiceckbox${question.questionId}_${idx}">
                                        ${option.optionText}
                                    </label>
                                </div>
                            </div>
                        `).join('')}
                    </div>`;
                break;

            case 3: // Single Textbox
                answerHtml = `<div class="row pl-2 pr-2">
                    <div class="col-xl-7 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                        <input type="text" class="form-control" placeholder="Enter Your Answer" 
                               data-question-id="${question.questionId}">
                    </div>
                </div>`;
                break;

            case 4: // Multiline Textbox
                answerHtml = `<div class="row pl-2 pr-2">
                    <div class="col-xl-7 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                        <textarea class="form-control textareasize" placeholder="Enter Your Answer" 
                                  data-question-id="${question.questionId}"></textarea>
                    </div>
                </div>`;
                break;

            case 5: // Single Select Dropdown
                answerHtml = `
                    <div class="row pl-2 pr-2">
                        <div class="col-xl-7 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                            <select class="selectpicker" id="singleSelect" data-style="lineheight12 bg-transfer" 
                                    data-live-search="true" data-question-id="${question.questionId}">
                                <option value="" selected="selected">Select</option>
                                ${(question.options || []).map(option => `
                                    <option value="${option.optionId}">${option.optionText}</option>
                                `).join('')}
                            </select>
                        </div>
                    </div>`;
                break;

            case 6: // Multi Select Dropdown
                answerHtml = `
                    <div class="row pl-2 pr-2">
                        <div class="col-xl-7 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                            <select class="selectpicker" multiple data-selected-text-format="count" 
                                    data-style="btn-light bg-transfer" data-actions-box="true" 
                                    data-question-id="${question.questionId}">
                                ${(question.options || []).map(option => `
                                    <option value="${option.optionId}">${option.optionText}</option>
                                `).join('')}
                            </select>
                        </div>
                    </div>`;
                break;

            case 7: // Date
                answerHtml = `
                    <div class="row pl-2 pr-2">
                        <div class="col-xl-3 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                            <div class="input-group date">
                                <input type="text" class="form-control datepicker" placeholder="dd/mm/yyyy" readonly
                                       id="allpreview_date${question.questionId}" 
                                       data-question-id="${question.questionId}">
                                <span class="input-group-addon inputgroups">
                                    <i class="mdi mdi-calendar"></i>
                                </span>
                            </div>
                        </div>
                    </div>`;
                break;

            case 0: // No Answer Required
            default:
                answerHtml = '<p class="mb-0">No answer input required.</p>';
                break;
        }
		return `
		    <div class="card mb-2 queshadow" 
		         data-question-id="${question.questionId}" 
		         data-question-type="${question.questionType}" 
		         data-validation="${question.validation || ''}">
		        <div class="card-body">
		            <div class="row pl-2 pr-2">
		                <div class="col-xl-1 col-lg-1 col-sm-2 col-xs-12 colmspadding">
		                    <span class="question">Q : ${questionNumber}</span>
		                </div>
		                <div class="col-xl-11 col-lg-11 col-sm-10 col-xs-12 colmspadding">
		                    <div class="form-group mb-0 text-justify">
		                        <p class="font-weight-700 mb-1 text-justify">${isRequired} ${question.questionText || 'N/A'}</p>
		                        <p class="mb-1">${question.description || 'N/A'}</p>
		                    </div>
		                    <div class="form-group mb-0">${answerHtml}</div>
		                </div>
		            </div>
		        </div>
		    </div>`;
    }

    // Function to initialize datepickers
    function initializeDatepickers() {
        $('.datepicker').datepicker({
            dateFormat: 'dd/mm/yy', // Matches your placeholder format
            changeMonth: true,      // Optional: month dropdown
            changeYear: true,       // Optional: year dropdown
            showOn: 'focus'         // Show on focus
        }).on('focus', function() {
            $(this).datepicker('show'); // Ensure it shows on focus
        });
    }

    // Initial call for any existing datepickers
    initializeDatepickers();
});


function fetchAllFormsWithDetails() {
    const jwtToken = $('input[name="_csrf"]').val();
    $.ajax({
        url: '/api/forms/available',
        method: 'GET',
        headers: {
            'Authorization': jwtToken
        },
        success: function (forms) {
            const moduleDropdown = $("#FormDrop");
            if (forms && forms.length > 0) {
                forms.forEach(module => {
                    moduleDropdown.append(
                        `<option value="${module.formId}">${module.formTitle}</option>`
                    );
                });
            } else {
                moduleDropdown.append('<option value="">No Forms Available</option>');
            }
            moduleDropdown.selectpicker('refresh');
        },
        error: function (xhr, status, error) {
            console.error("Error fetching forms:", error);
            alert('Failed to load forms. Please try again later.');
        }
    });
}