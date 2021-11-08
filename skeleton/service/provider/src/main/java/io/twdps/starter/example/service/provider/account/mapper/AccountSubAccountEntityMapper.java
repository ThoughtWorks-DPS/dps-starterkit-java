package io.twdps.starter.example.service.provider.account.mapper;

import io.twdps.starter.example.service.spi.account.model.SubAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AccountSubAccountEntityMapper {

  @Mapping(target = "accountId", source = "accountId")
  @Mapping(target = "pii", constant = "FIXME")
  io.twdps.starter.example.service.spi.subaccount.model.SubAccount toServiceModel(
      SubAccount src, String accountId);

  SubAccount fromServiceModel(io.twdps.starter.example.service.spi.subaccount.model.SubAccount src);

  default Optional<SubAccount> fromServiceModel(
      Optional<io.twdps.starter.example.service.spi.subaccount.model.SubAccount> src) {
    return Optional.ofNullable(fromServiceModel(src.orElse(null)));
  }

  default Page<SubAccount> fromServiceModelPage(
      Page<io.twdps.starter.example.service.spi.subaccount.model.SubAccount> src) {
    return src.map(this::fromServiceModel);
  }
}
