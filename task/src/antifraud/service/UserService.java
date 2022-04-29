package antifraud.service;

import antifraud.dtos.UserRequest;
import antifraud.dtos.UserResponse;
import antifraud.model.User;
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
        Optional<User> userByUsername = userRepository.findUserByUsernameIgnoreCase(userRequest.getUsername());
        if (userByUsername.isPresent()) {
            return Optional.empty();
        }
        User user = new User(userRequest.getName(), userRequest.getUsername(), passwordEncoder.encode(userRequest.getPassword()));
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
}
