package io.twdps.starter.example.data.account.provider;

import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.boot.test.data.spi.DataLoader;
import io.twdps.starter.example.data.account.model.AccountData;
import org.springframework.stereotype.Component;

@Component
public class AccountDataFactory extends NamedDataFactory<AccountData> {

  public AccountDataFactory(DataLoader<AccountData> loader) {
    super(loader);
  }
}
