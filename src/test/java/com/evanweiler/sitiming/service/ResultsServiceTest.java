package com.evanweiler.sitiming.service;

import com.evanweiler.sitiming.domain.Punch;
import com.evanweiler.sitiming.domain.Racer;
import com.evanweiler.sitiming.domain.RacerResult;
import com.evanweiler.sitiming.domain.Station;
import com.evanweiler.sitiming.repository.PunchRepository;
import com.evanweiler.sitiming.repository.RacerRepository;
import com.evanweiler.sitiming.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ResultsServiceTest {
    @Mock
    private PunchRepository punchRepository;
    @Mock
    private StationRepository stationRepository;
    @Mock
    private RacerRepository racerRepository;

    @InjectMocks
    private ResultsService resultsService;

    private static final String raceId = "raceId";
    private static final String categoryId = "categoryId";

    @BeforeEach
    void setup() {
        Mockito.lenient().when(racerRepository.getRacersByCategoryId(raceId, categoryId))
                .thenReturn(createCategoryRacerList());
    }

    // ex: 2023 Raging River Sunday, Jr. Sport category did a shortened version of CCDH
    @Test
    void getResultsForCategory_WhenHalfwayEndStation() {
        long minute = 1000L * 60 * 60;

        when(stationRepository.getStationsByCategoryId(raceId, categoryId))
                .thenReturn(createEdgeCaseStationList());
        when(punchRepository.getPunchesForCategory(raceId, categoryId))
                .thenReturn(createEdgeCasePunchList());

        List<RacerResult> racerResults = resultsService.getResultsForCategory("raceId", "categoryId");

        assertEquals(1, racerResults.size());

        Map<String, Long> resultsMap = racerResults.get(0).resultsMap();

        assertEquals(4L * minute, resultsMap.get("50/21"));
        assertEquals(6L * minute, resultsMap.get("30/31"));
        assertEquals(7L * minute, resultsMap.get("40/41"));
        assertEquals(17L * minute, resultsMap.get("total_time"));
        assertEquals(4, resultsMap.size());
    }

    private List<Punch> createEdgeCasePunchList() {
        // Jan 31st 2023 at noon
        long baseTime = 1675195200000L;
        long minute = 1000L * 60 * 60;

        // erroneous end punch at beginning, halfway punch for stage 1
        Punch p1 = new Punch(UUID.randomUUID().toString(), 1234567, 41, new Date(baseTime + (2 * minute)));
        Punch p2 = new Punch(UUID.randomUUID().toString(), 1234567, 50, new Date(baseTime + (5 * minute)));
        Punch p3 = new Punch(UUID.randomUUID().toString(), 1234567, 50, new Date(baseTime + (6 * minute)));
        Punch p4 = new Punch(UUID.randomUUID().toString(), 1234567, 21, new Date(baseTime + (10 * minute)));
        Punch p5 = new Punch(UUID.randomUUID().toString(), 1234567, 30, new Date(baseTime + (15 * minute)));
        Punch p6 = new Punch(UUID.randomUUID().toString(), 1234567, 31, new Date(baseTime + (21 * minute)));
        Punch p7 = new Punch(UUID.randomUUID().toString(), 1234567, 31, new Date(baseTime + (22 * minute)));
        Punch p8 = new Punch(UUID.randomUUID().toString(), 1234567, 40, new Date(baseTime + (25 * minute)));
        Punch p9 = new Punch(UUID.randomUUID().toString(), 1234567, 41, new Date(baseTime + (32 * minute)));

        return Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    private List<Station> createEdgeCaseStationList() {
        Station s5S = new Station(UUID.randomUUID().toString(), 50, 0, ";;", false);
        Station s5F = new Station(UUID.randomUUID().toString(), 21, 1,"S5: Stage 5;;", false);
        Station s3S = new Station(UUID.randomUUID().toString(), 30, 2,";;", true);
        Station s3F = new Station(UUID.randomUUID().toString(), 31, 3,"S3: Stage 3;;", false);
        Station s4S = new Station(UUID.randomUUID().toString(), 40, 4,";;", true);
        Station s4F = new Station(UUID.randomUUID().toString(), 41, 5,"S4: Stage 4;;", false);

        return Arrays.asList(s5S, s5F, s3S, s3F, s4S, s4F);
    }

    private List<Racer> createCategoryRacerList() {
        Racer racer = new Racer(
                UUID.randomUUID().toString(),
                123,
                "racerName",
                1234567,
                "teamName"
        );

        return List.of(racer);
    }
}
