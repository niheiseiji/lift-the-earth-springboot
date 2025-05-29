package com.lifttheearth.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.dto.group.CreateGroupTimelineRequestDto;
import com.lifttheearth.backend.dto.group.GroupTimelineDto;
import com.lifttheearth.backend.dto.group.UpdateGroupTimelineRequestDto;
import com.lifttheearth.backend.service.GroupTimelineService;

@RestController
@RequestMapping("/api/group-timelines")
public class GroupTimelineController {

    @Autowired
    private GroupTimelineService groupTimelineService;

    @PostMapping
    public ResponseEntity<GroupTimelineDto> createTimeline(@AuthenticationPrincipal User user,
            @RequestBody CreateGroupTimelineRequestDto request) {
        GroupTimelineDto timeline = groupTimelineService.createTimeline(user, request);
        return ResponseEntity.ok(timeline);
    }

    @GetMapping("/groups/{groupId}/timeline")
    public ResponseEntity<List<GroupTimelineDto>> getGroupTimeline(@AuthenticationPrincipal User user,
            @PathVariable Long groupId) {
        List<GroupTimelineDto> timelines = groupTimelineService.getGroupTimeline(groupId);
        return ResponseEntity.ok(timelines);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTimeline(@AuthenticationPrincipal User user, @PathVariable Long id,
            @RequestBody UpdateGroupTimelineRequestDto request) {
        groupTimelineService.updateTimeline(id, request);
        return ResponseEntity.ok("updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimeline(@AuthenticationPrincipal User user, @PathVariable Long id) {
        groupTimelineService.deleteTimeline(id);
        return ResponseEntity.ok("deleted");
    }
}