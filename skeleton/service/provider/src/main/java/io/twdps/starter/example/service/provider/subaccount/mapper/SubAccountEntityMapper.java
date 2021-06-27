package io.twdps.starter.example.service.provider.subaccount.mapper;

import io.twdps.starter.example.persistence.model.SubAccountEntity;
import io.twdps.starter.example.service.spi.subaccount.model.SubAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface SubAccountEntityMapper {

  @Mapping(target = "accountId", ignore = true)
  SubAccountEntity toEntity(SubAccount src);

  default Optional<SubAccountEntity> toEntity(Optional<SubAccount> src) {
    return Optional.ofNullable(toEntity(src.orElse(null)));
  }

  List<SubAccountEntity> toEntityList(List<SubAccount> src);

  SubAccount toModel(SubAccountEntity src);

  default Optional<SubAccount> toModel(Optional<SubAccountEntity> src) {
    return Optional.ofNullable(toModel(src.orElse(null)));
  }

  default Page<SubAccount> toModelPage(Page<SubAccountEntity> src) {
    return src.map(this::toModel);
  }

  List<SubAccount> toModelList(Iterable<SubAccountEntity> src);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "accountId", ignore = true)
  SubAccountEntity updateMetadata(SubAccount src, @MappingTarget SubAccountEntity dst);
}
