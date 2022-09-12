package com.ecore.tempo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecore.tempo.model.TeamEntity;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, String> {

}
