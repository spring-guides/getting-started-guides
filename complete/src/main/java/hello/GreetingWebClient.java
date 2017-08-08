package hello;

import static org.springframework.http.MediaType.TEXT_PLAIN;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class GreetingWebClient {

	WebClient webClient = WebClient.create();

	Mono<String> message = webClient.get()
	        .uri("http://localhost:8080/hello")
	        .accept(TEXT_PLAIN)
	        .exchange()
	        .flatMap(response -> response.bodyToMono(String.class));
}
