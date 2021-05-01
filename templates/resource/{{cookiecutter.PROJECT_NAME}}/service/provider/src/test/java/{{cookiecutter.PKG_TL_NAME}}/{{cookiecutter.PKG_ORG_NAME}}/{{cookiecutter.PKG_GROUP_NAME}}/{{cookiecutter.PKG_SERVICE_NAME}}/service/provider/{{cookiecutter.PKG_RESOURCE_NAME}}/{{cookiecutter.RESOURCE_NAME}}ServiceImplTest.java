package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}};

import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}Entity;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.RESOURCE_NAME}}EntityRepository;
{% if cookiecutter.CREATE_SUB_RESOURCE == "y" -%}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.SUB_RESOURCE_NAME}}Entity;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model.{{cookiecutter.SUB_RESOURCE_NAME}}EntityRepository;
{%- endif %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.provider.{{cookiecutter.PKG_RESOURCE_NAME}}.mapper.{{cookiecutter.RESOURCE_NAME}}EntityMapper;
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.RESOURCE_NAME}};
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
import {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.service.spi.{{cookiecutter.PKG_RESOURCE_NAME}}.model.{{cookiecutter.SUB_RESOURCE_NAME}};
{%- endif %}
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
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
  @Mock private {{cookiecutter.SUB_RESOURCE_NAME}}EntityRepository subResourceRepository;
{%- endif %}

  private final String username = "jsmith";
  private final String pii = "123-45-6789";
  private final String bogusName = "bogus";
  private final String firstName = "Joe";
  private final String lastName = "Smith";
  private final String identifier = "12345";
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}
  private final String subIdentifier = "abcde";
  private final String parentIdentifier = "12345";
{%- endif %}

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

  {%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  private {{cookiecutter.SUB_RESOURCE_NAME}} subResource;
  private {{cookiecutter.SUB_RESOURCE_NAME}} subOutput;
  private {{cookiecutter.SUB_RESOURCE_NAME}}Entity subEntity;
  private {{cookiecutter.SUB_RESOURCE_NAME}}Entity subAdded;
  private Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> empty{{cookiecutter.SUB_RESOURCE_NAME}} = Optional.empty();
  private Optional<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> emptySubEntity = Optional.empty();
  private Optional<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> optionalSubEntity;
  private Optional<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> optionalSubAdded;
  private Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> optionalSubOutput;
  private List<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> subEntityList;
  private List<{{cookiecutter.SUB_RESOURCE_NAME}}> subOutputList;
  private List<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> emptySubEntityList = Arrays.asList();
  private List<{{cookiecutter.SUB_RESOURCE_NAME}}> emptySubOutputList = Arrays.asList();
  private Page<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> subEntityPage;
  private Page<{{cookiecutter.SUB_RESOURCE_NAME}}> subOutputPage;
  private Page<{{cookiecutter.SUB_RESOURCE_NAME}}Entity> emptySubEntityPage;
  private Page<{{cookiecutter.SUB_RESOURCE_NAME}}> emptySubOutputPage;
{%- endif %}

  /** setup data for each test. */
  @BeforeEach
  public void setup() {

    manager = new {{cookiecutter.RESOURCE_NAME}}ServiceImpl(repository, mapper
{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}, subResourceRepository{%- endif -%}
    );

    // use the real mapper to generate consistent objects to use in mapper stubs
    {{cookiecutter.RESOURCE_NAME}}EntityMapper real = Mappers.getMapper({{cookiecutter.RESOURCE_NAME}}EntityMapper.class);

    resource =
        {{cookiecutter.RESOURCE_NAME}}.builder()
            .userName(username)
            .pii(pii)
            .firstName(firstName)
            .lastName(lastName)
            .build();
    entity = real.toEntity(resource);
    added =
        new {{cookiecutter.RESOURCE_NAME}}Entity(
            identifier,
            entity.getUserName(),
            entity.getPii(),
            entity.getFirstName(),
            entity.getLastName()
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %},
            entity.get{{cookiecutter.PARENT_RESOURCE_NAME}}Id()
{%- endif -%}
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

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

    subResource =
        {{cookiecutter.SUB_RESOURCE_NAME}}.builder()
            .userName(username)
            .firstName(firstName)
            .lastName(lastName)
            .build();
    subEntity = real.to{{cookiecutter.SUB_RESOURCE_NAME}}Entity(subResource);
    subAdded =
        new {{cookiecutter.SUB_RESOURCE_NAME}}Entity(
            subIdentifier,
            subEntity.getUserName(),
            subEntity.getPii(),
            subEntity.getFirstName(),
            subEntity.getLastName(),
            parentIdentifier);
    subOutput = real.to{{cookiecutter.SUB_RESOURCE_NAME}}Model(subAdded);
    optionalSubEntity = Optional.of(subEntity);
    optionalSubAdded = Optional.of(subAdded);
    optionalSubOutput = Optional.of(subOutput);
    subEntityList = Arrays.asList(subAdded, subAdded);
    subOutputList = Arrays.asList(subOutput, subOutput);
    subEntityPage = new PageImpl<>(subEntityList);
    subOutputPage = new PageImpl<>(subOutputList);
    emptySubEntityPage = new PageImpl<>(emptySubEntityList);
    emptySubOutputPage = new PageImpl<>(emptySubOutputList);
{%- endif %}
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

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  private void create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Entity(subResource)).thenReturn(subEntity);
    Mockito.when(mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Model(subAdded)).thenReturn(subOutput);
  }

  private void createOptional{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Model(optionalSubAdded)).thenReturn(optionalSubOutput);
  }

  private void createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}Model(emptySubEntity)).thenReturn(empty{{cookiecutter.SUB_RESOURCE_NAME}});
  }

  private void create{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}ModelPage(subEntityPage)).thenReturn(subOutputPage);
  }

  private void createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs() {
    Mockito.when(mapper.to{{cookiecutter.SUB_RESOURCE_NAME}}ModelPage(emptySubEntityPage)).thenReturn(emptySubOutputPage);
  }
{%- endif %}

  @Test
  public void findBy{{cookiecutter.RESOURCE_NAME}}IdFailTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(Mockito.any())).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> result = manager.findById("bogus");
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
    Mockito.when(repository.findByUserName(username)).thenReturn(optionalAdded);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByUserName(username);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByUserNameFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findByUserName(bogusName)).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByUserName(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findByLastNameTest() {

    createListMapperStubs();
    Mockito.when(repository.findByLastName(username, pageable)).thenReturn(entityPage);

    Page<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByLastName(username, pageable);

    Assertions.assertThat(response.getContent().isEmpty()).isFalse();
    Assertions.assertThat(response.getContent().get(0).getFirstName())
        .isEqualTo(added.getFirstName());
    Assertions.assertThat(response.getContent().get(0).getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByLastNameFailedTest() {

    createEmptyListMapperStubs();
    Mockito.when(repository.findByLastName(bogusName, pageable)).thenReturn(emptyEntityPage);

    Page<{{cookiecutter.RESOURCE_NAME}}> response = manager.findByLastName(bogusName, pageable);

    Assertions.assertThat(response.getContent().isEmpty()).isTrue();
  }

  @Test
  public void findByIdTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findById(identifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void findByIdFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogusName)).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.findById(bogusName);

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
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);
    Mockito.when(repository.save(added)).thenReturn(added);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.updateById(identifier, resource);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(resource.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(identifier);
  }

  @Test
  public void updateFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.updateById(identifier, resource);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void deleteTest() {

    createOptionalMapperStubs();
    Mockito.when(repository.findById(identifier)).thenReturn(optionalAdded);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.deleteById(identifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(added.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(added.getId());
  }

  @Test
  public void deleteFailedTest() {

    createEmptyMapperStubs();
    Mockito.when(repository.findById(bogusName)).thenReturn(emptyEntity);

    Optional<{{cookiecutter.RESOURCE_NAME}}> response = manager.deleteById(bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

{%- if cookiecutter.CREATE_SUB_RESOURCE == "y" %}

  @Test
  public void findBy{{cookiecutter.SUB_RESOURCE_NAME}}IdFailTest() {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(subResourceRepository.findById(Mockito.any())).thenReturn(emptySubEntity);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> result =
        manager.get{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, "bogus");
    Assertions.assertThat(!result.isPresent()).isTrue();
  }

  @Test
  public void add{{cookiecutter.SUB_RESOURCE_NAME}}Test() {

    create{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(subResourceRepository.save(subEntity)).thenReturn(subAdded);

    {{cookiecutter.SUB_RESOURCE_NAME}} response =
        manager.add{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subResource);

    Assertions.assertThat(response.getFirstName()).isEqualTo(subResource.getFirstName());
    Assertions.assertThat(response.getId()).isEqualTo(subAdded.getId());
    Assertions.assertThat(response.getId()).isEqualTo(subIdentifier);
  }

  @Test
  public void find{{cookiecutter.SUB_RESOURCE_NAME}}ByIdTest() {

    createOptional{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(subResourceRepository.findById(subIdentifier)).thenReturn(optionalSubAdded);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response =
        manager.get{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subIdentifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subAdded.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subAdded.getId());
  }

  @Test
  public void find{{cookiecutter.SUB_RESOURCE_NAME}}ByIdFailedTest() {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(subResourceRepository.findById(bogusName)).thenReturn(emptySubEntity);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response =
        manager.get{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void findAll{{cookiecutter.SUB_RESOURCE_NAME}}Test() {

    create{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs();
    Mockito.when(subResourceRepository.findAllBy{{cookiecutter.PARENT_RESOURCE_NAME}}Id(identifier, pageable))
        .thenReturn(subEntityPage);

    Page<{{cookiecutter.SUB_RESOURCE_NAME}}> response =
        manager.get{{cookiecutter.SUB_RESOURCE_NAME}}s(identifier, pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(2);
  }

  @Test
  public void findAll{{cookiecutter.SUB_RESOURCE_NAME}}EmptyTest() {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}ListMapperStubs();
    Mockito.when(subResourceRepository.findAllBy{{cookiecutter.PARENT_RESOURCE_NAME}}Id(identifier, pageable))
        .thenReturn(emptySubEntityPage);

    Page<{{cookiecutter.SUB_RESOURCE_NAME}}> response =
        manager.get{{cookiecutter.SUB_RESOURCE_NAME}}s(identifier, pageable);

    Assertions.assertThat(response.getContent().size()).isEqualTo(0);
  }

  @Test
  public void update{{cookiecutter.SUB_RESOURCE_NAME}}Test() {

    createOptional{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(mapper.update{{cookiecutter.SUB_RESOURCE_NAME}}Metadata(subResource, subAdded))
        .thenReturn(subAdded);
    Mockito.when(subResourceRepository.findById(subIdentifier)).thenReturn(optionalSubAdded);
    Mockito.when(subResourceRepository.save(subAdded)).thenReturn(subAdded);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response =
        manager.update{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subIdentifier, subResource);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subResource.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subIdentifier);
  }

  @Test
  public void update{{cookiecutter.SUB_RESOURCE_NAME}}FailedTest() {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(subResourceRepository.findById(subIdentifier)).thenReturn(emptySubEntity);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response =
        manager.update{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subIdentifier, subResource);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }

  @Test
  public void delete{{cookiecutter.SUB_RESOURCE_NAME}}Test() {

    createOptional{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(subResourceRepository.findById(subIdentifier)).thenReturn(optionalSubAdded);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response =
        manager.delete{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, subIdentifier);

    Assertions.assertThat(response.isPresent()).isTrue();
    Assertions.assertThat(response.get().getFirstName()).isEqualTo(subAdded.getFirstName());
    Assertions.assertThat(response.get().getId()).isEqualTo(subAdded.getId());
  }

  @Test
  public void delete{{cookiecutter.SUB_RESOURCE_NAME}}FailedTest() {

    createEmpty{{cookiecutter.SUB_RESOURCE_NAME}}MapperStubs();
    Mockito.when(subResourceRepository.findById(bogusName)).thenReturn(emptySubEntity);

    Optional<{{cookiecutter.SUB_RESOURCE_NAME}}> response =
        manager.delete{{cookiecutter.SUB_RESOURCE_NAME}}(identifier, bogusName);

    Assertions.assertThat(response.isEmpty()).isTrue();
  }
{%- endif %}
}
