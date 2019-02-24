package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.ProfessorBC;
import br.com.mauda.seminario.cientificos.junit.contract.TestsDoublePositiveField;
import br.com.mauda.seminario.cientificos.junit.contract.TestsEmailField;
import br.com.mauda.seminario.cientificos.junit.contract.TestsStringField;
import br.com.mauda.seminario.cientificos.junit.converter.ProfessorConverter;
import br.com.mauda.seminario.cientificos.junit.executable.ProfessorExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor;
import br.com.mauda.seminario.cientificos.model.Professor;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteProfessor {

    protected ProfessorBC bc = ProfessorBC.getInstance();
    protected ProfessorConverter converter = new ProfessorConverter();
    protected Professor professor;

    @BeforeEach
    void beforeEach() {
        this.professor = this.converter.create(EnumUtils.getInstanceRandomly(MassaProfessor.class));
    }

    @Tag("businessTest")
    @DisplayName("Criacao de um Professor")
    @ParameterizedTest(name = "Criacao do Professor [{arguments}]")
    @EnumSource(MassaProfessor.class)
    public void criar(@ConvertWith(ProfessorConverter.class) Professor object) {
        // Verifica se os atributos estao preenchidos corretamente
        assertAll(new ProfessorExecutable(object));
        this.bc.insert(object);
    }

    @Tag("businessTest")
    @Test
    @DisplayName("Criacao de um professor nulo")
    public void validarNulo() {
        assertThrows(() -> this.bc.insert(null), "ER0003");
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para o email do Professor")
    class EmailProfessor implements TestsEmailField {

        @Override
        public void setValue(String value) {
            TesteProfessor.this.professor.setEmail(value);
        }

        @Override
        public void executionMethod() {
            TesteProfessor.this.bc.insert(TesteProfessor.this.professor);
        }

        @Override
        public String getErrorMessage() {
            return "ER0060";
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para o nome do Professor")
    class NomeProfessor implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteProfessor.this.professor.setNome(value);
        }

        @Override
        public void executionMethod() {
            TesteProfessor.this.bc.insert(TesteProfessor.this.professor);
        }

        @Override
        public String getErrorMessage() {
            return "ER0061";
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para o telefone do Professor")
    class TelefoneProfessor implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteProfessor.this.professor.setTelefone(value);
        }

        @Override
        public void executionMethod() {
            TesteProfessor.this.bc.insert(TesteProfessor.this.professor);
        }

        @Override
        public String getErrorMessage() {
            return "ER0062";
        }

        @Override
        public int getMaxSizeField() {
            return 15;
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para o salario do Professor")
    class SalarioProfessor implements TestsDoublePositiveField {

        @Override
        public void setValue(Double value) {
            TesteProfessor.this.professor.setSalario(value);
        }

        @Override
        public void executionMethod() {
            TesteProfessor.this.bc.insert(TesteProfessor.this.professor);
        }

        @Override
        public String getErrorMessage() {
            return "ER0063";
        }
    }

    @Tag("businessTest")
    @Nested
    @DisplayName("Testes para a Instituicao dentro do Professor")
    class InstituicaoDoProfessor {

        @Tag("businessTest")
        @Test
        @DisplayName("Criacao de um professor com Instituicao nula")
        public void validarNulo() throws IllegalAccessException {
            // Metodo que seta a instituicao como null usando reflections
            FieldUtils.writeDeclaredField(TesteProfessor.this.professor, "instituicao", null, true);

            assertThrows(() -> TesteProfessor.this.bc.insert(TesteProfessor.this.professor), "ER0003");
        }

        @Tag("businessTest")
        @Nested
        @DisplayName("Testes para a cidade da Instituicao")
        class CidadeInstituicao implements TestsStringField {

            @Override
            public void setValue(String value) {
                TesteProfessor.this.professor.getInstituicao().setCidade(value);
            }

            @Override
            public void executionMethod() {
                TesteProfessor.this.bc.insert(TesteProfessor.this.professor);
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
                TesteProfessor.this.professor.getInstituicao().setEstado(value);
            }

            @Override
            public void executionMethod() {
                TesteProfessor.this.bc.insert(TesteProfessor.this.professor);
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
                TesteProfessor.this.professor.getInstituicao().setNome(value);
            }

            @Override
            public void executionMethod() {
                TesteProfessor.this.bc.insert(TesteProfessor.this.professor);
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
                TesteProfessor.this.professor.getInstituicao().setPais(value);
            }

            @Override
            public void executionMethod() {
                TesteProfessor.this.bc.insert(TesteProfessor.this.professor);
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
                TesteProfessor.this.professor.getInstituicao().setSigla(value);
            }

            @Override
            public void executionMethod() {
                TesteProfessor.this.bc.insert(TesteProfessor.this.professor);
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
}