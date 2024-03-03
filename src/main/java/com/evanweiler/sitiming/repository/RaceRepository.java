package com.evanweiler.sitiming.repository;

import com.evanweiler.sitiming.domain.Race;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
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
}
