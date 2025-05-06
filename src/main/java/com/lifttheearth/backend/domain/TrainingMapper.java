package com.lifttheearth.backend.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lifttheearth.backend.dto.training.TrainingMenuDto;
import com.lifttheearth.backend.dto.training.TrainingMenuSetDto;

@Component
public class TrainingMapper {

    public List<TrainingMenuDto> toMenuDtoList(List<? extends Object> menuEntities) {
        return menuEntities.stream()
                .map(menu -> {
                    if (menu instanceof TrainingMenu tm) {
                        return toMenuDto(tm.getId(), tm.getDisplayOrder(), tm.getName(), tm.getSets());
                    } else if (menu instanceof PresetTrainingMenu pm) {
                        return toMenuDto(pm.getId(), pm.getDisplayOrder(), pm.getName(), pm.getSets());
                    } else {
                        throw new IllegalArgumentException("Unsupported menu type");
                    }
                }).toList();
    }

    private TrainingMenuDto toMenuDto(Long id, Integer order, String name, List<? extends Object> sets) {
        return TrainingMenuDto.builder()
                .id(id)
                .displayOrder(order)
                .name(name)
                .sets(sets.stream()
                        .map(set -> {
                            if (set instanceof TrainingMenuSet s) {
                                return toSetDto(s.getId(), s.getSetOrder(), s.getReps(), s.getWeight());
                            } else if (set instanceof PresetTrainingMenuSet s) {
                                return toSetDto(s.getId(), s.getSetOrder(), s.getReps(), s.getWeight());
                            } else {
                                throw new IllegalArgumentException("Unsupported set type");
                            }
                        }).toList())
                .build();
    }

    private TrainingMenuSetDto toSetDto(Long id, Integer order, Integer reps, Integer weight) {
        return TrainingMenuSetDto.builder()
                .id(id)
                .setOrder(order)
                .reps(reps)
                .weight(weight)
                .build();
    }
}
