package net.orekhov.pageableandspecification.mapper;

import net.orekhov.pageableandspecification.model.Person;
import net.orekhov.pageableandspecification.model.PersonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

/**
 * Маппер для преобразования между сущностями {@link Person} и DTO {@link PersonDto}.
 *
 * <p>Использует библиотеку <b>MapStruct</b> для автоматической генерации кода маппинга.</p>
 *
 * <p><b>Особенности:</b></p>
 * <ul>
 *     <li>Маппинг в обе стороны: сущность → DTO и DTO → сущность.</li>
 *     <li>При преобразовании из DTO в сущность поле {@code id} игнорируется.</li>
 *     <li>Поддерживается преобразование списков объектов.</li>
 * </ul>
 *
 * <p>Аннотация {@code @Mapper(componentModel = "spring")} позволяет Spring автоматически управлять этим компонентом.</p>
 *
 * @see Person
 * @see PersonDto
 */
@Mapper(componentModel = "spring")
public interface PersonMapper {

    /**
     * Преобразует объект {@link PersonDto} в сущность {@link Person}.
     *
     * <p><b>Важно:</b> поле {@code id} будет проигнорировано при преобразовании.</p>
     *
     * @param personDto DTO объекта {@link Person}.
     * @return Сущность {@link Person} без установленного идентификатора.
     */
    @Mapping(target = "id", ignore = true)
    Person toEntity(PersonDto personDto);

    /**
     * Преобразует сущность {@link Person} в DTO {@link PersonDto}.
     *
     * @param person Сущность {@link Person}.
     * @return DTO {@link PersonDto}.
     */
    PersonDto toDto(Person person);

    /**
     * Преобразует список сущностей {@link Person} в список DTO {@link PersonDto}.
     *
     * @param persons Список сущностей {@link Person}.
     * @return Список DTO {@link PersonDto}.
     */
    List<PersonDto> toDtoList(List<Person> persons);
}
