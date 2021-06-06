package io.twdps.starter.example.data.subaccount.provider;

import io.twdps.starter.boot.test.data.provider.GenericDataFactory;
import io.twdps.starter.boot.test.data.spi.DataLoader;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import org.springframework.stereotype.Component;

@Component
public class SubAccountDataFactory extends GenericDataFactory<SubAccountData> {

  public SubAccountDataFactory(DataLoader<SubAccountData> loader) {
    super(loader);
  }
}
