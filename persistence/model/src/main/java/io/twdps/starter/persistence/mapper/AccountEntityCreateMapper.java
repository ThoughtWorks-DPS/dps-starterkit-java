package io.twdps.starter.persistence.mapper;

import io.twdps.starter.api.responses.AddAccountEntityResponse;
import io.twdps.starter.persistence.model.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountEntityCreateMapper {

  @Mapping(target = "response", expression = "java(\"Hello \" + a.getFirstName())")
  AddAccountEntityResponse toAddAccountEntityResponse(AccountEntity a);
}
