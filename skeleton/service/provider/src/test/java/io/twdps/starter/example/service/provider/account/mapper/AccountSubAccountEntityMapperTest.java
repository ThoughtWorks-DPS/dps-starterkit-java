package io.twdps.starter.example.service.provider.account.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.twdps.starter.boot.test.data.provider.NamedDataFactory;
import io.twdps.starter.example.data.subaccount.model.SubAccountData;
import io.twdps.starter.example.data.subaccount.provider.SubAccountDataFactory;
import io.twdps.starter.example.data.subaccount.provider.SubAccountTestData;
import io.twdps.starter.example.service.spi.account.model.SubAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class AccountSubAccountEntityMapperTest {

  private AccountSubAccountEntityMapper mapper;

  private final String parentIdentifier = "uuid-parent";
  private SubAccountTestData resourceTestDataLoader = new SubAccountTestData();
  private SubAccountDataFactory resourceTestData =
      new SubAccountDataFactory(resourceTestDataLoader);

  private SubAccountData reference;

  /** Setup mapper and test data factory before each test. */
  @BeforeEach
  public void setup() {
    mapper = Mappers.getMapper(AccountSubAccountEntityMapper.class);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
  }

  @Test
  public void mapperServiceSubAccountTest() {
    SubAccount resource = createResource(reference.getId());

    io.twdps.starter.example.service.spi.subaccount.model.SubAccount model =
        mapper.toServiceModel(resource, parentIdentifier);

    verifyServiceModel(model);
  }

  /**
   * convenience function to create resource object.
   *
   * @param id whether to create with identifier (null if not)
   * @return SubAccount object
   */
  private SubAccount createResource(String id) {
    return new SubAccount(
        id, reference.getUserName(), reference.getFirstName(), reference.getLastName());
    // TODO: Additional SubAccount data elements
  }

  /**
   * helper function to validate standard values.
   *
   * @param subResource the object to validate
   */
  protected void verifyServiceModel(
      io.twdps.starter.example.service.spi.subaccount.model.SubAccount subResource) {
    assertThat(subResource.getUserName()).isEqualTo(reference.getUserName());
    assertThat(subResource.getFirstName()).isEqualTo(reference.getFirstName());
    assertThat(subResource.getLastName()).isEqualTo(reference.getLastName());
    assertThat(subResource.getPii()).isEqualTo("FIXME");
    assertThat(subResource.getId()).isEqualTo(reference.getId());
    assertThat(subResource.getAccountId()).isEqualTo(parentIdentifier);
    // TODO: Add assertions for additional SubAccount fields
  }
}
