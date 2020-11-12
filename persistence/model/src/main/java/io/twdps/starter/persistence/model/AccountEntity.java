package io.twdps.starter.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @NonNull
  private String userName;
  @NonNull
  private String firstName;
  @NonNull
  private String lastName;
}
