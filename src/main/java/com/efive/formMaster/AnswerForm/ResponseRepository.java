package com.efive.formMaster.AnswerForm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<ResponseEntity, Long> {
	@Query("SELECT r FROM ResponseEntity r WHERE r.userId = :userId AND r.isDeleted = false")
	List<ResponseEntity> findByUserIdAndIsDeletedFalse(@Param("userId") Long userId);

	@Query("SELECT r FROM ResponseEntity r WHERE r.responseId = :responseId AND r.userId = :userId AND r.isDeleted = false")
	Optional<ResponseEntity> findByResponseIdAndUserId(@Param("responseId") Long responseId,
			@Param("userId") Long userId);
}