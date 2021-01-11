package io.twdps.starter.tracing;

import brave.Tracing;

@FunctionalInterface
public interface ZipkinTracerCustomizer {

  void customize(Tracing.Builder builder);
}
