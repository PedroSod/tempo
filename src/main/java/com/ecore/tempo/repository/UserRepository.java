package com.ecore.tempo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecore.tempo.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

}
