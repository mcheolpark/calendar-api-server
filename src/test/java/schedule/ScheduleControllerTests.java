package schedule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import schedule.repository.Schedule;
import schedule.service.DuplicationScheduleException;
import schedule.service.ScheduleService;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleControllerTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void insertAndSearch() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try{
            Date startDate = simpleDateFormat.parse("2019-10-21 01:00:00");
            Date endDate = simpleDateFormat.parse("2019-10-21 03:00:00");
            scheduleService.saveSchedule(new Schedule(startDate.getTime(), endDate.getTime(), "8월 데이터"));

            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDate = null;
            try{
                startDate = simpleDateFormat.parse("2019-10-01");
            }
            catch (ParseException e) {}

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.MONTH, 1);
            Date nextMonth = new Date(cal.getTimeInMillis());

            List list = scheduleService.searchSchedule(startDate.getTime(), nextMonth.getTime());

            Assert.assertEquals(list.size(), 1);

        }
        catch (ParseException e) {}
    }

    @Test
    public void checkDuplicationException() throws Exception {
        thrown.expect(DuplicationScheduleException.class);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try{
            Date startDate = simpleDateFormat.parse("2022-10-21 01:00:00");
            Date endDate = simpleDateFormat.parse("2022-10-21 03:00:00");
            scheduleService.saveSchedule(new Schedule(startDate.getTime(), endDate.getTime(), "2022년 8월 데이터"));

            scheduleService.saveSchedule(new Schedule(startDate.getTime(), endDate.getTime(), "2022년 8월 데이터"));
        }
        catch (ParseException e) {}
    }

    @Test
    public void delete() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = simpleDateFormat.parse("2019-10-01");

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.MONTH, 1);
        Date nextMonth = new Date(cal.getTimeInMillis());

        List list = scheduleService.searchSchedule(startDate.getTime(), nextMonth.getTime());
        Assert.assertEquals(list.size(), 1);

        scheduleService.deleteSchedule(((Schedule)list.get(0)).getId());

        list = scheduleService.searchSchedule(startDate.getTime(), nextMonth.getTime());
        Assert.assertEquals(list.size(), 0);
    }

    @Test
    public void update() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try{
            Date startDate = simpleDateFormat.parse("2023-10-21 01:00:00");
            Date endDate = simpleDateFormat.parse("2023-10-21 03:00:00");
            scheduleService.saveSchedule(new Schedule(startDate.getTime(), endDate.getTime(), "2023년 10월 데이터"));

            Date startMonth = simpleDateFormat.parse("2023-10-01");
            Date endMonth = simpleDateFormat.parse("2023-11-01");
            List list = scheduleService.searchSchedule(startMonth.getTime(), endMonth.getTime());


            Schedule schedule = new Schedule(startDate.getTime(), endDate.getTime(), "2023년 10월 데이터 - update");
            schedule.setId(((Schedule)list.get(0)).getId());
            scheduleService.updateSchedule(schedule);

            list = scheduleService.searchSchedule(startMonth.getTime(), endMonth.getTime());
            Assert.assertEquals(list.size(), 1);
            Assert.assertEquals(((Schedule)list.get(0)).getTitle(), "2023년 10월 데이터 - update");
        }
        catch (ParseException e) {}
    }
}
