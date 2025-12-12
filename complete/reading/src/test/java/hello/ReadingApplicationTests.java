package hello;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReadingApplicationTests {

    private MockWebServer server;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setup() throws Exception {
        this.server = new MockWebServer();
        this.server.start(8090);
    }

    @AfterEach
    public void teardown() throws Exception {
        this.server.close();
    }

    @Test
    public void toReadTest() {
        this.server.enqueue(new MockResponse().setResponseCode(200).setBody("books"));
        String books = testRestTemplate.getForObject("/to-read", String.class);
        assertThat(books).isEqualTo("books");
    }

    @Test
    public void toReadFailureTest() {
        this.server.enqueue(new MockResponse().setResponseCode(500));
        String books = testRestTemplate.getForObject("/to-read", String.class);
        assertThat(books).isEqualTo("Cloud Native Java (O'Reilly)");
    }
}
