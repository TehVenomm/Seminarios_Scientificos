package br.com.mauda.seminario.cientificos.junit.contract;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;

public interface TestsGenericField<E extends Object> {

    void setValue(E value);

    void executionMethod();

    String getErrorMessage();

    @Test
    @DisplayName("Campo com valor nulo")
    public default void validarNulo() {
        setValue(null);
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }
}