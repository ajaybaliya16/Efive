package com.efive.formMaster.admin.Service.ServiceImpl;

import com.efive.formMaster.admin.DTO.FormDTO;
import com.efive.formMaster.admin.DTO.OptionDTO;
import com.efive.formMaster.admin.DTO.QuestionDTO;
import com.efive.formMaster.admin.Entity.FormEntity;
import com.efive.formMaster.admin.Entity.QuestionEntity;
import com.efive.formMaster.admin.Entity.OptionEntity;
import com.efive.formMaster.admin.Service.FormService;
import com.efive.formMaster.admin.repo.FormRepository;
import com.efive.formMaster.admin.repo.OptionRepository;
import com.efive.formMaster.admin.repo.QuestionRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormServiceImpl implements FormService {

	private final FormRepository formRepository;
	private final QuestionRepository questionRepository;
	private final OptionRepository optionRepository;

	@Autowired
	public FormServiceImpl(FormRepository formRepository, QuestionRepository questionRepository,
			OptionRepository optionRepository) {
		this.formRepository = formRepository;
		this.questionRepository = questionRepository;
		this.optionRepository = optionRepository;

	}

	@Override
	public String createForm(FormDTO input) throws Exception {
		// Check for existing form title
//        if (formRepository.findByFormTitle(input.getFormTitle()).isPresent()) {
//            throw new RuntimeException("Form title already in use.");
//        }

		// Validate input
		if (input.getFormTitle() == null || input.getFormTitle().isEmpty()) {
			throw new IllegalArgumentException("Form title is required");
		}

		// Map FormDTO to FormEntity
		FormEntity formEntity = new FormEntity();
		formEntity.setFormTitle(input.getFormTitle());
		formEntity.setFormDescription(input.getFormDescription());
		formEntity.setAliasName(input.getAliasName());
		formEntity.setModule(input.getModule());
		formEntity.setCharacteristic(input.getCharacteristic());
		formEntity.setSubCharacteristic(input.getSubCharacteristic());
		formEntity.setRecurrence(input.getRecurrence());
		formEntity.setStartMonth(input.getStartMonth());
		formEntity.setCompliancePeriod(input.getCompliancePeriod());
		formEntity.setEffectiveDate(input.getEffectiveDate());
		formEntity.setIsActive(Optional.ofNullable(input.getIsActive()).orElse(true));
		formEntity.setCreatedBy(input.getCreatedBy());
		formEntity.setUpdatedBy(Optional.ofNullable(input.getUpdatedBy()).orElse((long) 0));
		formEntity.setIsDeleted(Optional.ofNullable(input.getIsDeleted()).orElse(false));

		// Initialize and map questions
		List<QuestionEntity> questionEntities = new ArrayList<>();
		if (input.getQuestions() != null && !input.getQuestions().isEmpty()) {
			questionEntities = input.getQuestions().stream().map(questionDTO -> {
				QuestionEntity questionEntity = new QuestionEntity();
				questionEntity.setForm(formEntity);
				questionEntity.setQuestionText(questionDTO.getQuestionText());
				questionEntity.setQuestionType(questionDTO.getQuestionType());
				questionEntity.setRequired(questionDTO.isRequired());
				questionEntity.setPosition(questionDTO.getPosition() != null ? questionDTO.getPosition() : 0);
				questionEntity.setDescription(questionDTO.getDescription());
				questionEntity.setQuestionLabel(questionDTO.getQuestionLabel());
				questionEntity.setValidation(questionDTO.getValidation()); // Map the validation field
//                    questionEntity.setIsActive(Optional.ofNullable(questionDTO.getIsActive()).orElse(true));

				// Map options
				List<OptionEntity> optionEntities = new ArrayList<>();
				if (questionDTO.getOptions() != null && !questionDTO.getOptions().isEmpty()) {
					optionEntities = questionDTO.getOptions().stream().map(optionDTO -> {
						OptionEntity optionEntity = new OptionEntity();
						optionEntity.setQuestion(questionEntity);
						optionEntity.setOptionText(optionDTO.getOptionText());
						optionEntity.setPosition(optionDTO.getPosition());
//                                optionEntity.setIsActive(Optional.ofNullable(optionDTO.getIsActive()).orElse(true));
						return optionEntity;
					}).collect(Collectors.toList());
				}
				questionEntity.setOptions(optionEntities);

				return questionEntity;
			}).collect(Collectors.toList());
		}

		// Set questions to formEntity and save
		formEntity.setQuestions(questionEntities);
		FormEntity savedForm = formRepository.save(formEntity);

		return "Form created successfully with ID: " + savedForm.getFormId();
	}

	@Override
	public String getNextFormId() {
		Long maxId = formRepository.findMaxFormId(); // Custom query in FormRepository
		Long nextId = (maxId == null) ? 1L : maxId + 1L;
		return "FORM-" + nextId;
	}

	@Override
	public List<FormDTO> fetchAllFormsWithDetails(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("User ID cannot be null");
		}

		// Fetch raw data filtered by userId
		List<Object[]> rawData = formRepository.fetchFormDataByCreatedBy(userId);
		Map<Long, FormDTO> formMap = new LinkedHashMap<>();

		for (Object[] row : rawData) {
			Long formId = ((Number) row[0]).longValue();
			String formTitle = (String) row[1];
			Boolean isActive = row[2] != null ? (Boolean) row[2] : false; // Handle null case
			Long createdBy = ((Number) row[3]).longValue(); // Assuming createdBy is in the query result

			// Only include forms where createdBy matches userId (redundant check since
			// query filters it)
			if (createdBy.equals(userId)) {
				FormDTO formDTO = formMap.computeIfAbsent(formId, k -> {
					FormDTO dto = new FormDTO();
					dto.setFormId(formId);
					dto.setFormTitle(formTitle);
					dto.setIsActive(isActive);
					dto.setCreatedBy(createdBy); // Optional: include in DTO if needed
					return dto;
				});
			}
		}

		return new ArrayList<>(formMap.values());
	}
	@Override
	public FormDTO fetchFormById(Long formId) {
	    List<Object[]> rawData = formRepository.fetchFormDataById(formId);

	    if (rawData.isEmpty()) {
	        throw new NoSuchElementException("Form not found with id: " + formId);
	    }

	    Map<Long, FormDTO> formMap = new LinkedHashMap<>();

	    for (Object[] row : rawData) {
	        Long id = ((Number) row[0]).longValue();
	        String formTitle = (String) row[1];
	        String formDescription = (String) row[2];
	        String aliasName = (String) row[3];
	        String module = (String) row[4];
	        String characteristic = (String) row[5];
	        String subCharacteristic = (String) row[6];
	        String recurrence = (String) row[7];
	        String startMonth = (String) row[8];
	        String compliancePeriod = (String) row[9];
	        String effectiveDate = (String) row[10];
	        Boolean isActive = (Boolean) row[11];

	        Long questionId = row[12] != null ? ((Number) row[12]).longValue() : null;
	        String questionText = (String) row[13];
	        String questionType = (String) row[14];
	        Boolean required = (Boolean) row[15];
	        String questionLabel = (String) row[16];
	        String questionDescription = (String) row[17];  // Changed from description
	        String validation = (String) row[18];

	        Long optionId = row[19] != null ? ((Number) row[19]).longValue() : null;
	        String optionText = (String) row[20];
	        Integer position = row[21] != null ? ((Number) row[21]).intValue() : null;

	        FormDTO formDTO = formMap.computeIfAbsent(id, k -> {
	            FormDTO dto = new FormDTO();
	            dto.setFormId(id);
	            dto.setFormTitle(formTitle);
	            dto.setFormDescription(formDescription);
	            dto.setAliasName(aliasName);
	            dto.setModule(module);
	            dto.setCharacteristic(characteristic);
	            dto.setSubCharacteristic(subCharacteristic);
	            dto.setRecurrence(recurrence);
	            dto.setStartMonth(startMonth);
	            dto.setCompliancePeriod(compliancePeriod);
	            dto.setEffectiveDate(effectiveDate);
	            dto.setIsActive(isActive);
	            dto.setQuestions(new ArrayList<>());
	            return dto;
	        });

	        if (questionId != null) {
	            Optional<QuestionDTO> existingQuestion = formDTO.getQuestions().stream()
	                    .filter(q -> q.getQuestionId().equals(questionId)).findFirst();

	            QuestionDTO questionDTO;
	            if (existingQuestion.isPresent()) {
	                questionDTO = existingQuestion.get();
	            } else {
	                questionDTO = new QuestionDTO();
	                questionDTO.setQuestionId(questionId);
	                questionDTO.setQuestionText(questionText);
	                questionDTO.setQuestionType(questionType);
	                questionDTO.setRequired(required);
	                questionDTO.setQuestionLabel(questionLabel);
	                questionDTO.setDescription(questionDescription);  // Changed from setDescription
	                questionDTO.setValidation(validation);
	                questionDTO.setOptions(new ArrayList<>());
	                formDTO.getQuestions().add(questionDTO);
	            }

	            if (optionId != null) {
	                OptionDTO optionDTO = new OptionDTO();
	                optionDTO.setOptionId(optionId);
	                optionDTO.setOptionText(optionText);
	                optionDTO.setPosition(position);
	                questionDTO.getOptions().add(optionDTO);
	            }
	        }
	    }

	    return formMap.values().stream().findFirst().orElse(null);
	}

	@Override
	@Transactional
	public String updateForm(FormDTO input) throws Exception {
		// Validate input
		if (input.getFormId() == null) {
			throw new IllegalArgumentException("Form ID is required for update");
		}
		if (input.getFormTitle() == null || input.getFormTitle().isEmpty()) {
			throw new IllegalArgumentException("Form title is required");
		}

		// Check if the form exists
		FormEntity existingForm = formRepository.findById(input.getFormId())
				.orElseThrow(() -> new NoSuchElementException("Form with ID " + input.getFormId() + " not found"));

		// Update FormEntity fields
		existingForm.setFormTitle(input.getFormTitle());
		existingForm.setFormDescription(input.getFormDescription());
		existingForm.setAliasName(input.getAliasName());
		existingForm.setModule(input.getModule());
		existingForm.setCharacteristic(input.getCharacteristic());
		existingForm.setSubCharacteristic(input.getSubCharacteristic());
		existingForm.setRecurrence(input.getRecurrence());
		existingForm.setStartMonth(input.getStartMonth());
		existingForm.setCompliancePeriod(input.getCompliancePeriod());
		existingForm.setEffectiveDate(input.getEffectiveDate());
		existingForm.setIsActive(Optional.ofNullable(input.getIsActive()).orElse(true));
		existingForm.setUpdatedBy(Optional.ofNullable(input.getUpdatedBy()).orElse((long) 0));
		existingForm.setUpdatedAt(LocalDateTime.now());
		existingForm.setIsDeleted(Optional.ofNullable(input.getIsDeleted()).orElse(false));

		// Handle questions with soft delete
		if (input.getQuestions() != null && !input.getQuestions().isEmpty()) {
			// Get the existing questions collection
			List<QuestionEntity> existingQuestions = existingForm.getQuestions();

			// Soft delete existing questions
			for (QuestionEntity existingQuestion : existingQuestions) {
				existingQuestion.setIsDeleted(true);
				existingQuestion.setUpdatedBy(input.getUpdatedBy());
				existingQuestion.setUpdatedAt(LocalDateTime.now());
				List<OptionEntity> existingOptions = existingQuestion.getOptions();
				for (OptionEntity option : existingOptions) {
					option.setIsDeleted(true);
					option.setUpdatedBy(input.getUpdatedBy());
					option.setUpdatedAt(LocalDateTime.now());
				}
			}

			// Add new questions to the existing collection
			List<QuestionEntity> newQuestions = input.getQuestions().stream().map(questionDTO -> {
				QuestionEntity questionEntity = new QuestionEntity();
				questionEntity.setForm(existingForm);
				questionEntity.setQuestionText(questionDTO.getQuestionText());
				questionEntity.setQuestionType(questionDTO.getQuestionType());
				questionEntity.setRequired(questionDTO.isRequired());
				questionEntity.setPosition(questionDTO.getPosition() != null ? questionDTO.getPosition() : 0);
				questionEntity.setDescription(questionDTO.getDescription());
				questionEntity.setValidation(questionDTO.getValidation()); // Map the validation field

				// Validate questionLabel
				String questionLabel = questionDTO.getQuestionLabel();
				if (questionLabel == null || questionLabel.trim().isEmpty()) {
					throw new IllegalArgumentException(
							"Question label cannot be null or empty for question: " + questionDTO.getQuestionText());
				}
				questionEntity.setQuestionLabel(questionLabel);

				questionEntity.setIsDeleted(false);
				questionEntity.setCreatedBy(input.getUpdatedBy());
				questionEntity.setCreatedAt(LocalDateTime.now());

				// Handle options
				List<OptionEntity> optionEntities = new ArrayList<>();
				if (questionDTO.getOptions() != null && !questionDTO.getOptions().isEmpty()) {
					optionEntities = questionDTO.getOptions().stream().map(optionDTO -> {
						OptionEntity optionEntity = new OptionEntity();
						optionEntity.setQuestion(questionEntity);
						optionEntity.setOptionText(optionDTO.getOptionText());
						optionEntity.setPosition(optionDTO.getPosition());
						optionEntity.setIsDeleted(false);
						optionEntity.setCreatedBy(input.getUpdatedBy());
						optionEntity.setCreatedAt(LocalDateTime.now());
						return optionEntity;
					}).collect(Collectors.toList());
				}
				questionEntity.setOptions(optionEntities);

				return questionEntity;
			}).collect(Collectors.toList());

			// Clear and add to the existing collection instead of replacing it
			existingQuestions.clear(); // This triggers orphanRemoval for deleted entities
			existingQuestions.addAll(newQuestions); // Add new questions to the same collection
		}

		// Save the updated entity
		FormEntity updatedForm = formRepository.save(existingForm);

		return "Form updated successfully with ID: " + updatedForm.getFormId();
	}

	@Override
	@Transactional
	public String softDeleteForm(Long formId, Long loggedInUserId) throws NoSuchElementException {
		FormEntity form = formRepository.findByIdAndNotDeleted(formId);
		if (form == null) {
			throw new NoSuchElementException("Form with ID " + formId + " not found or already deleted");
		}

		form.setIsDeleted(true);
		form.setUpdatedBy(loggedInUserId);
		form.setUpdatedAt(LocalDateTime.now());

		formRepository.save(form);
		return "Form with ID " + formId + " successfully soft deleted";
	}

	@Override
	public List<FormDTO> getAvailableFormsForUser(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("User ID cannot be null");
		}
		List<FormEntity> forms = formRepository.findAvailableFormsForUser(userId);
		return forms.stream().map(this::convertToSimpleDTO).collect(Collectors.toList());
	}

	// Simplified manual conversion method (only formId and formTitle)
	private FormDTO convertToSimpleDTO(FormEntity formEntity) {
		FormDTO formDTO = new FormDTO();
		formDTO.setFormId(formEntity.getFormId());
		formDTO.setFormTitle(formEntity.getFormTitle());
		return formDTO;
	}
}
