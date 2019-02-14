package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertEquals;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertNotNull;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertTrue;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
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

    @Tag("businessTest")
    @DisplayName("CheckIn de uma inscricao para o Seminario")
    @ParameterizedTest(name = "CheckIn da inscricao [{arguments}] para o Seminario")
    @EnumSource(MassaInscricaoCheckIn.class)
    public void checkInscricao(@ConvertWith(AcaoInscricaoDTOConverter.class) AcaoInscricaoDTO object) {
        Inscricao inscricao = object.getInscricao();

        // Compra a inscricao pro seminario
        this.bc.comprar(inscricao, object.getEstudante(), object.getDireitoMaterial());

        this.validarCompra(inscricao);

        // Realiza o check in da inscricao pro seminario
        this.bc.realizarCheckIn(inscricao);

        // Verifica se os atributos estao preenchidos
        Assertions.assertAll(new InscricaoExecutable(inscricao));

        assertNotNull(inscricao.getDataCheckIn(), "A data de checkin deve estar preenchida em uma inscricao com situacao checkin");

        assertTrue(DateUtils.isSameDay(inscricao.getDataCheckIn(), new Date()), "Data do checkin nao eh igual a hoje");

        // Verifica se a situacao da inscricao ficou como comprado
        assertEquals(inscricao.getSituacao(), SituacaoInscricaoEnum.CHECKIN,
            "Situacao da inscricao nao eh checkIn - trocar a situacao no metodo realizarCheckIn()");
    }

    private void validarCompra(Inscricao inscricao) {
        // Verifica se os atributos estao preenchidos
        Assertions.assertAll(new InscricaoExecutable(inscricao));

        assertNotNull(inscricao.getDataCompra(), "A data de compra deve estar preenchida em uma inscricao com situacao comprado");

        assertTrue(DateUtils.isSameDay(inscricao.getDataCompra(), new Date()), "Data da compra nao eh igual a hoje");

        // Verifica se a situacao da inscricao ficou como comprado
        assertEquals(inscricao.getSituacao(), SituacaoInscricaoEnum.COMPRADO,
            "Situacao da inscricao nao eh comprado - trocar a situacao no metodo comprar()");
    }

    @Test
    @DisplayName("Compra com inscricao nula")
    public void validarCompraComInscricaoNula() {
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class, () -> this.bc.realizarCheckIn(null));
        Assertions.assertEquals("ER0040", exception.getMessage());
    }

    @Test
    @DisplayName("Compra com situacao da inscricao diferente de Disponivel")
    public void validarCompraComSituacaoInscricaoNaoDisponivel() {
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