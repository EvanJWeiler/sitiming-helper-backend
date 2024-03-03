package com.evanweiler.sitiming.repository;

import com.evanweiler.sitiming.domain.Punch;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PunchRepository extends CrudRepository<Punch, String> {

    @Query("""
                SELECT
                    dp.ID AS 'id', dp.CardNumber AS 'card_number',
                    dp.ControlCode AS 'control_code', dp.CalculatedTimeOfDay AS 'time_of_day'
                FROM Download d
                JOIN DownloadPunch dp ON d.ID = dp.DownloadID
                WHERE d.EventID = :raceId
                AND dp.ControlCode >= 10
                AND dp.StationMode = 2
                ORDER BY dp.CardNumber, dp.VisitedOrder;
    """)
    List<Punch> getPunchesByRaceId(@Param("raceId") String raceId);
}
