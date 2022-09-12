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
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecore.tempo.exception.DuplicatedRoleException;
import com.ecore.tempo.exception.RecordNotFoundException;
import com.ecore.tempo.helper.FileReader;
import com.ecore.tempo.model.RoleEntity;
import com.ecore.tempo.repository.RoleRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
class RoleServiceTest {

    @Mock
    private             RoleRepository roleRepository;
    @InjectMocks
    private             RoleService    roleService;
    public static final String         ROLES_JSON = "roles/roles.json";

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldSaveTest() throws IOException {
        RoleEntity role = getRoleEntity();
        when(roleRepository.save(role)).thenReturn(role);
        roleService.save(role);
        verify(roleRepository, times(1)).save(any());
    }

    @Test
    void shouldCreateByNameTest() throws IOException {
        RoleEntity role = getRoleEntity();
        when(roleRepository.existsByName(role.getName())).thenReturn(false);
        when(roleRepository.save(role)).thenReturn(role);
        roleService.createByName(role.getName());
        verify(roleRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowDuplicatedRoleExceptionTest() {
        String name = "name";
        when(roleRepository.existsByName(name)).thenReturn(true);
        assertThrows(DuplicatedRoleException.class, () ->
                         roleService.createByName(name),
                     "Role with the name: " + name + " already exists!");
    }

    @Test
    void shouldGetRoleTest() throws IOException {
        RoleEntity role = getRoleEntity();
        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
        assertEquals(role, roleService.getRole(role.getId()));
        verify(roleRepository, times(1)).findById(role.getId());
    }

    @Test
    void shouldThrowRecordNotFoundExceptionTest() {
        String id = "id";
        when(roleRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () ->
                         roleService.getRole(id),
                     "No Role found for id : " + id);
    }

    @Test
    void shouldGetAll() throws IOException {
        List<RoleEntity> roles = getListOfRoleEntity();
        when(roleRepository.findAll()).thenReturn(roles);
        assertEquals(roles, roleService.getAll());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void shouldDelete() {
        String name = "name";
        doNothing().when(roleRepository).deleteByName(name);
        roleService.delete(name);
        verify(roleRepository, times(1)).deleteByName(name);

    }

    private List<RoleEntity> getListOfRoleEntity() throws IOException {
        String jsonAsString = FileReader.readFile(ROLES_JSON);
        List<RoleEntity> membershipEntityList = new ObjectMapper().readValue(jsonAsString, new TypeReference<>() {
        });
        return membershipEntityList;
    }

    private RoleEntity getRoleEntity() throws IOException {
        return getListOfRoleEntity().get(0);
    }

}