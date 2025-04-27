package net.orekhov.pageableandspecification.util;

import java.util.Random;
/**
 * Утилитный класс для генерации случайных строк, состоящих из латинских букв.
 *
 * <p><b>Особенности:</b></p>
 * <ul>
 *     <li>Использует фиксированный набор букв (A-Z, a-z).</li>
 *     <li>Генерация происходит с использованием {@link Random} для случайности.</li>
 *     <li>Все методы статические — класс не предполагает создания экземпляра.</li>
 * </ul>
 *
 */
public class RandomStringGenerator {
    /**
     * Строка, содержащая все возможные символы для генерации:
     * латинские буквы верхнего и нижнего регистра.
     */
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * Генератор случайных чисел для выбора символов из строки {@link #LETTERS}.
     */
    private static final Random random = new Random();

    /**
     * Генерирует случайную строку заданной длины из латинских букв.
     *
     * <p><b>Пример использования:</b></p>
     * <pre>{@code
     * String randomName = RandomStringGenerator.generateRandomName(10);
     * System.out.println(randomName); // Например: "aBcDeFgHiJ"
     * }</pre>
     *
     * @param length Длина строки, которую необходимо сгенерировать.
     * @return Случайная строка указанной длины.
     * @throws IllegalArgumentException если {@code length} меньше 0.
     */
    public static String generateRandomName(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        }
        return sb.toString();
    }
}
