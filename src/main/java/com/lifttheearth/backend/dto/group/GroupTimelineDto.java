package com.lifttheearth.backend.dto.group;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupTimelineDto {
    private Long id;
    private Long userId;
    private String username;
    private Long trainingId;
    private String comment;
    private String imageUrl;
    private Boolean isPublic;
    private String createdAt;
    private String profileImageUrl;
    private String uniqueName;
    private String displayName;

    public GroupTimelineDto(Long id, Long userId, Long trainingId, String comment, String imageUrl,
            Boolean isPublic, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.trainingId = trainingId;
        this.comment = comment;
        this.imageUrl = imageUrl;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
    }
}