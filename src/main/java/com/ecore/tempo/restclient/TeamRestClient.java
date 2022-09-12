package com.ecore.tempo.restclient;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecore.tempo.dto.restclient.ExternalServiceTeamDto;

@FeignClient(name = "teamClient", url = "${teams-api.url}")
public interface TeamRestClient {

    @GetMapping
    List<ExternalServiceTeamDto> getTeams();

    @GetMapping("{id}")
    Optional<ExternalServiceTeamDto> getTeam(@PathVariable String id);
}
