package com.lifttheearth.backend.controller;

import com.lifttheearth.backend.dto.training.TrainingDto;
import com.lifttheearth.backend.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping
    public List<TrainingDto> getAll() {
        return trainingService.getAll();
    }

    @GetMapping("/{id}")
    public TrainingDto getById(@PathVariable Long id) {
        return trainingService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto create(@RequestBody TrainingDto dto) {
        return trainingService.create(dto);
    }

    @PutMapping("/{id}")
    public TrainingDto update(@PathVariable Long id, @RequestBody TrainingDto dto) {
        return trainingService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        trainingService.delete(id);
    }
}
