package com.evanweiler.sitiming.repository;

import com.evanweiler.sitiming.domain.CategoryWithStatus;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryWithStatusRepository extends CrudRepository<CategoryWithStatus, String> {

    @Query("""
        SELECT
            c.ID AS 'id', c.Name AS 'name', COUNT(e.ID) AS 'total_racers',
            sum(IIF(ee.AllReturnedOrLostBroken = 0, 1, 0)) AS 'racers_on_course'
        FROM Class c
        LEFT JOIN EntryEvent ee ON c.ID = ee.ClassID
        LEFT JOIN Entry E ON ee.EntryID = E.ID
        WHERE c.EventID = :raceId
        GROUP BY c.Name, c.ID
        ORDER BY IIF(COUNT(e.ID) = 0, 9999, 0), c.Name
    """)
    List<CategoryWithStatus> getCategoryStatusByRaceId(
            @Param("raceId") String raceId
    );
}
