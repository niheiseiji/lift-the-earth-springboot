package com.lifttheearth.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifttheearth.backend.domain.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByGroupId(Long groupId);

    List<GroupMember> findByUserId(Long userId);
}