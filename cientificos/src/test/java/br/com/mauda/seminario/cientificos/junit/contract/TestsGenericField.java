package br.com.mauda.seminario.cientificos.junit.contract;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public interface TestsGenericField<E extends Object> {

    void setValue(E value);

    void executionMethod();

    String getErrorMessage();

    @Test
    @DisplayName("Campo com valor nulo")
    public default void validarNulo() {
        setValue(null);
        assertThrows(() -> executionMethod(), getErrorMessage());
    }
}