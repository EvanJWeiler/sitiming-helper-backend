package com.evanweiler.sitiming.service;

import com.evanweiler.sitiming.domain.Punch;
import com.evanweiler.sitiming.domain.RacerResult;
import com.evanweiler.sitiming.domain.Station;
import com.evanweiler.sitiming.repository.PunchRepository;
import com.evanweiler.sitiming.repository.RacerRepository;
import com.evanweiler.sitiming.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ResultsService {
    private final PunchRepository punchRepository;
    private final StationRepository stationRepository;
    private final RacerRepository racerRepository;

    private static final String TOTAL_TIME = "total_time";

    public List<RacerResult> getResultsForCategory(String raceId, String categoryId) {
        Set<String> catStages = getValidStageKeySet(stationRepository.getStationsByCategoryId(raceId, categoryId));
        List<Punch> catPunches = punchRepository.getPunchesForCategory(raceId, categoryId);

        return racerRepository.getRacersByCategoryId(raceId, categoryId)
                .stream()
                .map(racer -> new RacerResult(
                    racer.id(),
                    racer.bibNumber(),
                    racer.racerName(),
                    racer.teamName(),
                    buildResultsMapForRacer(racer.cardNumber(), catStages, catPunches)
                ))
                .sorted(Comparator.comparing(racer -> racer.resultsMap().get(TOTAL_TIME)))
                .toList();
    }

    private Map<String, Long> buildResultsMapForRacer(Integer cardNumber,
                                                      Set<String> validStages, List<Punch> catPunches) {
        Map<String, Long> returnMap = new LinkedHashMap<>();
        long totalTime = 0L;

        List<Punch> punchList = catPunches
                .stream()
                .filter(punch -> punch.cardNumber().equals(cardNumber))
                .toList();

        // iterate through list and generate list of times between punches, skipping excluded times
        for (int i = 0; i < punchList.size() - 1; i++) {
            long timeBetween = punchList.get(i + 1).timeOfDay().getTime() - punchList.get(i).timeOfDay().getTime();
            String key = punchList.get(i).controlCode().toString() + "/" + punchList.get(i + 1).controlCode().toString();

            if (validStages.contains(key)) {
                totalTime += timeBetween;
                returnMap.put(key, timeBetween);
            }
        }

        if (returnMap.values().size() == validStages.size()) {
            returnMap.put(TOTAL_TIME, totalTime);
        } else {
            returnMap.put(TOTAL_TIME, 999999999L);
        }

        return returnMap;
    }

    // TODO: error checking here in case category isn't setup correctly (i.e. station list is odd number long)
    private Set<String> getValidStageKeySet(List<Station> catStations) {
        Set<String> returnSet = new HashSet<>();

        for (int i = 0; i < catStations.size() - 1; i += 2) {
            returnSet.add(
                    catStations.get(i).controlCode().toString() +
                    "/" +
                    catStations.get(i + 1).controlCode().toString()
            );
        }

        return returnSet;
    }
}
