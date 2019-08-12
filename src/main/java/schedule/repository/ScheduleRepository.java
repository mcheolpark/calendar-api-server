package schedule.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    @Query(value = "select a from Schedule a where a.start > :start AND a.start < :end order by start ASC")
    List<Schedule> findByStart(@Param("start") long start, @Param("end") long end);
    //List<Schedule> findByStartAtBetween(Date start, Date end);

    @Query(value = "select a from Schedule a where (a.start <= :start AND :start < a.end) OR (a.start < :end AND :end <= a.end)")
    List<Schedule> findDublicatieScheduleBySave(@Param("start") long start, @Param("end") long end);

    @Query(value = "select a from Schedule a where (a.id <> :id) AND ((a.start <= :start AND :start < a.end) OR (a.start < :end AND :end <= a.end))")
    List<Schedule> findDublicatieScheduleByUpdate(@Param("id") long id, @Param("start") long start, @Param("end") long end);
}
