package com.evanweiler.sitiming.repository;

import com.evanweiler.sitiming.domain.Racer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RacerRepository extends CrudRepository<Racer, String> {

    @Query("""
                SELECT
                    e.ID AS 'id', e.Name AS 'name', e.Club AS 'team_name', e.RaceNumber AS 'bib_number',
                    ee.ClassID AS 'category_id', c.Name AS 'category_name', ee.AllReturnedOrLostBroken AS 'checked_in',
                    sc.CardNumber AS 'card_number'
                FROM EntryEvent ee
                JOIN Entry e ON ee.EntryID = e.ID
                JOIN Class c ON ee.ClassID = c.ID
                JOIN SiCard sc ON ee.ID = sc.EntryEventID
                WHERE ee.EventID = :raceId
                ORDER BY e.RaceNumber
    """)
    List<Racer> getRacersByRaceId(@Param("raceId") String raceId);
}
