package antifraud.controller;

import antifraud.dtos.DeletedUserResponse;
import antifraud.dtos.UserRequest;
import antifraud.dtos.UserResponse;
import antifraud.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) {
        Optional<UserResponse> optionalUserResponse = userService.addUser(userRequest);
        return optionalUserResponse.map(response -> ResponseEntity.status(201).body(response)).orElseGet(() -> ResponseEntity.status(409).build());
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
}
