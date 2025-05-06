package com.lifttheearth.backend.service;

import com.lifttheearth.backend.domain.Training;
import com.lifttheearth.backend.domain.TrainingMapper;
import com.lifttheearth.backend.domain.TrainingMenu;
import com.lifttheearth.backend.domain.TrainingMenuSet;
import com.lifttheearth.backend.dto.training.TrainingDto;
import com.lifttheearth.backend.dto.training.TrainingMenuDto;
import com.lifttheearth.backend.dto.training.TrainingMenuSetDto;
import com.lifttheearth.backend.dto.training.TrainingSummaryDto;
import com.lifttheearth.backend.repository.TrainingRepository;
import com.lifttheearth.backend.security.OwnerCheck;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    public List<TrainingDto> getAll() {
        return trainingRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TrainingDto> getAllByUser(Long userId) {
        return trainingRepository.findByUserIdOrderByPerformedAtDesc(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @OwnerCheck
    public TrainingDto getById(Long id) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        return toDto(training);
    }

    public TrainingDto create(TrainingDto dto) {
        Training entity = toEntity(dto);
        return toDto(trainingRepository.save(entity));
    }

    @OwnerCheck
    public TrainingDto update(Long id, TrainingDto dto) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        Training updated = toEntity(dto);
        updated.setId(id);
        return toDto(trainingRepository.save(updated));
    }

    @OwnerCheck
    public void delete(Long id) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        trainingRepository.deleteById(id);
    }

    private TrainingDto toDto(Training training) {
        return TrainingDto.builder()
                .id(training.getId())
                .userId(training.getUserId())
                .performedAt(training.getPerformedAt())
                .trainingMenus(trainingMapper.toMenuDtoList(training.getTrainingMenus()))
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

    public TrainingSummaryDto getSummary(Long userId) {
        List<Training> trainings = trainingRepository.findByUserIdOrderByPerformedAtDesc(userId);

        int totalTrainings = trainings.size();
        int trainingsLast7Days = 0;
        int trainingsLast30Days = 0;
        int totalLiftedWeightKg = 0;
        int maxBench = 0;
        int maxDeadlift = 0;
        int maxSquat = 0;

        LocalDate now = LocalDate.now();

        for (Training training : trainings) {
            LocalDate date = training.getPerformedAt().toLocalDate();
            if (!date.isBefore(now.minusDays(7)))
                trainingsLast7Days++;
            if (!date.isBefore(now.minusDays(30)))
                trainingsLast30Days++;

            for (TrainingMenu menu : training.getTrainingMenus()) {
                String menuName = menu.getName();

                for (TrainingMenuSet set : menu.getSets()) {
                    if ("ベンチプレス".equals(menuName)) {
                        maxBench = Math.max(maxBench, set.getWeight());
                    } else if ("デッドリフト".equals(menuName)) {
                        maxDeadlift = Math.max(maxDeadlift, set.getWeight());
                    } else if ("スクワット".equals(menuName)) {
                        maxSquat = Math.max(maxSquat, set.getWeight());
                    }

                    totalLiftedWeightKg += set.getWeight() * set.getReps();
                }
            }
        }

        int big3TotalWeight = maxBench + maxDeadlift + maxSquat;

        return TrainingSummaryDto.builder()
                .totalLiftedWeightKg(totalLiftedWeightKg)
                .trainingsLast7Days(trainingsLast7Days)
                .trainingsLast30Days(trainingsLast30Days)
                .totalTrainings(totalTrainings)
                .maxBenchPress(maxBench)
                .maxDeadlift(maxDeadlift)
                .maxSquat(maxSquat)
                .big3TotalWeight(big3TotalWeight)
                .build();
    }
}
