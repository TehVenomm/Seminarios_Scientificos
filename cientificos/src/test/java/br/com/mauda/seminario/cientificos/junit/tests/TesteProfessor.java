package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertNull;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertTrue;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.ProfessorBC;
import br.com.mauda.seminario.cientificos.junit.contract.TestsDoublePositiveField;
import br.com.mauda.seminario.cientificos.junit.contract.TestsEmailField;
import br.com.mauda.seminario.cientificos.junit.contract.TestsStringField;
import br.com.mauda.seminario.cientificos.junit.converter.ProfessorConverter;
import br.com.mauda.seminario.cientificos.junit.converter.dao.ProfessorDAOConverter;
import br.com.mauda.seminario.cientificos.junit.executable.ProfessorExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor;
import br.com.mauda.seminario.cientificos.model.Professor;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

class TesteProfessor {

    protected ProfessorBC bc = ProfessorBC.getInstance();
    protected ProfessorConverter converter = new ProfessorConverter();
    protected Professor professor;

    @BeforeEach
    void beforeEach() {
        this.professor = this.converter.create(EnumUtils.getInstanceRandomly(MassaProfessor.class));
    }

    @DisplayName("Criacao de um Professor")
    @ParameterizedTest(name = "Criacao do Professor [{arguments}]")
    @EnumSource(MassaProfessor.class)
    void criar(@ConvertWith(ProfessorDAOConverter.class) Professor object) {
        // Verifica se os atributos estao preenchidos corretamente
        assertAll(new ProfessorExecutable(object));

        // Realiza o insert no banco de dados atraves da Business Controller
        this.bc.insert(object);

        // Verifica se o id eh maior que zero, indicando que foi inserido no banco
        assertTrue(object.getId() > 0, "Insert nao foi realizado corretamente pois o ID do objeto nao foi gerado");

        // Obtem uma nova instancia do BD a partir do ID gerado
        Professor objectBD = this.bc.findById(object.getId());

        // Realiza as verificacoes entre o objeto em memoria e o obtido do banco
        assertAll(new ProfessorExecutable(object, objectBD));
    }

    @DisplayName("Atualizacao dos atributos de um Professor")
    @ParameterizedTest(name = "Atualizacao do Professor [{arguments}]")
    @EnumSource(MassaProfessor.class)
    void atualizar(@ConvertWith(ProfessorDAOConverter.class) Professor object) {
        // Cria o objeto
        this.criar(object);

        // Atualiza as informacoes de um objeto
        this.converter.update(object, EnumUtils.getInstanceRandomly(MassaProfessor.class));

        // Realiza o update no banco de dados atraves da Business Controller
        this.bc.update(object);

        // Obtem uma nova instancia do BD a partir do ID gerado
        Professor objectBD = this.bc.findById(object.getId());

        // Realiza as verificacoes entre o objeto em memoria e o obtido do banco
        assertAll(new ProfessorExecutable(object, objectBD));

        // Realiza o delete no banco de dados atraves da Business Controller para nao deixar o registro
        this.bc.delete(object);
    }

    @DisplayName("Delecao de um Professor")
    @ParameterizedTest(name = "Delecao do Professor [{arguments}]")
    @EnumSource(MassaProfessor.class)
    void deletar(@ConvertWith(ProfessorDAOConverter.class) Professor object) {
        // Realiza a insercao do objeto no banco de dados
        this.criar(object);

        // Remove o objeto do BD
        this.bc.delete(object);

        // Obtem o objeto do BD a partir do ID do objeto
        Professor objectBD = this.bc.findById(object.getId());

        // Verifica se o objeto deixou de existir no BD
        assertNull(objectBD, "O objeto deveria estar deletado do banco de dados");
    }

    @Test
    @DisplayName("Criacao de um professor nulo")
    void validarNulo() {
        assertThrows(() -> this.bc.insert(null), "ER0003");
    }

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

    @Nested
    @DisplayName("Testes para a Instituicao dentro do Professor")
    class InstituicaoDoProfessor {

        @Test
        @DisplayName("Criacao de um professor com Instituicao nula")
        void validarNulo() throws IllegalAccessException {
            // Metodo que seta a instituicao como null usando reflections
            FieldUtils.writeDeclaredField(TesteProfessor.this.professor, "instituicao", null, true);

            assertThrows(() -> TesteProfessor.this.bc.insert(TesteProfessor.this.professor), "ER0003");
        }

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