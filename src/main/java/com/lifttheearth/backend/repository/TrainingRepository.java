package com.lifttheearth.backend.repository;

import com.lifttheearth.backend.domain.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}
