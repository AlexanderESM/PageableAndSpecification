package net.orekhov.pageableandspecification.model;

import java.util.List;

public record ResponseDto(

        List<PersonDto> persons,
        int pageNumber,
        int elementToPage,
        int countPage,
        long countPersons
) {
}
