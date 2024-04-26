package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

public record CategoryWithStatus(
        @Id
        String id,
        String name,
        Integer totalRacers,
        Integer racersOnCourse
) {}
