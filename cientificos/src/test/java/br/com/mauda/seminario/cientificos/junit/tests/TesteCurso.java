package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.CursoBC;
import br.com.mauda.seminario.cientificos.junit.contract.TestsStringField;
import br.com.mauda.seminario.cientificos.junit.converter.CursoConverter;
import br.com.mauda.seminario.cientificos.junit.executable.CursoExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaCurso;
import br.com.mauda.seminario.cientificos.model.Curso;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

class TesteCurso {

    protected CursoBC bc = CursoBC.getInstance();
    protected CursoConverter converter = new CursoConverter();
    protected Curso curso;

    @BeforeEach
    void beforeEach() {
        this.curso = this.converter.create(EnumUtils.getInstanceRandomly(MassaCurso.class));
    }

    @DisplayName("Criacao de um Curso")
    @ParameterizedTest(name = "Criacao do Curso [{arguments}]")
    @EnumSource(MassaCurso.class)
    void criar(@ConvertWith(CursoConverter.class) Curso object) {
        // Verifica se os atributos estao preenchidos corretamente
        assertAll(new CursoExecutable(object));
        this.bc.insert(object);
    }

    @Test
    @DisplayName("Criacao de um curso nulo")
    void validarNulo() {
        assertThrows(() -> this.bc.insert(null), "ER0003");
    }

    @Nested
    @DisplayName("Testes para o nome do Curso")
    class NomeCurso implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteCurso.this.curso.setNome(value);
        }

        @Override
        public void executionMethod() {
            TesteCurso.this.bc.insert(TesteCurso.this.curso);
        }

        @Override
        public String getErrorMessage() {
            return "ER0020";
        }
    }

    @Nested
    @DisplayName("Testes para a Area Cientifica dentro do Curso")
    class AreaCientificaDoCurso {

        @Test
        @DisplayName("Criacao de um curso com area cientifica nula")
        void validarNulo() throws IllegalAccessException {
            // Metodo que seta a area cientifica como null usando reflections
            FieldUtils.writeDeclaredField(TesteCurso.this.curso, "areaCientifica", null, true);

            assertThrows(() -> TesteCurso.this.bc.insert(TesteCurso.this.curso), "ER0003");
        }

        @Nested
        @DisplayName("Testes para o nome da Area Cientifica")
        class NomeAreaCientifica implements TestsStringField {

            @Override
            public void setValue(String value) {
                TesteCurso.this.curso.getAreaCientifica().setNome(value);
            }

            @Override
            public void executionMethod() {
                TesteCurso.this.bc.insert(TesteCurso.this.curso);
            }

            @Override
            public String getErrorMessage() {
                return "ER0010";
            }
        }
    }
}