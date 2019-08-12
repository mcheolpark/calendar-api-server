package schedule.service;

public class PostResponse {
    public boolean isSuccess() {
        return success;
    }

    public PostResponse(boolean success) {
        this.success = success;
    }

    private boolean success;
}
