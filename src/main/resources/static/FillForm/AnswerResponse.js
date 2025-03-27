$(document).ready(function () {

$(document).on('click', '#submitFormBtn', function () {
    const formId = $("#FormDrop").val();
    const jwtToken = $('input[name="_csrf"]').val();
    const responses = collectFormResponses();

    if (!validateResponses(responses)) {
        toastr.error("Please fill all required fields before submitting.");
        return;
    }

    const submissionData = {
        formId: formId,
        answers: responses.map(response => ({
            questionId: response.questionId,
            answerText: Array.isArray(response.answerText) ? response.answerText.join(", ") : response.answerText,
            answerTypeId: response.answerTypeId,
            optionIds: response.optionIds
        }))
    };

    toastr.info('Submitting form data...');
    $.ajax({
        url: '/api/forms/ans/submit',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(submissionData),
        headers: {
            'Authorization': jwtToken
        },
        success: function (response) {
            toastr.success('Form submitted successfully!');
            resetForm();
			$('#FormDrop').empty(); 

			$("#FormDrop").selectpicker('refresh');
            fetchAllFormsWithDetails();
        },
        error: function (xhr, status, error) {
            toastr.error('Failed to submit form: ' + (xhr.responseJSON?.message || error));
        }
    });
});

function collectFormResponses() {
    const responses = [];
    const questionCards = $('#questionsContainer .card');

    if (questionCards.length === 0) {
        toastr.warning('No questions found in the form.');
        return responses;
    }

    questionCards.each(function () {
        const questionCard = $(this);
        const questionId = questionCard.data('question-id');
        const questionLabel = questionCard.find('.font-weight-700').text().replace('*', '').trim();
        const isRequired = questionCard.find('.text-danger').length > 0;
        const validation = questionCard.data('validation');
        let answerText = null;
        let answerTypeId = null;
        let optionIds = null;

        console.log(`Question ID: ${questionId}, Validation: ${validation}`);

        if (questionCard.find('input[type="radio"]').length) {
            answerTypeId = 1;
            const selectedRadio = questionCard.find('input[type="radio"]:checked');
            if (selectedRadio.length) {
                answerText = selectedRadio.next('label').text().trim();
                optionIds = [selectedRadio.val()];
            }
        } else if (questionCard.find('input[type="checkbox"]').length) {
            answerTypeId = 2;
            answerText = [];
            const checkedBoxes = questionCard.find('input[type="checkbox"]:checked');
            if (checkedBoxes.length) {
                checkedBoxes.each(function () {
                    answerText.push($(this).next('label').text().trim());
                });
                optionIds = checkedBoxes.map(function () { return $(this).val(); }).get();
            }
        } else if (questionCard.find('.selectpicker').length) {
            answerTypeId = questionCard.find('.selectpicker').attr('multiple') ? 6 : 5;
            const selectedValues = questionCard.find('.selectpicker').val();
            if (selectedValues) {
                if (Array.isArray(selectedValues)) {
                    answerText = selectedValues.map(val =>
                        questionCard.find(`option[value="${val}"]`).text()
                    );
                    optionIds = selectedValues;
                } else {
                    answerText = questionCard.find(`option[value="${selectedValues}"]`).text();
                    optionIds = [selectedValues];
                }
            }
        } else if (questionCard.find('input[type="text"]').length) {
            const textInput = questionCard.find('input[type="text"]');
            const inputId = textInput.attr('id');
            answerTypeId = (inputId && inputId.includes('date')) ? 7 : 3;
            answerText = textInput.val().trim();
        } else if (questionCard.find('textarea').length) {
            answerTypeId = 4;
            answerText = questionCard.find('textarea').val().trim();
        }

        responses.push({
            questionId: questionId,
            questionLabel: questionLabel,
            isRequired: isRequired,
            answerText: answerText,
            answerTypeId: answerTypeId,
            optionIds: optionIds,
            validation: validation
        });
    });
    return responses;
}

function resetForm() {
    $('#FormDrop').val('').selectpicker('refresh');
    $('.showformfill').hide();
    $('#questionsContainer').empty();
    $('#ansTitle').text('');
    $('#ansDesc').text('');
}

$('#resetbtn').on('click', function (e) {
    e.preventDefault();
    $('#FormDrop').val('').selectpicker('refresh');
    $('.showformfill').hide();
    $('#questionsContainer').find('input[type="text"]').val('');
    $('#questionsContainer').find('textarea').val('');
    $('#questionsContainer').find('input[type="radio"]').prop('checked', false);
    $('#questionsContainer').find('input[type="checkbox"]').prop('checked', false);
    $('#questionsContainer').find('.selectpicker').val('').selectpicker('refresh');
    toastr.success('Form has been reset successfully!');
});

// Modified validateResponses function
function validateResponses(responses) {
    let isValid = true;
    if (responses.length === 0) {
        toastr.error('No responses collected. Please ensure the form has questions.');
        return false;
    }

    // --- Start of Change ---
    // Collect all validation errors in an array instead of returning early
    const validationErrors = [];

    responses.forEach(response => {
        // Check for required fields
        if (response.isRequired && (!response.answerText || (Array.isArray(response.answerText) && response.answerText.length === 0))) {
            validationErrors.push(`Please provide an answer for required question: "${response.questionLabel}"`);
            isValid = false;
        }

        // Validate Single Textbox (3) and Multiline Textbox (4) with validation
        if ((response.answerTypeId === 3 || response.answerTypeId === 4) && response.answerText && response.validation) {
            const text = response.answerText;
            let regex;
            switch (response.validation) {
                case 0: // All Character (no restriction)
                    break;
                case 1: // Only Character (letters and spaces, no numbers or special chars)
                    regex = /^[A-Za-z\s]*$/;
                    if (!regex.test(text)) {
                        toastr.warning(`"${response.questionLabel}" must contain only letters and spaces.`);
                        isValid = false;
                    }
                    break;
                case 2: // Only Alphabet (letters only, no spaces or special chars)
                    regex = /^[A-Za-z]*$/;
                    if (!regex.test(text)) {
                        toastr.warning(`"${response.questionLabel}" must contain only letters.`);
                        isValid = false;
                    }
                    break;
                case 3: // Alphabet & Number (alphanumeric only)
                    regex = /^[A-Za-z0-9\s]*$/;
                    if (!regex.test(text)) {
                        toastr.warning(`"${response.questionLabel}" must contain only letters and numbers.`);
                        isValid = false;
                    }
                    break;
                case "4": // Only Number (numeric only)
                    regex = /^[0-9]*$/;
                    if (!regex.test(text)) {
                        validationErrors.push(`"${response.questionLabel}" must contain only numbers.`);
                        isValid = false;
                    }
                    break;
            }
        }
    });

   /* // Display all collected errors at once
    if (validationErrors.length > 0) {
        validationErrors.forEach(error => toastr.warning(error));
    }
    // --- End of Change ---
*/
    return isValid;
}

// Modified applyInputValidation function
function applyInputValidation() {
    $('#questionsContainer .card').each(function () {
        const questionCard = $(this);
        const questionType = questionCard.data('question-type');
        const validation = questionCard.data('validation');

        if (questionType === "3" || questionType === "4") { // Adjusted to match string type from data-question-type
            const input = questionCard.find('input[type="text"], textarea');
            if (validation) {
                input.on('input', function (e) {
                    let value = $(this).val();
                    let newValue = '';

                    switch (validation) {
                        case "0":
                            newValue = value;
                            break;
                        case "1":
                            newValue = value.replace(/[^A-Za-z\s]/g, '');
                            break;
                        case "2":
                            newValue = value.replace(/[^A-Za-z]/g, '');
                            break;
                        case "3":
                            newValue = value.replace(/[^A-Za-z0-9\s]/g, '');
                            break;
                        case "4":
                            newValue = value.replace(/[^0-9]/g, '');
                            if (value !== newValue) {
                                $(this).val(newValue);
                                toastr.warning('Please enter only numbers.');
                            }
                            break;
                        default:
                            newValue = value;
                    }

                    if (value !== newValue && validation !== "4") {
                        $(this).val(newValue);
                        toastr.warning('Input restricted based on validation rules.');
                    }
                });
            }
        }
    });
}
});
