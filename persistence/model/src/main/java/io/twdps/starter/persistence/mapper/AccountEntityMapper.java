package io.twdps.starter.persistence.mapper;

import io.twdps.starter.api.requests.AddEntityRequest;
import io.twdps.starter.persistence.model.AccountEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountEntityMapper {

  @Mapping(source = "firstName", target = "firstName")
  AccountEntity toAccountEntity(AddEntityRequest addEntityRequest);

  List<AccountEntity> toAccountEntityList(List<AddEntityRequest> addEntityRequests);
}
