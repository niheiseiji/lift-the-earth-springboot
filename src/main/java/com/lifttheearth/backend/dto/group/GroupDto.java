package com.lifttheearth.backend.dto.group;

import lombok.Data;

@Data
public class GroupDto {
    private Long id;
    private String groupId;
    private String name;
    private Long createdUserId;
    private String createdAt;
} 