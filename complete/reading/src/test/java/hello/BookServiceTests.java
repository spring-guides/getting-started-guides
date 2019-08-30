package hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RunWith(SpringRunner.class)
@RestClientTest(BookService.class)
public class BookServiceTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void readingListTest() {
        this.server.expect(requestTo("http://localhost:8090/recommended"))
                .andRespond(withSuccess("books", MediaType.TEXT_PLAIN));
        assertThat(bookService.readingList()).isEqualTo("books");
    }
}
