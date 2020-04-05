package albedo.web.rest

import albedo.domain.User
import albedo.service.UserService
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.security.authentication.providers.PasswordEncoder
import io.micronaut.security.token.generator.TokenGenerator
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class BaseRestSpecification extends Specification {

    @Inject
    EmbeddedServer server

    @Inject
    @Client("/")
    RxHttpClient client

    @Inject
    UserService userService

    @Inject
    PasswordEncoder passwordEncoder

    @Inject
    TokenGenerator tokenGenerator

    void cleanup() {
        userService.deleteAll()
    }

    protected User createUser(String username, String password = 'changeit') {
        def user = new User(username: username, password: passwordEncoder.encode(password), enabled: true, authorities: ['USER'])
        userService.save(user)
        return user
    }
}
