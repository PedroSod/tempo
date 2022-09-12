package com.ecore.tempo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecore.tempo.exception.RecordNotFoundException;
import com.ecore.tempo.model.UserEntity;
import com.ecore.tempo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User", id));
    }

    public void save(UserEntity user) {
        userRepository.save(user);
    }

}
