package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

public record Station(
        @Id
        String id,
        Integer controlCode,
        Integer orderNum,
        String stageLabel,
        boolean excludedFromTime
) {}
