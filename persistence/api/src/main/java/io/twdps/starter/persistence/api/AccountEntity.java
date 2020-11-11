package io.twdps.starter.persistence.api;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Data //Lombok: https://projectlombok.org/features/Data
@Builder //lombok - good for testing
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userName;
    private String firstName;
    private String lastName;
}
