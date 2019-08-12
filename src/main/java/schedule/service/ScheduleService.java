package schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.repository.ScheduleRepository;
import schedule.repository.Schedule;
import org.apache.commons.collections4.IterableUtils;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> searchSchedule(long startDate, long nextDate) {
        List mutableList = IterableUtils.toList(scheduleRepository.findByStart(startDate, nextDate));

        return mutableList;
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    public void saveSchedule(Schedule schedule) throws Exception{
        List mutableList = IterableUtils.toList(scheduleRepository.findDublicatieScheduleBySave(schedule.getStart(), schedule.getEnd()));
        if(mutableList.size() > 0) {
            throw new DuplicationScheduleException();
        }
        scheduleRepository.save(schedule);
    }

    public void updateSchedule(Schedule schedule) throws Exception{
        List mutableList = IterableUtils.toList(scheduleRepository.findDublicatieScheduleByUpdate(schedule.getId(), schedule.getStart(), schedule.getEnd()));
        if(mutableList.size() > 0) {
            throw new DuplicationScheduleException();
        }
        scheduleRepository.save(schedule);
    }
}
