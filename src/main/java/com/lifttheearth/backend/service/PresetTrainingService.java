package com.lifttheearth.backend.service;

import com.lifttheearth.backend.domain.PresetTraining;
import com.lifttheearth.backend.domain.PresetTrainingMenu;
import com.lifttheearth.backend.domain.PresetTrainingMenuSet;
import com.lifttheearth.backend.domain.TrainingMapper;
import com.lifttheearth.backend.dto.training.PresetTrainingDto;
import com.lifttheearth.backend.dto.training.TrainingMenuDto;
import com.lifttheearth.backend.repository.PresetTrainingRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PresetTrainingService {

    private final PresetTrainingRepository presetTrainingRepository;
    private final TrainingMapper trainingMapper;

    public List<PresetTrainingDto> getPresetsByUserId(Long userId) {
        return presetTrainingRepository.findByUserId(userId).stream()
                .map(this::toDto)
                .toList();
    }

    public PresetTrainingDto createPreset(PresetTrainingDto dto) {
        PresetTraining saved = presetTrainingRepository.save(toEntity(dto));
        return toDto(saved);
    }

    public PresetTrainingDto getPresetById(Long id, Long userId) {
        PresetTraining preset = presetTrainingRepository.findById(id)
                .filter(p -> p.getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Not found"));
        return toDto(preset);
    }

    public PresetTrainingDto updatePreset(Long id, PresetTrainingDto dto) {
        PresetTraining existing = presetTrainingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        existing.setPresetName(dto.getPresetName());
        existing.setPresetTrainingMenus(toEntityMenus(dto.getTrainingMenus(), existing));
        existing.setUpdatedAt(LocalDateTime.now());

        PresetTraining saved = presetTrainingRepository.save(existing);
        return toDto(saved);
    }

    public void deletePreset(Long id) {
        if (!presetTrainingRepository.existsById(id)) {
            throw new RuntimeException("Not found");
        }
        presetTrainingRepository.deleteById(id);
    }

    private PresetTrainingDto toDto(PresetTraining training) {
        return PresetTrainingDto.builder()
                .id(training.getId())
                .userId(training.getUserId())
                .presetName(training.getPresetName())
                .trainingMenus(trainingMapper.toMenuDtoList(training.getPresetTrainingMenus()))
                .build();
    }

    private PresetTraining toEntity(PresetTrainingDto dto) {
        PresetTraining preset = PresetTraining.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .presetName(dto.getPresetName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .presetTrainingMenus(List.of())
                .build();

        preset.setPresetTrainingMenus(toEntityMenus(dto.getTrainingMenus(), preset));
        return preset;
    }

    private List<PresetTrainingMenu> toEntityMenus(List<TrainingMenuDto> menuDtos, PresetTraining preset) {
        return menuDtos.stream()
                .map(menuDto -> {
                    PresetTrainingMenu menu = PresetTrainingMenu.builder()
                            .presetTraining(preset)
                            .displayOrder(menuDto.getDisplayOrder())
                            .name(menuDto.getName())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();

                    List<PresetTrainingMenuSet> sets = menuDto.getSets().stream()
                            .map(setDto -> PresetTrainingMenuSet.builder()
                                    .presetTrainingMenu(menu)
                                    .setOrder(setDto.getSetOrder())
                                    .reps(setDto.getReps())
                                    .weight(setDto.getWeight())
                                    .createdAt(LocalDateTime.now())
                                    .updatedAt(LocalDateTime.now())
                                    .build())
                            .toList();

                    menu.setSets(sets);
                    return menu;
                })
                .toList();
    }
}
