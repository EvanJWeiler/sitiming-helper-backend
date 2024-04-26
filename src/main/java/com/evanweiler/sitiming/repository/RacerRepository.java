package com.evanweiler.sitiming.repository;

import com.evanweiler.sitiming.domain.Racer;
import com.evanweiler.sitiming.domain.RacerResult;
import com.evanweiler.sitiming.domain.RacerStatus;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RacerRepository extends CrudRepository<RacerResult, String> {

    @Query("""
        SELECT
            e.ID AS 'id', e.RaceNumber AS 'bib_number', e.Name AS 'racer_name',
            sc.CardNumber as 'card_number', e.Club AS 'team_name'
        FROM Entry e
        JOIN EntryEvent ee on e.ID = ee.EntryID
        JOIN Class c on ee.ClassID = c.ID
        JOIN SiCard sc ON ee.ID = sc.EntryEventID
        WHERE ee.EventID = :raceId
        AND c.ID = :categoryId
        ORDER BY e.RaceNumber
    """)
    List<Racer> getRacersByCategoryId(
            @Param("raceId") String raceId,
            @Param("categoryId") String categoryId
    );

    @Query("""
        SELECT
            e.ID AS 'id', e.RaceNumber AS 'bib_number', e.Name AS 'racer_name',
            sc.CardNumber as 'card_number', e.Club AS 'team_name',
            ee.AllReturnedOrLostBroken AS 'checked_in', c.Name AS 'category_name'
        FROM Entry e
        JOIN EntryEvent ee on e.ID = ee.EntryID
        JOIN Class c on c.ID = ee.ClassID
        JOIN SiCard sc ON ee.ID = sc.EntryEventID
        WHERE ee.EventID = :raceId
        ORDER BY e.RaceNumber
    """)
    List<RacerStatus> getAllRacersStatus(
            @Param("raceId") String raceId
    );

    @Query("""
        SELECT
            e.ID AS 'id', e.RaceNumber AS 'bib_number', e.Name AS 'racer_name',
            sc.CardNumber as 'card_number', e.Club AS 'team_name',
            ee.AllReturnedOrLostBroken AS 'checked_in', c.Name AS 'category_name'
        FROM Entry e
        JOIN EntryEvent ee on e.ID = ee.EntryID
        JOIN Class c on c.ID = ee.ClassID
        JOIN SiCard sc ON ee.ID = sc.EntryEventID
        WHERE ee.EventID = :raceId
        AND c.ID = :categoryId
        ORDER BY e.RaceNumber
    """)
    List<RacerStatus> getRacersStatusByCategoryId(
            @Param("raceId") String raceId,
            @Param("categoryId") String categoryId
    );
}
