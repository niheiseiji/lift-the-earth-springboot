package com.lifttheearth.backend.repository;

import com.lifttheearth.backend.domain.Training;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByUserIdOrderByPerformedAtDesc(Long userId);
}
