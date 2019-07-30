package br.com.mauda.seminario.cientificos.junit.contract;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public interface TestsIntegerPositiveField extends TestsGenericField<Integer> {

    @Test
    @DisplayName("Campo preechido com valor negativo")
    public default void validarValorNegativo() {
        setValue(-1);
        assertThrows(() -> executionMethod(), getErrorMessage());
    }

    @Test
    @DisplayName("Campo preechido com valor zero")
    public default void validarValorZero() {
        setValue(0);
        assertThrows(() -> executionMethod(), getErrorMessage());
    }
}