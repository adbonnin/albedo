package albedo.security

import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification
import spock.lang.Subject

import javax.inject.Inject

@MicronautTest
class BCryptPasswordEncoderSpec extends Specification {

    @Subject
    @Inject
    BCryptPasswordEncoder passwordEncoder

    void "doit encoder un mot de passe"() {
        when:
        def encodedPassword = passwordEncoder.encode(password)

        then:
        encodedPassword.size() == 60
        encodedPassword.startsWith('$')

        where:
        password = 'changeit'
    }

    void "doit verifier que le mot de passe correspond a celui encode"() {
        expect:
        passwordEncoder.matches(password, encodedPassword) == expectedMatches

        where:
        password      || expectedMatches
        'changeit'    || true
        'changeitnow' || false

        encodedPassword = '$2a$10$CFC7Mwyn9CoKkt9STh0FU.soNGrkNEvF9TT9mURFlxtT4k1mSS2TO'
    }
}
