package com.evanweiler.sitiming.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

public record CategoryWithStages(
        @Id
        String id,
        String name,
        List<Stage> stageList
) {}
