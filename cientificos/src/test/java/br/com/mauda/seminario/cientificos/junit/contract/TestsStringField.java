package br.com.mauda.seminario.cientificos.junit.contract;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;

public interface TestsStringField extends TestsGenericField<String> {

    default int getMaxSizeField() {
        return 50;
    }

    @Override
    @Test
    @DisplayName("Campo com valor nulo")
    public default void validarNulo() {
        setValue(null);
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Campo preechido com somente espacos em branco")
    public default void validarValorComEspacos() {
        setValue("     ");
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Campo com numero de caracteres maior que o permitido")
    public default void validarValorcomTamanhoExcedido() {
        setValue(RandomStringUtils.random(getMaxSizeField() + 1));
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }
}