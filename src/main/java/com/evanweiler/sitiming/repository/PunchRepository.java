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
            dp.ID AS 'id', dp.CardNumber as 'card_number', dp.ControlCode AS 'control_code', dp.CalculatedTimeOfDay AS 'time_of_day'
        FROM DownloadPunch dp
        JOIN Download d ON dp.DownloadID = d.ID
        JOIN Class c ON d.EventId = c.EventID
        WHERE d.EventID = :raceId
        AND c.ID = :categoryId
        AND dp.StationMode = 2
        ORDER BY dp.VisitedOrder;
    """)
    List<Punch> getPunchesForCategory(
            @Param("raceId") String raceId,
            @Param("categoryId") String categoryId
    );
}
