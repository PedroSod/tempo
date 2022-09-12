package com.ecore.tempo.dto.restclient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
public class ExternalServiceTeamDto  implements Serializable {

    private static final long serialVersionUID = 3184317326177799715L;

    private String      id;
    private String      name;
    private String      teamLeadId;
    private Set<String> teamMemberIds;

}
