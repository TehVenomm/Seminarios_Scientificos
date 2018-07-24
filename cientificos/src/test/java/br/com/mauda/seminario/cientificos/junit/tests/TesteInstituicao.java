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

import br.com.mauda.seminario.cientificos.bc.InstituicaoBC;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.junit.contract.TestsStringField;
import br.com.mauda.seminario.cientificos.junit.converter.InstituicaoConverter;
import br.com.mauda.seminario.cientificos.junit.executable.InstituicaoExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaInstituicao;
import br.com.mauda.seminario.cientificos.model.Instituicao;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteInstituicao {

    protected InstituicaoBC bc = InstituicaoBC.getInstance();
    protected InstituicaoConverter converter = new InstituicaoConverter();
    protected Instituicao instituicao;

    @BeforeEach
    void beforeEach() {
        this.instituicao = this.converter.create(EnumUtils.getInstanceRandomly(MassaInstituicao.class));
    }

    @Tag("businessTest")
    @DisplayName("Criacao de uma Instituicao")
    @ParameterizedTest(name = "Criacao da Instituicao [{arguments}]")
    @EnumSource(MassaInstituicao.class)
    public void criar(@ConvertWith(InstituicaoConverter.class) Instituicao object) {
        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new InstituicaoExecutable(object));
        this.bc.insert(object);
    }

    @Tag("businessTest")
    @Test
    @DisplayName("Criacao de uma Instituicao nula")
    public void validarNulo() {
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> this.bc.insert(null));
        Assertions.assertEquals("ER0003", exception.getMessage());
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para a cidade da Instituicao")
    class CidadeInstituicao implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteInstituicao.this.instituicao.setCidade(value);
        }

        @Override
        public void executionMethod() {
            TesteInstituicao.this.bc.insert(TesteInstituicao.this.instituicao);
        }

        @Override
        public String getErrorMessage() {
            return "ER0050";
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para o estado da Instituicao")
    class EstadoInstituicao implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteInstituicao.this.instituicao.setEstado(value);
        }

        @Override
        public void executionMethod() {
            TesteInstituicao.this.bc.insert(TesteInstituicao.this.instituicao);
        }

        @Override
        public String getErrorMessage() {
            return "ER0051";
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para o nome da Instituicao")
    class NomeInstituicao implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteInstituicao.this.instituicao.setNome(value);
        }

        @Override
        public void executionMethod() {
            TesteInstituicao.this.bc.insert(TesteInstituicao.this.instituicao);
        }

        @Override
        public String getErrorMessage() {
            return "ER0052";
        }

        @Override
        public int getMaxSizeField() {
            return 100;
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para o pais da Instituicao")
    class PaisInstituicao implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteInstituicao.this.instituicao.setPais(value);
        }

        @Override
        public void executionMethod() {
            TesteInstituicao.this.bc.insert(TesteInstituicao.this.instituicao);
        }

        @Override
        public String getErrorMessage() {
            return "ER0053";
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para a sigla da Instituicao")
    class SiglaInstituicao implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteInstituicao.this.instituicao.setSigla(value);
        }

        @Override
        public void executionMethod() {
            TesteInstituicao.this.bc.insert(TesteInstituicao.this.instituicao);
        }

        @Override
        public String getErrorMessage() {
            return "ER0054";
        }

        @Override
        public int getMaxSizeField() {
            return 10;
        }
    }
}