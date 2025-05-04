package com.lifttheearth.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "training_menu_sets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingMenuSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_menu_id", nullable = false)
    private TrainingMenu trainingMenu;

    private Integer setOrder;
    private Integer reps;
    private Integer weight;
}
