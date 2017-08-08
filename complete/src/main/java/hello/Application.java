package hello;

import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		WebClient client = WebClient.create("http://localhost:8080");

		Mono<ClientResponse> result = client.get()
				.uri("/hello")
				.accept(MediaType.TEXT_PLAIN)
				.exchange();

		System.out.println(">> result = " + result.block().toEntity(String.class));
	}
}
