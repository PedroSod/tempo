package com.ecore.tempo.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.ecore.tempo.exception.RecordNotFoundException;
import com.ecore.tempo.model.TeamEntity;
import com.ecore.tempo.repository.TeamRepository;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamEntity> getAll() {
        return teamRepository.findAll();
    }

    public TeamEntity getTeam(String id) {
        return teamRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Team", id));
    }

    public void save(TeamEntity team) {
        teamRepository.save(team);
    }

}
