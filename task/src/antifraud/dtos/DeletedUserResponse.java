package antifraud.dtos;

public class DeletedUserResponse {
    private final String username;
    private final String status = "Deleted successfully!";

    public DeletedUserResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "DeletedUserResponse{" +
                "username='" + username + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
