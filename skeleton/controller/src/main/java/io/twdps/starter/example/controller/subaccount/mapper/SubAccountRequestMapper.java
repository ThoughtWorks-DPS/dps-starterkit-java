package io.twdps.starter.example.controller.subaccount.mapper;

import io.twdps.starter.example.api.responses.PagedResponse;
import io.twdps.starter.example.api.subaccount.requests.SubAccountRequest;
import io.twdps.starter.example.api.subaccount.responses.SubAccountResponse;
import io.twdps.starter.example.service.spi.subaccount.model.SubAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface SubAccountRequestMapper {

  @Mapping(constant = "UNKNOWN_ID", target = "id")
  SubAccount toModel(SubAccountRequest request);

  @Mapping(
      target = "fullName",
      expression = "java(String.format(\"%s %s\",src.getFirstName(),src.getLastName()))")
  SubAccountResponse toSubAccountResponse(SubAccount src);

  default SubAccountResponse toSubAccountResponse(Optional<SubAccount> src) {
    return toSubAccountResponse(src.orElse(null));
  }

  /**
   * convert to PagedResponse<>.
   *
   * @param src Page<> object
   * @return PagedResponse<>
   */
  default PagedResponse<SubAccountResponse> toSubAccountResponsePage(Page<SubAccount> src) {
    return new PagedResponse<>(
        toSubAccountResponseList(src.getContent()),
        src.getTotalPages(),
        src.getTotalElements(),
        src.getNumber(),
        src.getNumberOfElements());
  }

  List<SubAccountResponse> toSubAccountResponseList(List<SubAccount> src);
}
