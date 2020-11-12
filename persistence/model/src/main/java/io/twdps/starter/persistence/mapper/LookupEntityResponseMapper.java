package io.twdps.starter.persistence.mapper;

import io.twdps.starter.api.responses.LookupEntityResponse;
import io.twdps.starter.persistence.model.AccountEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LookupEntityResponseMapper {

  @Mapping(target = "fullName", expression = "java(String.format(\"%s %s\",a.getFirstName(),a.getLastName()))")
  LookupEntityResponse toLookupEntityResponse(AccountEntity a);

  List<LookupEntityResponse> toLookupEntityResponseList(List<AccountEntity> accountEntities);
}
