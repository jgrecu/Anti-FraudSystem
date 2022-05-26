package antifraud.service;

import antifraud.dtos.*;
import antifraud.exceptions.BadRequestException;
import antifraud.exceptions.HttpConflictException;
import antifraud.model.User;
import antifraud.model.UserStatus;
import antifraud.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserResponse> addUser(UserRequest userRequest) {
        int numUsers = userRepository.findAll().size();

        String role = "";
        boolean isEnabled = false;

        if (numUsers > 0) {
            role = "MERCHANT";
        } else {
            role = "ADMINISTRATOR";
            isEnabled = true;
        }

        Optional<User> userByUsername = userRepository.findUserByUsernameIgnoreCase(userRequest.getUsername());
        if (userByUsername.isPresent()) {
            return Optional.empty();
        }

        User user = new User(userRequest.getName(),
                userRequest.getUsername(), passwordEncoder.encode(userRequest.getPassword()), role, isEnabled);
        userRepository.save(user);
        return Optional.of(new UserResponse(user));
    }

    public List<UserResponse> listUsers() {
        List<User> userList = userRepository.findAll();

        return userList.stream().map(UserResponse::new).collect(Collectors.toList());
    }

    public boolean deleteUser(String username) {
        Optional<User> optionalUser = userRepository.findUserByUsernameIgnoreCase(username);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return true;
        }
        return false;
    }

    public Optional<UserResponse> editUserRole(EditUserRoleRequest editUserRoleRequest) {
        String requestRole = editUserRoleRequest.getRole();

        if (!"SUPPORT".equalsIgnoreCase(requestRole) &&
                !"MERCHANT".equalsIgnoreCase(requestRole)) {
            throw new BadRequestException("Unknown or not allowed role");
        }

        Optional<User> userByUsername = userRepository.findUserByUsernameIgnoreCase(editUserRoleRequest.getUsername());
        if (userByUsername.isPresent()) {
            User user = userByUsername.get();

            if (user.getRole().equals(requestRole)) {
                throw new HttpConflictException("This role is already assigned");
            }

            user.setRole(requestRole);
            userRepository.save(user);
            return Optional.of(new UserResponse(user));
        }
        return Optional.empty();
    }

    public Optional<StatusResponse> lockUnlockUser(UnlockUserRequest unlockUserRequest) {
        Optional<User> userByUsername = userRepository.findUserByUsernameIgnoreCase(unlockUserRequest.getUsername());
        if (userByUsername.isPresent()) {
            User user = userByUsername.get();
            if (user.getRole().equals("ADMINISTRATOR")) {
                throw new BadRequestException("Cannot lock ADMINISTRATOR");
            }
            boolean enabled = unlockUserRequest.getOperation().equals(UserStatus.UNLOCK);
            user.setEnabled(enabled);
            userRepository.save(user);
            String status = String.format("User %s %sed!", user.getUsername(), unlockUserRequest.getOperation().toString().toLowerCase());
            return Optional.of(new StatusResponse(status));
        }
        return Optional.empty();
    }
}
