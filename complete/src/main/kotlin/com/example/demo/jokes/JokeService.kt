package com.example.demo.jokes

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.util.retry.Retry
import java.time.Duration

@Service
class JokeService(private val webClient: WebClient) {

    companion object ENDPOINT {
        const val JOKE_API = "https://v2.jokeapi.dev/joke/Any?safe-mode"
    }

    suspend fun getSomeSafeJokes(): Joke {

        return webClient.get()
            .uri(JOKE_API)
            .retrieve()
            .bodyToMono(Joke::class.java)

            .retryWhen(
//                Retry.backoff(3, Duration.ofMillis(1))
                Retry.backoff(3, Duration.ofMillis(250))
                    .minBackoff(Duration.ofMillis(100))
            )
            .timeout(Duration.ofSeconds(3))
            .doOnError {
                println("------------> got error <------------ ${it}")
            }
            .awaitSingle()


//        return webClient
//            .get()
//            .uri(JOKE_API)
//            .retrieve()
//            .awaitBody()
    }
}
