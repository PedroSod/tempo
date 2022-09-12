package com.ecore.tempo.service;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import com.ecore.tempo.dto.MembershipCreateRequestDto;
import com.ecore.tempo.exception.DuplicatedMembershipException;
import com.ecore.tempo.helper.ServiceHelper;
import com.ecore.tempo.model.MembershipEntity;
import com.ecore.tempo.model.RoleEntity;
import com.ecore.tempo.repository.MembershipRepository;

@Service
public class MembershipService {

    public static final String               DEFAULT_ROLE = "1245a4bf-adfe-415c-941b-1739af070301";
    private final       MembershipRepository membershipRepository;
    private final       RoleService          roleService;
    private final       UserService          userService;
    private final       TeamService          teamService;

    public MembershipService(MembershipRepository membershipRepository, RoleService roleService, UserService userService, TeamService teamService) {
        this.membershipRepository = membershipRepository;
        this.roleService = roleService;
        this.userService = userService;
        this.teamService = teamService;
    }

    public List<MembershipEntity> getAll() {
        return membershipRepository.findAll();
    }

    public void save(MembershipCreateRequestDto dto) {
        dto.setRoleId(verifyRoleId(dto.getRoleId()));
        if (membershipRepository.existsByTeamIdAndAndUserIdAndRoleId(dto.getTeamId(), dto.getUserId(), dto.getRoleId())) {
            throw new DuplicatedMembershipException();
        }
        membershipRepository.save(membershipBuilder(dto));
    }

    @Transactional
    public void delete(MembershipCreateRequestDto dto) {
        membershipRepository.deleteByTeamIdAndAndUserIdAndRoleId(dto.getTeamId(), dto.getUserId(), dto.getRoleId());
    }

    private MembershipEntity membershipBuilder(MembershipCreateRequestDto dto) {
        return MembershipEntity.builder()
                               .team(teamService.getTeam(dto.getTeamId()))
                               .user(userService.getUser(dto.getUserId()))
                               .role(roleService.getRole(dto.getRoleId()))
                               .id(ServiceHelper.generateUUID())
                               .build();
    }

    public List<MembershipEntity> getMembershipsByRole(String roleId) {
        return membershipRepository.findAllByRoleId(roleId);
    }

    public List<RoleEntity> getRolesByMembership(String teamId, String userId) {
        List<RoleEntity>       result = new ArrayList<>();
        List<MembershipEntity> rows   = membershipRepository.findAllByTeamIdAndUserId(teamId, userId);
        rows.forEach(row -> result.add(row.getRole()));
        return result;
    }

    private String verifyRoleId(String roleId) {
        return Strings.isNotBlank(roleId) ? roleId : DEFAULT_ROLE;
    }

}
