package com.efive.formMaster.complateForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efive.formMaster.AnswerForm.AnswerFormEntity;
import com.efive.formMaster.AnswerForm.AnswerFormRepository;
import com.efive.formMaster.AnswerForm.ResponseEntity;
import com.efive.formMaster.AnswerForm.ResponseRepository;
import com.efive.formMaster.admin.Entity.FormEntity;
import com.efive.formMaster.admin.Entity.OptionEntity;
import com.efive.formMaster.admin.Entity.QuestionEntity;
import com.efive.formMaster.admin.Entity.User;
import com.efive.formMaster.admin.repo.FormRepository;
import com.efive.formMaster.admin.repo.OptionRepository;
import com.efive.formMaster.admin.repo.QuestionRepository;
import com.efive.formMaster.admin.repo.UserRepo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ComFormServiceImpl implements ComFormService {

	@Autowired
	private ResponseRepository responseRepository;

	@Autowired
	private AnswerFormRepository answerFormRepository;

	@Autowired
	private FormRepository formRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private OptionRepository optionRepository;

	@Autowired
	private UserRepo userRepository;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public List<CompletedFormDTO> getCompletedForms(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("User ID cannot be null");
		}
		// Filter responses by the logged-in user's ID from session
		List<ResponseEntity> responses = responseRepository.findByUserIdAndIsDeletedFalse(userId);
		return responses.stream().map(response -> {
			CompletedFormDTO dto = new CompletedFormDTO();
			dto.setResponseId(response.getResponseId());
			dto.setCompletedDate(dateFormat.format(response.getSubmittedAt()));
			FormEntity form = formRepository.findById(response.getFormId()).orElse(null);
			if (form != null) {
				dto.setFormName(form.getFormTitle());
				dto.setFormId(form.getFormId());
			}
			dto.setFormNumber("FORM-" + String.format("%02d", response.getResponseId()));
			User user = userRepository.findById(response.getCreatedBy()).orElse(null);
			if (user != null) {
				dto.setCreatedBy(user.getUsername());
			}
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public FormPreviewDTO getFormPreview(Long responseId, Long userId) {
		ResponseEntity response = responseRepository.findByResponseIdAndUserId(responseId, userId)
				.orElseThrow(() -> new IllegalArgumentException("Response not found or not authorized"));

		FormPreviewDTO dto = new FormPreviewDTO();
		dto.setCompletedDate(dateFormat.format(response.getSubmittedAt()));

		FormEntity form = formRepository.findById(response.getFormId())
				.orElseThrow(() -> new IllegalArgumentException("Form not found"));
		dto.setFormTitle(form.getFormTitle());
		dto.setFormDescription(form.getFormDescription());

		List<AnswerFormEntity> answers = answerFormRepository.findByResponseId(responseId);

		// Group answers by questionId
		Map<Long, List<AnswerFormEntity>> groupedAnswers = answers.stream()
				.collect(Collectors.groupingBy(AnswerFormEntity::getQuestionId));

		List<QuestionAnswerDTO> questionAnswers = groupedAnswers.entrySet().stream().map(entry -> {
			Long questionId = entry.getKey();
			List<AnswerFormEntity> answerList = entry.getValue();

			QuestionAnswerDTO qaDto = new QuestionAnswerDTO();
			QuestionEntity question = questionRepository.findById(questionId)
					.orElseThrow(() -> new IllegalArgumentException("Question not found"));
			qaDto.setQuestionId(question.getQuestionId());
			qaDto.setQuestionLabel(question.getQuestionLabel());
			qaDto.setQuestionText(question.getQuestionText());
			qaDto.setRequired(question.isRequired() != null && question.isRequired());

			// Combine all answer texts into a single string
			String combinedAnswerText = answerList.stream().map(answer -> {
				if (answer.getOptionId() != null) {
					OptionEntity option = optionRepository.findById(answer.getOptionId())
							.orElseThrow(() -> new IllegalArgumentException("Option not found"));
					return option.getOptionText();
				}
				return answer.getAnswerText();
			}).filter(Objects::nonNull).collect(Collectors.joining(", "));

			qaDto.setAnswerText(combinedAnswerText);
			return qaDto;
		}).collect(Collectors.toList());

		dto.setQuestions(questionAnswers);
		return dto;
	}
}