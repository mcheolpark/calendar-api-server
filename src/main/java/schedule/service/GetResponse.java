package schedule.service;
import java.util.List;

public class GetResponse {
    private List values;

    public GetResponse(List values) {
        this.values = values;
    }

    public List getValues() {
        return this.values;
    }
}
