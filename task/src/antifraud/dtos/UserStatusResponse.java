package antifraud.dtos;

public class UserStatusResponse {
    private final String status;

    public UserStatusResponse(String status) {
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
