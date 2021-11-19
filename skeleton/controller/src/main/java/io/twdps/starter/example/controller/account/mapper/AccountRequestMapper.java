package io.twdps.starter.example.controller.account.mapper;

import io.twdps.starter.example.api.account.requests.AccountRequest;
import io.twdps.starter.example.api.account.responses.AccountResponse;
import io.twdps.starter.example.api.responses.PagedResponse;
import io.twdps.starter.example.service.spi.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AccountRequestMapper {

  @Mapping(constant = "UNKNOWN_ID", target = "id")
  Account toModel(AccountRequest request);

  // TODO: possibly remove this constructed field value
  @Mapping(
      target = "fullName",
      expression = "java(String.format(\"%s %s\",src.getFirstName(),src.getLastName()))")
  AccountResponse toAccountResponse(Account src);

  default AccountResponse toAccountResponse(Optional<Account> src) {
    return toAccountResponse(src.orElse(null));
  }

  /**
   * convert to PagedResponse<>.
   *
   * @param src Page<> object
   * @return PagedResponse<>
   */
  default PagedResponse<AccountResponse> toAccountResponsePage(Page<Account> src) {
    return new PagedResponse<>(
        toAccountResponseList(src.getContent()),
        src.getTotalPages(),
        src.getTotalElements(),
        src.getNumber(),
        src.getNumberOfElements());
  }

  List<AccountResponse> toAccountResponseList(List<Account> src);
}
