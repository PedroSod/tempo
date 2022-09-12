package com.ecore.tempo.script;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ecore.tempo.dto.restclient.ExternalServiceTeamDto;
import com.ecore.tempo.exception.RecordNotFoundException;
import com.ecore.tempo.model.TeamEntity;
import com.ecore.tempo.model.UserEntity;
import com.ecore.tempo.restclient.TeamRestClient;
import com.ecore.tempo.service.TeamService;
import com.ecore.tempo.service.UserService;

@Service
public class InitialTeamDataService {

    private final TeamService    teamService;
    private final TeamRestClient teamRestClient;
    private final UserService    userService;


    public InitialTeamDataService(TeamService teamService, TeamRestClient teamRestClient, UserService userService) {
        this.teamService = teamService;
        this.teamRestClient = teamRestClient;
        this.userService = userService;
    }


    public String createAllTeams() {
        List<ExternalServiceTeamDto> teams = teamRestClient.getTeams();
        teams.forEach(team -> {
            TeamEntity entity = getTeamEntity(team.getId());
            teamService.save(entity);
        });

        return teams.size() + " teams were created";
    }

    private UserEntity getUserEntity(String id) {
        return userService.getUser(id);
    }

    private TeamEntity getTeamEntity(String id) {
        ExternalServiceTeamDto externalServiceTeamDto = teamRestClient.getTeam(id).orElseThrow(() -> new RecordNotFoundException("Team", id));
        return TeamEntity.builder()
                         .id(externalServiceTeamDto.getId())
                         .name(externalServiceTeamDto.getName())
                         .teamLeader(getUserEntity(externalServiceTeamDto.getTeamLeadId()))
                         .teamMembers(getUsers(externalServiceTeamDto.getTeamMemberIds()))
                         .build();
    }

    private Set<UserEntity> getUsers(Set<String> ids) {
        Set<UserEntity> userEntities = new HashSet<>();
        ids.forEach(id -> userEntities.add(getUserEntity(id)));
        return userEntities;
    }
}
