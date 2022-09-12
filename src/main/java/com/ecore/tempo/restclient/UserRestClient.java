package com.ecore.tempo.restclient;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecore.tempo.dto.restclient.ExternalServiceUserDto;

@FeignClient(name = "userClient", url = "${users-api.url}")
public interface UserRestClient {

    @GetMapping
    List<ExternalServiceUserDto> getUsers();

    @GetMapping("{id}")
    Optional<ExternalServiceUserDto> getUser(@PathVariable String id);
}
