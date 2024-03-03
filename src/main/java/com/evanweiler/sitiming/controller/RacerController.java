package com.evanweiler.sitiming.controller;

import com.evanweiler.sitiming.domain.Racer;
import com.evanweiler.sitiming.repository.RacerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/racers")
public class RacerController {

    private final RacerRepository racerRepository;

    @Autowired
    public RacerController(RacerRepository racerRepository) {
        this.racerRepository = racerRepository;
    }

    @GetMapping()
    public List<Racer> getRacersByRaceId(
            @RequestParam(name="raceId") String raceId
    ) {
        return racerRepository.getRacersByRaceId(raceId);
    }
}
