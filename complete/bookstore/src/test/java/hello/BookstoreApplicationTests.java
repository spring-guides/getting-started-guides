package hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookstoreApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void recommendedTest() {
        webTestClient.get().uri("/recommended").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(m -> m.equals("Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)"));
    }
}
