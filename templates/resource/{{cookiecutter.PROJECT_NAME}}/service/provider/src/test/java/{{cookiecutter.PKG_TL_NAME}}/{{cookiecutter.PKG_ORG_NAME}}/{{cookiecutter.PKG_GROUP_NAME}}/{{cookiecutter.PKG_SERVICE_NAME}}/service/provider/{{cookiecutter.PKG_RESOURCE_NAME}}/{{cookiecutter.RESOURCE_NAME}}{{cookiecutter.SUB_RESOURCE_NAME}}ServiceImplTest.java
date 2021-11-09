{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.test.data.provider.NamedDataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}Data;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}DataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}DataProperties;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.provider.{{cookiecutter.SUB_RESOURCE_NAME}}TestData;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.SUB_RESOURCE_NAME}}Entity;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.SUB_RESOURCE_NAME}}EntityRepository;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.{{cookiecutter.SUB_RESOURCE_NAME}}Service;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}EntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}ServiceImplTest {

  private {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}ServiceImpl manager;

  @Mock private {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}EntityMapper mapper;
  @Mock private {{cookiecutter.SUB_RESOURCE_NAME}}Service resourceService;

  private final {{cookiecutter.SUB_RESOURCE_NAME}}TestData resourceTestDataLoader = new {{cookiecutter.SUB_RESOURCE_NAME}}TestData();
  private final {{cookiecutter.SUB_RESOURCE_NAME}}DataFactory resourceTestData= new {{cookiecutter.SUB_RESOURCE_NAME}}DataFactory(
      resourceTestDataLoader);

  private {{cookiecutter.SUB_RESOURCE_NAME}}Data reference;
  private {{cookiecutter.SUB_RESOURCE_NAME}}Data bogus;

  private final Pageable pageable = Pageable.unpaged();
  private final String parentIdentifier = "uuid-parent";
  private {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}} serviceResource;
  private {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}} serviceOutput;
  private List<{{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}>
      serviceOutputList;
  private List<{{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}>
      emptyServiceOutputList = Arrays.asList();
  private Page<{{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}>
      serviceOutputPage;
  private Page<{{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}>
      emptyServiceOutputPage;
  private Optional<{{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}>
      optionalServiceOutput;
  private Optional<{{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}>
      emptyServiceOutput = Optional.empty();
  private {{cookiecutter.SUB_RESOURCE_NAME}} subResource;
  private {{cookiecutter.SUB_RESOURCE_NAME}} subOutput;
  private Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> emptySubResource = Optional.empty();
  private Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> optionalSubOutput;
  private List<{{cookiecutter.SUB_RESOURCE_NAME}}> subOutputList;
  private List<{{cookiecutter.SUB_RESOURCE_NAME}}> emptySubOutputList = Arrays.asList();
  private Page<{{cookiecutter.SUB_RESOURCE_NAME}}> subOutputPage;
  private Page<{{cookiecutter.SUB_RESOURCE_NAME}}> emptySubOutputPage;

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    manager = new {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}ServiceImpl(mapper, resourceService);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    bogus = resourceTestData.createBySpec("bogus");

    subResource =
        {{cookiecutter.SUB_RESOURCE_NAME}}.builder()
            .userName(reference.getUserName())
            .firstName(reference.getFirstName())
            .lastName(reference.getLastName())
            .build();
    serviceResource =
        {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}.builder()
            .userName(reference.getUserName())
            .firstName(reference.getFirstName())
            .lastName(reference.getLastName())
            .pii(reference.getPii())
            .build();
    serviceOutput =
        {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_SUB_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}}.builder()
            .id(reference.getId())
            .userName(reference.getUserName())
            .firstName(reference.getFirstName())
            .lastName(reference.getLastName())
            .pii(reference.getPii())
            .{{cookiecutter.RESOURCE_VAR_NAME}}Id(parentIdentifier)
            .build();
    optionalServiceOutput = Optional.of(serviceOutput);
    serviceOutputList = Arrays.asList(serviceOutput, serviceOutput);
    serviceOutputPage = new PageImpl<>(serviceOutputList);
    emptyServiceOutputPage = new PageImpl<>(emptyServiceOutputList);
    emptySubOutputPage = new PageImpl<>(emptySubOutputList);

    // use the real mapper to generate consistent objects to use in mapper stubs
    {{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}EntityMapper real = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}{{cookiecutter.SUB_RESOURCE_NAME}}EntityMapper.class);
    subOutput = real.fromServiceModel(serviceOutput);
    optionalSubOutput = Optional.of(subOutput);
    subOutputList = Arrays.asList(subOutput, subOutput);
    subOutputPage = new PageImpl<>(subOutputList);

    }

  private void create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs() {
    Mockito.when(mapper.toServiceModel(subResource, parentIdentifier)).thenReturn(serviceResource);
  }

  private void createReverse{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs() {
    Mockito.when(mapper.fromServiceModel(serviceOutput)).thenReturn(subOutput);
  }

  private void createOptional{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs() {
    Mockito.when(mapper.fromServiceModel(optionalServiceOutput)).thenReturn(optionalSubOutput);
  }

  private void createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs() {
    Mockito.when(mapper.fromServiceModel(emptyServiceOutput)).thenReturn(emptySubResource);
  }

  private void create{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs() {
    Mockito.when(mapper.fromServiceModelPage(serviceOutputPage)).thenReturn(subOutputPage);
  }

  private void createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs() {
    Mockito.when(mapper.fromServiceModelPage(emptyServiceOutputPage)).thenReturn(emptySubOutputPage);
  }

  @Test
  public void findByIdFailTest() {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(resourceService.findById(Mockito.any())).thenReturn(emptyServiceOutput);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> result = manager.findById(parentIdentifier, bogus.getId());
    Assertions.assertThat(!result.isPresent()).isTrue();
  }

  @Test
  public void addTest() {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    createReverse{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(resourceService.add(serviceResource)).thenReturn(serviceOutput);

    {{cookiecutter.SUB_RESOURCE_NAME}} response = manager.add(parentIdentifier, subResource);

    Assertions.assertThat(response.getFirstName()).isEqualTo(subResource.getFirstName());
    Assertions.assertThat(response.getId()).isEqualTo(reference.getId());
  }

  @Test
  public void findByIdTest() {

    createOptional{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(resourceService.findById(reference.getId())).thenReturn(optionalServiceOutput);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response = manager.findById(parentIdentifier, reference.getId());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subOutput.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subOutput.getId());
  }

  @Test
  public void findByIdFailedTest() {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(resourceService.findById(bogus.getId())).thenReturn(emptyServiceOutput);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response = manager.findById(parentIdentifier, bogus.getId());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findAllTest() {

    create{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs();
    Mockito.when(resourceService.findAllBy{{cookiecutter.RESOURCE_NAME}}Id(parentIdentifier, pageable))
        .thenReturn(serviceOutputPage);

    Page<{{cookiecutter.SUB_RESOURCE_NAME}}> response = manager.findAll(parentIdentifier, pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(2);
  }

  @Test
  public void findAllEmptyTest() {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs();
    Mockito.when(resourceService.findAllBy{{cookiecutter.RESOURCE_NAME}}Id(parentIdentifier, pageable))
        .thenReturn(emptyServiceOutputPage);

    Page<{{cookiecutter.SUB_RESOURCE_NAME}}> response = manager.findAll(parentIdentifier, pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(0);
  }

  @Test
  public void updateByIdTest() {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    createOptional{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(resourceService.updateById(reference.getId(), serviceResource)).thenReturn(optionalServiceOutput);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response =
        manager.updateById(parentIdentifier, reference.getId(), subResource);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subResource.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void updateByIdFailedTest() {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(resourceService.updateById(reference.getId(), serviceResource))
        .thenReturn(emptyServiceOutput);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response =
        manager.updateById(parentIdentifier, reference.getId(), subResource);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void deleteByIdTest() {

    createOptional{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(resourceService.deleteById(reference.getId())).thenReturn(optionalServiceOutput);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response = manager.deleteById(parentIdentifier, reference.getId());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subOutput.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subOutput.getId());
  }

  @Test
  public void deleteByIdFailedTest() {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(resourceService.deleteById(bogus.getId())).thenReturn(emptyServiceOutput);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response = manager.deleteById(parentIdentifier, bogus.getId());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }
}
{%- endif %}
