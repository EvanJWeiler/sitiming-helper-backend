package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

public record Racer(
        @Id
        String id,
        String name,
        String teamName,
        Integer bibNumber,
        String categoryId,
        String categoryName,
        Boolean checkedIn,
        Integer cardNumber
) {}
