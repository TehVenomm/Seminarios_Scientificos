package br.com.mauda.seminario.cientificos.junit.tests.queries;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertEquals;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.SeminarioBC;
import br.com.mauda.seminario.cientificos.dto.SeminarioDTO;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.junit.executable.AreaCientificaExecutable;
import br.com.mauda.seminario.cientificos.junit.executable.ProfessorExecutable;
import br.com.mauda.seminario.cientificos.junit.executable.SeminarioExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaSeminario;
import br.com.mauda.seminario.cientificos.junit.provider.FindAllSource;
import br.com.mauda.seminario.cientificos.model.Seminario;

public class TesteSeminarioQueries {

    protected SeminarioBC bc = SeminarioBC.getInstance();

    @Tag("queriesDaoTest")
    @DisplayName("Pesquisa de um Seminario pelos metodos findAll e findById")
    @ParameterizedTest(name = "Pesquisa do Seminario [{arguments}] pelos metodos findAll e findById")
    @FindAllSource(value = SeminarioBC.class)
    public void pesquisar(Seminario objetoFindAll) {
        // Busca pelo FindById
        Seminario objetoFindId = this.bc.findById(objetoFindAll.getId());

        // Realiza as verificacoes entre o objeto obtido pelo metodo findAll e o objeto obtido pelo findById
        Assertions.assertAll(new SeminarioExecutable(objetoFindAll, objetoFindId));

        SeminarioDTO filter = new SeminarioDTO();
        // Seta a informacao do filtro
        filter.setId(objetoFindAll.getId());

        // Obtem as informacoes do banco de dados
        Collection<Seminario> objetosFindByFilter = this.bc.findByFilter(filter);

        assertEquals(objetosFindByFilter.size(), 1, "O metodo findByFilter deveria ter retornado apenas 1 resultado, ao buscar pelo ID.");

        // Verifica se os objetos sao iguais
        Assertions.assertAll(new SeminarioExecutable(objetoFindAll, objetosFindByFilter.iterator().next()));
    }

    /**
     * Realiza um teste com o filtro nulo, esperando que ocorram problemas
     */
    @Tag("queriesDaoTest")
    @Test
    @DisplayName("FindByFilter utilizando um filtro nulo")
    public void validarNulo() {
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> this.bc.findByFilter(null));
        Assertions.assertEquals("ER0001", exception.getMessage());
    }

    @Tag("queriesDaoTest")
    @Test
    @DisplayName("FindByFilter utilizando um filtro vazio")
    public void validarFiltroVazio() {
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class,
            () -> this.bc.findByFilter(new SeminarioDTO()));
        Assertions.assertEquals("ER0001", exception.getMessage());
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com a data do Seminario")
    @ParameterizedTest(name = "Pesquisa do Seminario a partir da data [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaSeminario.class)
    public void validarFiltroDataSeminario(MassaSeminario massa) {
        SeminarioDTO filter = new SeminarioDTO();
        filter.setData(massa.getData());

        // Obtem as informacoes do banco de dados
        Collection<Seminario> results = this.bc.findByFilter(filter);
        assertEquals(1, results.size(), "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        Assertions.assertAll(new SeminarioExecutable(results.iterator().next(), massa));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o titulo do Seminario")
    @ParameterizedTest(name = "Pesquisa do Seminario a partir do titulo [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaSeminario.class)
    public void validarFiltroTituloSeminario(MassaSeminario massa) {
        SeminarioDTO filter = new SeminarioDTO();
        filter.setTitulo(massa.getTitulo());

        // Obtem as informacoes do banco de dados
        Collection<Seminario> results = this.bc.findByFilter(filter);
        assertEquals(1, results.size(), "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        Assertions.assertAll(new SeminarioExecutable(results.iterator().next(), massa));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com a descricao do Seminario")
    @ParameterizedTest(name = "Pesquisa do Seminario a partir da descricao [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaSeminario.class)
    public void validarFiltroDescricaoSeminario(MassaSeminario massa) {
        SeminarioDTO filter = new SeminarioDTO();
        filter.setDescricao(massa.getDescricao());

        // Obtem as informacoes do banco de dados
        Collection<Seminario> results = this.bc.findByFilter(filter);
        assertEquals(1, results.size(), "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        Assertions.assertAll(new SeminarioExecutable(results.iterator().next(), massa));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com a mesa redonda do Seminario")
    @ParameterizedTest(name = "Pesquisa do Seminario a partir da mesa redonda [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaSeminario.class)
    public void validarFiltroMesaRedondaSeminario(MassaSeminario massa) {
        SeminarioDTO filter = new SeminarioDTO();
        filter.setMesaRedonda(massa.getMesaRedonda());

        // Obtem as informacoes do banco de dados
        Collection<Seminario> results = this.bc.findByFilter(filter);
        assertTrue(results.size() == 2 || results.size() == 4,
            "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o nome da area cientifica")
    @ParameterizedTest(name = "Pesquisa do Seminario a partir do nome da area cientifica [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaSeminario.class)
    public void validarFiltroNomeAreaCientifica(MassaSeminario massa) {
        SeminarioDTO filter = new SeminarioDTO();
        filter.setNomeAreaCientifica(massa.getAreaCientifica().getNome());

        // Obtem as informacoes do banco de dados
        Collection<Seminario> results = this.bc.findByFilter(filter);
        assertEquals(2, results.size(), "O metodo findByFilter deveria ter retornado 2 resultados, favor deletar os itens duplicados");

        Assertions.assertAll(new AreaCientificaExecutable(results.iterator().next().getAreasCientificas().get(0), massa.getAreaCientifica()));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o nome do professor")
    @ParameterizedTest(name = "Pesquisa do Seminario a partir do nome do professor [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaSeminario.class)
    public void validarFiltroNomeProfessor(MassaSeminario massa) {
        SeminarioDTO filter = new SeminarioDTO();
        filter.setNomeProfessor(massa.getProfessor().getNome());

        // Obtem as informacoes do banco de dados
        Collection<Seminario> results = this.bc.findByFilter(filter);
        assertEquals(1, results.size(), "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        Assertions.assertAll(new ProfessorExecutable(results.iterator().next().getProfessores().get(0), massa.getProfessor()));
    }
}