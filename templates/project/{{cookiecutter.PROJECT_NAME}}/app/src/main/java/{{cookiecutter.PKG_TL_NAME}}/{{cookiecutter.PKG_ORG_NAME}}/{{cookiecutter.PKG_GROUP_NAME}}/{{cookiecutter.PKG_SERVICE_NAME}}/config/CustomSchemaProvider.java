package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.config;

import io.swagger.v3.oas.models.media.Schema;

public interface CustomSchemaProvider {

  /**
   * Create the Schema object for OpenAPI configuration bean.
   *
   * @return created Schema object
   */
  Schema create();

  /**
   * return the name of the schema object.
   *
   * @return name of the schema object
   */
  String getName();

}
