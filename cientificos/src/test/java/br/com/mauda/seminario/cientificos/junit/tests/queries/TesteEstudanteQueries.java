package br.com.mauda.seminario.cientificos.junit.tests.queries;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.EstudanteBC;
import br.com.mauda.seminario.cientificos.dto.EstudanteDTO;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.junit.executable.EstudanteExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaEstudante;
import br.com.mauda.seminario.cientificos.junit.provider.FindAllSource;
import br.com.mauda.seminario.cientificos.model.Estudante;

public class TesteEstudanteQueries {

    protected EstudanteBC bc = EstudanteBC.getInstance();

    @Tag("queriesDaoTest")
    @DisplayName("Pesquisa de um Estudante pelos metodos findAll e findById")
    @ParameterizedTest(name = "Pesquisa do Estudante [{arguments}] pelos metodos findAll e findById")
    @FindAllSource(value = EstudanteBC.class)
    public void pesquisar(Estudante objetoFindAll) {
        // Busca pelo FindById
        Estudante objetoFindId = this.bc.findById(objetoFindAll.getId());

        // Realiza as verificacoes entre o objeto obtido pelo metodo findAll e o objeto obtido pelo findById
        Assertions.assertAll(new EstudanteExecutable(objetoFindAll, objetoFindId));

        EstudanteDTO filter = new EstudanteDTO();
        // Seta a informacao do filtro
        filter.setId(objetoFindAll.getId());

        // Obtem as informacoes do banco de dados
        Collection<Estudante> objetosFindByFilter = this.bc.findByFilter(filter);

        assertEquals(objetosFindByFilter.size(), 1, "O metodo findByFilter deveria ter retornado apenas 1 resultado, ao buscar pelo ID.");

        // Verifica se os objetos sao iguais
        Assertions.assertAll(new EstudanteExecutable(objetoFindAll, objetosFindByFilter.iterator().next()));
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
            () -> this.bc.findByFilter(new EstudanteDTO()));
        Assertions.assertEquals("ER0001", exception.getMessage());
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o email do Estudante")
    @ParameterizedTest(name = "Pesquisa do Estudante a partir do email [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaEstudante.class)
    public void validarFiltroEmailEstudante(MassaEstudante massa) {
        EstudanteDTO filter = new EstudanteDTO();
        filter.setEmail(massa.getEmail());

        // Obtem as informacoes do banco de dados
        Collection<Estudante> results = this.bc.findByFilter(filter);
        assertEquals(1, results.size(), "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        Assertions.assertAll(new EstudanteExecutable(results.iterator().next(), massa));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o nome do Estudante")
    @ParameterizedTest(name = "Pesquisa do Estudante a partir do nome [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaEstudante.class)
    public void validarFiltroNomeEstudante(MassaEstudante massa) {
        EstudanteDTO filter = new EstudanteDTO();
        filter.setNome(massa.getNome());

        // Obtem as informacoes do banco de dados
        Collection<Estudante> results = this.bc.findByFilter(filter);
        assertEquals(1, results.size(), "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        Assertions.assertAll(new EstudanteExecutable(results.iterator().next(), massa));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o telefone do Estudante")
    @ParameterizedTest(name = "Pesquisa do Estudante a partir do telefone [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaEstudante.class)
    public void validarFiltroTelefoneEstudante(MassaEstudante massa) {
        EstudanteDTO filter = new EstudanteDTO();
        filter.setTelefone(massa.getTelefone());

        // Obtem as informacoes do banco de dados
        Collection<Estudante> results = this.bc.findByFilter(filter);
        assertEquals(1, results.size(), "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        Assertions.assertAll(new EstudanteExecutable(results.iterator().next(), massa));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com a cidade da instituicao")
    @ParameterizedTest(name = "Pesquisa do Estudante a partir da cidade da Instituicao [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaEstudante.class)
    public void validarFiltroCidadeInstituicao(MassaEstudante massa) {
        EstudanteDTO filter = new EstudanteDTO();
        filter.setCidade(massa.getInstituicao().getCidade());

        // Obtem as informacoes do banco de dados
        Collection<Estudante> results = this.bc.findByFilter(filter);
        assertEquals(1, results.size(), "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        Assertions.assertAll(new EstudanteExecutable(results.iterator().next(), massa));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o estado da instituicao")
    @ParameterizedTest(name = "Pesquisa do Estudante a partir do estado da Instituicao [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaEstudante.class)
    public void validarFiltroEstadoInstituicao(MassaEstudante massa) {
        EstudanteDTO filter = new EstudanteDTO();
        filter.setEstado(massa.getInstituicao().getEstado());

        // Obtem as informacoes do banco de dados
        Collection<Estudante> results = this.bc.findByFilter(filter);
        assertEquals(6, results.size(), "O metodo findByFilter deveria ter retornado 6 resultados, favor deletar os itens duplicados");
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o nome da instituicao")
    @ParameterizedTest(name = "Pesquisa do Estudante a partir do nome da Instituicao [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaEstudante.class)
    public void validarFiltroNomeInstituicao(MassaEstudante massa) {
        EstudanteDTO filter = new EstudanteDTO();
        filter.setNomeInstituicao(massa.getInstituicao().getNome());

        // Obtem as informacoes do banco de dados
        Collection<Estudante> results = this.bc.findByFilter(filter);
        assertEquals(1, results.size(), "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        Assertions.assertAll(new EstudanteExecutable(results.iterator().next(), massa));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o pais da instituicao")
    @ParameterizedTest(name = "Pesquisa do Estudante a partir do pais da Instituicao [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaEstudante.class)
    public void validarFiltroPaisInstituicao(MassaEstudante massa) {
        EstudanteDTO filter = new EstudanteDTO();
        filter.setPais(massa.getInstituicao().getPais());

        // Obtem as informacoes do banco de dados
        Collection<Estudante> results = this.bc.findByFilter(filter);
        assertEquals(6, results.size(), "O metodo findByFilter deveria ter retornado 6 resultados, favor deletar os itens duplicados");
    }
}