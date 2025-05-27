package com.lifttheearth.backend.dto.group;

import lombok.Data;
import java.util.List;

@Data
public class GroupMembersResponseDto {
    private List<MemberDto> members;

    @Data
    public static class MemberDto {
        private Long userId;
        private String username;
        private String joinedAt;
    }
} 