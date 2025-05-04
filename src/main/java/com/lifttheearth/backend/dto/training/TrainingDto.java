package com.lifttheearth.backend.dto.training;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingDto {
    private Long id;
    private Long userId;
    private LocalDateTime performedAt;
    private List<TrainingMenuDto> trainingMenus;
}
