package io.twdps.starter.example.persistence.model;

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
@Table(name = "account", schema = "example")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(
      name = "uuid",
      strategy = "uuid2",
      parameters = {})
  @XmlAttribute
  private String id;

  // TODO: Refactor AccountEntity by renaming variable names, migrate types
  @NonNull private String userName;
  @NonNull private String pii;
  @NonNull private String firstName;
  @NonNull private String lastName;
  // TODO: Additional AccountEntity data elements
}
