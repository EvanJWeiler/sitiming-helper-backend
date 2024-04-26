package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

public record RaceEntry(
        @Id
        String id,
        Boolean checkedIn
) {}
