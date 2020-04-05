package albedo.security;

import io.micronaut.security.authentication.providers.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Singleton;

@Singleton
public class BCryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
