package io.twdps.starter.example.api.subaccount.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import io.twdps.starter.example.api.responses.PagedResponse;
import lombok.NonNull;

import java.util.List;

// this class is necessary for easier documentation in springdoc
// current versions cannot directly determine the type of the list in
// PagedResponse<SubAccountResponse>
@Schema(
    name = "PagedAccountResponse",
    description = "Bundled list of resources with paging metadata")
public class PagedSubAccountResponse extends PagedResponse<SubAccountResponse> {

  public PagedSubAccountResponse(
      @NonNull List<SubAccountResponse> items,
      @NonNull Integer totalPages,
      @NonNull Long totalItems,
      @NonNull Integer pageNumber,
      @NonNull Integer pageSize) {
    super(items, totalPages, totalItems, pageNumber, pageSize);
  }
}
