package com.plaing.domain.sample;

import com.plaing.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

  private final SampleService sampleService;

  public SampleController(SampleService sampleService) {
    this.sampleService = sampleService;
  }

  @GetMapping("/health")
  public ApiResponse<String> health() {
    return ApiResponse.ok(sampleService.ping());
  }
}
