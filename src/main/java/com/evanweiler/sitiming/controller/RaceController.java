package com.evanweiler.sitiming.controller;

import com.evanweiler.sitiming.domain.Race;
import com.evanweiler.sitiming.domain.RaceInfo;
import com.evanweiler.sitiming.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/races")
public class RaceController {
    private final RaceRepository raceRepository;

    @GetMapping()
    public List<Race> getAllRaces() {
        return raceRepository.getAllRaces();
    }

    @GetMapping("/info")
    public RaceInfo getRaceInfo(
            @RequestParam(name="raceId") String raceId
    ) {
        return raceRepository.getRaceInfo(raceId);
    }
}
