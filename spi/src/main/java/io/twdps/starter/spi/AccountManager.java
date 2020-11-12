package io.twdps.starter.spi;

import io.twdps.starter.persistence.model.AccountEntity;
import java.util.List;

public interface AccountManager {

  AccountEntity addEntity(AccountEntity accountEntity);

  List<AccountEntity> findByLastName(String lastName);
}
