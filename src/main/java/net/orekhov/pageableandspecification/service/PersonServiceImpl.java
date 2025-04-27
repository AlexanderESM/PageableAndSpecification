package net.orekhov.pageableandspecification.service;

import lombok.RequiredArgsConstructor;
import net.orekhov.pageableandspecification.mapper.PersonMapper;
import net.orekhov.pageableandspecification.model.Person;
import net.orekhov.pageableandspecification.model.RequestDto;
import net.orekhov.pageableandspecification.model.ResponseDto;
import net.orekhov.pageableandspecification.repository.PersonRepository;
import net.orekhov.pageableandspecification.specification.PersonSpecification;
import net.orekhov.pageableandspecification.util.RandomStringGenerator;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.orekhov.pageableandspecification.model.PersonDto;
import java.util.List;
import java.util.Random;


/**
 * Реализация сервиса для управления сущностями {@link Person}.
 *
 * <p>Позволяет:</p>
 * <ul>
 *     <li>Создавать персон вручную и случайным образом.</li>
 *     <li>Получать всех персон (списком или постранично).</li>
 *     <li>Фильтровать результаты поиска по различным полям.</li>
 * </ul>
 *
 * <p>Использует репозиторий {@link PersonRepository} для взаимодействия с базой данных и {@link PersonMapper} для преобразования в DTO.</p>
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    /**
     * Создаёт новую запись персоны на основе DTO.
     *
     * @param personDto DTO персоны для создания.
     * @return DTO сохранённой персоны.
     */
    @Override
    public PersonDto createPerson(PersonDto personDto) {
        Person savedPerson = personRepository.save(personMapper.toEntity(personDto));
        return personMapper.toDto(savedPerson);
    }

    /**
     * Генерирует и сохраняет указанное количество случайных персон.
     *
     * @param count Количество персон для создания.
     * @return Сообщение об успешной операции.
     */
    @Override
    public String createRandomPersons(int count) {
        for (int i = 0; i < count; i++) {
            PersonDto personDto = PersonDto.builder()
                    .name(RandomStringGenerator.generateRandomName(5))
                    .surname(RandomStringGenerator.generateRandomName(8))
                    .age(new Random().nextInt(100))
                    .sex("Male")
                    .numberPassport(new Random().nextInt(100000))
                    .build();
            createPerson(personDto);
        }

        return "Успешно создано: "+ count +" сущностей в БД";
    }

    /**
     * Получает список всех персон без пагинации.
     *
     * @return Список всех персон в формате {@link PersonDto}.
     */
    @Override
    public List<PersonDto> findAllPersons() {
        List<Person> all = personRepository.findAll();
        return all.stream().map(personMapper::toDto).toList();
    }

    /**
     * Получает постраничный список персон с возможностью сортировки.
     *
     * @param pageNumber   Номер страницы (начинается с 0).
     * @param pageSize     Размер страницы.
     * @param sortField    Поле для сортировки.
     * @param sortDirection Направление сортировки: asc или desc.
     * @return Страница сущностей {@link Person}.
     */
    @Override
    public Page<Person> findAllPersonsPage(int pageNumber, int pageSize, String sortField, String sortDirection) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        return personRepository.findAll(pageable);
    }

    /**
     * Получает постраничный список персон в формате DTO с возможностью сортировки.
     *
     * @param pageNumber   Номер страницы.
     * @param pageSize     Размер страницы.
     * @param sortField    Поле для сортировки.
     * @param sortDirection Направление сортировки: asc или desc.
     * @return Объект {@link ResponseDto} с данными и метаинформацией о пагинации.
     */
    @Override
    public ResponseDto findAllPersonsPageDto(int pageNumber, int pageSize, String sortField, String sortDirection) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        Page<Person> all = personRepository.findAll(pageable);

        return new ResponseDto(
                all.getContent().stream().map(personMapper::toDto).toList(),
                all.getPageable().getPageNumber(),
                all.getPageable().getPageSize(),
                all.getTotalPages(),
                all.getTotalElements()
        );
    }

    /**
     * Получает постраничный и отфильтрованный список персон в формате DTO.
     *
     * <p>Фильтрация осуществляется по параметрам, переданным в {@link RequestDto}.</p>
     *
     * @param requestDto DTO с параметрами фильтрации и пагинации.
     * @return Объект {@link ResponseDto} с фильтрованными данными и метаинформацией о пагинации.
     */
    @Override
    public ResponseDto findAllPersonsPageDtoFil(RequestDto requestDto) {

        Pageable pageable = PageRequest.of(requestDto.page(),
                requestDto.size(),
                Sort.by(Sort.Direction.fromString(requestDto.sortDirection()), requestDto.sortField()));

        Specification<Person> spec = PersonSpecification.personSpecification(
                requestDto.numberPassport(),
                requestDto.name(),
                requestDto.surname(),
                requestDto.age(),
                requestDto.sex(),
                requestDto.startAge(),
                requestDto.finishAge()
        );

        Page<Person> personPage = personRepository.findAll(spec, pageable);

        return new ResponseDto(
                personPage.getContent().stream().map(personMapper::toDto).toList(),
                personPage.getPageable().getPageNumber(),
                personPage.getPageable().getPageSize(),
                personPage.getTotalPages(),
                personPage.getTotalElements()
        );
    }
}
