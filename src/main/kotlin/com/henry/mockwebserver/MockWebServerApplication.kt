package com.henry.mockwebserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MockWebServerApplication

fun main(args: Array<String>) {
	runApplication<MockWebServerApplication>(*args)
}
