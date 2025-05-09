package com.lifttheearth.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.lifttheearth.backend.domain.common.Auditable;

@Entity
@Table(name = "preset_training_menus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetTrainingMenu extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "preset_training_id", nullable = false)
    private PresetTraining presetTraining;

    private Integer displayOrder;
    private String name;

    @OneToMany(mappedBy = "presetTrainingMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PresetTrainingMenuSet> sets;
}
