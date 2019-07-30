package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertNull;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
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

public class TesteAreaCientifica {

    protected AreaCientificaBC bc = AreaCientificaBC.getInstance();
    protected AreaCientificaConverter converter = new AreaCientificaConverter();
    protected AreaCientifica areaCientifica;

    @BeforeEach
    void beforeEach() {
        this.areaCientifica = this.converter.create(EnumUtils.getInstanceRandomly(MassaAreaCientifica.class));
    }

    @Tag("queriesDaoTest")
    @DisplayName("Criacao de uma Area Cientifica")
    @ParameterizedTest(name = "Criacao da Area Cientifica [{arguments}]")
    @EnumSource(MassaAreaCientifica.class)
    public void criar(@ConvertWith(AreaCientificaConverter.class) AreaCientifica object) {
        // Verifica se os atributos estao preenchidos corretamente
        assertAll(new AreaCientificaExecutable(object));

        // Realiza o insert no banco de dados atraves da Business Controller
        this.bc.insert(object);

        // Verifica se o id eh maior que zero, indicando que foi inserido no banco
        assertTrue(object.getId() > 0, "Insert nao foi realizado corretamente pois o ID do objeto nao foi gerado");

        // Obtem uma nova instancia do BD a partir do ID gerado
        AreaCientifica objectBD = this.bc.findById(object.getId());

        // Realiza as verificacoes entre o objeto em memoria e o obtido do banco
        assertAll(new AreaCientificaExecutable(object, objectBD));
    }

    @Tag("queriesDaoTest")
    @DisplayName("Atualizacao dos atributos de uma Area Cientifica")
    @ParameterizedTest(name = "Atualizacao da Area Cientifica [{arguments}]")
    @EnumSource(MassaAreaCientifica.class)
    public void atualizar(@ConvertWith(AreaCientificaConverter.class) AreaCientifica object) {
        // Cria o objeto
        this.criar(object);

        // Atualiza as informacoes de um objeto
        this.converter.update(object, EnumUtils.getInstanceRandomly(MassaAreaCientifica.class));

        // Realiza o update no banco de dados atraves da Business Controller
        this.bc.update(object);

        // Obtem uma nova instancia do BD a partir do ID gerado
        AreaCientifica objectBD = this.bc.findById(object.getId());

        // Realiza as verificacoes entre o objeto em memoria e o obtido do banco
        assertAll(new AreaCientificaExecutable(object, objectBD));

        // Realiza o delete no banco de dados atraves da Business Controller para nao deixar o registro
        this.bc.delete(object);
    }

    @Tag("queriesDaoTest")
    @DisplayName("Delecao de uma Area Cientifica")
    @ParameterizedTest(name = "Delecao da Area Cientifica [{arguments}]")
    @EnumSource(MassaAreaCientifica.class)
    public void deletar(@ConvertWith(AreaCientificaConverter.class) AreaCientifica object) {
        // Realiza a insercao do objeto no banco de dados
        this.criar(object);

        // Remove o objeto do BD
        this.bc.delete(object);

        // Obtem o objeto do BD a partir do ID do objeto
        AreaCientifica objectBD = this.bc.findById(object.getId());

        // Verifica se o objeto deixou de existir no BD
        assertNull(objectBD, "O objeto deveria estar deletado do banco de dados");
    }

    @Tag("queriesDaoTest")
    @Test
    @DisplayName("Criacao de uma Area Cientifica nula")
    public void validarNulo() {
        assertThrows(() -> this.bc.insert(null), "ER0003");
    }

    @Tag("queriesDaoTest")
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