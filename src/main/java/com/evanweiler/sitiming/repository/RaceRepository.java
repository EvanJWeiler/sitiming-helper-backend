package com.evanweiler.sitiming.repository;

import com.evanweiler.sitiming.domain.Race;
import com.evanweiler.sitiming.domain.RaceInfo;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceRepository extends CrudRepository<Race, String> {

    @Query("""
        SELECT
            e.ID as 'id', e.Name as 'name'
        FROM Event e
        order by e.Date desc
    """)
    List<Race> getAllRaces();

    @Query("""
        SELECT
            e.ID as 'id', e.Name AS 'name', COUNT(ee.ID) AS 'total_racers',
            sum(IIF(ee.AllReturnedOrLostBroken = 0, 1, 0)) AS 'racers_on_course'
        FROM Event e
        LEFT JOIN EntryEvent ee
            ON e.ID = ee.EventID
            AND ee.ClassID IS NOT NULL
        WHERE e.ID = :raceId
        GROUP BY e.ID, e.Name;
    """)
    RaceInfo getRaceInfo(
            @Param("raceId") String raceId
    );
}
