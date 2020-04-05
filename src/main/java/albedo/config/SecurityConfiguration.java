package albedo.config;

import albedo.domain.User;
import albedo.service.UserService;
import albedo.utils.Flowables;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.security.authentication.providers.AuthoritiesFetcher;
import io.micronaut.security.authentication.providers.UserFetcher;

import java.util.Optional;

@Factory
public class SecurityConfiguration {

    private final UserService userService;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public UserFetcher userFetcher() {
        return username -> {
            final Optional<User> user = userService.findByUsername(username);
            return Flowables.just(user);
        };
    }

    @Bean
    public AuthoritiesFetcher authoritiesFetcher() {
        return username -> {
            final Optional<User> user = userService.findByUsername(username);
            return Flowables.just(user.map(User::getAuthorities));
        };
    }
}
