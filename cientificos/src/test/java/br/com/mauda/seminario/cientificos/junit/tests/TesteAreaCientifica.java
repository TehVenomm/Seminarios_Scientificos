package br.com.mauda.seminario.cientificos.junit.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.AreaCientificaBC;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.junit.contract.TestsStringField;
import br.com.mauda.seminario.cientificos.junit.converter.AreaCientificaConverter;
import br.com.mauda.seminario.cientificos.junit.executable.AreaCientificaExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaAreaCientifica;
import br.com.mauda.seminario.cientificos.model.AreaCientifica;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteAreaCientifica {

    protected AreaCientificaBC bc = AreaCientificaBC.getInstance();
    protected AreaCientificaConverter converter = new AreaCientificaConverter();
    protected AreaCientifica areaCientifica;

    @BeforeEach
    void beforeEach() {
        this.areaCientifica = this.converter.create(EnumUtils.getInstanceRandomly(MassaAreaCientifica.class));
    }

    @Tag("businessTest")
    @DisplayName("Criacao de uma Area Cientifica")
    @ParameterizedTest(name = "Criacao da Area Cientifica [{arguments}]")
    @EnumSource(MassaAreaCientifica.class)
    public void criar(@ConvertWith(AreaCientificaConverter.class) AreaCientifica object) {
        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new AreaCientificaExecutable(object));
        this.bc.insert(object);
    }

    @Tag("businessTest")
    @Test
    @DisplayName("Criacao de uma Area Cientifica nula")
    public void validarNulo() {
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> this.bc.insert(null));
        Assertions.assertEquals("ER0003", exception.getMessage());
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para o nome de uma Area Cientifica")
    class NomeAreaCientifica implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteAreaCientifica.this.areaCientifica.setNome(value);
        }

        @Override
        public void executionMethod() {
            TesteAreaCientifica.this.bc.insert(TesteAreaCientifica.this.areaCientifica);
        }

        @Override
        public String getErrorMessage() {
            return "ER0010";
        }
    }
}