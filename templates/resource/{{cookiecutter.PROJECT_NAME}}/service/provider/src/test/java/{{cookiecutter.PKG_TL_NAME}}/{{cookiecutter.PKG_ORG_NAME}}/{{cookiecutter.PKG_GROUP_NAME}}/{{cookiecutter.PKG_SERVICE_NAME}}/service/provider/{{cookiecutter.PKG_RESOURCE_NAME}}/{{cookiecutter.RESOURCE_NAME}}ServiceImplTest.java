package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.starter.boot.test.data.provider.NamedDataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}}Data;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}DataFactory;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}DataProperties;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.data.{{cookiecutter.PKG_RESOURCE_NAME}}.provider.{{cookiecutter.RESOURCE_NAME}}TestData;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}Entity;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}EntityRepository;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}EntityMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
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
public class {{cookiecutter.RESOURCE_NAME}}ServiceImplTest {

  private {{cookiecutter.RESOURCE_NAME}}ServiceImpl manager;

  @Mock private {{cookiecutter.RESOURCE_NAME}}EntityRepository repository;
  @Mock private {{cookiecutter.RESOURCE_NAME}}EntityMapper mapper;

  private {{cookiecutter.RESOURCE_NAME}}TestData resourceTestDataLoader = new {{cookiecutter.RESOURCE_NAME}}TestData();
  private {{cookiecutter.RESOURCE_NAME}}DataFactory resourceTestData = new {{cookiecutter.RESOURCE_NAME}}DataFactory(resourceTestDataLoader);

  private {{cookiecutter.RESOURCE_NAME}}Data reference;
  private {{cookiecutter.RESOURCE_NAME}}Data bogus;

  private {{cookiecutter.RESOURCE_NAME}} resource;
  private {{cookiecutter.RESOURCE_NAME}} output;
  private {{cookiecutter.RESOURCE_NAME}}Entity entity;
  private {{cookiecutter.RESOURCE_NAME}}Entity added;
  private Optional<{{cookiecutter.RESOURCE_NAME}}> empty{{cookiecutter.RESOURCE_NAME}} = Optional.empty();
  private Optional<{{cookiecutter.RESOURCE_NAME}}Entity> emptyEntity = Optional.empty();
  private Optional<{{cookiecutter.RESOURCE_NAME}}Entity> optionalEntity;
  private Optional<{{cookiecutter.RESOURCE_NAME}}Entity> optionalAdded;
  private Optional<{{cookiecutter.RESOURCE_NAME}}> optionalOutput;
  private List<{{cookiecutter.RESOURCE_NAME}}Entity> entityList;
  private List<{{cookiecutter.RESOURCE_NAME}}> outputList;
  private List<{{cookiecutter.RESOURCE_NAME}}Entity> emptyEntityList = Arrays.asList();
  private List<{{cookiecutter.RESOURCE_NAME}}> emptyOutputList = Arrays.asList();
  private Page<{{cookiecutter.RESOURCE_NAME}}Entity> entityPage;
  private Page<{{cookiecutter.RESOURCE_NAME}}> outputPage;
  private Page<{{cookiecutter.RESOURCE_NAME}}Entity> emptyEntityPage;
  private Page<{{cookiecutter.RESOURCE_NAME}}> emptyOutputPage;
  private Pageable pageable = Pageable.unpaged();

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    manager = new {{cookiecutter.RESOURCE_NAME}}ServiceImpl(repository, mapper);

    reference = resourceTestData.createBySpec(NamedDataFactory.DEFAULT_SPEC);
    bogus = resourceTestData.createBySpec("bogus");

    // use the real mapper to generate consistent objects to use in mapper stubs
    {{cookiecutter.RESOURCE_NAME}}EntityMapper real = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}EntityMapper.class);

    resource =
        {{cookiecutter.RESOURCE_NAME}}.builder()
            .userName(reference.getUserName())
            .pii(reference.getPii())
            .firstName(reference.getFirstName())
            .lastName(reference.getLastName())
            // TODO: Additional {{cookiecutter.RESOURCE_NAME}} data elements
            .build();
    entity = real.toEntity(resource);
    added =
        new {{cookiecutter.RESOURCE_NAME}}Entity(
            reference.getId(),
            entity.getUserName(),
            entity.getPii(),
            entity.getFirstName(),
            entity.getLastName()
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %},
            // TODO: Additional {{cookiecutter.RESOURCE_NAME}}Entity data elements
            entity.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()
{%- else %}
            // TODO: Additional {{cookiecutter.RESOURCE_NAME}}Entity data elements
{%- endif %}
            );
    output = real.toModel(added);
    optionalEntity = Optional.of(entity);
    optionalAdded = Optional.of(added);
    optionalOutput = Optional.of(output);
    entityList = Arrays.asList(added, added);
    outputList = Arrays.asList(output, output);
    entityPage = new PageImpl<>(entityList);
    outputPage = new PageImpl<>(outputList);
    emptyEntityPage = new PageImpl<>(emptyEntityList);
    emptyOutputPage = new PageImpl<>(emptyOutputList);

  }

  private void createMapperStubs() {
    Mockito.when(mapper.toEntity(resource)).thenReturn(entity);
    Mockito.when(mapper.toModel(added)).thenReturn(output);
  }

  private void createOptionalMapperStubs() {
    Mockito.when(mapper.toModel(optionalAdded)).thenReturn(optionalOutput);
  }

  private void createEmptyMapperStubs() {
    Mockito.when(mapper.toModel(emptyEntity)).thenReturn(empty{{cookiecutter.RESOURCE_NAME}});
  }

  private void createListMapperStubs() {
    Mockito.when(mapper.toModelPage(entityPage)).thenReturn(outputPage);
  }

  private void createEmptyListMapperStubs() {
    Mockito.when(mapper.toModelPage(emptyEntityPage)).thenReturn(emptyOutputPage);
  }

  @Test
  public void findBy{{cookiecutter.RESOURCE_NAME}}IdFailTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(Mockito.any())).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> result = manager.findById(bogus.getId());
    Assertions.assertThat(!result.isPresent()).isTrue();
  }

  @Test
  public void add{{cookiecutter.RESOURCE_NAME}}Test() {

    createMapperStubs();
    Mockito.when(repository.save(entity)).thenReturn(added);

    {{cookiecutter.RESOURCE_NAME}} response = manager.add(resource);

    Assertions.assertThat(response.getFirstName()).isEqualTo(resource.getFirstName());
    Assertions.assertThat(response.getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByUserNameTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findByUserName(reference.getUserName())).thenReturn(optionalAdded);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByUserName(reference.getUserName());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByUserNameFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findByUserName(bogus.getUserName())).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByUserName(bogus.getUserName());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findByLastNameTest() {

    createListMapperStubs();
    Mockito.when(repository.findByLastName(reference.getLastName(), pageable)).thenReturn(entityPage);

    Page<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByLastName(reference.getLastName(), pageable);

    Assertions.assertThat(response.getContent().isEmpty()).isFalse();
    Assertions.assertThat(response.getContent().get(0).getFirstName())
        .isEqualTo(added.getFirstName());
    Assertions.assertThat(response.getContent().get(0).getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByLastNameFailedTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findByLastName(bogus.getLastName(), pageable)).thenReturn(emptyEntityPage);

    Page<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByLastName(bogus.getLastName(), pageable);

    Assertions.assertThat(response.getContent().isEmpty()).isTrue();
  }

  @Test
  public void findByIdTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(reference.getId())).thenReturn(optionalAdded);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findById(reference.getId());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByIdFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogus.getId())).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findById(bogus.getId());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findAllTest() {

    createListMapperStubs();
    Mockito.when(repository.findAll(pageable)).thenReturn(entityPage);

    Page<{{cookiecutter.RESOURCE_NAME}}> response = manager.findAll(pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(2);
  }

  @Test
  public void findAllEmptyTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findAll(pageable)).thenReturn(emptyEntityPage);

    Page<{{cookiecutter.RESOURCE_NAME}}> response = manager.findAll(pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(0);
  }

  @Test
  public void updateTest() {

    createOptionalMapperStubs();
    Mockito.when(mapper.updateMetadata(resource, added)).thenReturn(added);
    Mockito.when(repository.findById(reference.getId())).thenReturn(optionalAdded);
    Mockito.when(repository.save(added)).thenReturn(added);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.updateById(reference.getId(), resource);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(resource.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(reference.getId());
  }

  @Test
  public void updateFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(reference.getId())).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.updateById(reference.getId(), resource);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void deleteTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(reference.getId())).thenReturn(optionalAdded);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.deleteById(reference.getId());

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void deleteFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogus.getId())).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.deleteById(bogus.getId());

    Assertions.assertThat(response.isEmpty()).isTrue();
  }
}
