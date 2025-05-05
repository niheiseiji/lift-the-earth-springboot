package com.lifttheearth.backend.dto.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TrainingSummaryDto {
    // 総リフト重量
    private int totalLiftedWeightKg;
    // 直近7日間のトレーニング回数
    private int trainingsLast7Days;
    // 直近30日間のトレーニング回数
    private int trainingsLast30Days;
    // 累計トレーニング回数
    private int totalTrainings;

    // BIG3
    private int maxBenchPress;
    private int maxDeadlift;
    private int maxSquat;
    private int big3TotalWeight;
}