package se.nackademin.backend2.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class UserResourceTest {

    @Autowired
    private WebTestClient client;

    @Test
    void shouldGenerateTokenOnLogin() {

        String signupBody = "{\"username\":\"user\",\"password\":\"pass\", \"roles\":[\"consumer\", \"admin\"]}";
        client.post()
                .uri("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(signupBody)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        String body = "{\"username\":\"user\",\"password\":\"pass\"}";
        String token = new String(client.post()
                .uri("/login")
                .bodyValue(body)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody().returnResult().getResponseBody());

        System.out.println(token);
    }


}