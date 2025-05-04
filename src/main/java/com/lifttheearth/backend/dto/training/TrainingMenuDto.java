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
public class TrainingMenuDto {
    private Long id;
    private Integer displayOrder;
    private String name;
    private List<TrainingMenuSetDto> sets;
}
