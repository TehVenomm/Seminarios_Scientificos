package br.com.mauda.seminario.cientificos.junit.contract;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public interface TestsEmailField extends TestsGenericField<String> {

    default int getMaxSizeField() {
        return 50;
    }

    @Test
    @DisplayName("Campo com numero de caracteres maior que o permitido")
    public default void validarNomeExcedido() {
        setValue("eu101010101010101010101101010101@instituicao.com.br");
        assertThrows(() -> executionMethod(), getErrorMessage());
    }

    @Test
    @DisplayName("Email sem arroba")
    public default void validarEmailSemArroba() {
        setValue("estudanteinstituicao.com.br");
        assertThrows(() -> executionMethod(), getErrorMessage());
    }

    @Test
    @DisplayName("Email sem ponto")
    public default void validarEmailSemPonto() {
        setValue("estudante@instituicaocombr");
        assertThrows(() -> executionMethod(), getErrorMessage());
    }
}