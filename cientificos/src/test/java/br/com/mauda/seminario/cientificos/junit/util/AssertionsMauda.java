package br.com.mauda.seminario.cientificos.junit.util;

import java.util.function.BooleanSupplier;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;

public class AssertionsMauda extends Assertions {

    /**
     * <em>Verifica</em> se os objetos {@code expected} e {@code actual} são iguais. Utilizando o método equals da classe Object.
     * <p>
     * Se ambos são {@code null}, são considerados iguais.
     * <p>
     * Se necessário, a mensagem de falha será obtida tardiamentea partir do supplied {@code MensagensUtils.getErrorMessage(message)}.
     *
     * @see Object#equals(Object)
     */
    public static void assertEquals(Object expected, Object actual, String message) {
        Assertions.assertEquals(expected, actual, MensagensUtils.getErrorMessage(message));
    }

    /**
     * <em>Asserts</em> that {@code actual} is not {@code null}.
     * <p>
     * Fails with the supplied failure {@code message}.
     */
    public static void assertNotNull(Object actual, String message) {
        Assertions.assertNotNull(actual, MensagensUtils.getErrorMessage(message));
    }

    /**
     * <em>Asserts</em> that {@code actual} is not {@code null}.
     * <p>
     * Fails with the supplied failure {@code message}.
     */
    public static void assertNull(Object actual, String message) {
        Assertions.assertNull(actual, MensagensUtils.getErrorMessage(message));
    }

    /**
     * <em>Asserts</em> that the information{@code information} is not blank {@code true}.
     * <p>
     * If necessary, the failure message will be retrieved lazily from the supplied {@code messageSupplier}.
     */
    public static void assertIsNotBlank(String information, String message) {
        Assertions.assertTrue(StringUtils.isNotBlank(information), MensagensUtils.getErrorMessage(message));
    }

    /**
     * <em>Asserts</em> that the supplied {@code condition} is {@code true}.
     * <p>
     * Fails with the supplied failure {@code message}.
     */
    public static void assertTrue(BooleanSupplier booleanSupplier, String message) {
        Assertions.assertTrue(booleanSupplier, MensagensUtils.getErrorMessage(message));
    }
}
