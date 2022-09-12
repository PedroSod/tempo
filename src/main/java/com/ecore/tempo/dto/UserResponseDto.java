package com.ecore.tempo.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
public class UserResponseDto  implements Serializable {

    private static final long serialVersionUID = 3184317326177799715L;

    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String avatarUrl;
    private String location;
}
