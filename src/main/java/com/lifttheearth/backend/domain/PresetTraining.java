package com.lifttheearth.backend.domain;

import java.util.List;

import com.lifttheearth.backend.domain.common.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
