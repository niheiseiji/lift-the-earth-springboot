package com.lifttheearth.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.lifttheearth.backend.domain.common.Auditable;

@Entity
@Table(name = "training_menus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingMenu extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    private Integer displayOrder;
    private String name;

    @OneToMany(mappedBy = "trainingMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingMenuSet> sets;
}
