package com.lifttheearth.backend.dto.group;

import lombok.Data;

@Data
public class CreateGroupRequestDto {
    private String groupId;
    private String name;
    private Long createdUserId;
}