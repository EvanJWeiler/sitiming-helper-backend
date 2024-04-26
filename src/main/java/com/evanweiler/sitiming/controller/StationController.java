package com.evanweiler.sitiming.controller;

import com.evanweiler.sitiming.domain.Station;
import com.evanweiler.sitiming.repository.StationRepository;
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
@RequestMapping("/api/stations")
public class StationController {
    private final StationRepository stationRepository;

    @GetMapping()
    public List<Station> getStations(
            @RequestParam(name="raceId") String raceId,
            @RequestParam(name="categoryId", required = false) Optional<String> categoryId
    ) {
        if (categoryId.isPresent()) {
            return stationRepository.getStationsByCategoryId(raceId, categoryId.get());
        }

        return stationRepository.getStations(raceId);
    }
}
