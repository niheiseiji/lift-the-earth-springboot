package com.lifttheearth.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.dto.group.CreateGroupRequestDto;
import com.lifttheearth.backend.dto.group.GroupDto;
import com.lifttheearth.backend.dto.group.GroupMembersResponseDto;
import com.lifttheearth.backend.dto.group.JoinGroupResponseDto;
import com.lifttheearth.backend.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@AuthenticationPrincipal User user,
            @RequestBody CreateGroupRequestDto request) {
        request.setCreatedUserId(user.getId());
        GroupDto group = groupService.createGroup(request);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/search")
    public ResponseEntity<GroupDto> searchGroup(@AuthenticationPrincipal User user, @RequestParam String groupId) {
        GroupDto group = groupService.getGroupByGroupId(groupId);
        return ResponseEntity.ok(group);
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<JoinGroupResponseDto> joinGroup(@AuthenticationPrincipal User user,
            @PathVariable Long groupId) {
        JoinGroupResponseDto response = groupService.joinGroup(groupId, user.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<GroupMembersResponseDto> getGroupMembers(@AuthenticationPrincipal User user,
            @PathVariable Long groupId) {
        GroupMembersResponseDto response = groupService.getGroupMembers(groupId);
        return ResponseEntity.ok(response);
    }
}