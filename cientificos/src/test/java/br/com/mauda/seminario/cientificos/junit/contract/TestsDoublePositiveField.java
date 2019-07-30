package br.com.mauda.seminario.cientificos.junit.contract;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public interface TestsDoublePositiveField extends TestsGenericField<Double> {

    @Test
    @DisplayName("Campo preechido com valor negativo")
    public default void validarValorNegativo() {
        setValue(-1d);
        assertThrows(() -> executionMethod(), getErrorMessage());
    }

    @Test
    @DisplayName("Campo preechido com valor zero")
    public default void validarValorZero() {
        setValue(0d);
        assertThrows(() -> executionMethod(), getErrorMessage());
    }
}