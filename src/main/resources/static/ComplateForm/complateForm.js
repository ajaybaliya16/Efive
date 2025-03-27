        $(document).ready(function() {
            // Initialize DataTable
            var formTable = $('#form_datatable').DataTable({
                destroy: true,
                scrollX: true,
                "bAutoWidth": true,
                paging: true,
                "bLengthChange": false,
                "columnDefs": [{
                    "targets": 2,
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

			const jwtToken = $('input[name="_csrf"]').val();
console.log(jwtToken);
            // Fetch completed forms data
            $.ajax({
                url: '/api/forms/completed',
                method: 'GET',
                headers: {
                    'Authorization': jwtToken
                },
                success: function(data) {
                    formTable.clear();
                    data.forEach(function(form) {
                        formTable.row.add([
                            form.completedDate,
                            form.formNumber,
                            form.formName,
                            form.createdBy,
                            `<span data-toggle="modal" data-target="#all_question_preview" class="preview-btn" data-response-id="${form.responseId}">
                                <a href="javascript:void(0)" data-toggle="tooltip" data-placement="bottom" title="Preview" class="text-info fa-size">
                                    <i class="fa fa-eye"></i>
                                </a>
                            </span>`
                        ]);
                    });
                    formTable.draw();
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching completed forms:', error);
                }
            });
			// Handle preview button click
			$(document).on('click', '.preview-btn', function() {
			    const responseId = $(this).data('response-id');
			    const jwtToken = $('input[name="_csrf"]').val();

			    $.ajax({
			        url: `/api/forms/preview/${responseId}`,
			        method: 'GET',
			        headers: {
			            'Authorization': jwtToken
			        },
			        success: function(data) {
			            // Populate modal with data
			            $('#previewCompletedDate').text(data.completedDate);
			            $('#previewFormTitle').text(data.formTitle);
			            $('#previewFormDescription').text(data.formDescription);

			            // Populate questions and answers
			            const questionsContainer = $('#previewQuestionsContainer');
			            questionsContainer.empty();
			            data.questions.forEach(function(qa, index) {
			                const isRequired = qa.required ? '<span class="text-danger">*</span>' : '';
			                
			                // Build answers HTML using forEach for multiple answers
			                let answersHtml = '';
			                if (Array.isArray(qa.answerText)) {
			                    qa.answerText.forEach(function(answer, answerIndex) {
			                        answersHtml += `
			                            <p class="mb-1 text-justify">${answer || 'N/A'}</p>
			                        `;
			                    });
			                } else {
			                    // Handle single answer case
			                    answersHtml = `<p class="mb-1 text-justify">${qa.answerText || 'N/A'}</p>`;
			                }

			                const questionHtml = `
			                    <div class="card mb-2 queshadow">
			                        <div class="card-body">
			                            <div class="row pl-2 pr-2">
			                                <div class="col-xl-1 col-lg-1 col-sm-2 colmspadding">
			                                    <span class="question">Q : ${index + 1}</span>
			                                </div>
			                                <div class="col-xl-11 col-lg-11 col-sm-10 colmspadding">
			                                    <div class="form-group mb-0">
			                                        <p class="font-weight-700 mb-1 text-justify">${isRequired} ${qa.questionLabel || 'N/A'}</p>
			                                        <p class="mb-1 text-justify">${qa.questionText || 'N/A'}</p>
			                                    </div>
			                                    <div class="form-group mb-0">
			                                        <div class="row pl-2 pr-2">
			                                            <div class="col-xl-12 col-lg-12 col-sm-12 colmspadding">
			                                                <p class="font-weight-700 mb-1 text-justify">Answer</p>
			                                                ${answersHtml}
			                                            </div>
			                                        </div>
			                                    </div>
			                                </div>
			                            </div>
			                        </div>
			                    </div>`;
			                questionsContainer.append(questionHtml);
			            });

			            // Show the modal
			            $('#all_question_preview').modal('show');
			        },
			        error: function(xhr, status, error) {
			            console.error('Error fetching form preview:', error);
			        }
			    });
			});
			});