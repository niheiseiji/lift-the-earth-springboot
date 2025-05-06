package com.lifttheearth.backend.controller;

import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.dto.training.PresetTrainingDto;
import com.lifttheearth.backend.service.PresetTrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/api/preset-trainings")
@RequiredArgsConstructor
public class PresetTrainingController {

    private final PresetTrainingService presetTrainingService;

    @GetMapping
    public List<PresetTrainingDto> getAll(@AuthenticationPrincipal User user) {
        return presetTrainingService.getPresetsByUserId(user.getId());
    }

    @PostMapping
    public PresetTrainingDto create(@AuthenticationPrincipal User user, @RequestBody PresetTrainingDto dto) {
        dto.setUserId(user.getId());
        return presetTrainingService.createPreset(dto);
    }

    @GetMapping("/{id}")
    public PresetTrainingDto getById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return presetTrainingService.getPresetById(id, user.getId());
    }

    @PutMapping("/{id}")
    public PresetTrainingDto update(@PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestBody PresetTrainingDto dto) {
        dto.setUserId(user.getId());
        return presetTrainingService.updatePreset(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        presetTrainingService.deletePreset(id);
    }
}
