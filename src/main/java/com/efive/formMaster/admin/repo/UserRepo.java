package com.efive.formMaster.admin.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efive.formMaster.admin.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

//	User findByusername(String username);
    Optional<User> findByEmail(String email);
    
    List<User> findByIsDeletedFalse();    
    


    
} 
