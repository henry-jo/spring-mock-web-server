package com.henry.mockwebserver.sample

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

class SampleServiceSpec : DescribeSpec({
    val webClient = WebClient.create()
    val sampleService = SampleService(webClient = webClient)

    lateinit var mockServer: MockWebServer
    beforeTest {
        mockServer = MockWebServer().also { it.start() }
    }

    afterTest {
        mockServer.shutdown()
    }

    describe("MockWebServer response stubbing") {
        val mockServerUrl = mockServer.url("/sample").toString() // set MockWebServer url
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", MediaType.APPLICATION_JSON)
            .setBody("성공")
        mockServer.enqueue(mockResponse)

        val result = sampleService.callExternalGetApi(mockServerUrl)

        result shouldBe "성공"
    }

    describe("MockWebServer request checking") {
        val mockServerUrl = mockServer.url("/sample").toString() // set MockWebServer url
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", MediaType.APPLICATION_JSON)
            .setBody("성공")
        mockServer.enqueue(mockResponse)

        val result = sampleService.callExternalPostApi("요청값", mockServerUrl)
        val sampleServiceRequest = mockServer.takeRequest()

        result shouldBe "성공"
        sampleServiceRequest.method shouldBe "POST"
        sampleServiceRequest.path shouldBe "/sample"
        sampleServiceRequest.body.readUtf8() shouldBe "요청값"
    }
})
