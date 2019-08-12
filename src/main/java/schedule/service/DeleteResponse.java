package schedule.service;

public class DeleteResponse {
    public boolean isSuccess() {
        return success;
    }

    public DeleteResponse(boolean success) {
        this.success = success;
    }

    private boolean success;
}
