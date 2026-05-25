package com.plaing.domain.sample

import com.plaing.common.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sample")
class SampleController(
    private val sampleService: SampleService,
) {
    @GetMapping("/health")
    fun health(): ApiResponse<String> = ApiResponse.ok(sampleService.ping())
}
