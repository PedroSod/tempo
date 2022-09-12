package com.ecore.tempo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecore.tempo.dto.MembershipCreateRequestDto;
import com.ecore.tempo.exception.DuplicatedMembershipException;
import com.ecore.tempo.helper.FileReader;
import com.ecore.tempo.helper.ServiceHelper;
import com.ecore.tempo.model.MembershipEntity;
import com.ecore.tempo.model.RoleEntity;
import com.ecore.tempo.repository.MembershipRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
class MembershipServiceTest {

    @Mock
    private             MembershipRepository membershipRepository;
    @Mock
    private             RoleService          roleService;
    @Mock
    private             ServiceHelper        serviceHelper;
    @Mock
    private             UserService          userService;
    @Mock
    private             TeamService          teamService;
    @InjectMocks
    private             MembershipService    membershipService;
    private static      ObjectMapper         objectMapper;
    public static final String               MEMBERSHIPS_JSON               = "membership/membershipsEntity.json";
    public static final String               MEMBERSHIP_CREATE_REQUEST_JSON = "membership/membershipCreateRequest.json";

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldGetAllTest() throws IOException {
        List<MembershipEntity> mockCollection = getListOfMembership();
        when(membershipRepository.findAll()).thenReturn(mockCollection);
        List<MembershipEntity> membershipEntityList = membershipService.getAll();
        assertEquals(mockCollection, membershipEntityList);
        verify(membershipRepository, times(1)).findAll();
    }

    @Test
    void shouldSaveTest() throws IOException {
        MembershipCreateRequestDto dto = getMembershipCreateRequestDto();
        MembershipEntity membership = getMembershipEntity();
        when(membershipRepository.existsByTeamIdAndAndUserIdAndRoleId(dto.getTeamId(), dto.getUserId(), dto.getRoleId())).thenReturn(false);
        when(membershipRepository.save(membership)).thenReturn(membership);
        membershipService.save(dto);
        verify(membershipRepository, times(1)).save(any());

    }

    @Test
    void shouldThrowDuplicatedMembershipExceptionTest() throws IOException {
        MembershipCreateRequestDto dto = getMembershipCreateRequestDto();
        when(membershipRepository.existsByTeamIdAndAndUserIdAndRoleId(dto.getTeamId(), dto.getUserId(), dto.getRoleId())).thenReturn(true);
        assertThrows(DuplicatedMembershipException.class, () ->
                         membershipService.save(dto),
                     "This Membership already exists!");
    }

    @Test
    void shouldDeleteTest() throws IOException {
        MembershipCreateRequestDto dto = getMembershipCreateRequestDto();
        doNothing().when(membershipRepository).deleteByTeamIdAndAndUserIdAndRoleId(dto.getTeamId(), dto.getUserId(), dto.getRoleId());
        membershipService.delete(dto);
        verify(membershipRepository, times(1)).deleteByTeamIdAndAndUserIdAndRoleId(dto.getTeamId(), dto.getUserId(), dto.getRoleId());
    }

    @Test
    void shouldGetMembershipsByRoleTest() throws IOException {
        List<MembershipEntity> mockCollection = getListOfMembership();
        String                 roleId         = "id";
        when(membershipRepository.findAllByRoleId(roleId)).thenReturn(mockCollection);
        List<MembershipEntity> membershipEntityList = membershipService.getMembershipsByRole(roleId);
        assertEquals(mockCollection, membershipEntityList);
        verify(membershipRepository, times(1)).findAllByRoleId(roleId);
    }

    @Test
    void ShouldGetRolesByMembershipTest() throws IOException {
        String                 teamId         = "teamId";
        String                 userId         = "userId";
        List<MembershipEntity> mockCollection = getListOfMembership();
        List<RoleEntity>       roleEntities   = Collections.singletonList(mockCollection.get(0).getRole());
        when(membershipRepository.findAllByTeamIdAndUserId(teamId, userId)).thenReturn(mockCollection);
        List<RoleEntity> roleEntityList = membershipService.getRolesByMembership(teamId, userId);
        assertEquals(roleEntities, roleEntityList);
        verify(membershipRepository, times(1)).findAllByTeamIdAndUserId(teamId, userId);
    }

    private MembershipEntity getMembershipEntity() throws IOException {
        return getListOfMembership().get(0);
    }

    private List<MembershipEntity> getListOfMembership() throws IOException {
        String jsonAsString = FileReader.readFile(MEMBERSHIPS_JSON);
        List<MembershipEntity> membershipEntityList = objectMapper.readValue(jsonAsString, new TypeReference<>() {
        });
        return membershipEntityList;
    }


    private MembershipCreateRequestDto getMembershipCreateRequestDto() throws IOException {
        String                     jsonAsString               = FileReader.readFile(MEMBERSHIP_CREATE_REQUEST_JSON);
        MembershipCreateRequestDto membershipCreateRequestDto = objectMapper.readValue(jsonAsString, MembershipCreateRequestDto.class);
        return membershipCreateRequestDto;
    }
}