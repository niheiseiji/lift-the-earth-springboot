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
@Table(name = "preset_trainings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresetTraining extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String presetName;

    @OneToMany(mappedBy = "presetTraining", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PresetTrainingMenu> presetTrainingMenus;
}
