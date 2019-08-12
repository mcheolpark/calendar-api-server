package schedule.service;

/**
 * Created by user on 2019. 8. 9..
 */
public class PutResponse {
    public boolean isSuccess() {
        return success;
    }

    public PutResponse(boolean success) {
        this.success = success;
    }

    private boolean success;
}
