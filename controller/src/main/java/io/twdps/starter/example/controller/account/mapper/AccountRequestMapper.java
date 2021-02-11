package io.twdps.starter.example.controller.account.mapper;

import io.twdps.starter.example.api.account.requests.AccountRequest;
import io.twdps.starter.example.api.account.responses.AccountResponse;
import io.twdps.starter.example.service.spi.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AccountRequestMapper {

  @Mapping(constant = "UNKNOWN_ID", target = "id")
  Account toModel(AccountRequest request);

  @Mapping(
      target = "fullName",
      expression = "java(String.format(\"%s %s\",src.getFirstName(),src.getLastName()))")
  AccountResponse toAccountResponse(Account src);

  default AccountResponse toAccountResponse(Optional<Account> src) {
    return toAccountResponse(src.orElse(null));
  }

  List<AccountResponse> toAccountResponseList(List<Account> src);
}
