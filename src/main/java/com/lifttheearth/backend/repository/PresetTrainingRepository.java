package com.lifttheearth.backend.repository;

import com.lifttheearth.backend.domain.PresetTraining;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PresetTrainingRepository extends JpaRepository<PresetTraining, Long> {
    List<PresetTraining> findByUserId(Long userId);
}