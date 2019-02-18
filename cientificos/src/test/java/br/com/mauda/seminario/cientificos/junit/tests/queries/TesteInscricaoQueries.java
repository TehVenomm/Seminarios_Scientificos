package br.com.mauda.seminario.cientificos.junit.tests.queries;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertEquals;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertTrue;
 
import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.InscricaoBC;
import br.com.mauda.seminario.cientificos.dto.InscricaoDTO;
import br.com.mauda.seminario.cientificos.junit.executable.InscricaoExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaInscricao;
import br.com.mauda.seminario.cientificos.model.Inscricao;
import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;

public class TesteInscricaoQueries {

    protected InscricaoBC bc = InscricaoBC.getInstance();

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
        assertThrows(() -> this.bc.findByFilter(new InscricaoDTO()), "ER0001");
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com a situacao como Disponivel")
    @Test
    public void testFindByFilterDisponivel() {
        InscricaoDTO filter = new InscricaoDTO();
        filter.getSituacoes().add(SituacaoInscricaoEnum.DISPONIVEL);

        // Obtem as informacoes do banco de dados
        Collection<Inscricao> results = this.bc.findByFilter(filter);
        assertEquals(44, results.size(),
            "O metodo findByFilter deveria ter retornado 44 resultados, verificar se existem problemas na compra e checkin");

        // Verifica para cada item da collection se esta de acordo com o esperado
        results.stream().forEach(inscricao -> assertAll(new InscricaoExecutable(inscricao)));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com a situacao como Comprado")
    @Test
    public void testFindByFilterComprado() {
        InscricaoDTO filter = new InscricaoDTO();
        filter.getSituacoes().add(SituacaoInscricaoEnum.COMPRADO);

        // Obtem as informacoes do banco de dados
        Collection<Inscricao> results = this.bc.findByFilter(filter);
        assertEquals(18, results.size(),
            "O metodo findByFilter deveria ter retornado 18 resultados, verificar se existem problemas na compra e checkin");

        // Verifica para cada item da collection se esta de acordo com o esperado
        results.stream().forEach(inscricao -> assertAll(new InscricaoExecutable(inscricao)));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com a situacao como CheckIn")
    @Test
    public void testFindByFilterCheckIn() {
        InscricaoDTO filter = new InscricaoDTO();
        filter.getSituacoes().add(SituacaoInscricaoEnum.CHECKIN);

        // Obtem as informacoes do banco de dados
        Collection<Inscricao> results = this.bc.findByFilter(filter);
        assertEquals(18, results.size(),
            "O metodo findByFilter deveria ter retornado 18 resultados, verificar se existem problemas na compra e checkin");

        // Verifica para cada item da collection se esta de acordo com o esperado
        results.stream().forEach(inscricao -> assertAll(new InscricaoExecutable(inscricao)));
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o direito ao material do seminario")
    @ParameterizedTest(name = "Pesquisa da Inscricao a partir do direito ao material [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaInscricao.class)
    public void validarFiltroDireitoMaterial(MassaInscricao massa) {
        InscricaoDTO filter = new InscricaoDTO();
        filter.setDireitoMaterial(massa.isDireitoMaterial());

        // Obtem as informacoes do banco de dados
        Collection<Inscricao> results = this.bc.findByFilter(filter);
        assertEquals(18, results.size(), "O metodo findByFilter deveria ter retornado 18 resultados, favor deletar os itens duplicados");
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro a data do seminario")
    @ParameterizedTest(name = "Pesquisa da Inscricao a partir do data do seminario [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaInscricao.class)
    public void validarFiltroDataSeminario(MassaInscricao massa) {
        InscricaoDTO filter = new InscricaoDTO();
        filter.setDataSeminario(massa.getSeminario().getData());

        // Obtem as informacoes do banco de dados
        Collection<Inscricao> results = this.bc.findByFilter(filter);
        assertTrue(results.size() == 10 || results.size() == 15,
            "O metodo findByFilter deveria ter retornado 10 ou 15 resultados, favor deletar os itens duplicados");

    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro o titulo do seminario")
    @ParameterizedTest(name = "Pesquisa da Inscricao a partir do titulo do seminario [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaInscricao.class)
    public void validarFiltroTituloSeminario(MassaInscricao massa) {
        InscricaoDTO filter = new InscricaoDTO();
        filter.setTituloSeminario(massa.getSeminario().getTitulo());

        // Obtem as informacoes do banco de dados
        Collection<Inscricao> results = this.bc.findByFilter(filter);
        assertTrue(results.size() == 10 || results.size() == 15,
            "O metodo findByFilter deveria ter retornado 10 ou 15 resultados, favor deletar os itens duplicados");

    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro o nome do estudante")
    @ParameterizedTest(name = "Pesquisa da Inscricao a partir do nome do estudante [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaInscricao.class)
    public void validarFiltroNomeEstudante(MassaInscricao massa) {
        InscricaoDTO filter = new InscricaoDTO();
        filter.setNomeEstudante(massa.getEstudante().getNome());

        // Obtem as informacoes do banco de dados
        Collection<Inscricao> results = this.bc.findByFilter(filter);
        assertEquals(6, results.size(), "O metodo findByFilter deveria ter retornado 6 resultados, favor deletar os itens duplicados");

    }
}