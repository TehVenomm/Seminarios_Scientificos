package br.com.mauda.seminario.cientificos.junit.tests;

import java.util.Date;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.SeminarioBC;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.junit.contract.TestsDateFutureField;
import br.com.mauda.seminario.cientificos.junit.contract.TestsGenericField;
import br.com.mauda.seminario.cientificos.junit.contract.TestsIntegerPositiveField;
import br.com.mauda.seminario.cientificos.junit.contract.TestsStringField;
import br.com.mauda.seminario.cientificos.junit.converter.SeminarioConverter;
import br.com.mauda.seminario.cientificos.junit.executable.SeminarioExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaSeminario;
import br.com.mauda.seminario.cientificos.model.Seminario;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteSeminario {

    protected SeminarioBC bc = SeminarioBC.getInstance();
    protected SeminarioConverter converter = new SeminarioConverter();
    protected Seminario seminario;

    @BeforeEach
    void beforeEach() {
        this.seminario = this.converter.create(EnumUtils.getInstanceRandomly(MassaSeminario.class));
    }

    @Tag("businessTest")
    @DisplayName("Criacao de um Seminario")
    @ParameterizedTest(name = "Criacao do Seminario [{arguments}]")
    @EnumSource(MassaSeminario.class)
    public void criar(@ConvertWith(SeminarioConverter.class) Seminario object) {
        // Verifica se os atributos estao preenchidos corretamente
        assertAll(new SeminarioExecutable(object));
        this.bc.insert(object);
    }

    @Tag("businessTest")
    @Test
    @DisplayName("Criacao de um seminario nulo")
    public void validarNulo() {
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> this.bc.insert(null));
        Assertions.assertEquals("ER0003", exception.getMessage());
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para o titulo do Seminario")
    class TituloSeminario implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteSeminario.this.seminario.setTitulo(value);
        }

        @Override
        public void executionMethod() {
            TesteSeminario.this.bc.insert(TesteSeminario.this.seminario);
        }

        @Override
        public String getErrorMessage() {
            return "ER0072";
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para a descricao do Seminario")
    class DescricaoSeminario implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteSeminario.this.seminario.setDescricao(value);
        }

        @Override
        public void executionMethod() {
            TesteSeminario.this.bc.insert(TesteSeminario.this.seminario);
        }

        @Override
        public String getErrorMessage() {
            return "ER0071";
        }

        @Override
        public int getMaxSizeField() {
            return 200;
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para a data do Seminario")
    class DataSeminario implements TestsDateFutureField {

        @Override
        public void setValue(Date value) {
            TesteSeminario.this.seminario.setData(value);
        }

        @Override
        public void executionMethod() {
            TesteSeminario.this.bc.insert(TesteSeminario.this.seminario);
        }

        @Override
        public String getErrorMessage() {
            return "ER0070";
        }

    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para a mesa redonda do Seminario")
    class MesaRedondaSeminario implements TestsGenericField<Boolean> {

        @Override
        public void setValue(Boolean value) {
            TesteSeminario.this.seminario.setMesaRedonda(value);
        }

        @Override
        public void executionMethod() {
            TesteSeminario.this.bc.insert(TesteSeminario.this.seminario);
        }

        @Override
        public String getErrorMessage() {
            return "ER0073";
        }

    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para a quantidade de inscricoes do Seminario")
    class QuantidadeInscricoesSeminario implements TestsIntegerPositiveField {

        @Override
        public void setValue(Integer value) {
            TesteSeminario.this.seminario.setQtdInscricoes(value);
        }

        @Override
        public void executionMethod() {
            TesteSeminario.this.bc.insert(TesteSeminario.this.seminario);
        }

        @Override
        public String getErrorMessage() {
            return "ER0074";
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para as Areas Cientificas dentro do Seminario")
    class AreasCientificasDoSeminario {

        @Tag("businessTest")
        @Test
        @DisplayName("Criacao de um seminario sem areas cientificas")
        public void validarBranco() {
            TesteSeminario.this.seminario.getAreasCientificas().clear();
            SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class,
                () -> TesteSeminario.this.bc.insert(TesteSeminario.this.seminario));
            Assertions.assertEquals("ER0076", exception.getMessage(), "O campo professores contem um valor nulo");
        }

        @Tag("businessTest")
        @Test
        @DisplayName("Criacao de um seminario com area cientifica nula")
        public void validarNulo() {
            TesteSeminario.this.seminario.getAreasCientificas().clear();
            TesteSeminario.this.seminario.getAreasCientificas().add(null);
            SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class,
                () -> TesteSeminario.this.bc.insert(TesteSeminario.this.seminario));
            Assertions.assertEquals("ER0003", exception.getMessage(), "O campo areasCientificas contem um valor nulo");
        }

        @Tag("businessTest")
        @Nested
        @DisplayName("Testes para o nome da Area Cientifica")
        class NomeAreaCientifica implements TestsStringField {

            @Override
            public void setValue(String value) {
                TesteSeminario.this.seminario.getAreasCientificas().get(0).setNome(value);
            }

            @Override
            public void executionMethod() {
                TesteSeminario.this.bc.insert(TesteSeminario.this.seminario);
            }

            @Override
            public String getErrorMessage() {
                return "ER0010";
            }
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para os professores dentro do Seminario")
    class ProfessoresDoSeminario {

        @Tag("businessTest")
        @Test
        @DisplayName("Criacao de um seminario sem professores")
        public void validarBranco() {
            TesteSeminario.this.seminario.getProfessores().clear();
            SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class,
                () -> TesteSeminario.this.bc.insert(TesteSeminario.this.seminario));
            Assertions.assertEquals("ER0075", exception.getMessage(), "O campo professores contem um valor nulo");
        }

        @Tag("businessTest")
        @Test
        @DisplayName("Criacao de um seminario com professor nulo")
        public void validarNulo() {
            TesteSeminario.this.seminario.getProfessores().clear();
            TesteSeminario.this.seminario.getProfessores().add(null);
            SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class,
                () -> TesteSeminario.this.bc.insert(TesteSeminario.this.seminario));
            Assertions.assertEquals("ER0003", exception.getMessage(), "O campo professores contem um valor nulo");
        }

        @Tag("businessTest")
        @Nested
        @DisplayName("Testes para o nome do Professor")
        class NomeProfessore implements TestsStringField {

            @Override
            public void setValue(String value) {
                TesteSeminario.this.seminario.getProfessores().get(0).setNome(value);
            }

            @Override
            public void executionMethod() {
                TesteSeminario.this.bc.insert(TesteSeminario.this.seminario);
            }

            @Override
            public String getErrorMessage() {
                return "ER0061";
            }
        }
    }

    // TODO Criar testes para as Inscricoes

}