package com.ecore.tempo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecore.tempo.dto.RoleResponseDto;
import com.ecore.tempo.service.MembershipService;
import com.ecore.tempo.service.RoleService;

@RestController
@RequestMapping("role")
public class RoleController {

    private final RoleService       roleService;
    private final ModelMapper       defaultModelMapper;
    private final MembershipService membershipService;


    public RoleController(RoleService roleService, ModelMapper defaultModelMapper, MembershipService membershipService) {
        this.roleService = roleService;
        this.defaultModelMapper = defaultModelMapper;
        this.membershipService = membershipService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@RequestParam String roleName) {
        roleService.createByName(roleName);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void remove(@RequestParam String roleName) {
        roleService.delete(roleName);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> listAll() {
        return ResponseEntity.ok(roleService.getAll().stream()
                                            .map(item -> defaultModelMapper.map(item, RoleResponseDto.class))
                                            .toList());
    }



    @GetMapping("membership")
    public ResponseEntity<List<RoleResponseDto>> getByMembership(@RequestParam String teamId,
                                                               @RequestParam String userId) {
        return ResponseEntity.ok(membershipService.getRolesByMembership(teamId, userId).stream()
                                                  .map(row -> defaultModelMapper.map(row, RoleResponseDto.class)).toList());
    }


}
