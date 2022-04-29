package antifraud.dtos;

import antifraud.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class UserResponse {
    @NotEmpty
    private final Long id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String username;

    public UserResponse(Long id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
