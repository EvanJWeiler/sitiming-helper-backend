package com.evanweiler.sitiming.service;

import com.evanweiler.sitiming.domain.Category;
import com.evanweiler.sitiming.domain.CategoryWithStages;
import com.evanweiler.sitiming.domain.Stage;
import com.evanweiler.sitiming.domain.Station;
import com.evanweiler.sitiming.repository.CategoryRepository;
import com.evanweiler.sitiming.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final StationRepository stationRepository;

    public List<CategoryWithStages> getCategoryResultByRaceId(String raceId) {
        return categoryRepository.getCategoryByRaceId(raceId)
                .stream()
                .map(category -> new CategoryWithStages(
                        category.id(),
                        category.name(),
                        getStagesForCategoryId(raceId, category.id())
                )).toList();
    }

    private List<Stage> getStagesForCategoryId(String raceId, String categoryId) {
        List<Stage> stages = new ArrayList<>();
        List<Station> catStations = stationRepository.getStationsByCategoryId(raceId, categoryId);

        for (int i = 0; i < catStations.size() - 1; i += 2) {
            stages.add(new Stage(
                    catStations.get(i).controlCode().toString() + "/" + catStations.get(i + 1).controlCode().toString(),
                    catStations.get(i + 1).stageLabel().substring(0, catStations.get(i + 1).stageLabel().indexOf(";"))
            ));
        }

        return stages;
    }
}
