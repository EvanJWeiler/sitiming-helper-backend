package com.evanweiler.sitiming.controller;

import com.evanweiler.sitiming.domain.Race;
import com.evanweiler.sitiming.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/races")
public class RaceController {
    private final RaceRepository raceRepository;

    @Autowired
    public RaceController(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @GetMapping()
    public List<Race> getAllRaces() {
        return raceRepository.getAllRaces();
    }
}
