package com.ecore.tempo.script;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.ecore.tempo.model.RoleEntity;
import com.ecore.tempo.service.RoleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class InitialRoleDataService {

    public static final String       ROLES_JSON = "roles/roles.json";
    private final       RoleService  roleService;
    private final       ObjectMapper objectMapper;


    public InitialRoleDataService(RoleService roleService, ObjectMapper objectMapper) {
        this.roleService = roleService;
        this.objectMapper = objectMapper;
    }


    public String createAllRoles() throws IOException {
        String jsonAsString = getRolesJson();
        List<RoleEntity> roleEntities = objectMapper.readValue(jsonAsString, new TypeReference<>() {
        });
        roleEntities.forEach(roleService::save);
        return roleEntities.size() + " roles were created";
    }

    private String getRolesJson() {
        StringBuilder content = new StringBuilder();
        Scanner       scan    = new Scanner(InitialRoleDataService.class.getClassLoader().getResourceAsStream(ROLES_JSON));
        while (scan.hasNextLine()) {
            content.append(scan.nextLine());
        }
        return content.toString();
    }
}
