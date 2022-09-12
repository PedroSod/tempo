package com.ecore.tempo.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ecore.tempo.config.ApplicationConfig;
import com.ecore.tempo.helper.FileReader;
import com.ecore.tempo.model.RoleEntity;
import com.ecore.tempo.service.MembershipService;
import com.ecore.tempo.service.RoleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoleController.class)
@Import(value = {ApplicationConfig.class})
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @MockBean
    private MembershipService membershipService;

    private static ObjectMapper objectMapper;


    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
    }

    public static final String ROLES_JSON = "roles/roles.json";

    @Test
    void shouldListAll() throws Exception {
        List<RoleEntity> mockCollection = getRoleResponseDtoList();
        when(roleService.getAll()).thenReturn(mockCollection);
        MvcResult               result       = mockMvc.perform(get(("/role"))).andReturn();
        MockHttpServletResponse response     = result.getResponse();
        String                  jsonAsString = FileReader.readFile(ROLES_JSON);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(jsonAsString, response.getContentAsString(), true);
        verify(roleService, times(1)).getAll();

    }


    @Test
    void shouldGetByMembership() throws Exception {
        String           teamId         = "teamId";
        String           userId         = "userId";
        List<RoleEntity> mockCollection = getRoleResponseDtoList();
        when(membershipService.getRolesByMembership(teamId,userId)).thenReturn(mockCollection);
        MvcResult               result       = mockMvc.perform(get(String.format("%s%s%s%s", "/role/membership?teamId=", teamId, "&userId=", userId)))
                                                      .andReturn();
        MockHttpServletResponse response     = result.getResponse();
        String                  jsonAsString = FileReader.readFile(ROLES_JSON);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(jsonAsString, response.getContentAsString(), true);
        verify(membershipService, times(1)).getRolesByMembership(teamId,userId);

    }

    private List<RoleEntity> getRoleResponseDtoList() throws IOException {
        String jsonAsString = FileReader.readFile(ROLES_JSON);
        List<RoleEntity> roleEntities = objectMapper.readValue(jsonAsString, new TypeReference<>() {
        });
        return roleEntities;
    }
}