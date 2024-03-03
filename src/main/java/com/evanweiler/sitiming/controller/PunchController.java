package com.evanweiler.sitiming.controller;

import com.evanweiler.sitiming.domain.Punch;
import com.evanweiler.sitiming.repository.PunchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/punches")
public class PunchController {

    private final PunchRepository punchRepository;

    @Autowired
    public PunchController(PunchRepository punchRepository) {
        this.punchRepository = punchRepository;
    }

    @GetMapping()
    public List<Punch> getPunchesByRaceId(
            @RequestParam(name="raceId") String raceId
    ) {
        return punchRepository.getPunchesByRaceId(raceId);
    }
}
