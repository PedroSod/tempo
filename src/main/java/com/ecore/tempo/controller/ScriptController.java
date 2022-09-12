package com.ecore.tempo.controller;


import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecore.tempo.script.InitialRoleDataService;
import com.ecore.tempo.script.InitialTeamDataService;
import com.ecore.tempo.script.InitialUserDataService;

@RestController
@RequestMapping("script")
public class ScriptController {

    private final InitialUserDataService initialUserDataService;
    private final InitialTeamDataService initialTeamDataService;
    private final InitialRoleDataService initialRoleDataService;

    public ScriptController(InitialUserDataService initialUserDataService, InitialTeamDataService initialTeamDataService,
                            InitialRoleDataService initialRoleDataService) {
        this.initialUserDataService = initialUserDataService;
        this.initialTeamDataService = initialTeamDataService;
        this.initialRoleDataService = initialRoleDataService;
    }

    @PostMapping("/fill/users")
    public ResponseEntity<String> fillUsers() {
        return ResponseEntity.ok(initialUserDataService.createAllUser());
    }

    @PostMapping("/fill/teams")
    public ResponseEntity<String> fillTeams() {
        return ResponseEntity.ok(initialTeamDataService.createAllTeams());
    }

    @PostMapping("/fill/roles")
    public ResponseEntity<String> fillRoles() throws IOException {
        return ResponseEntity.ok(initialRoleDataService.createAllRoles());
    }

}
