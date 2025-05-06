package com.lifttheearth.backend.dto.training;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetTrainingDto {
    private Long id;
    private Long userId;
    private String presetName;
    private List<TrainingMenuDto> trainingMenus;
}
