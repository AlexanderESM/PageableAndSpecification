package net.orekhov.pageableandspecification.controller;

import net.orekhov.pageableandspecification.model.Person;
import net.orekhov.pageableandspecification.model.PersonDto;
import net.orekhov.pageableandspecification.model.RequestDto;
import net.orekhov.pageableandspecification.model.ResponseDto;
import net.orekhov.pageableandspecification.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Контроллер для управления сущностями {@link Person}.
 *
 * <p>Позволяет:</p>
 * <ul>
 *     <li>Создавать случайные записи {@link Person}.</li>
 *     <li>Получать список всех персон.</li>
 *     <li>Получать персон постранично с сортировкой.</li>
 *     <li>Получать DTO персон с поддержкой пагинации и фильтрации.</li>
 * </ul>
 *
 * <p>Все эндпоинты начинаются с префикса <b>/person</b>.</p>
 *
 */

@RestController
@RequestMapping("person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    /**
     * Создание указанного количества случайных персон.
     *
     * @param count Количество персон для создания.
     * @return Строка с результатом операции.
     */
    @GetMapping("/create-count/{count}")
    public ResponseEntity<String> createCount(@PathVariable int count) {
        return ResponseEntity.ok(personService.createRandomPersons(count));
    }

    /**
     * Получение списка всех персон без пагинации.
     *
     * @return Список объектов {@link PersonDto}.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<PersonDto>> getAll() {
        return ResponseEntity.ok(personService.findAllPersons());
    }


    /**
     * Получение всех персон с пагинацией и сортировкой.
     *
     * @param page          Номер страницы (от 0).
     * @param size          Размер страницы.
     * @param sortField     Поле для сортировки.
     * @param sortDirection Направление сортировки: asc или desc.
     * @return Страница объектов {@link Person}.
     */
    @GetMapping("/getAllPages")
    public ResponseEntity<Page<Person>> getAllPages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "numberPassport") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return ResponseEntity.ok(personService.findAllPersonsPage(page, size, sortField, sortDirection));
    }

    /**
     * Получение всех персон в формате DTO с пагинацией и сортировкой.
     *
     * @param page          Номер страницы (от 0).
     * @param size          Размер страницы.
     * @param sortField     Поле для сортировки.
     * @param sortDirection Направление сортировки: asc или desc.
     * @return Объект {@link ResponseDto} с данными о персонах и информацией о страницах.
     */
    @GetMapping("/getAllDto")
    public ResponseEntity<ResponseDto> getAllDto(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "numberPassport") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return ResponseEntity.ok(personService.findAllPersonsPageDto(page, size, sortField, sortDirection));
    }

    /**
     * Получение персон с фильтрацией и пагинацией.
     *
     * <p><b>Как работает {@code @ModelAttribute}:</b></p>
     * <ul>
     *   <li>Автоматически связывает параметры HTTP-запроса с полями объекта {@link RequestDto}.</li>
     *   <li>Поддерживает все типы параметров: query (?name=value), form-data, x-www-form-urlencoded.</li>
     *   <li>Если поле не передано в запросе, оно останется {@code null} (или значением по умолчанию).</li>
     * </ul>
     *
     * <p><b>Пример запроса:</b></p>
     * <pre>{@code
     * GET /person/getAll-filter?page=0&size=10&name=Иван&surname=Иванов
     * }</pre>
     *
     * @param requestDto DTO с параметрами фильтрации и пагинации.
     * @return {@link ResponseEntity} с {@link ResponseDto}, содержащим отфильтрованные персональные данные.
     *
     * @see RequestDto
     * @see ResponseDto
     */

    @GetMapping("/getAll-filter")
    public ResponseEntity<ResponseDto> getAllDtoFil(@ModelAttribute RequestDto requestDto) {
        return ResponseEntity.ok(personService.findAllPersonsPageDtoFil(requestDto));
    }

}
