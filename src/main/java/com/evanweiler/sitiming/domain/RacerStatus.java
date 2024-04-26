package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

public record RacerStatus(
        @Id
        String id,
        Integer bibNumber,
        String racerName,
        Integer cardNumber,
        String teamName,
        Boolean checkedIn,
        String categoryName
) {}
