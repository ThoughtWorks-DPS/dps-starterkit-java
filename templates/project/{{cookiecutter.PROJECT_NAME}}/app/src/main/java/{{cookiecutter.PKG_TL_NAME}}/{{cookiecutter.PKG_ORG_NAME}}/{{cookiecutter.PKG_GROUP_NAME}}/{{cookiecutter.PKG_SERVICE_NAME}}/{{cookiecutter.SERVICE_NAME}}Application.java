package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}};

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j //lombok creates our logger as 'log' for us
@SpringBootApplication(scanBasePackages = {"{{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}", "io.twdps.starter.boot"})
public class {{cookiecutter.SERVICE_NAME}}Application {

  /** main function.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    new SpringApplication(StarterApplication.class).run(args);
    log.info("\n\n\n\n\n---------------{{cookiecutter.SERVICE_NAME}} API Started.----------------\n\n\n\n\n");
  }
}
