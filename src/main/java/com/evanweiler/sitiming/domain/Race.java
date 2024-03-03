package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

public record Race(
        @Id
        String id,
        String name
) {}
