package com.lifttheearth.backend.dto.group;

import lombok.Data;

@Data
public class UpdateGroupTimelineRequestDto {
    private String comment;
    private String imageUrl;
    private Boolean isPublic;
}