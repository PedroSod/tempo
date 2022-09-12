package com.ecore.tempo.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MembershipCreateRequestDto implements Serializable {

    private static final long serialVersionUID = 3184317326177799715L;

    @NotBlank
    private String teamId;
    @NotBlank
    private String userId;
    private String roleId;

}
