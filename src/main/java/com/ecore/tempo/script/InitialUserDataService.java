package com.ecore.tempo.script;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecore.tempo.dto.restclient.ExternalServiceUserDto;
import com.ecore.tempo.exception.RecordNotFoundException;
import com.ecore.tempo.model.UserEntity;
import com.ecore.tempo.restclient.UserRestClient;
import com.ecore.tempo.service.UserService;

@Service
public class InitialUserDataService {

    private final UserService    userService;
    private final UserRestClient userRestClient;
    private final ModelMapper    defaultModelMapper;


    public InitialUserDataService(UserService userService, UserRestClient userRestClient, ModelMapper defaultModelMapper) {
        this.userService = userService;
        this.userRestClient = userRestClient;
        this.defaultModelMapper = defaultModelMapper;
    }


    public String createAllUser() {
        List<ExternalServiceUserDto> users = userRestClient.getUsers();
        users.forEach(user -> {
            UserEntity entity = getUserEntity(user.getId());
            userService.save(entity);
        });
        return users.size() + " users were created";
    }


    private UserEntity getUserEntity(String id) {
        return defaultModelMapper.map(userRestClient.getUser(id).orElseThrow(() -> new RecordNotFoundException("User", id)), UserEntity.class);
    }

}
