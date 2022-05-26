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
    @NotBlank
    private final String role;

    public UserResponse(Long id, String name, String username, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.role = role;
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.role = user.getRole();
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

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
