package antifraud.dtos;

import antifraud.model.UserStatus;

import javax.validation.constraints.NotBlank;

public class UnlockUserRequest {
    @NotBlank
    private String username;

    private UserStatus operation;

    public UnlockUserRequest() {
    }

    public UnlockUserRequest(String username, UserStatus operation) {
        this.username = username;
        this.operation = operation;
    }

    public String getUsername() {
        return username;
    }

    public UserStatus getOperation() {
        return operation;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOperation(UserStatus operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "UnlockUserRequest{" +
                "username='" + username + '\'' +
                ", operation=" + operation +
                '}';
    }
}
