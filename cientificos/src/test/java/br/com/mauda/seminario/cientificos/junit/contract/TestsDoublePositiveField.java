package br.com.mauda.seminario.cientificos.junit.contract;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;

public interface TestsDoublePositiveField extends TestsGenericField<Double> {

    @Test
    @DisplayName("Campo preechido com valor negativo")
    public default void validarValorNegativo() {
        setValue(-1d);
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Campo preechido com valor zero")
    public default void validarValorZero() {
        setValue(0d);
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }
}