package com.lifttheearth.backend.service;

import com.lifttheearth.backend.domain.Training;
import com.lifttheearth.backend.domain.TrainingMenu;
import com.lifttheearth.backend.domain.TrainingMenuSet;
import com.lifttheearth.backend.dto.training.TrainingDto;
import com.lifttheearth.backend.dto.training.TrainingMenuDto;
import com.lifttheearth.backend.dto.training.TrainingMenuSetDto;
import com.lifttheearth.backend.repository.TrainingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public List<TrainingDto> getAll() {
        return trainingRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TrainingDto getById(Long id) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        return toDto(training);
    }

    public TrainingDto create(TrainingDto dto) {
        Training entity = toEntity(dto);
        return toDto(trainingRepository.save(entity));
    }

    public TrainingDto update(Long id, TrainingDto dto) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        Training updated = toEntity(dto);
        updated.setId(id);
        return toDto(trainingRepository.save(updated));
    }

    public void delete(Long id) {
        trainingRepository.deleteById(id);
    }

    private TrainingDto toDto(Training training) {
        return TrainingDto.builder()
                .id(training.getId())
                .userId(training.getUserId())
                .performedAt(training.getPerformedAt())
                .trainingMenus(training.getTrainingMenus().stream().map(menu -> TrainingMenuDto.builder()
                        .id(menu.getId())
                        .displayOrder(menu.getDisplayOrder())
                        .name(menu.getName())
                        .sets(menu.getSets().stream().map(set -> TrainingMenuSetDto.builder()
                                .id(set.getId())
                                .setOrder(set.getSetOrder())
                                .reps(set.getReps())
                                .weight(set.getWeight())
                                .build()).collect(Collectors.toList()))
                        .build()).collect(Collectors.toList()))
                .build();
    }

    private Training toEntity(TrainingDto dto) {
        Training training = new Training();
        training.setUserId(dto.getUserId());
        training.setPerformedAt(dto.getPerformedAt());

        List<TrainingMenu> menus = dto.getTrainingMenus().stream().map(menuDto -> {
            TrainingMenu menu = new TrainingMenu();
            menu.setId(menuDto.getId());
            menu.setDisplayOrder(menuDto.getDisplayOrder());
            menu.setName(menuDto.getName());
            menu.setSets(menuDto.getSets().stream().map(setDto -> {
                TrainingMenuSet set = new TrainingMenuSet();
                set.setId(setDto.getId());
                set.setSetOrder(setDto.getSetOrder());
                set.setReps(setDto.getReps());
                set.setWeight(setDto.getWeight());
                set.setTrainingMenu(menu);
                return set;
            }).collect(Collectors.toList()));
            menu.setTraining(training);
            return menu;
        }).collect(Collectors.toList());

        training.setTrainingMenus(menus);
        return training;
    }
}
