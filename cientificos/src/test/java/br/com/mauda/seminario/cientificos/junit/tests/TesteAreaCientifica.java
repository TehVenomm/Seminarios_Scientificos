package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.AreaCientificaBC;
import br.com.mauda.seminario.cientificos.junit.contract.TestsStringField;
import br.com.mauda.seminario.cientificos.junit.converter.AreaCientificaConverter;
import br.com.mauda.seminario.cientificos.junit.executable.AreaCientificaExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaAreaCientifica;
import br.com.mauda.seminario.cientificos.model.AreaCientifica;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

class TesteAreaCientifica {

    protected AreaCientificaBC bc = AreaCientificaBC.getInstance();
    protected AreaCientificaConverter converter = new AreaCientificaConverter();
    protected AreaCientifica areaCientifica;

    @BeforeEach
    void beforeEach() {
        this.areaCientifica = this.converter.create(EnumUtils.getInstanceRandomly(MassaAreaCientifica.class));
    }

    @DisplayName("Criacao de uma Area Cientifica")
    @ParameterizedTest(name = "Criacao da Area Cientifica [{arguments}]")
    @EnumSource(MassaAreaCientifica.class)
    void criar(@ConvertWith(AreaCientificaConverter.class) AreaCientifica object) {
        // Verifica se os atributos estao preenchidos corretamente
        assertAll(new AreaCientificaExecutable(object));
        this.bc.insert(object);
    }

    @Test
    @DisplayName("Criacao de uma Area Cientifica nula")
    void validarNulo() {
        assertThrows(() -> this.bc.insert(null), "ER0003");
    }

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