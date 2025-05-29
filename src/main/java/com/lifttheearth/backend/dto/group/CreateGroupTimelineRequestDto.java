package com.lifttheearth.backend.dto.group;

import lombok.Data;

@Data
public class CreateGroupTimelineRequestDto {
    private Long trainingId;
    private String comment;
    private String imageUrl;
    private Boolean isPublic = true;
    private Long groupId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}