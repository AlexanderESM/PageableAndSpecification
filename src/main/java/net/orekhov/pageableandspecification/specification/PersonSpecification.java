package net.orekhov.pageableandspecification.specification;


import jakarta.persistence.criteria.Predicate;
import net.orekhov.pageableandspecification.model.Person;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс, содержащий спецификации (условия фильтрации) для поиска объектов {@link 'Person'} в базе данных.
 * Использует {@link Specification} и {@link 'CriteriaBuilder'} для построения динамических запросов.
 *
 * <p><b>Пример использования:</b></p>
 * <pre>{@code
 * Specification<Person> spec = PersonSpecification.personSpecification(
 *     "123456",       // numberPassport
 *     "Иван",        // name
 *     "Иванов",      // surname
 *     null,          // age (не фильтруем)
 *     "MALE",        // sex
 *     "20",          // startAge (начало диапазона)
 *     "30"           // finishAge (конец диапазона)
 * );
 * List<Person> persons = personRepository.findAll(spec);
 * }</pre>
 *
 * <p><b>Особенности:</b></p>
 * <ul>
 *   <li>Поддерживает фильтрацию по всем полям {@link 'Person'}.</li>
 *   <li>Если параметр равен {@code null}, соответствующее условие игнорируется.</li>
 *   <li>Для возраста можно указать как точное значение ({@code age}), так и диапазон ({@code startAge-finishAge}).</li>
 * </ul>
 *
 * <p><b>Основные методы CriteriaBuilder (cb):</b></p>
 * <table border="1">
 *   <tr><th>Метод</th><th>Описание</th><th>Пример</th></tr>
 *   <tr>
 *     <td>{@code equal(x, y)}</td>
 *     <td>Проверка на равенство (x = y)</td>
 *     <td>{@code cb.equal(root.get("name"), "Alice")}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code notEqual(x, y)}</td>
 *     <td>Проверка на неравенство (x != y)</td>
 *     <td>{@code cb.notEqual(root.get("age"), 18)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code gt(x, y)}</td>
 *     <td>Больше чем (x > y)</td>
 *     <td>{@code cb.gt(root.get("age"), 20)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code lt(x, y)}</td>
 *     <td>Меньше чем (x < y)</td>
 *     <td>{@code cb.lt(root.get("age"), 30)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code ge(x, y)}</td>
 *     <td>Больше или равно (x >= y)</td>
 *     <td>{@code cb.ge(root.get("age"), 18)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code le(x, y)}</td>
 *     <td>Меньше или равно (x <= y)</td>
 *     <td>{@code cb.le(root.get("age"), 25)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code between(x, min, max)}</td>
 *     <td>Значение в диапазоне (x BETWEEN min AND max)</td>
 *     <td>{@code cb.between(root.get("age"), 18, 30)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code like(x, pattern)}</td>
 *     <td>Совпадение с шаблоном (x LIKE pattern)</td>
 *     <td>{@code cb.like(root.get("name"), "%A%")}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code isNull(x)}</td>
 *     <td>Проверка на NULL (x IS NULL)</td>
 *     <td>{@code cb.isNull(root.get("email"))}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code isNotNull(x)}</td>
 *     <td>Проверка на NOT NULL (x IS NOT NULL)</td>
 *     <td>{@code cb.isNotNull(root.get("phone"))}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code and(predicates)}</td>
 *     <td>Логическое И (AND)</td>
 *     <td>{@code cb.and(predicate1, predicate2)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code or(predicates)}</td>
 *     <td>Логическое ИЛИ (OR)</td>
 *     <td>{@code cb.or(predicate1, predicate2)}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code not(predicate)}</td>
 *     <td>Логическое НЕ (NOT)</td>
 *     <td>{@code cb.not(cb.equal(root.get("name"), "Bob"))}</td>
 *   </tr>
 *   <tr>
 *     <td>{@code asc(x)} / {@code desc(x)}</td>
 *     <td>Сортировка по возрастанию/убыванию</td>
 *     <td>{@code cb.asc(root.get("name"))}</td>
 *   </tr>
 * </table>
 *
 * @see 'Specification'
 * @see 'CriteriaBuilder'
 */

public class PersonSpecification {

    /**
     * Создает {@link Specification} для фильтрации объектов {@link 'Person'}.
     *
     * @param numberPassport Номер паспорта (точное совпадение). Если {@code null}, условие не добавляется.
     * @param name Имя (точное совпадение). Если {@code null}, условие не добавляется.
     * @param surname Фамилия (точное совпадение). Если {@code null}, условие не добавляется.
     * @param age Точный возраст. Если {@code null}, условие не добавляется.
     * @param sex Пол (точное совпадение). Если {@code null}, условие не добавляется.
     * @param startAge Начало диапазона возраста. Если указан без {@code finishAge},
     *                 выбираются лица с возрастом >= {@code startAge}.
     * @param finishAge Конец диапазона возраста. Если указан, фильтрует по диапазону
     *                  {@code startAge-finishAge} (включительно).
     * @return {@link 'Specification<Person>'} для использования в {@link 'JpaRepository#findAll(Specification)'}.
     *
     * @implNote Логика работы с возрастом:
     * <ul>
     *   <li>Если {@code startAge != null} и {@code finishAge == null}: возраст >= {@code startAge}.</li>
     *   <li>Если {@code startAge != null} и {@code finishAge != null}: возраст между {@code startAge} и {@code finishAge}.</li>
     * </ul>
     */

    public static Specification<Person> personSpecification(
            String numberPassport,
            String name,
            String surname,
            String age,
            String sex,
            String startAge,
            String finishAge
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (numberPassport != null) {
                predicates.add(cb.equal(root.get("numberPassport"), numberPassport));
            }
            if (name != null) {
                predicates.add(cb.equal(root.get("name"), name));
            }
            if (surname != null) {
                predicates.add(cb.equal(root.get("surname"), surname));
            }
            if (age != null) {
                predicates.add(cb.equal(root.get("age"), age));
            }
            if (sex != null) {
                predicates.add(cb.equal(root.get("sex"), sex));
            }

            Optional.ofNullable(startAge)
                    .map(s -> finishAge == null
                            ? cb.greaterThanOrEqualTo(root.get("age"), s)
                            : cb.between(root.get("age"), s, finishAge))
                    .ifPresent(predicates::add);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

