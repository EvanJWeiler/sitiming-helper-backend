package com.evanweiler.sitiming.controller;

import com.evanweiler.sitiming.domain.CategoryWithStages;
import com.evanweiler.sitiming.domain.CategoryWithStatus;
import com.evanweiler.sitiming.repository.CategoryWithStatusRepository;
import com.evanweiler.sitiming.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryWithStatusRepository categoryWithStatusRepository;
    private final CategoryService categoryService;

    @GetMapping("/status")
    public List<CategoryWithStatus> getCategoryStatusByRaceId(
            @RequestParam(name = "raceId") String raceId
    ) {
        return categoryWithStatusRepository.getCategoryStatusByRaceId(raceId);
    }

    @GetMapping("/stages")
    public List<CategoryWithStages> getCategoryWithStagesByRaceId(
            @RequestParam(name = "raceId") String raceId
    ) {
        return categoryService.getCategoryResultByRaceId(raceId);
    }
}
