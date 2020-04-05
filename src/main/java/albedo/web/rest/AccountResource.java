package albedo.web.rest;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

@Controller("/api")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class AccountResource {

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/authenticated")
    public String isAuthenticated(Authentication authentication) {
        return authentication.getName();
    }
}
