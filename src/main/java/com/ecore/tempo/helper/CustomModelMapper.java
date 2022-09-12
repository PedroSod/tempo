package com.ecore.tempo.helper;


import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.ecore.tempo.dto.MembershipResponseDto;
import com.ecore.tempo.dto.TeamResponseDto;
import com.ecore.tempo.model.MembershipEntity;
import com.ecore.tempo.model.TeamEntity;


public class CustomModelMapper {

    private CustomModelMapper() {
    }

    private static ModelMapper modelMapper;

    public static MembershipResponseDto toMembershipResponseDto(MembershipEntity membership) {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<MembershipEntity, MembershipResponseDto>() {
            @Override
            protected void configure() {
                skip(destination.getTeam().getTeamMembers());
            }
        });
        return modelMapper.map(membership, MembershipResponseDto.class);
    }

    public static TeamResponseDto toTeamResponseDto(TeamEntity entity) {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<TeamEntity, TeamResponseDto>() {
            @Override
            protected void configure() {
                skip(destination.getTeamMembers());
            }
        });
        return modelMapper.map(entity, TeamResponseDto.class);
    }
}
