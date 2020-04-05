package albedo.repository;

import albedo.domain.User;
import albedo.service.JsonProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.adbonnin.cz2128.JsonSetRepository;

import javax.inject.Singleton;
import java.nio.file.Paths;
import java.util.Optional;

@Singleton
public class UserRepository extends JsonSetRepository<User> {

    public static final String USERS_FILE = "users.json";

    public UserRepository(JsonProviderService providerService, ObjectMapper mapper) {
        super(User.class, providerService.getJsonProvider(Paths.get(USERS_FILE)), mapper);
    }

    public Optional<User> findByUsername(String username) {
        return findFirst(user -> username.equals(user.getUsername()));
    }
}
