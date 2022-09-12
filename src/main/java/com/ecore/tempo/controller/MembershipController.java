package com.ecore.tempo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecore.tempo.dto.MembershipCreateRequestDto;
import com.ecore.tempo.dto.MembershipResponseDto;
import com.ecore.tempo.helper.CustomModelMapper;
import com.ecore.tempo.service.MembershipService;


@RestController
@RequestMapping("membership")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping
    public ResponseEntity<List<MembershipResponseDto>> listAll() {
        List<MembershipResponseDto> list = membershipService.getAll().stream()
                                                            .map(CustomModelMapper::toMembershipResponseDto)
                                                            .toList();
        return ResponseEntity.ok(list);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = "application/json")
    public void create(@Validated @RequestBody MembershipCreateRequestDto dto) {
        membershipService.save(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(consumes = "application/json")
    public void remove(@Validated @RequestBody MembershipCreateRequestDto dto) {
        membershipService.delete(dto);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<List<MembershipResponseDto>> getMembershipsByRole(@PathVariable String roleId) {
        List<MembershipResponseDto> list = membershipService.getMembershipsByRole(roleId).stream()
                                                            .map(CustomModelMapper::toMembershipResponseDto).toList();
        return ResponseEntity.ok(list);
    }

}
