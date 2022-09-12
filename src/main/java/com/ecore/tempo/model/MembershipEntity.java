package com.ecore.tempo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "team_membership")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MembershipEntity {

    @Id
    private String     id;
    @OneToOne
    private UserEntity user;
    @OneToOne
    private TeamEntity team;
    @OneToOne
    private RoleEntity role;
}
