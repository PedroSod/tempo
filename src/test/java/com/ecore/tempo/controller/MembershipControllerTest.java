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
import com.ecore.tempo.model.MembershipEntity;
import com.ecore.tempo.service.MembershipService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MembershipController.class)
@Import(value = {ApplicationConfig.class})
class MembershipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MembershipService membershipService;

    private static ObjectMapper objectMapper;

    public static final String MEMBERSHIPS_JSON               = "membership/membershipsEntity.json";
    public static final String MEMBERSHIP_RESPONSE_JSON       = "membership/membershipResponse.json";


    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldListAllTest() throws Exception {
        List<MembershipEntity> mockCollection = getListOfMembership();
        when(membershipService.getAll()).thenReturn(mockCollection);

        MvcResult result = mockMvc.perform(get(("/membership"))).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonAsString = FileReader.readFile(MEMBERSHIP_RESPONSE_JSON);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(membershipService, times(1)).getAll();
        JSONAssert.assertEquals(jsonAsString, response.getContentAsString(), true);

    }

    @Test
    void shouldGetMembershipsByRole() throws Exception {
        String                 roleId         = "roleId";
        List<MembershipEntity> mockCollection = getListOfMembership();
        when(membershipService.getMembershipsByRole(roleId)).thenReturn(mockCollection);

        MvcResult result = mockMvc.perform(get(((String.format("%s/%s", "/membership", roleId))))).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonAsString = FileReader.readFile(MEMBERSHIP_RESPONSE_JSON);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(jsonAsString, response.getContentAsString(), true);
        verify(membershipService, times(1)).getMembershipsByRole(roleId);

    }

    private List<MembershipEntity> getListOfMembership() throws IOException {
        String jsonAsString = FileReader.readFile(MEMBERSHIPS_JSON);
        List<MembershipEntity> membershipEntityList = objectMapper.readValue(jsonAsString, new TypeReference<>() {
        });
        return membershipEntityList;
    }

}