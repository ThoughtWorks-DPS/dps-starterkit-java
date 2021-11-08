package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;

@Entity
@Table(name = "{{cookiecutter.RESOURCE_TABLE_NAME}}", schema = "{{cookiecutter.PKG_SERVICE_NAME}}")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class {{cookiecutter.RESOURCE_NAME}}Entity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2", parameters = {})
  @XmlAttribute
  private String id;

  @NonNull private String userName;
  @NonNull private String pii;
  @NonNull private String firstName;
  @NonNull private String lastName;
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %}
  private String {{cookiecutter.PARENT_RESOURCE_VAR_NAME}}Id;
{%- endif %}
}
