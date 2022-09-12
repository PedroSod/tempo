package com.ecore.tempo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecore.tempo.model.MembershipEntity;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipEntity, String> {

    List<MembershipEntity> findAllByTeamIdAndUserId(String teamId, String userId);

    List<MembershipEntity> findAllByRoleId(String roleId);

    void deleteByTeamIdAndAndUserIdAndRoleId(String teamId, String userId, String roleId);

    boolean existsByTeamIdAndAndUserIdAndRoleId(String teamId, String userId, String roleId);

}
