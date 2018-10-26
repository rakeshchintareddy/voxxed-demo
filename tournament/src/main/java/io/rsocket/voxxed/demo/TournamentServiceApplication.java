package io.rsocket.voxxed.demo;

import io.micrometer.core.instrument.MeterRegistry;
import io.netifi.proteus.Proteus;
import io.netifi.proteus.micrometer.ProteusMeterRegistrySupplier;
import io.netifi.proteus.tracing.ProteusTracerSupplier;
import io.opentracing.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.function.Supplier;

@SpringBootApplication
public class TournamentServiceApplication {
  public static void main(String... args) {
    SpringApplication.run(TournamentServiceApplication.class, args);
  }

  @Bean
  public Supplier<MeterRegistry> meterRegistrySupplier(Proteus proteus) {
    return new ProteusMeterRegistrySupplier(proteus, Optional.empty(), Optional.empty(), Optional.empty());
  }

  @Bean
  public Supplier<Tracer> tracerSupplier(Proteus proteus) {
    return new ProteusTracerSupplier(proteus, Optional.empty());
  }
}
