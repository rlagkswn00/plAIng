package com.plaing.domain.sample;

import org.springframework.stereotype.Service;

@Service
public class SampleService {

  public String ping() {
    return "pong";
  }
}
