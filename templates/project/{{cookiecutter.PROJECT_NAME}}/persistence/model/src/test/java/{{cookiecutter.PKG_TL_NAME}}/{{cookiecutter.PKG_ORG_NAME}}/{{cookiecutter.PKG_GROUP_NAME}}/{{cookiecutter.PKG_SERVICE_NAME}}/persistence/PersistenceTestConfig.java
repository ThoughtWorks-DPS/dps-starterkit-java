package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence;

import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(
    scanBasePackages = {"{{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}"})
public class PersistenceTestConfig {

}
