package br.com.mauda.seminario.cientificos.junit.contract;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;

public interface TestsEmailField extends TestsGenericField<String> {

    default int getMaxSizeField() {
        return 50;
    }

    @Test
    @DisplayName("Campo com numero de caracteres maior que o permitido")
    public default void validarNomeExcedido() {
        setValue("eu101010101010101010101101010101@instituicao.com.br");
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Email sem arroba")
    public default void validarEmailSemArroba() {
        setValue("estudanteinstituicao.com.br");
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Email sem ponto")
    public default void validarEmailSemPonto() {
        setValue("estudante@instituicaocombr");
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }
}