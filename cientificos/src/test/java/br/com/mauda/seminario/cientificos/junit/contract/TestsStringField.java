package br.com.mauda.seminario.cientificos.junit.contract;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public interface TestsStringField extends TestsGenericField<String> {

    default int getMaxSizeField() {
        return 50;
    }

    @Override
    @Test
    @DisplayName("Campo com valor nulo")
    public default void validarNulo() {
        setValue(null);
        assertThrows(() -> executionMethod(), getErrorMessage());
    }

    @Test
    @DisplayName("Campo preechido com somente espacos em branco")
    public default void validarValorComEspacos() {
        setValue("     ");
        assertThrows(() -> executionMethod(), getErrorMessage());
    }

    @Test
    @DisplayName("Campo com numero de caracteres maior que o permitido")
    public default void validarValorcomTamanhoExcedido() {
        setValue(RandomStringUtils.random(getMaxSizeField() + 1));
        assertThrows(() -> executionMethod(), getErrorMessage());
    }
}