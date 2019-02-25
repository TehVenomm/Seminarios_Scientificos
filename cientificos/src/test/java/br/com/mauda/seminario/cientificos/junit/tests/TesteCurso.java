package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertNull;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertTrue;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.CursoBC;
import br.com.mauda.seminario.cientificos.junit.contract.TestsStringField;
import br.com.mauda.seminario.cientificos.junit.converter.CursoConverter;
import br.com.mauda.seminario.cientificos.junit.converter.dao.CursoDAOConverter;
import br.com.mauda.seminario.cientificos.junit.executable.CursoExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaCurso;
import br.com.mauda.seminario.cientificos.model.Curso;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteCurso {

    protected CursoBC bc = CursoBC.getInstance();
    protected CursoConverter converter = new CursoConverter();
    protected Curso curso;

    @BeforeEach
    void beforeEach() {
        this.curso = this.converter.create(EnumUtils.getInstanceRandomly(MassaCurso.class));
    }

    @Tag("queriesDaoTest")
    @DisplayName("Criacao de um Curso")
    @ParameterizedTest(name = "Criacao do Curso [{arguments}]")
    @EnumSource(MassaCurso.class)
    public void criar(@ConvertWith(CursoDAOConverter.class) Curso object) {
        // Verifica se os atributos estao preenchidos corretamente
        assertAll(new CursoExecutable(object));

        // Realiza o insert no banco de dados atraves da Business Controller
        this.bc.insert(object);

        // Verifica se o id eh maior que zero, indicando que foi inserido no banco
        assertTrue(object.getId() > 0, "Insert nao foi realizado corretamente pois o ID do objeto nao foi gerado");

        // Obtem uma nova instancia do BD a partir do ID gerado
        Curso objectBD = this.bc.findById(object.getId());

        // Realiza as verificacoes entre o objeto em memoria e o obtido do banco
        assertAll(new CursoExecutable(object, objectBD));
    }

    @Tag("queriesDaoTest")
    @DisplayName("Atualizacao dos atributos de um Curso")
    @ParameterizedTest(name = "Atualizacao do Curso [{arguments}]")
    @EnumSource(MassaCurso.class)
    public void atualizar(@ConvertWith(CursoDAOConverter.class) Curso object) {
        // Cria o objeto
        this.criar(object);

        // Atualiza as informacoes de um objeto
        this.converter.update(object, EnumUtils.getInstanceRandomly(MassaCurso.class));

        // Realiza o update no banco de dados atraves da Business Controller
        this.bc.update(object);

        // Obtem uma nova instancia do BD a partir do ID gerado
        Curso objectBD = this.bc.findById(object.getId());

        // Realiza as verificacoes entre o objeto em memoria e o obtido do banco
        assertAll(new CursoExecutable(object, objectBD));

        // Realiza o delete no banco de dados atraves da Business Controller para nao deixar o registro
        this.bc.delete(object);
    }

    @Tag("queriesDaoTest")
    @DisplayName("Delecao de um Curso")
    @ParameterizedTest(name = "Delecao do Curso [{arguments}]")
    @EnumSource(MassaCurso.class)
    public void deletar(@ConvertWith(CursoDAOConverter.class) Curso object) {
        // Realiza a insercao do objeto no banco de dados
        this.criar(object);

        // Remove o objeto do BD
        this.bc.delete(object);

        // Obtem o objeto do BD a partir do ID do objeto
        Curso objectBD = this.bc.findById(object.getId());

        // Verifica se o objeto deixou de existir no BD
        assertNull(objectBD, "O objeto deveria estar deletado do banco de dados");
    }

    @Tag("queriesDaoTest")
    @Test
    @DisplayName("Criacao de um curso nulo")
    public void validarNulo() {
        assertThrows(() -> this.bc.insert(null), "ER0003");
    }

    @Tag("queriesDaoTest")
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

    @Tag("queriesDaoTest")
    @Nested
    @DisplayName("Testes para a Area Cientifica dentro do Curso")
    class AreaCientificaDoCurso {

        @Test
        @DisplayName("Criacao de um curso com area cientifica nula")
        public void validarNulo() throws IllegalAccessException {
            // Metodo que seta a area cientifica como null usando reflections
            FieldUtils.writeDeclaredField(TesteCurso.this.curso, "areaCientifica", null, true);

            assertThrows(() -> TesteCurso.this.bc.insert(TesteCurso.this.curso), "ER0003");
        }

        @Tag("queriesDaoTest")
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