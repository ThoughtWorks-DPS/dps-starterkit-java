package io.twdps.starter.example.service.provider.account.mapper;

import io.twdps.starter.example.persistence.model.AccountEntity;
import io.twdps.starter.example.service.spi.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AccountEntityMapper {

  AccountEntity toEntity(Account src);

  default Optional<AccountEntity> toEntity(Optional<Account> src) {
    return Optional.ofNullable(toEntity(src.orElse(null)));
  }

  List<AccountEntity> toEntityList(List<Account> src);

  Account toModel(AccountEntity src);

  default Optional<Account> toModel(Optional<AccountEntity> src) {
    return Optional.ofNullable(toModel(src.orElse(null)));
  }

  default Page<Account> toModelPage(Page<AccountEntity> src) {
    return src.map(this::toModel);
  }

  List<Account> toModelList(Iterable<AccountEntity> src);

  @Mapping(target = "id", ignore = true)
  AccountEntity updateMetadata(Account src, @MappingTarget AccountEntity dst);
}
