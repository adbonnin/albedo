package albedo.service;

import albedo.domain.User;
import albedo.repository.UserRepository;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.Set;

@Singleton
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Set<User> findAll() {
        return userRepository.findAll();
    }

    public boolean save(User user) {
        return userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
