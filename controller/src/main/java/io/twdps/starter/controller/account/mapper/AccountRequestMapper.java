package io.twdps.starter.controller.account.mapper;

import io.twdps.starter.api.account.requests.AccountRequest;
import io.twdps.starter.api.account.responses.AccountResponse;
import io.twdps.starter.api.account.responses.AddAccountResponse;
import io.twdps.starter.service.spi.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AccountRequestMapper {

  @Mapping(target = "response", expression = "java(\"Hello \" + a.getFirstName())")
  AddAccountResponse toAddAccountResponse(Account a);

  @Mapping(constant = "UNKNOWN_ID", target = "id")
  Account toModel(AccountRequest request);

  @Mapping(
      target = "fullName",
      expression = "java(String.format(\"%s %s\",a.getFirstName(),a.getLastName()))")
  AccountResponse toAccountResponse(Account a);

  default AccountResponse toAccountResponse(Optional<Account> src) {
    return toAccountResponse(src.orElse(null));
  }

  List<AccountResponse> toResponseList(List<Account> accounts);
}
