package com.evanweiler.sitiming.repository;

import com.evanweiler.sitiming.domain.Category;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {

    @Query("""
                SELECT 
                    c.ID AS 'id', c.Name AS 'name', COUNT(e.ID) AS 'num_racers'
                FROM Class c
                LEFT JOIN EntryEvent ee ON c.ID = ee.ClassID
                LEFT JOIN Entry E ON ee.EntryID = E.ID
                WHERE c.EventID = :raceId
                GROUP BY c.Name, c.ID
                ORDER BY c.Name
    """)
    List<Category> getCategoriesByRaceId(@Param("raceId") String raceId);
}
