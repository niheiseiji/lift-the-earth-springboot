package com.lifttheearth.backend.domain;

import com.lifttheearth.backend.domain.common.Auditable;

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
public class TrainingMenuSet extends Auditable {
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
