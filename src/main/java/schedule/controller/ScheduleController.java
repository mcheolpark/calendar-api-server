package schedule.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import schedule.service.DuplicationScheduleException;
import schedule.service.PostResponse;
import schedule.service.PutResponse;
import schedule.service.GetResponse;
import schedule.service.DeleteResponse;
import schedule.service.ScheduleService;
import schedule.repository.Schedule;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:9000")
@RestController
public class ScheduleController {
    //private static final String template = "Schedule, %s!";
    //private final AtomicLong counter = new AtomicLong();

    @Autowired
    private ScheduleService scheduleService;


    @GetMapping("/schedule/health-check")
    public ResponseEntity schedule() {
        return new ResponseEntity<PostResponse>(new PostResponse(true), HttpStatus.OK);
    }

    @GetMapping(value = "/schedule/{year}/{month}")
    public ResponseEntity searchSchedule(@PathVariable("year") String year, @PathVariable("month") String month) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            try{
                startDate = simpleDateFormat.parse(year + "-" + month + "-" + "01");
            }
            catch (ParseException e) {}

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.MONTH, 1);
            Date nextMonth = new Date(cal.getTimeInMillis());

            List list = scheduleService.searchSchedule(startDate.getTime(), nextMonth.getTime());
            GetResponse getResponse = new GetResponse(list);

            return new ResponseEntity<GetResponse>(getResponse, HttpStatus.OK);
        }
        catch (Throwable t) {
            return new ResponseEntity<GetResponse>(new GetResponse(null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/schedule/{id}")
    public ResponseEntity deleteSchedule(@PathVariable("id") long id) {
        try {
            scheduleService.deleteSchedule(id);

            return new ResponseEntity<DeleteResponse>(new DeleteResponse(true), HttpStatus.OK);
        } catch (Throwable t) {
            return new ResponseEntity<DeleteResponse>(new DeleteResponse(false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/post-schedule", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity postSchedule(@RequestBody Schedule schedule) {
        try {
            scheduleService.saveSchedule(schedule);
            return new ResponseEntity<PostResponse>(new PostResponse(true), HttpStatus.OK);
        }
        catch(DuplicationScheduleException e) {
            return new ResponseEntity<PostResponse>(new PostResponse(false), HttpStatus.BAD_REQUEST);
        }
        catch (Throwable t) {
            return new ResponseEntity<PostResponse>(new PostResponse(false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/put-schedule", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity putSchedule(@RequestBody Schedule schedule) {
        try {
            scheduleService.updateSchedule(schedule);
            return new ResponseEntity<PutResponse>(new PutResponse(true), HttpStatus.OK);
        }
        catch(DuplicationScheduleException e) {
            return new ResponseEntity<PostResponse>(new PostResponse(false), HttpStatus.BAD_REQUEST);
        }
        catch (Throwable t) {
            return new ResponseEntity<PutResponse>(new PutResponse(false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
