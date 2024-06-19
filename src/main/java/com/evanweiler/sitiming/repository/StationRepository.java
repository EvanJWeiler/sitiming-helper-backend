package com.evanweiler.sitiming.repository;

import com.evanweiler.sitiming.domain.Station;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends CrudRepository<Station, String> {

    @Query("""
        SELECT
            cm.ID AS 'id', cm.PublishedCode AS 'control_code',
            cm.Description AS 'stage_description'
        FROM ControlMaster cm
        WHERE cm.EventID = :raceId
        AND cm.StationMode1 = 2
        ORDER BY cm.PublishedCode;
    """)
    List<Station> getStations(
            @Param("raceId") String raceId
    );

    @Query("""
        SELECT
            cm.ID AS 'id', cm.PublishedCode AS 'control_code',
            cc.Number AS 'num_order', CAST(cc.ExcludeFromTime AS BIT) AS 'excluded_from_time',
            IIF(cc.Activities IS NULL, cc.StageName, SUBSTRING(cc.Activities, 1, LEN(cc.Activities) - 2)) AS 'stage_label'
        FROM ControlMaster cm
        JOIN CourseControl cc ON cm.ID = cc.ControlMasterID
        JOIN Class c ON cc.CourseID = c.CourseID
        WHERE c.EventID = :raceId
        AND c.ID = :categoryId
        AND cm.StationMode1 = 2
        ORDER BY cc.Number;
    """)
    List<Station> getStationsByCategoryId(
            @Param("raceId") String raceId,
            @Param("categoryId") String categoryId
    );
}
