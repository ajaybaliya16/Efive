// userValidation.js

$(document).ready(function () {



	fetchUserInfo();

	const priceInput = $("#contactNo");
	priceInput.on("input", function () {
		const invalidChars = /[^0-9]/g; // Allow only numbers (0-9)
		$(this).val($(this).val().replace(invalidChars, ""));

		if ($(this).val().length >= 10) {
			$(this).val($(this).val().substring(0, 10));
		}
	});

	const nameInput = $("#firstName,#lastName");
	nameInput.on("input", function () {
		const invalidChars = /[^a-zA-Z\s]/g;
		$(this).val($(this).val().replace(invalidChars, ""));
		if ($(this).val().length >= 30) {
			$(this).val($(this).val().substring(0, 30));
		}
	});
	// Image preview functionality
	$('#userImage').on('change', function (e) {
		const file = e.target.files[0];
		if (file) {
			const reader = new FileReader();
			reader.onload = function (e) {
				$('#previewImage').attr('src', e.target.result);

			};
			reader.readAsDataURL(file);
		}
	});

	// Remove image functionality
	$('#removeImage').on('click', function () {
		$('#previewImage').attr('src', 'assets/images/users/default_user.png');
		$('#userImage').val('');
	});

	// Form submission with validation
	$('.save_port_details').on('click', function (e) {
		e.preventDefault();

		if (!validateInputs()) {
			return false; // Prevents modal from closing
		}

		submitData();
	});
	$('#canclebtn,#btn').on('click', function (e) {
		e.preventDefault();
		clearUserForm();
	});

	// Clear form function
	function clearUserForm() {
		$('#firstName').val('');
		$('#lastName').val('');
		$('#email').val('');
		$('#contactNo').val('');
		$('#gender').val('');
		$('#valid_from').val('');
		$('#valid_to').val('');
		setTimeout(function () {
		$('#role').val('');
					$('#role').selectpicker('refresh');
				}, 500);
		$('#active').prop('checked', false);
		$('#userImage').val('');
		$('#previewImage').attr('src', 'assets/images/users/default_user.png');
		$('#saveUser').removeData('id');
		$('#saveUser').text('Save');
	}

	// Trigger form clear and show modal for adding new user
	$(document).on('click', '.btn-primary', function () {
		clearUserForm();
	});

	// Validation function without form
	function validateInputs() {
		let isValid = true;

		// Reset previous error states
		$('#portfolio_add_detail .form-control').removeClass('error-border');
		$('#portfolio_add_detail .error-message').remove();

		// Helper function to safely get value
		function getSafeValue($element) {
			return $element.length && $element.val() !== undefined ? $element.val().trim() : '';
		}

		// First Name validation
		const $firstNameInput = $('#firstName');
		const firstName = getSafeValue($firstNameInput);
		if (!firstName || firstName.length < 2) {
			showError($firstNameInput, 'First Name is required');
			isValid = false;
			return false;
		}

		// Last Name validation
		const $lastNameInput = $('#lastName');
		const lastName = getSafeValue($lastNameInput);
		if (!lastName || lastName.length < 2) {
			showError($lastNameInput, 'Last Name is required');
			isValid = true;
			return false;

		}

		// Email validation
		const $emailInput = $('#email');
		const email = getSafeValue($emailInput);
		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		if (!email) {
			showError($emailInput, 'Email is required');
			isValid = false;
			return false;

		} else if (!emailRegex.test(email)) {
			showError($emailInput, 'Please enter a valid email');
			isValid = false;
			return false;

		}

		// Contact No validation (optional but must be valid if provided)
		const $contactNoInput = $('#contactNo');
		const contactNo = getSafeValue($contactNoInput);
		const phoneRegex = /^[0-9]{10}$/;
		if (contactNo && !phoneRegex.test(contactNo)) {
			showError($contactNoInput, 'Please enter a valid 10-digit phone number');
			isValid = false;
			return false;

		}

		// Gender validation
		const $genderSelect = $('#gender');
		const gender = getSafeValue($genderSelect);
		if (!gender) {
			showError($genderSelect, 'Gender is required');
			isValid = false;
			return false;

		}

		// Role validation
		const $roleSelect = $('#role');
		const role = getSafeValue($roleSelect);
		if (!role) {
			showError($roleSelect, 'Role is required');
			isValid = false;
			return false;

		}

		// Valid From validation (optional but must be valid date if provided)
		const $validFromInput = $('#valid_from');
		const validFrom = getSafeValue($validFromInput);
		if (validFrom && !isValidDate(validFrom)) {
			showError($validFromInput, 'Please enter a valid date (dd/mm/yyyy)');
			isValid = false;
			return false;

		}
		

		// Valid To validation (optional but must be valid date and after validFrom if provided)
		const $validToInput = $('#valid_to');
		const validTo = getSafeValue($validToInput);
		if (validTo) {
			if (!isValidDate(validTo)) {
				showError($validToInput, 'Please enter a valid date (dd/mm/yyyy)');
				isValid = false;
				return false;

			} else if (validFrom && compareDates(validFrom, validTo) > 0) {
				showError($validToInput, 'Valid To must be after Valid From');
				isValid = false;
				return false;

			}
		}

		// Image validation
		function validateImage() {
			const $imageInput = $('#userImage');
			const imageFile = $imageInput[0]?.files[0];
			let isValid = true; // Assume valid by default

			return new Promise((resolve) => {
				if (!imageFile) {
					$.confirm({
						title: 'Warning!',
						content: 'Please upload a user image',
						theme: 'material',
						type: 'orange',
						buttons: {
							ok: {
								text: 'OK',
								btnClass: 'btn-orange',
								action: function () {
									resolve(false); // Continue process, isValid remains true
								}
							}
						}
					});
				} else {
					const validImageTypes = ['image/jpeg', 'image/png', 'image/gif'];

					if (!validImageTypes.includes(imageFile.type)) {
						$.confirm({
							title: 'Error!',
							content: 'Please upload a valid image (JPEG, PNG, or GIF)',
							theme: 'material',
							type: 'red',
							buttons: {
								ok: {
									text: 'OK',
									btnClass: 'btn-red',
									action: function () {
										resolve(isValid); // Continue process, isValid remains true
									}
								}
							}
						});
					} else if (imageFile.size > 5 * 1024 * 1024) { // 5MB limit
						$.confirm({
							title: 'Error!',
							content: 'Image size must be less than 5MB',
							theme: 'material',
							type: 'red',
							buttons: {
								ok: {
									text: 'OK',
									btnClass: 'btn-red',
									action: function () {
										resolve(isValid); // Continue process, isValid remains true
									}
								}
							}
						});
					} else {
						resolve(isValid); // No issues, proceed immediately
					}
				}
			});
		}

		return isValid;
	}

	// Show error message function
	function showError($element, message) {
	
		$.alert({
			title: 'Validation Error!',
			content: message,
			theme: 'material',
			type: 'red'
		});
	}

	// Date validation function
	function isValidDate(dateString) {
		const regex = /^(\d{2})\/(\d{2})\/(\d{4})$/;
		if (!regex.test(dateString)) return false;

		const [day, month, year] = dateString.split('/').map(Number);
		const date = new Date(year, month - 1, day);
		return date.getDate() === day &&
			date.getMonth() === month - 1 &&
			date.getFullYear() === year;
	}

	// Compare dates function
	function compareDates(date1, date2) {
		const [d1Day, d1Month, d1Year] = date1.split('/').map(Number);
		const [d2Day, d2Month, d2Year] = date2.split('/').map(Number);
		const date1Obj = new Date(d1Year, d1Month - 1, d1Day);
		const date2Obj = new Date(d2Year, d2Month - 1, d2Day);
		return date1Obj > date2Obj ? 1 : date1Obj < date2Obj ? -1 : 0;
	}
	let currentuserID = '';

	$(document).on('click', '.edit-btn', function () {
		const userId = $(this).data('id');
		currentuserID = userId;
		console.log(userId);
		const validFromData = $(this).data('validfrom');
		const validToData = $(this).data('validto');
		const firstName = $(this).data('firstname');
		const lastName = $(this).data('lastname');
		const email = $(this).data('email');
		const contactNo = $(this).data('contactno');
		const gender = $(this).data('gender');
		const validFrom = validFromData ? moment(validFromData, 'YYYY/MM/DD').format('DD/MM/YYYY') : '';
		const validTo = validToData ? moment(validToData, 'YYYY/MM/DD').format('DD/MM/YYYY') : '';
		const roleId = $(this).data('roleid');
		const isActive = $(this).data('isactive');
		const image = $(this).data('image'); // Corrected extraction
		let avatarSrc = image ? `${image}` : 'assets/images/users/default_user.png';

		console.log("Selected Image Path:", avatarSrc);

		// Populate form fields
		$('#firstName').val(firstName);
		$('#lastName').val(lastName);
		$('#email').val(email);
		$('#contactNo').val(contactNo);

		setTimeout(function () {
			$('#gender').val(gender);
			$('#gender').selectpicker('refresh');
		}, 500);

		$('#valid_from').val(validFrom);
		$('#valid_to').val(validTo);

		setTimeout(function () {
			$('#role').val(roleId);
			$('#role').selectpicker('refresh');
		}, 500);

		// Handle isActive checkbox
		if (isActive === true || isActive === 'true' || isActive === 1) {
			$('#active').prop('checked', true);
		} else {
			$('#active').prop('checked', false);
		}

		// Update Image Preview
		$('#previewImage').attr('src', avatarSrc);

		// Update Save button to indicate an update
		$('#saveUser').data('id', userId);
		$('#saveUser').text('Update');

		// Show the modal
		$('.addmodal').modal('show');
	});

	$(document).keypress(function (e) {
		if (e.which == 13) {
			submitData();
		}
	});
	// AJAX submission without form
	function submitData() {
		const formData = new FormData();

		function convertDateFormat(dateStr) {
			if (!dateStr) return '';
			const [day, month, year] = dateStr.split('/');
			return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
		}

		// Manually append all input values
		formData.append('firstName', $('#firstName').val().trim());
		formData.append('lastName', $('#lastName').val().trim());
		formData.append('email', $('#email').val().trim());
		formData.append('contactNo', $('#contactNo').val().trim());
		formData.append('gender', $('#gender').val());
		formData.append('validFrom', convertDateFormat($('#valid_from').val().trim()));
		formData.append('validTo', convertDateFormat($('#valid_to').val().trim()));
		formData.append('roleId', $('#role').val());
		formData.append('file', $('#userImage')[0].files[0]);

		const userId = currentuserID;
		console.log("User ID:", userId);
		
		function getSafeValue($element) {
		        return $element.length && $element.val() !== undefined ? $element.val().trim() : '';
		    }
		if(!userId){
			
			const $imageInput1 = $('#userImage');
			const imageinput1 = getSafeValue($imageInput1);
	
			if(!imageinput1){
				showError($imageInput1, 'Please Select Image File');
							isValid = false;
							return false;
			}
		}



		console.log(formData)
		const jwtToken = $('input[name="_csrf"]').val(); // Get token from hidden input
		console.log("JWT Token:", jwtToken);




		const url = userId ? `/update/${userId}` : '/auth/signup';
		const method = userId ? 'PUT' : 'POST';

		$.ajax({
		    url: url, // e.g., '/signup'
		    type: method, // e.g., 'POST'
		    data: formData,
		    headers: {
		        'Authorization': jwtToken // Add JWT header
		    },
		    processData: false,
		    contentType: false,
		    beforeSend: function () {
		        $('.preloader').show();
		    },
		    success: function (response) {
		        $('.preloader').hide();
			
		        // Use the message from the response, fallback to default if empty
		        const successMsg = response.message || 'User registered successfully';
		        $.alert({
		            title: 'Success!',
		            content: successMsg,
		            theme: 'material',
		            type: 'green',
		            buttons: {
		                ok: {
		                    text: 'OK',
		                    btnClass: 'btn-green'
		                }
		            }
		        });

		        clearUserForm();
		        fetchUserInfo();
		    },
		    error: function (xhr) {
		        $('.preloader').hide();
		        // Extract error message from responseJSON, fallback to generic message
		        const errorMsg = xhr.responseJSON?.error || 'Email already in Use';
		        $.alert({
		            title: 'Error!',
		            content: errorMsg,
		            theme: 'material',
		            type: 'red',
		            buttons: {
		                ok: {
		                    text: 'OK',
		                    btnClass: 'btn-red'
		                }
		            }
		        });
		    }
		});


	}

	function fetchUserInfo() {
		const jwtToken = $('input[name="_csrf"]').val();
		console.log("JWT Token:", jwtToken);

		$.ajax({
			url: '/api/users/all',
			method: 'GET',
			headers: {
				"Authorization": jwtToken
			},

			success: function (users) {
				console.log("Raw data:", users);
				console.log("Data type:", typeof users);
				console.log("Is array?", Array.isArray(users));

				// Ensure data is an array
				if (!Array.isArray(users)) {
					console.error("Data is not an array:", users);
					alert("Unexpected response format from server.");
					return;
				}

				// Destroy existing DataTable if initialized
				if ($.fn.DataTable.isDataTable('#users_datatable')) {
					$('#users_datatable').DataTable().clear().destroy();
				}

				const tableBody = $("#users_datatable tbody");
				tableBody.empty();

				users.forEach(function (data) {
					// Fallback to default image if imageName is missing
					let avatarSrc = data.imageName ? `/image/${data.imageName}` : 'assets/images/users/default_user.png';

					let row = `
	                    <tr>
	                        <td class="text-center">
	                            <h2 class="table-avatar">
	                                <a href="javascript:void(0)" 
	                                   data-toggle="popover" 
	                                   data-trigger="hover" 
	                                   data-html="true" 
	                                   data-placement="right" 
	                                   data-template='<div class="popover fade bs-popover-right" role="tooltip" x-placement="right"><div class="arrow"></div><h3 class="popover-header p-0 border_radius6"></h3></div>' 
	                                   data-title="<img src='${avatarSrc}' width='150' height='150' class='border_radius6'>">
	                                    <img src="${avatarSrc}" alt="" class="img-radius avatar">
	                                </a>
	                                <span>${data.firstName || ''} ${data.lastName || ''}</span>
	                            </h2>
	                        </td>
	                        <td>${data.email || '-'}</td>
	                        <td>${data.contactNo || '-'}</td>
	                        <td>${data.validFrom || '-'}</td>
	                        <td>${data.validTo || '-'}</td>
	                        <td>${data.gender === '1' ? 'Male' : data.gender === '2' ? 'Female' : '-'}</td>
	                        <td>${data.roleId === 1 ? 'Admin' : data.roleId === 2 ? 'User' : '-'}</td>
	                        <td>${data.isActive ? 'Yes' : 'No'}</td>
	                        <td class="text-center">
	                            <a href="javascript:void(0)" 
	                               data-toggle="tooltip" 
	                               data-placement="bottom" 
	                               data-original-title="Edit" 
	                               class="text-success fa-size client_add_btn edit-btn"
	                               data-id="${data.id || ''}"
	                               data-firstName="${data.firstName || ''}"
	                               data-lastName="${data.lastName || ''}"
	                               data-email="${data.email || ''}"
	                               data-contactNo="${data.contactNo || ''}"
	                               data-gender="${data.gender || ''}"
	                               data-validFrom="${data.validFrom || ''}"
	                               data-validTo="${data.validTo || ''}"
	                               data-roleId="${data.roleId || ''}"
	                               data-isActive="${data.isActive || ''}"
								   data-image="${avatarSrc || ''}">
	                                <i class="fa fa-pencil"></i>
	                            </a>
	                            <span class="delete-user-alert">
	                                <a href="javascript:void(0)" 
	                                   class="text-danger fa-size" 
	                                   data-toggle="tooltip" 
	                                   data-placement="bottom" 
	                                   data-original-title="Delete" 
	                                   data-id="${data.id || ''}">
	                                    <i class="fa fa-trash"></i>
	                                </a>
	                            </span>
	                        </td>
	                    </tr>
	                `;
					tableBody.append(row);
				});

				// Initialize tooltips and popovers after adding rows
				$('[data-toggle="tooltip"]').tooltip();
				$('[data-toggle="popover"]').popover();

				// Reinitialize DataTable
				$('#users_datatable').DataTable({
					destroy: true,
					scrollX: true,
					"bAutoWidth": true,
					paging: true,
					"bLengthChange": false,
					"columnDefs": [{
						"targets": [5],
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
			error: function (xhr) {
				console.error("Error fetching users:", xhr);
				alert("Failed to load user data. Please try again.");
				if (xhr.status === 401) {
					window.location.href = '/index';
				}
			}
		});
	}
	$(document).on('click', '.delete-user-alert a', function () {
		const userId = $(this).data('id');
		const jwtToken = $('input[name="_csrf"]').val();

		$.confirm({
			title: 'Confirm Deletion',
			content: 'Are you sure you want to delete this user?',
			theme: 'material',
			type: 'red',
			buttons: {
				confirm: function () {
					$.ajax({
						url: `/delete/${userId}`,
						type: 'DELETE',
						headers: {
							'Authorization': jwtToken
						},
						success: function (response) {
							$.alert({
								title: 'Success!',
								content: response,
								theme: 'material',
								type: 'green'
							});
							fetchUserInfo(); // Refresh the user list
							location.reload();
						},
						error: function (xhr) {
							const errorMsg = xhr.responseText || 'Error deleting user';
							$.alert({
								title: 'Error!',
								content: errorMsg,
								theme: 'material',
								type: 'red'
							});
						}
					});
				},
				cancel: function () {
					// Do nothing
				}
			}
		});
	});
	
});
