package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.InscricaoBC;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.junit.converter.dao.AcaoInscricaoDTODAOConverter;
import br.com.mauda.seminario.cientificos.junit.converter.dto.AcaoInscricaoDTOConverter;
import br.com.mauda.seminario.cientificos.junit.dto.AcaoInscricaoDTO;
import br.com.mauda.seminario.cientificos.junit.executable.InscricaoExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaInscricaoCheckIn;
import br.com.mauda.seminario.cientificos.junit.massa.MassaInscricaoComprar;
import br.com.mauda.seminario.cientificos.model.Inscricao;
import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteAcaoCheckInSobreInscricao {

    protected InscricaoBC bc = InscricaoBC.getInstance();
    protected AcaoInscricaoDTOConverter converter = new AcaoInscricaoDTOConverter();
    protected AcaoInscricaoDTO acaoInscricaoDTO;

    @BeforeEach
    void beforeEach() {
        this.acaoInscricaoDTO = this.converter.create(EnumUtils.getInstanceRandomly(MassaInscricaoComprar.class));
    }

    @Tag("queriesDaoTest")
    @DisplayName("CheckIn de uma inscricao para o Seminario")
    @ParameterizedTest(name = "CheckIn da inscricao [{arguments}] para o Seminario")
    @EnumSource(MassaInscricaoCheckIn.class)
    public void checkInscricao(@ConvertWith(AcaoInscricaoDTODAOConverter.class) AcaoInscricaoDTO object) {
        Inscricao inscricao = object.getInscricao();

        // Realiza o check in da inscricao pro seminario
        this.bc.realizarCheckIn(inscricao);

        // Verifica se os atributos estao preenchidos
        Assertions.assertAll(new InscricaoExecutable(inscricao));

        // Verifica se a situacao da inscricao ficou como checkin
        assertEquals(inscricao.getSituacao(), SituacaoInscricaoEnum.CHECKIN,
            "Situacao da inscricao nao eh checkIn - trocar a situacao no metodo realizarCheckIn()");
    }

    @Tag("queriesDaoTest")
    @Test
    @DisplayName("CheckIn com inscricao nula")
    public void validarCheckInComInscricaoNula() {
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> this.bc.realizarCheckIn(null));
        Assertions.assertEquals("ER0040", exception.getMessage());
    }

    @Tag("queriesDaoTest")
    @Test
    @DisplayName("CheckIn com situacao da inscricao diferente de Disponivel")
    public void validarCheckInComSituacaoInscricaoNaoDisponivel() {
        this.acaoInscricaoDTO.getInscricao().setSituacao(SituacaoInscricaoEnum.DISPONIVEL);
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class,
            () -> this.bc.realizarCheckIn(this.acaoInscricaoDTO.getInscricao()));
        Assertions.assertEquals("ER0043", exception.getMessage());

        this.acaoInscricaoDTO.getInscricao().setSituacao(SituacaoInscricaoEnum.CHECKIN);
        exception = Assertions.assertThrows(SeminariosCientificosException.class,
            () -> this.bc.realizarCheckIn(this.acaoInscricaoDTO.getInscricao()));
        Assertions.assertEquals("ER0043", exception.getMessage());
    }
}