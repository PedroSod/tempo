package com.ecore.tempo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecore.tempo.model.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {

    void deleteByName(String name);

    boolean existsByName(String name);

}
