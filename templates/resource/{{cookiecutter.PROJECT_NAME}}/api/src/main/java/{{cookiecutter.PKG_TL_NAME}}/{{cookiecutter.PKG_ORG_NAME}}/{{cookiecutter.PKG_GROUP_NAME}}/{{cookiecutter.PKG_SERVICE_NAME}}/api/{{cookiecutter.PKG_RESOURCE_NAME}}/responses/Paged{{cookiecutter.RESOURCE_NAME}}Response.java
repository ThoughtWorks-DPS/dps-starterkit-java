package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.{{cookiecutter.PKG_RESOURCE_NAME}}.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.api.responses.PagedResponse;
import lombok.NonNull;

import java.util.List;

// this class is necessary for easier documentation in springdoc
// current versions cannot directly determine the type of the list in
// PagedResponse<{{cookiecutter.RESOURCE_NAME}}Response>
@Schema(
    name = "PagedAccountResponse",
    description = "Bundled list of resources with paging metadata")
public class Paged{{cookiecutter.RESOURCE_NAME}}Response extends PagedResponse<{{cookiecutter.RESOURCE_NAME}}Response> {

  public Paged{{cookiecutter.RESOURCE_NAME}}Response(
      @NonNull List<{{cookiecutter.RESOURCE_NAME}}Response> items,
      @NonNull Integer totalPages,
      @NonNull Long totalItems,
      @NonNull Integer pageNumber,
      @NonNull Integer pageSize) {
    super(items, totalPages, totalItems, pageNumber, pageSize);
  }
}
