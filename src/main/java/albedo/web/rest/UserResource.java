package albedo.web.rest;

import albedo.service.UserService;
import albedo.service.dto.UserDTO;
import albedo.service.mapper.UserMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/api")
public class UserResource {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserResource(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Get("/users")
    public HttpResponse<List<UserDTO>> getAllUsers() {
        return HttpResponse.ok(userService.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList()));
    }
}
