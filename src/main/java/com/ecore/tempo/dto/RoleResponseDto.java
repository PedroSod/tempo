package com.ecore.tempo.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
public class RoleResponseDto  implements Serializable {

    private static final long serialVersionUID = 3184317326177799715L;
    private String id;
    private String name;

}
