package com.evanweiler.sitiming.repository;

import com.evanweiler.sitiming.domain.RaceEntry;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceEntryRepository extends CrudRepository<RaceEntry, String> {

    @Query("""
        SELECT
            ee.ID AS 'id',
            ee.AllReturnedOrLostBroken AS 'checked_in'
        FROM EntryEvent ee
        WHERE ee.EventID = :raceId
        AND ee.ClassID = :categoryId
    """)
    List<RaceEntry> getEntriesForCategory(
            @Param("raceId") String raceId,
            @Param("categoryId") String categoryId
    );
}
