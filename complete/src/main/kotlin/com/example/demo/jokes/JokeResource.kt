package com.example.demo.jokes

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JokeResource(private val jokeService: JokeService) {

    @GetMapping("/joke")
    suspend fun joke() = jokeService.getSomeSafeJokes()

}

