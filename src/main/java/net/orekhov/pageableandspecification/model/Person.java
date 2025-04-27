package net.orekhov.pageableandspecification.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "persons")
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer numberPassport;

    private String name;
    private String surname;
    private Integer age;
    private String sex;

}

