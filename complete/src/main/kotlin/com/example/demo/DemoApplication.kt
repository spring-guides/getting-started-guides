package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.hateoas.support.WebStack

//tag::enableHATEOAS[]
@SpringBootApplication
@EnableHypermediaSupport(stacks = [WebStack.WEBFLUX], type = [EnableHypermediaSupport.HypermediaType.HAL])
class DemoApplication
//end::enableHATEOAS[]

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
