package com.efive.formMaster.admin.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efive.formMaster.admin.Entity.FormEntity;

import jakarta.transaction.Transactional;

public interface FormRepository extends JpaRepository<FormEntity, Long> {

	Object findByFormTitle(String formTitle);

	@Query(value = "SELECT MAX(f.form_id) FROM forms f", nativeQuery = true)
	Long findMaxFormId();

//	@Query(value = """
//		    SELECT f.form_id, f.form_title, f.form_description, f.alias_name, 
//		           f.module, f.characteristic, f.sub_characteristic, f.recurrence, 
//		           f.start_month, f.compliance_period, f.effective_date,
//		           q.question_id, q.question_text, q.question_type, q.is_required ,
//		           o.option_id, o.option_text
//		    FROM forms f
//		    LEFT JOIN questions q ON f.form_id = q.form_id
//		    LEFT JOIN options o ON q.question_id = o.question_id
//		    """, nativeQuery = true)
//		List<Object[]> fetchFormData();

//	@Query(value = "SELECT f.form_id, f.form_title, f.is_active " + "FROM forms f"+ "WHERE f.form_id = :formId AND f.isDeleted = false", nativeQuery = true)
//	List<Object[]> fetchFormData();
	@Query(value = "SELECT f.form_id, f.form_title, f.is_active " + "FROM forms f "
			+ "WHERE f.isDeleted = false", nativeQuery = true)
	List<Object[]> fetchFormData();

	@Query(value = "SELECT f.form_id, f.form_title, f.form_description, f.alias_name, "
	        + "f.module, f.characteristic, f.sub_characteristic, f.recurrence, "
	        + "f.start_month, f.compliance_period, f.effective_date, f.is_active, "
	        + "q.question_id, q.question_text, q.question_type, q.is_required, "
	        + "q.question_label, q.description AS question_description, q.validation, "
	        + "o.option_id, o.option_text, o.position "
	        + "FROM forms f "
	        + "LEFT JOIN questions q ON f.form_id = q.form_id "
	        + "LEFT JOIN options o ON q.question_id = o.question_id "
	        + "WHERE f.form_id = :formId", nativeQuery = true)
	List<Object[]> fetchFormDataById(@Param("formId") Long formId);

//	@Modifying
//	@Transactional
//	@Query("UPDATE FormEntity f SET f.isDeleted = true, f.updatedBy = :updatedBy, f.updatedAt = :updatedAt WHERE f.formId = :formId")
//	void softDeleteById(@Param("formId") Long formId, @Param("updatedBy") Long updatedBy,
//			@Param("updatedAt") String updatedAt);

	@Modifying
	@Transactional
	@Query(value = "UPDATE forms SET is_deleted = true, updated_by = :updatedBy, updated_at = :updatedAt WHERE form_id = :formId", nativeQuery = true)
	void softDeleteById(@Param("formId") Long formId, @Param("updatedBy") Long updatedBy,
			@Param("updatedAt") String updatedAt);

	@Query(value = "SELECT * FROM forms WHERE form_id = :formId AND isdeleted = false", nativeQuery = true)
	FormEntity findByIdAndNotDeleted(@Param("formId") Long formId);

	@Query(value = "SELECT f.* " +
            "FROM forms f " +
            "WHERE (f.form_id NOT IN ( " +
            "    SELECT fs.form_id " +
            "    FROM form_submissions fs " +
            "    WHERE fs.user_id = :userId" +
            ") " +
            "OR NOT EXISTS ( " +
            "    SELECT 1 " +
            "    FROM form_submissions fs " +
            "    WHERE fs.user_id = :userId" +
            ")) " +
            "AND f.isdeleted = false " +
            "AND f.is_active = true", // Added condition to filter only active forms
     nativeQuery = true)
List<FormEntity> findAvailableFormsForUser(@Param("userId") Long userId);

	@Query(value = "SELECT f.form_id, f.form_title, f.is_active, f.created_by " + "FROM forms f "
			+ "WHERE f.created_by = :userId AND f.isdeleted = false", nativeQuery = true)
	List<Object[]> fetchFormDataByCreatedBy(@Param("userId") Long userId);

}