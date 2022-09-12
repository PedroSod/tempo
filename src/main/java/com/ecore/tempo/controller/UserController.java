package com.ecore.tempo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecore.tempo.dto.UserResponseDto;
import com.ecore.tempo.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final ModelMapper defaultModelMapper;

    public UserController(UserService userService, ModelMapper defaultModelMapper) {
        this.userService = userService;
        this.defaultModelMapper = defaultModelMapper;
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.getAll().stream().map(
            user -> defaultModelMapper.map(user, UserResponseDto.class)).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String id) {
        return ResponseEntity.ok(defaultModelMapper.map(userService.getUser(id), UserResponseDto.class));
    }

}
