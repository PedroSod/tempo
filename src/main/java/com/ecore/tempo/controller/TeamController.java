package com.ecore.tempo.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecore.tempo.dto.TeamResponseDto;
import com.ecore.tempo.dto.UserResponseDto;
import com.ecore.tempo.helper.CustomModelMapper;
import com.ecore.tempo.service.TeamService;

@RestController
@RequestMapping("team")
public class TeamController {

    private final TeamService teamService;
    private final ModelMapper defaultModelMapper;


    public TeamController(TeamService teamService, ModelMapper defaultModelMapper) {
        this.teamService = teamService;
        this.defaultModelMapper = defaultModelMapper;
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDto>> getTeams() {
        return ResponseEntity.ok(teamService.getAll().stream().map(
            CustomModelMapper::toTeamResponseDto).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<TeamResponseDto> getTeam(@PathVariable String id) {
        return ResponseEntity.ok(CustomModelMapper.toTeamResponseDto(teamService.getTeam(id)));
    }

    @GetMapping("{id}/members")
    public ResponseEntity<Set<UserResponseDto>> getMembers(@PathVariable String id) {
        Set<UserResponseDto> members = teamService.getTeam(id).getTeamMembers().stream()
                                                  .map(row -> defaultModelMapper.map(row, UserResponseDto.class)).collect(Collectors.toSet());

        return ResponseEntity.ok(members);
    }

}
