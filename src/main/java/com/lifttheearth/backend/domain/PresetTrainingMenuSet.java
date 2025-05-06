package com.lifttheearth.backend.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preset_training_menu_sets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetTrainingMenuSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "preset_training_menu_id", nullable = false)
    private PresetTrainingMenu presetTrainingMenu;

    private Integer setOrder;
    private Integer reps;
    private Integer weight;

    private Long createdUserId;
    private Long updatedUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
