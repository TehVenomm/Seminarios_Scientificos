package br.com.mauda.seminario.cientificos.junit.tests.queries;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertEquals;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.CursoBC;
import br.com.mauda.seminario.cientificos.dto.CursoDTO;
import br.com.mauda.seminario.cientificos.junit.executable.AreaCientificaExecutable;
import br.com.mauda.seminario.cientificos.junit.executable.CursoExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaCurso;
import br.com.mauda.seminario.cientificos.junit.provider.FindAllSource;
import br.com.mauda.seminario.cientificos.model.Curso;

public class TesteCursoQueries {

    protected CursoBC bc = CursoBC.getInstance();

    @Tag("queriesDaoTest")
    @DisplayName("Pesquisa de um Curso pelos metodos findAll e findById")
    @ParameterizedTest(name = "Pesquisa do Curso [{arguments}] pelos metodos findAll e findById")
    @FindAllSource(value = CursoBC.class)
    public void pesquisar(Curso objetoFindAll) {
        // Busca pelo FindById
        Curso objetoFindId = this.bc.findById(objetoFindAll.getId());

        // Realiza as verificacoes entre o objeto obtido pelo metodo findAll e o objeto obtido pelo findById
        assertAll(new CursoExecutable(objetoFindAll, objetoFindId));

        CursoDTO filter = new CursoDTO();
        // Seta a informacao do filtro
        filter.setId(objetoFindAll.getId());

        // Obtem as informacoes do banco de dados
        Collection<Curso> objetosFindByFilter = this.bc.findByFilter(filter);

        assertEquals(objetosFindByFilter.size(), 1, "O metodo findByFilter deveria ter retornado apenas 1 resultado, ao buscar pelo ID.");

        // Verifica se os objetos sao iguais
        assertAll(new CursoExecutable(objetoFindAll, objetosFindByFilter.iterator().next()));
    }

    /**
     * Realiza um teste com o filtro nulo, esperando que ocorram problemas
     */
    @Tag("queriesDaoTest")
    @Test
    @DisplayName("FindByFilter utilizando um filtro nulo")
    public void validarNulo() {
        assertThrows(() -> this.bc.findByFilter(null), "ER0001");
    }

    @Tag("queriesDaoTest")
    @Test
    @DisplayName("FindByFilter utilizando um filtro vazio")
    public void validarFiltroVazio() {
        assertThrows(() -> this.bc.findByFilter(new CursoDTO()), "ER0001");
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o nome do curso")
    @ParameterizedTest(name = "Pesquisa do Curso a partir do nome do Curso [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaCurso.class)
    public void validarFiltroNomeCurso(MassaCurso massa) {
        CursoDTO filter = new CursoDTO();
        filter.setNome(massa.getNome());

        // Obtem as informacoes do banco de dados
        Collection<Curso> results = this.bc.findByFilter(filter);
        assertEquals(1, results.size(), "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        assertAll(new CursoExecutable(results.iterator().next(), massa));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o nome da area cientifica")
    @ParameterizedTest(name = "Pesquisa do Curso a partir do nome da Area Cientifica [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaCurso.class)
    public void validarFiltroNomeAreaCientifica(MassaCurso massa) {
        CursoDTO filter = new CursoDTO();
        filter.setNomeAreaCientifica(massa.getAreaCientifica().getNome());

        // Obtem as informacoes do banco de dados
        Collection<Curso> results = this.bc.findByFilter(filter);
        assertEquals(3, results.size(), "O metodo findByFilter deveria ter retornado 3 resultados, favor deletar os itens duplicados");

        assertAll(new AreaCientificaExecutable(results.iterator().next().getAreaCientifica(), massa.getAreaCientifica()));
    }
}