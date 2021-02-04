package {{cookiecutter.PKG_TL_NAME}}.{{cookiecutter.PKG_ORG_NAME}}.{{cookiecutter.PKG_GROUP_NAME}}.{{cookiecutter.PKG_SERVICE_NAME}}.persistence.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;

@Entity
@Table(name = "{{cookiecutter.RESOURCE_TABLE_NAME}}")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class {{cookiecutter.RESOURCE_NAME}}Entity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2", parameters = {})
  @XmlAttribute
  private String id;

  @NonNull private String userName;
  @NonNull private String firstName;
  @NonNull private String lastName;
}
