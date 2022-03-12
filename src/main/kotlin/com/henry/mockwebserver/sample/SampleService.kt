package com.henry.mockwebserver.sample

import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class SampleService(
    private val webClient: WebClient
) {
    fun callExternalGetApi(externalApiUrl: String): String? {
        return webClient.get()
            .uri(externalApiUrl)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }

    fun callExternalPostApi(request: String, externalApiUrl: String): String? {
        return webClient.post()
            .uri(externalApiUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }
}
