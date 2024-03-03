package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

public record Category(
        @Id
        String id,
        String name,
        Integer numRacers
) {}
