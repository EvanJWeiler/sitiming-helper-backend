package com.evanweiler.sitiming.controller;

import com.evanweiler.sitiming.domain.Category;
import com.evanweiler.sitiming.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping()
    public List<Category> getCategoriesByRaceId(
            @RequestParam(name="raceId") String raceId
    ) {
        return categoryRepository.getCategoriesByRaceId(raceId);
    }
}
