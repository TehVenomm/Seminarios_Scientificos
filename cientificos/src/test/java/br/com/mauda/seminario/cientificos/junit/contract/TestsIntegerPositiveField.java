package br.com.mauda.seminario.cientificos.junit.contract;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;

public interface TestsIntegerPositiveField extends TestsGenericField<Integer> {

    @Test
    @DisplayName("Campo preechido com valor negativo")
    public default void validarValorNegativo() {
        setValue(-1);
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Campo preechido com valor zero")
    public default void validarValorZero() {
        setValue(0);
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }
}