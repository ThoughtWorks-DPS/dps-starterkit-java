package io.twdps.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j //lombok creates our logger as 'log' for us
@SpringBootApplication(
    scanBasePackages = {"io.twdps.starter", "io.twdps.starter.boot"})
public class Service {

  /** main function.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    new SpringApplication(Service.class).run(args);
    log.info(
        "\n\n\n\n\n---------------------Service API Started.------------------------\n\n\n\n\n");
  }
}
