package antifraud.service;

import antifraud.model.User;
import antifraud.model.UserDetailsImpl;
import antifraud.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsernameIgnoreCase(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserDetailsImpl(user);
        }

        throw new UsernameNotFoundException("Not found: " + username);
    }
}
