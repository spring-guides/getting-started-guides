package com.example.demo.jokes

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.kotlin.core.publisher.toMono

@Component
class JokeWebClientConfiguration() {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun client(): WebClient = WebClient
        .builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .filter(logRequest(logger))
        .build()

    fun logRequest(log: Logger) = ExchangeFilterFunction.ofRequestProcessor {
        log.debug("Request: {} {}", it.method(), it.url())
        it.headers().forEach { name, values -> values.forEach { value -> log.debug("{}={}", name, value) } }
        it.toMono()
    }
}
