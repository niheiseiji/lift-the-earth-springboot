package com.lifttheearth.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lifttheearth.backend.domain.GroupTimeline;
import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.dto.group.CreateGroupTimelineRequestDto;
import com.lifttheearth.backend.dto.group.GroupTimelineDto;
import com.lifttheearth.backend.dto.group.UpdateGroupTimelineRequestDto;
import com.lifttheearth.backend.repository.GroupTimelineRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupTimelineService {

    private final GroupTimelineRepository groupTimelineRepository;

    public GroupTimelineDto createTimeline(User user, CreateGroupTimelineRequestDto request) {
        GroupTimeline timeline = new GroupTimeline();
        timeline.setUser(user);
        timeline.setUserId(user.getId());
        timeline.setTrainingId(request.getTrainingId());
        timeline.setComment(request.getComment());
        timeline.setImageUrl(request.getImageUrl());
        timeline.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : true);
        timeline.setCreatedUserId(user.getId());
        timeline.setCreatedAt(java.time.LocalDateTime.now());
        timeline.setUpdatedAt(java.time.LocalDateTime.now());
        timeline.setDeleteFlag(false);
        timeline.setGroupId(request.getGroupId());
        timeline = groupTimelineRepository.save(timeline);
        return toDto(timeline);
    }

    public List<GroupTimelineDto> getGroupTimeline(Long groupId) {
        List<GroupTimeline> timelines = groupTimelineRepository.findByGroupId(groupId);
        return timelines.stream().map(timeline -> {
            GroupTimelineDto dto = toDto(timeline);
            User user = timeline.getUser();
            if (user != null) {
                dto.setUsername(user.getEmail());
                dto.setDisplayName(user.getDisplayName());
                dto.setUniqueName(user.getUniqueName());
                dto.setProfileImageUrl(user.getProfileImageUrl());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public void updateTimeline(Long id, UpdateGroupTimelineRequestDto request) {
        GroupTimeline timeline = groupTimelineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Timeline not found"));
        timeline.setComment(request.getComment());
        timeline.setImageUrl(request.getImageUrl());
        timeline.setIsPublic(request.getIsPublic());
        timeline.setUpdatedAt(java.time.LocalDateTime.now());
        groupTimelineRepository.save(timeline);
    }

    public void deleteTimeline(Long id) {
        GroupTimeline timeline = groupTimelineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Timeline not found"));
        groupTimelineRepository.delete(timeline);
    }

    private GroupTimelineDto toDto(GroupTimeline timeline) {
        GroupTimelineDto dto = new GroupTimelineDto();
        dto.setId(timeline.getId());
        dto.setTrainingId(timeline.getTrainingId());
        dto.setComment(timeline.getComment());
        dto.setImageUrl(timeline.getImageUrl());
        dto.setIsPublic(timeline.getIsPublic());
        dto.setCreatedAt(timeline.getCreatedAt().toString());
        return dto;
    }
}