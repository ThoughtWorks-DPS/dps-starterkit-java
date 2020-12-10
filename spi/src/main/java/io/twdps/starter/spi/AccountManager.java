package io.twdps.starter.spi;

import io.twdps.starter.persistence.model.AccountEntity;
import java.util.List;
import java.util.Optional;

public interface AccountManager {

  AccountEntity addEntity(AccountEntity accountEntity);

  List<AccountEntity> findByLastName(String lastName);

  Optional<AccountEntity> findByUserName(String userName);
}
