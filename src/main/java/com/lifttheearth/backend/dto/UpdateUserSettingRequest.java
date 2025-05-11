package com.lifttheearth.backend.dto;

import lombok.Data;

@Data
public class UpdateUserSettingRequest {
    private String displayName;
    private String uniqueName;
}
