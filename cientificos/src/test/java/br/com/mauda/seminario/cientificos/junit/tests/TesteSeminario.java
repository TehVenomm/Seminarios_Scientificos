package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertTrue;

import java.util.Date;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.SeminarioBC;
import br.com.mauda.seminario.cientificos.junit.contract.TestsDateFutureField;
import br.com.mauda.seminario.cientificos.junit.contract.TestsGenericField;
import br.com.mauda.seminario.cientificos.junit.contract.TestsIntegerPositiveField;
import br.com.mauda.seminario.cientificos.junit.contract.TestsStringField;
import br.com.mauda.seminario.cientificos.junit.converter.SeminarioConverter;
import br.com.mauda.seminario.cientificos.junit.converter.dao.SeminarioDAOConverter;
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

    @DisplayName("Criacao de um Seminario")
    @ParameterizedTest(name = "Criacao do Seminario [{arguments}]")
    @EnumSource(MassaSeminario.class)
    void criar(@ConvertWith(SeminarioDAOConverter.class) Seminario object) {
        // Verifica se os atributos estao preenchidos corretamente
        assertAll(new SeminarioExecutable(object));

        // Realiza o insert no banco de dados atraves da Business Controller
        this.bc.insert(object);

        // Verifica se o id eh maior que zero, indicando que foi inserido no banco
        assertTrue(object.getId() > 0, "Insert nao foi realizado corretamente pois o ID do objeto nao foi gerado");

        // Obtem uma nova instancia do BD a partir do ID gerado
        Seminario objectBD = this.bc.findById(object.getId());

        // Realiza as verificacoes entre o objeto em memoria e o obtido do banco
        assertAll(new SeminarioExecutable(object, objectBD));
    }

    @Test
    @DisplayName("Criacao de um seminario nulo")
    void validarNulo() {
        assertThrows(() -> this.bc.insert(null), "ER0003");
    }

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

    @Nested
    @DisplayName("Testes para as Areas Cientificas dentro do Seminario")
    class AreasCientificasDoSeminario {

        @Test
        @DisplayName("Criacao de um seminario com area cientifica nula")
        void validarNulo() throws IllegalAccessException {
            // Metodo que seta as areas cientificas como null usando reflections
            FieldUtils.writeDeclaredField(TesteSeminario.this.seminario, "areasCientificas", null, true);

            assertThrows(() -> TesteSeminario.this.bc.insert(TesteSeminario.this.seminario), "ER0076");
        }

        @Test
        @DisplayName("Criacao de um seminario sem areas cientificas")
        void validarBranco() {
            TesteSeminario.this.seminario.getAreasCientificas().clear();
            assertThrows(() -> TesteSeminario.this.bc.insert(TesteSeminario.this.seminario), "ER0076");
        }

        @Test
        @DisplayName("Criacao de um seminario com area cientifica nula")
        void validarAreaNula() {
            TesteSeminario.this.seminario.getAreasCientificas().clear();
            TesteSeminario.this.seminario.getAreasCientificas().add(null);
            assertThrows(() -> TesteSeminario.this.bc.insert(TesteSeminario.this.seminario), "ER0003");
        }

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

    @Nested
    @DisplayName("Testes para os professores dentro do Seminario")
    class ProfessoresDoSeminario {

        @Test
        @DisplayName("Criacao de um seminario com professor nulo")
        void validarNulo() throws IllegalAccessException {
            // Metodo que seta os professores como null usando reflections
            FieldUtils.writeDeclaredField(TesteSeminario.this.seminario, "professores", null, true);

            assertThrows(() -> TesteSeminario.this.bc.insert(TesteSeminario.this.seminario), "ER0075");
        }

        @Test
        @DisplayName("Criacao de um seminario sem professores")
        void validarBranco() {
            TesteSeminario.this.seminario.getProfessores().clear();
            assertThrows(() -> TesteSeminario.this.bc.insert(TesteSeminario.this.seminario), "ER0075");
        }

        @Test
        @DisplayName("Criacao de um seminario com professor nulo")
        void validarProfessorNulo() {
            TesteSeminario.this.seminario.getProfessores().clear();
            TesteSeminario.this.seminario.getProfessores().add(null);
            assertThrows(() -> TesteSeminario.this.bc.insert(TesteSeminario.this.seminario), "ER0003");
        }

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