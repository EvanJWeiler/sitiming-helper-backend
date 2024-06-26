package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

public record Racer(
        @Id
        String id,
        Integer bibNumber,
        String racerName,
        Integer cardNumber,
        String teamName
) {}
