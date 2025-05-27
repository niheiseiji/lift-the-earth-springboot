package com.lifttheearth.backend.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinGroupResponseDto {
    private String message;
    private String groupId;
    private String joinedAt;
}