package io.twdps.starter.example.data.account.provider;

import io.twdps.starter.boot.test.data.provider.GenericDataLoader;
import io.twdps.starter.example.data.account.model.AccountData;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("test-data.account")
public class AccountDataProperties extends GenericDataLoader<AccountData> {}
