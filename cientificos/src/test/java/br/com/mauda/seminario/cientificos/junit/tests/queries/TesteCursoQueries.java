package br.com.mauda.seminario.cientificos.junit.tests.queries;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.CursoBC;
import br.com.mauda.seminario.cientificos.dto.CursoDTO;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
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
        Assertions.assertAll(new CursoExecutable(objetoFindAll, objetoFindId));

        CursoDTO filter = new CursoDTO();
        // Seta a informacao do filtro
        filter.setId(objetoFindAll.getId());

        // Obtem as informacoes do banco de dados
        Collection<Curso> objetosFindByFilter = this.bc.findByFilter(filter);

        Assertions.assertEquals(objetosFindByFilter.size(), 1);

        // Verifica se os objetos sao iguais
        Assertions.assertAll(new CursoExecutable(objetoFindAll, objetosFindByFilter.iterator().next()));
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
            () -> this.bc.findByFilter(new CursoDTO()));
        Assertions.assertEquals("ER0001", exception.getMessage());
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
        Assertions.assertEquals(1, results.size(),
            "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        Assertions.assertAll(new CursoExecutable(results.iterator().next(), massa));
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
        Assertions.assertEquals(3, results.size(),
            "O metodo findByFilter deveria ter retornado 3 resultados, favor deletar os itens duplicados");

        Assertions.assertAll(new AreaCientificaExecutable(results.iterator().next().getAreaCientifica(), massa.getAreaCientifica()));
    }
}