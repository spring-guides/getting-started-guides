package hello;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookServiceTests {

    @Autowired
    private BookService bookService;

    @Test
    public void readingListTest() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.start(8090);
            server.enqueue(new MockResponse().setResponseCode(200).setBody("books"));
            StepVerifier.create(bookService.readingList())
                    .expectNext("books").verifyComplete();
        }
    }
}
