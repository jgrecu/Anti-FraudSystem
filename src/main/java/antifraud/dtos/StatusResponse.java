package antifraud.dtos;

public class StatusResponse {
    private final String status;

    public StatusResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "UserStatusResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}
