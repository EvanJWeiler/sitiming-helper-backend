package com.evanweiler.sitiming.controller;

import com.evanweiler.sitiming.domain.RacerResult;
import com.evanweiler.sitiming.domain.RacerStatus;
import com.evanweiler.sitiming.repository.RacerRepository;
import com.evanweiler.sitiming.service.ResultsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/racers")
public class RacerController {
    private final RacerRepository racerRepository;
    private final ResultsService resultsService;

    @GetMapping("/status")
    public List<RacerStatus> getRacersStatus(
            @RequestParam(name="raceId") String raceId,
            @RequestParam(name="categoryId", required = false) Optional<String> categoryId
    ) {
        if (categoryId.isPresent()) {
            return racerRepository.getRacersStatusByCategoryId(raceId, categoryId.get());
        }

        return racerRepository.getAllRacersStatus(raceId);
    }

    @GetMapping("/results")
    public List<RacerResult> getRacersResults(
            @RequestParam(name="raceId") String raceId,
            @RequestParam(name="categoryId") String categoryId
    ) {
        return resultsService.getResultsForCategory(raceId, categoryId);
    }
}
