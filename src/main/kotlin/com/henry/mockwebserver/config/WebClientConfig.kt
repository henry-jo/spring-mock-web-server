package com.henry.mockwebserver.config

import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit

private const val TIMEOUT_MS = 1000L

@Configuration
class WebClientConfig {

    @Primary
    @Bean
    fun webClient(builder: WebClient.Builder): WebClient =
        buildWebClientWithTimeout(builder)

    private fun buildWebClientWithTimeout(builder: WebClient.Builder): WebClient {
        val httpClient: HttpClient =
            HttpClient
                .create()
                .followRedirect(true)
                .doOnConnected { connection ->
                    connection.apply {
                        addHandlerLast(
                            ReadTimeoutHandler(TIMEOUT_MS, TimeUnit.MILLISECONDS)
                        )
                        addHandlerLast(
                            WriteTimeoutHandler(TIMEOUT_MS, TimeUnit.MILLISECONDS)
                        )
                    }
                }

        return builder.clientConnector(ReactorClientHttpConnector(httpClient)).build()
    }
}
