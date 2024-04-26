package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

import java.util.Map;

public record RacerResult(
        @Id
        String id,
        Integer bibNumber,
        String racerName,
        String teamName,
        Map<String, Long> resultsMap
) {}
