package io.twdps.starter.example.data.account.provider;

import io.twdps.starter.boot.test.data.provider.GenericDataFactory;
import io.twdps.starter.boot.test.data.spi.DataLoader;
import io.twdps.starter.example.data.account.model.AccountData;
import org.springframework.stereotype.Component;

@Component
public class AccountDataFactory extends GenericDataFactory<AccountData> {

  public AccountDataFactory(DataLoader<AccountData> loader) {
    super(loader);
  }
}
