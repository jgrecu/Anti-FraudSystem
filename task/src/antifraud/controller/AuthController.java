package antifraud.controller;

import antifraud.dtos.*;
import antifraud.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) {
        Optional<UserResponse> optionalUserResponse = userService.addUser(userRequest);
        return optionalUserResponse.map(response -> ResponseEntity.status(201).body(response))
                .orElseGet(() -> ResponseEntity.status(409).build());
    }

    @GetMapping("/list")
    public List<UserResponse> listAllUsers() {
        return userService.listUsers();
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<DeletedUserResponse> deleteUser(@PathVariable String username) {
        boolean deletedUser = userService.deleteUser(username);
        if (deletedUser) {
            return ResponseEntity.ok(new DeletedUserResponse(username));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/role")
    public ResponseEntity<UserResponse> changeUserRole(@RequestBody @Valid EditUserRoleRequest editUserRoleRequest) {

        Optional<UserResponse> optionalUserResponse = userService.editUserRole(editUserRoleRequest);

        if (optionalUserResponse.isPresent()) {
            UserResponse userResponse = optionalUserResponse.get();
            return ResponseEntity.ok(userResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/access")
    public ResponseEntity<StatusResponse> lockUnlockUser(@RequestBody @Valid UnlockUserRequest unlockUserRequest) {
        Optional<StatusResponse> userStatusResponse = userService.lockUnlockUser(unlockUserRequest);

        return userStatusResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
