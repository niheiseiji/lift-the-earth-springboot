package com.lifttheearth.backend.dto.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingMenuSetDto {
    private Long id;
    private Integer setOrder;
    private Integer reps;
    private Integer weight;
}
