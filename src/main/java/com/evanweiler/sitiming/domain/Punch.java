package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

import java.util.Date;

public record Punch(
        @Id
        String id,
        Integer cardNumber,
        Integer controlCode,
        Date timeOfDay
) {}
