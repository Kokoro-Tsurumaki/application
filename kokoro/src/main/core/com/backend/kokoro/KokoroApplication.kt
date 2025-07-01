package com.backend.kokoro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class KokoroApplication{
	@GetMapping("/")
	fun hello(): String = "Hello Kokoro!"
}

fun main(args: Array<String>) {
	runApplication<KokoroApplication>(*args)
}


