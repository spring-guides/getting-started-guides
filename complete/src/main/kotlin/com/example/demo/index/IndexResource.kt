package com.example.demo.index


import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

//tag::indexResource[]
@RestController
class IndexResource() {
    companion object REL {
        const val REL_SPRING_INITIALIZR = "start-spring"
    }

    @GetMapping("/")
    suspend fun index(): EntityModel<Unit> {
        val selfLink = toSelfLink()
       return EntityModel.of(Unit, selfLink)
           .add(Link.of("http://start.spring.io").withRel(REL_SPRING_INITIALIZR))
    }

    private suspend fun toSelfLink() = WebFluxLinkBuilder.linkTo(
        WebFluxLinkBuilder.methodOn(IndexResource::class.java).index()).withSelfRel().toMono().awaitSingle()
}
//end::indexResource[]
