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

import br.com.mauda.seminario.cientificos.bc.EstudanteBC;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.junit.contract.TestsEmailField;
import br.com.mauda.seminario.cientificos.junit.contract.TestsStringField;
import br.com.mauda.seminario.cientificos.junit.converter.EstudanteConverter;
import br.com.mauda.seminario.cientificos.junit.converter.dao.EstudanteDAOConverter;
import br.com.mauda.seminario.cientificos.junit.executable.EstudanteExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaEstudante;
import br.com.mauda.seminario.cientificos.model.Estudante;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteEstudante {

    protected EstudanteBC bc = EstudanteBC.getInstance();
    protected EstudanteConverter converter = new EstudanteConverter();
    protected Estudante estudante;

    @BeforeEach
    void beforeEach() {
        this.estudante = this.converter.create(EnumUtils.getInstanceRandomly(MassaEstudante.class));
    }

    @Tag("MapeamentoDAOTest")
    @DisplayName("Criacao de um Estudante")
    @ParameterizedTest(name = "Criacao do Estudante [{arguments}]")
    @EnumSource(MassaEstudante.class)
    public void criar(@ConvertWith(EstudanteDAOConverter.class) Estudante object) {
        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new EstudanteExecutable(object));

        // Realiza o insert no banco de dados atraves da Business Controller
        this.bc.insert(object);

        // Verifica se o id eh maior que zero, indicando que foi inserido no banco
        Assertions.assertTrue(object.getId() > 0, "Insert nao foi realizado corretamente pois o ID do objeto nao foi gerado");

        // Obtem uma nova instancia do BD a partir do ID gerado
        Estudante objectBD = this.bc.findById(object.getId());

        // Realiza as verificacoes entre o objeto em memoria e o obtido do banco
        Assertions.assertAll(new EstudanteExecutable(object, objectBD));
    }

    @Tag("MapeamentoDAOTest")
    @Test
    @DisplayName("Criacao de um estudante nulo")
    public void validarNulo() {
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> this.bc.insert(null));
        Assertions.assertEquals("ER0003", exception.getMessage());
    }

    @Tag("MapeamentoDAOTest")
    @Nested
    @DisplayName("Testes para o email do Estudante")
    class EmailEstudante implements TestsEmailField {

        @Override
        public void setValue(String value) {
            TesteEstudante.this.estudante.setEmail(value);
        }

        @Override
        public void executionMethod() {
            TesteEstudante.this.bc.insert(TesteEstudante.this.estudante);
        }

        @Override
        public String getErrorMessage() {
            return "ER0030";
        }
    }

    @Tag("MapeamentoDAOTest")
    @Nested
    @DisplayName("Testes para o nome do Estudante")
    class NomeEstudante implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteEstudante.this.estudante.setNome(value);
        }

        @Override
        public void executionMethod() {
            TesteEstudante.this.bc.insert(TesteEstudante.this.estudante);
        }

        @Override
        public String getErrorMessage() {
            return "ER0031";
        }
    }

    @Tag("MapeamentoDAOTest")
    @Nested
    @DisplayName("Testes para o telefone do Estudante")
    class TelefoneEstudante implements TestsStringField {

        @Override
        public void setValue(String value) {
            TesteEstudante.this.estudante.setTelefone(value);
        }

        @Override
        public void executionMethod() {
            TesteEstudante.this.bc.insert(TesteEstudante.this.estudante);
        }

        @Override
        public String getErrorMessage() {
            return "ER0032";
        }

        @Override
        public int getMaxSizeField() {
            return 15;
        }
    }

    @Tag("MapeamentoDAOTest")
    @Nested
    @DisplayName("Testes para a Instituicao dentro do Estudante")
    class InstituicaoDoEstudante {

        @Tag("MapeamentoDAOTest")
        @Test
        @DisplayName("Criacao de um estudante com Instituicao nula")
        public void validarNulo() {
            TesteEstudante.this.estudante.setInstituicao(null);
            SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class,
                () -> TesteEstudante.this.bc.insert(TesteEstudante.this.estudante));
            Assertions.assertEquals("ER0003", exception.getMessage());
        }

        @Tag("MapeamentoDAOTest")
        @Nested
        @DisplayName("Testes para a cidade da Instituicao")
        class CidadeInstituicao implements TestsStringField {

            @Override
            public void setValue(String value) {
                TesteEstudante.this.estudante.getInstituicao().setCidade(value);
            }

            @Override
            public void executionMethod() {
                TesteEstudante.this.bc.insert(TesteEstudante.this.estudante);
            }

            @Override
            public String getErrorMessage() {
                return "ER0050";
            }
        }

        @Tag("MapeamentoDAOTest")
        @Nested
        @DisplayName("Testes para o estado da Instituicao")
        class EstadoInstituicao implements TestsStringField {

            @Override
            public void setValue(String value) {
                TesteEstudante.this.estudante.getInstituicao().setEstado(value);
            }

            @Override
            public void executionMethod() {
                TesteEstudante.this.bc.insert(TesteEstudante.this.estudante);
            }

            @Override
            public String getErrorMessage() {
                return "ER0051";
            }
        }

        @Tag("MapeamentoDAOTest")
        @Nested
        @DisplayName("Testes para o nome da Instituicao")
        class NomeInstituicao implements TestsStringField {

            @Override
            public void setValue(String value) {
                TesteEstudante.this.estudante.getInstituicao().setNome(value);
            }

            @Override
            public void executionMethod() {
                TesteEstudante.this.bc.insert(TesteEstudante.this.estudante);
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

        @Tag("MapeamentoDAOTest")
        @Nested
        @DisplayName("Testes para o pais da Instituicao")
        class PaisInstituicao implements TestsStringField {

            @Override
            public void setValue(String value) {
                TesteEstudante.this.estudante.getInstituicao().setPais(value);
            }

            @Override
            public void executionMethod() {
                TesteEstudante.this.bc.insert(TesteEstudante.this.estudante);
            }

            @Override
            public String getErrorMessage() {
                return "ER0053";
            }
        }

        @Tag("MapeamentoDAOTest")
        @Nested
        @DisplayName("Testes para a sigla da Instituicao")
        class SiglaInstituicao implements TestsStringField {

            @Override
            public void setValue(String value) {
                TesteEstudante.this.estudante.getInstituicao().setSigla(value);
            }

            @Override
            public void executionMethod() {
                TesteEstudante.this.bc.insert(TesteEstudante.this.estudante);
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