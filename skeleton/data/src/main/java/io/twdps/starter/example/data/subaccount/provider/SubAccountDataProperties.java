package io.twdps.starter.example.data.subaccount.provider;

import io.twdps.starter.boot.test.data.provider.GenericDataLoader;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("test-data.subaccount")
public class SubAccountDataProperties extends GenericDataLoader<SubAccountData> {}
