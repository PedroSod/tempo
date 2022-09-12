package com.ecore.tempo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity(name = "team_data")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeamEntity {

    @Id
    private String          id;
    private String          name;
    @OneToOne
    private UserEntity      teamLeader;
    @ManyToMany
    private Set<UserEntity> teamMembers;

}
