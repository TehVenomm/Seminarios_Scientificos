package br.com.mauda.seminario.cientificos.junit.tests.queries;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.AreaCientificaBC;
import br.com.mauda.seminario.cientificos.dto.AreaCientificaDTO;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.junit.executable.AreaCientificaExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaAreaCientifica;
import br.com.mauda.seminario.cientificos.junit.provider.FindAllSource;
import br.com.mauda.seminario.cientificos.model.AreaCientifica;

public class TesteAreaCientificaQueries {

    protected AreaCientificaBC bc = AreaCientificaBC.getInstance();

    @Tag("queriesDaoTest")
    @DisplayName("Pesquisa de uma Area Cientifica pelos metodos findAll e findById")
    @ParameterizedTest(name = "Pesquisa da Area Cientifica [{arguments}] pelos metodos findAll e findById")
    @FindAllSource(value = AreaCientificaBC.class)
    public void pesquisar(AreaCientifica objetoFindAll) {
        // Busca pelo FindById
        AreaCientifica objetoFindId = this.bc.findById(objetoFindAll.getId());

        // Realiza as verificacoes entre o objeto obtido pelo metodo findAll e o objeto obtido pelo findById
        Assertions.assertAll(new AreaCientificaExecutable(objetoFindAll, objetoFindId));

        AreaCientificaDTO filter = new AreaCientificaDTO();
        // Seta a informacao do filtro
        filter.setId(objetoFindAll.getId());

        // Obtem as informacoes do banco de dados
        Collection<AreaCientifica> objetosFindByFilter = this.bc.findByFilter(filter);

        Assertions.assertEquals(objetosFindByFilter.size(), 1);

        // Verifica se os objetos sao iguais
        Assertions.assertAll(new AreaCientificaExecutable(objetoFindAll, objetosFindByFilter.iterator().next()));
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
            () -> this.bc.findByFilter(new AreaCientificaDTO()));
        Assertions.assertEquals("ER0001", exception.getMessage());
    }

    @Tag("queriesDaoTest")
    @DisplayName("FindByFilter utilizando um filtro com o nome da area cientifica")
    @ParameterizedTest(name = "Pesquisa da Area Cientifica a partir do nome [{arguments}] pelo metodo FindByFilter")
    @EnumSource(MassaAreaCientifica.class)
    public void validarFiltroNomeArea(MassaAreaCientifica massa) {
        AreaCientificaDTO filter = new AreaCientificaDTO();
        filter.setNome(massa.getNome());

        // Obtem as informacoes do banco de dados
        Collection<AreaCientifica> results = this.bc.findByFilter(filter);
        Assertions.assertEquals(1, results.size(),
            "O metodo findByFilter deveria ter retornado apenas 1 resultado, favor deletar os itens duplicados");

        Assertions.assertAll(new AreaCientificaExecutable(results.iterator().next(), massa));
    }
}