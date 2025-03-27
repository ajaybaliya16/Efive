$(document).ready(function () {
    // Other existing code...

    $(document).on('click', '#viewform', function () { // Targeting the preview button with class
        const formId = $(this).data('id'); // Get formId from data-id attribute
        const jwtToken = $('input[name="_csrf"]').val();

        console.log("Preview Form ID:", formId);

        if (!formId) {
            toastr.error('Form ID is undefined. Please check the button configuration.');
            return;
        }

        $.ajax({
            url: `/api/forms/get/${formId}`,
            method: 'GET',
            headers: {
                'Authorization': jwtToken
            },
            beforeSend: function () {
                $('.preloader').show();
            },
            success: function (data) {
                console.log("Preview Data:", data);
                $('.preloader').hide();


                // Update title and description
                $('#previewTitle').text(data.formTitle || 'N/A');
                $('#previewDescription').text(data.formDescription || 'N/A');

                // Generate question cards dynamically
                const questionsContainer = $('#questionsContainer');
                questionsContainer.empty();

                (data.questions || []).forEach((question, index) => {
                    const questionHtml = generateQuestionCard(question, index + 1);
                    questionsContainer.append(questionHtml);
                });
                $('.selectpicker').selectpicker('refresh');

                // Show the modal
                $('#all_question_preview').modal('show');
                toastr.success('Form preview loaded successfully');
            },
            error: function (xhr, status, error) {
                if (xhr.status === 404) {
                    toastr.error('Form not found with ID: ' + formId);
                } else {
                    toastr.error('Failed to fetch form preview: ' + (xhr.responseJSON?.message || error));
                }
            }
        });
    });

    // Function to generate question card based on question type
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


        //string to intger convert
        const type = parseInt(question.questionType, 10);
        const isRequired = question.required ? '<span class="text-danger">*</span>' : '';
        let answerHtml = '';

        switch (type) {
            case 1: // Single Choice (Radio Buttons)
                answerHtml = `
                    <div class="row pl-2 pr-2">
                        ${(question.options || []).map((option, idx) => `
                            <div class="col-xl-3 col-lg-3 col-sm-3 col-xs-12 colmspadding">
                                <div class="custom-control custom-radio">
                                    <input type="radio" id="choice${questionNumber}_${idx}" name="choice${questionNumber}" 
                                           class="custom-control-input" ${idx === 0 ? 'checked=""' : ''} >
                                    <label class="custom-control-label font-weight-300 m-t-5" for="choice${questionNumber}_${idx}">
                                        ${option.optionText || 'Choice ' + (idx + 1)}
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
                                <div class="custom-control custom-checkbox displayblock">
                                    <input type="checkbox" class="custom-control-input" id="choiceckbox${questionNumber}_${idx}" >
                                    <label class="custom-control-label font-weight-300 m-t-5" for="choiceckbox${questionNumber}_${idx}">
                                        ${option.optionText || 'Choice ' + (idx + 1)}
                                    </label>
                                </div>
                            </div>
                        `).join('')}
                    </div>`;
                break;

            case 3: // Single Textbox
                answerHtml = `
                    <div class="row pl-2 pr-2">
                        <div class="col-xl-7 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                            <input type="text" class="form-control" placeholder="Enter Your Answer" >
                        </div>
                    </div>`;
                break;

            case 4: // Multiline Textbox
                answerHtml = `
                    <div class="row pl-2 pr-2">
                        <div class="col-xl-7 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                            <textarea class="form-control textareasize" placeholder="Enter Your Answer" ></textarea>
                        </div>
                    </div>`;
                break;

            case 5: // Single Select Dropdown
                answerHtml = `
				        <div class="row pl-2 pr-2">
				            <div class="col-xl-7 col-lg-12 col-sm-12 col-xs-12 colmspadding">
				                <select class="selectpicker" data-style="lineheight12 bg-transfer" data-live-search="true">
				                    <option value="" selected="selected">Select</option>
				                    ${(question.options || []).map((option, idx) => `
				                        <option value="${idx + 1}">${option.optionText || 'Choice ' + (idx + 1)}</option>
				                    `).join('')}
				                </select>
				            </div>
				        </div>
				    `;

                break;

            case 6: // Multi Select Dropdown
                answerHtml = `
					        <div class="row pl-2 pr-2">
					            <div class="col-xl-7 col-lg-12 col-sm-12 col-xs-12 colmspadding">
					                <select class="selectpicker" multiple data-selected-text-format="count" 
					                        data-style="btn-light bg-transfer" data-actions-box="true">
					                    ${(question.options || []).map(option => `
					                        <option value="${option.position}">${option.optionText || 'Choice ' + option.position}</option>
					                    `).join('')}
					                </select>
					            </div>
					        </div>
					    `;

                // Assuming you append it to some container
                // Then refresh the selectpicker
                $('.selectpicker').selectpicker('refresh');
                break;

            case 7: // Date
                answerHtml = `
                    <div class="row pl-2 pr-2">
                        <div class="col-xl-3 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                            <div class="input-group date">
                                <input type="text" class="form-control" placeholder="dd/mm/yyyy" 
                                       id="allpreview_date${questionNumber}" >
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
            <div class="card mb-2 queshadow">
                <div class="card-body">
                    <div class="row pl-2 pr-2">
                        <div class="col-xl-1 col-lg-1 col-sm-2 col-xs-12 colmspadding">
                            <span class="question">Q : ${questionNumber}</span>
                        </div>
                        <div class="col-xl-11 col-lg-11 col-sm-10 col-xs-12 colmspadding">
                            <div class="form-group mb-0 text-justify">
                                <p class="font-weight-700 mb-1 text-justify">${isRequired} ${question.questionText || 'No Description Require'}</p>
                                <p class="mb-1">${question.description || 'No Description Require'}</p>
                            </div>
                            <div class="form-group mb-0">
                                ${answerHtml}
                            </div>
                        </div>
                    </div>
                </div>
            </div>`;
    }

    // Ensure Bootstrap modal close works
    $('#all_question_preview').on('hidden.bs.modal', function () {
        $(this).removeClass('show');
    });
});