package com.example.demo.index

import com.example.demo.jokes.JokeResource
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

//tag::indexResourceCo[]
@RestController
class IndexResource {
    companion object REL {
        const val REL_SPRING_INITIALIZR = "start-spring"
        const val REL_JOKE = "joke"
    }

    @GetMapping("/")
    suspend fun index(): EntityModel<Unit> {
        val selfLink = toSelfLink()
        return EntityModel.of(Unit, selfLink)
            .add(Link.of("http://start.spring.io").withRel(REL_SPRING_INITIALIZR))
            .add(linkTo(methodOn(JokeResource::class.java).joke()).withRel(REL_JOKE).toMono().awaitSingle())
    }

    private suspend fun toSelfLink(): Link = linkTo(methodOn(IndexResource::class.java).index()).withSelfRel()
        .toMono() // <1>
        .awaitSingle() // <2>
}
//end::indexResourceCo[]
