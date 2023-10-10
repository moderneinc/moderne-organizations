package io.moderne.organizations;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public record OrganizationMetricController(PrometheusMeterRegistry meterRegistry) {
  @GetMapping("/metrics/prometheus")
  Mono<String> scrape() {
    return Mono.just(meterRegistry.scrape(TextFormat.CONTENT_TYPE_OPENMETRICS_100));
  }
}

