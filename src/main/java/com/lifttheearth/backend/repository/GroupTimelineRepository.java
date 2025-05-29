package com.lifttheearth.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifttheearth.backend.domain.GroupTimeline;

public interface GroupTimelineRepository extends JpaRepository<GroupTimeline, Long> {
    List<GroupTimeline> findByUserId(Long userId);

    List<GroupTimeline> findByGroupId(Long groupId);
}