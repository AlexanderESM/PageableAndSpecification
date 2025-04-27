package net.orekhov.pageableandspecification.model;

import lombok.Builder;

@Builder
public record PersonDto(

        Integer numberPassport,
        String name,
        String surname,
        int age,
        String sex
) {
}
