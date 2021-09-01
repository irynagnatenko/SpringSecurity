package se.nackademin.backend2.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class UserResourceTest {

    @Autowired
    private WebTestClient client;

    @Test
    void uppgift_1_shouldBeAbleToCreateUser() {
        String signupBody = "{\"username\":\"user\",\"password\":\"pass\", \"roles\":[\"ADMIN\"]}";
        client.post()
                .uri("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(signupBody)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void uppgift_2_shouldBeAbleToLogin() {
        String signupBody = "{\"username\":\"user\",\"password\":\"pass\", \"roles\":[\"ADMIN\"]}";
        client.post()
                .uri("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(signupBody)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        String loginBody = "{\"username\":\"user\",\"password\":\"pass\"}";
        client.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginBody)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void uppgift_3_shouldBeAbleToLoginAndGetTokenBack() {
        String signupBody = "{\"username\":\"user\",\"password\":\"pass\", \"roles\":[\"ADMIN\"]}";
        client.post()
                .uri("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(signupBody)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        String loginBody = "{\"username\":\"user\",\"password\":\"pass\"}";
        String token = client.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginBody)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(String.class)
                .getResponseBody().blockFirst();

        System.out.println(token);
        assertEquals(token.split("\\.").length, 3);
    }

    @Test
    void uppgift_4_och_5_shouldHandleRoles() {

        String signupBody = "{\"username\":\"user\",\"password\":\"pass\", \"roles\":[\"ADMIN\"]}";
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

        client.get().uri("/admin/hello").header("Authorization", "Bearer " + token).exchange().expectStatus().is2xxSuccessful();
    }


}