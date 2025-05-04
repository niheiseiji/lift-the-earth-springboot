package com.lifttheearth.backend.controller;

import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.dto.training.TrainingDto;
import com.lifttheearth.backend.security.OwnerCheck;
import com.lifttheearth.backend.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping
    public List<TrainingDto> getAll(@AuthenticationPrincipal User user) {
        return trainingService.getAllByUser(user.getId());
    }

    @OwnerCheck
    @GetMapping("/{id}")
    public TrainingDto getById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return trainingService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto create(@AuthenticationPrincipal User user, @RequestBody TrainingDto dto) {
        dto.setUserId(user.getId());
        return trainingService.create(dto);
    }

    @OwnerCheck
    @PutMapping("/{id}")
    public TrainingDto update(@PathVariable Long id, @RequestBody TrainingDto dto,
            @AuthenticationPrincipal User user) {
        dto.setUserId(user.getId());
        return trainingService.update(id, dto);
    }

    @OwnerCheck
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        trainingService.delete(id);
    }
}
