package albedo.web.rest

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest

@MicronautTest
class AccountResourceSpec extends BaseRestSpecification {

    void "should return an unauthorized status when the user is not authenticated"() {
        when:
        def request = HttpRequest.GET('/api/authenticated')
        client.toBlocking().exchange(request)

        then:
        def e = thrown(HttpClientResponseException)
        e.status == HttpStatus.UNAUTHORIZED
    }

    void "should return the username when the user is authenticated"() {
        given:
        createUser(username, password)

        when:
        def request = HttpRequest.GET('/api/authenticated').basicAuth(username, password)
        def response = client.toBlocking().exchange(request, String)

        then:
        response.status == HttpStatus.OK
        response.body() == username

        where:
        username = 'spock'
        password = 'changeit'
    }
}
