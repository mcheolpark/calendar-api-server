package schedule.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private long start;
    private long end;
    private String title;

    public Schedule() {}

    public Schedule(long start, long end, String title) {
        this.start = start;
        this.end = end;
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format(
                "Schedule[id=%d, start='%d', end='%d', title='%s']",
                id, start , end, title);
    }

    public long getId() {
        return id;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public void setId(long id) {
        this.id = id;
    }
}
