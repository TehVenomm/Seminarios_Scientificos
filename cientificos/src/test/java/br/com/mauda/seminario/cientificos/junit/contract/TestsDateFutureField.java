package br.com.mauda.seminario.cientificos.junit.contract;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;

public interface TestsDateFutureField extends TestsGenericField<Date> {

    @Test
    @DisplayName("Campo preechido com data antes da atual")
    public default void validarValorAnteriorDataAtual() {
        setValue(DateUtils.addDays(new Date(), -30));
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> executionMethod());
        Assertions.assertEquals(getErrorMessage(), exception.getMessage());
    }
}