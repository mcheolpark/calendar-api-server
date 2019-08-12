package schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import schedule.repository.Schedule;
import schedule.repository.ScheduleRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //  임시테스
    @Bean
    public CommandLineRunner demo(ScheduleRepository repository) {
        return (args) -> {
            // save a couple of customers
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try{
                Date startDate = simpleDateFormat.parse("2019-08-21 01:00:00");
                Date endDate = simpleDateFormat.parse("2019-08-21 03:00:00");
                repository.save(new Schedule(startDate.getTime(), endDate.getTime(), "8월 데이터"));


                startDate = simpleDateFormat.parse("2019-09-21 01:00:00");
                endDate = simpleDateFormat.parse("2019-09-21 03:00:00");
                repository.save(new Schedule(startDate.getTime(), endDate.getTime(), "9월 데이터"));
            }
            catch (ParseException e) {}
        };
    }
}
