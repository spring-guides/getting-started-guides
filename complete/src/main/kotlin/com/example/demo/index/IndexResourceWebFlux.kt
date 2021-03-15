package com.example.demo.index

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

//tag::indexResourceWebFlux[]
@RestController
@RequestMapping(produces = [MediaTypes.HAL_JSON_VALUE])
class IndexResourceWebFlux {

    companion object REL {
        const val REL_SPRING_INITIALIZR = "start-spring"
    }

    @GetMapping(value = ["/webflux"])
    fun index(serverWebExchange: ServerWebExchange): Mono<EntityModel<Unit>> {
        return Mono.empty<Link>().toFlux()
            .concatWith(linkTo(methodOn(IndexResourceWebFlux::class.java).index(serverWebExchange)).withSelfRel().toMono())
            .concatWith(Link.of("http://start.spring.io").withRel(IndexResource.REL_SPRING_INITIALIZR).toMono())
            .collectMap { link -> link.rel.toString() }
            .map { linkMap -> linkMap.values }
            .map { link -> EntityModel.of(Unit, link) }
    }
}
//end::indexResourceWebFlux[]
