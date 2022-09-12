package com.ecore.tempo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class TeamResponseDto  implements Serializable {

    private static final long serialVersionUID = 3184317326177799715L;

    private String               id;
    private String               name;
    private UserResponseDto      teamLeader;
    private Set<UserResponseDto> teamMembers;
}
