package com.ecore.tempo.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ecore.tempo.exception.DuplicatedRoleException;
import com.ecore.tempo.exception.RecordNotFoundException;
import com.ecore.tempo.helper.ServiceHelper;
import com.ecore.tempo.model.RoleEntity;
import com.ecore.tempo.repository.RoleRepository;


@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public void save(RoleEntity entity) {
        roleRepository.save(entity);
    }

    public void createByName(String name) {
        if (roleRepository.existsByName(name)) {
            throw new DuplicatedRoleException(name);
        }
        save(RoleEntity.builder()
                       .id(ServiceHelper.generateUUID())
                       .name(name)
                       .build());
    }

    public RoleEntity getRole(String id) {
        return roleRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Role", id));
    }

    public Collection<RoleEntity> getAll() {
        return roleRepository.findAll();
    }

    @Transactional
    public void delete(String name) {
        roleRepository.deleteByName(name);
    }

}
