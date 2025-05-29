package com.lifttheearth.backend.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "group_timelines")
@Data
public class GroupTimeline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column
    private Long trainingId;

    @Column(nullable = false)
    private Boolean isPublic;

    @Column
    private String comment;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private Long createdUserId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean deleteFlag;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false)
    private Group group;

    @Column(name = "group_id", nullable = false)
    private Long groupId;
}