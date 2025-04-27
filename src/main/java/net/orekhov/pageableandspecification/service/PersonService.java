package net.orekhov.pageableandspecification.service;

import net.orekhov.pageableandspecification.model.Person;
import net.orekhov.pageableandspecification.model.PersonDto;
import net.orekhov.pageableandspecification.model.RequestDto;
import net.orekhov.pageableandspecification.model.ResponseDto;
import org.springframework.data.domain.Page;


import java.util.List;

public interface PersonService {

    PersonDto createPerson(PersonDto personDto);

    String createRandomPersons(int count);

    List<PersonDto> findAllPersons();

    Page<Person> findAllPersonsPage(int pageNumber, int pageSize, String sortField, String sortDirection);

    ResponseDto findAllPersonsPageDto(int pageNumber, int pageSize, String sortField, String sortDirection);

    ResponseDto findAllPersonsPageDtoFil(RequestDto requestDto);
}
