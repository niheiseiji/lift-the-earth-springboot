package com.lifttheearth.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lifttheearth.backend.domain.Group;
import com.lifttheearth.backend.domain.GroupMember;
import com.lifttheearth.backend.dto.group.CreateGroupRequestDto;
import com.lifttheearth.backend.dto.group.GroupDto;
import com.lifttheearth.backend.dto.group.GroupMembersResponseDto;
import com.lifttheearth.backend.dto.group.JoinGroupResponseDto;
import com.lifttheearth.backend.repository.GroupMemberRepository;
import com.lifttheearth.backend.repository.GroupRepository;
import com.lifttheearth.backend.repository.GroupTimelineRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupTimelineRepository groupTimelineRepository;

    private Group toEntity(CreateGroupRequestDto dto) {
        Group group = new Group();
        group.setGroupId("#" + dto.getGroupId());
        group.setName(dto.getName());
        group.setCreatedUserId(dto.getCreatedUserId());
        group.setCreatedAt(java.time.LocalDateTime.now());
        group.setUpdatedAt(java.time.LocalDateTime.now());
        group.setDeleteFlag(false);
        return group;
    }

    public GroupDto createGroup(CreateGroupRequestDto request) {
        Group group = toEntity(request);
        group = groupRepository.save(group);

        // 作成ユーザーをグループのメンバーとして登録
        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUserId(request.getCreatedUserId());
        member.setJoinedAt(java.time.LocalDateTime.now());
        member.setCreatedUserId(request.getCreatedUserId());
        member.setCreatedAt(java.time.LocalDateTime.now());
        member.setUpdatedAt(java.time.LocalDateTime.now());
        member.setDeleteFlag(false);
        groupMemberRepository.save(member);

        return toDto(group);
    }

    public GroupDto getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        return toDto(group);
    }

    public GroupDto getGroupByGroupId(String groupId) {
        Group group = groupRepository.findByGroupId(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        return toDto(group);
    }

    public JoinGroupResponseDto joinGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUserId(userId);
        member.setJoinedAt(java.time.LocalDateTime.now());
        member.setCreatedUserId(userId);
        member.setCreatedAt(java.time.LocalDateTime.now());
        member.setUpdatedAt(java.time.LocalDateTime.now());
        member.setDeleteFlag(false);
        groupMemberRepository.save(member);
        return new JoinGroupResponseDto("joined", group.getGroupId(), member.getJoinedAt().toString());
    }

    public GroupMembersResponseDto getGroupMembers(Long groupId) {
        List<GroupMember> members = groupMemberRepository.findByGroupId(groupId);
        List<GroupMembersResponseDto.MemberDto> memberDtos = members.stream().map(member -> {
            GroupMembersResponseDto.MemberDto dto = new GroupMembersResponseDto.MemberDto();
            dto.setUserId(member.getUserId());
            dto.setUsername("ユーザー名"); // 仮のユーザー名
            dto.setJoinedAt(member.getJoinedAt().toString());
            return dto;
        }).collect(Collectors.toList());
        GroupMembersResponseDto response = new GroupMembersResponseDto();
        response.setMembers(memberDtos);
        return response;
    }

    public List<GroupDto> getGroupsByUserId(Long userId) {
        List<GroupMember> members = groupMemberRepository.findByUserId(userId);
        return members.stream()
                .map(member -> toDto(member.getGroup()))
                .collect(Collectors.toList());
    }

    private GroupDto toDto(Group group) {
        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setGroupId(group.getGroupId());
        dto.setName(group.getName());
        dto.setCreatedUserId(group.getCreatedUserId());
        dto.setCreatedAt(group.getCreatedAt().toString());
        return dto;
    }
}