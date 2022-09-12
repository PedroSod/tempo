package com.ecore.tempo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembershipResponseDto  implements Serializable {

    private static final long serialVersionUID = 3184317326177799715L;

    private TeamResponseDto team;
    private UserResponseDto user;
    private RoleResponseDto role;

}
