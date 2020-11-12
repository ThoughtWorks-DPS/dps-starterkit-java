package io.twdps.starter.config;

import static zipkin2.codec.SpanBytesDecoder.JSON_V2;
import static zipkin2.codec.SpanBytesDecoder.PROTO3;
import static zipkin2.codec.SpanBytesEncoder.JSON_V1;

import brave.Tracing;
import brave.handler.SpanHandler;
import brave.opentracing.BraveTracer;
import brave.sampler.BoundarySampler;
import brave.sampler.CountingSampler;
import brave.sampler.Sampler;
import io.opentracing.Tracer;
import io.twdps.starter.config.properties.ZipkinConfigurationProperties;
import io.twdps.starter.tracing.ZipkinTracerCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.okhttp3.OkHttpSender;

@Configuration
@ConditionalOnProperty(prefix = "opentracing.zipkin", name = "enabled", havingValue = "true")
public class TracingConfig {

  @Value("${spring.application.name:unknown-spring-boot}")
  private String serviceName;

  @Bean
  public Tracer tracer(SpanHandler spanHandler, Sampler sampler) {

    var braveTracing = Tracing.newBuilder()
        .localServiceName(serviceName)
        .supportsJoin(false)
        .addSpanHandler(spanHandler)
        .build();
    // use this to create an OpenTracing Tracer
    return BraveTracer.create(braveTracing);
  }

  @Bean
  public SpanHandler spanHandler(ZipkinConfigurationProperties properties) {
    var url = properties.getHttpSender().getBaseUrl();
    if (properties.getHttpSender().getEncoder().name().equals(JSON_V2.name())
        || properties.getHttpSender().getEncoder().name().equals(PROTO3.name())) {
      url += (url.endsWith("/") ? "" : "/") + "api/v2/spans";
    } else if (properties.getHttpSender().getEncoder().name().equals(JSON_V1.name())) {
      url += (url.endsWith("/") ? "" : "/") + "api/v1/spans";
    }
    return AsyncZipkinSpanHandler.create(OkHttpSender.create(url));
  }

  @Bean
  public Sampler sampler(ZipkinConfigurationProperties properties) {
    if (properties.getBoundarySampler().getRate() != null) {
      return BoundarySampler.create(properties.getBoundarySampler().getRate());
    }

    if (properties.getCountingSampler().getRate() != null) {
      return CountingSampler.create(properties.getCountingSampler().getRate());
    }

    return Sampler.ALWAYS_SAMPLE;
  }

  @Bean
  public ZipkinTracerCustomizer zipkinTracerCustomizer() {
    return (Tracing.Builder builder) -> builder.supportsJoin(false);
  }
}

