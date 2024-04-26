package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

public record RaceInfo(
        @Id
        String id,
        String name,
        Integer totalRacers,
        Integer racersOnCourse
) {}
