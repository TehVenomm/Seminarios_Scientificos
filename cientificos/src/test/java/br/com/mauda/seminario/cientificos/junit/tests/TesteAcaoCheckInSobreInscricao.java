package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertEquals;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.bc.InscricaoBC;
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

    @Tag("MapeamentoDAOTest")
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
        assertAll(new InscricaoExecutable(inscricao));

        // Verifica se a situacao da inscricao ficou como comprado
        assertEquals(inscricao.getSituacao(), SituacaoInscricaoEnum.CHECKIN,
            "Situacao da inscricao nao eh checkIn - trocar a situacao no metodo realizarCheckIn()");
    }

    private void validarCompra(Inscricao inscricao) {
        // Verifica se os atributos estao preenchidos
        assertAll(new InscricaoExecutable(inscricao));

        // Verifica se a situacao da inscricao ficou como comprado
        assertEquals(inscricao.getSituacao(), SituacaoInscricaoEnum.COMPRADO,
            "Situacao da inscricao nao eh comprado - trocar a situacao no metodo comprar()");
    }

    @Test
    @DisplayName("CheckIn com inscricao nula")
    public void validarCheckInComInscricaoNula() {
        assertThrows(() -> this.bc.realizarCheckIn(null), "ER0040");
    }

    @Test
    @DisplayName("CheckIn com situacao da inscricao diferente de Disponivel")
    public void validarCheckInComSituacaoInscricaoNaoDisponivel() {
        this.acaoInscricaoDTO.getInscricao().setSituacao(SituacaoInscricaoEnum.DISPONIVEL);
        assertThrows(() -> this.bc.realizarCheckIn(this.acaoInscricaoDTO.getInscricao()), "ER0043");

        this.acaoInscricaoDTO.getInscricao().setSituacao(SituacaoInscricaoEnum.CHECKIN);
        assertThrows(() -> this.bc.realizarCheckIn(this.acaoInscricaoDTO.getInscricao()), "ER0043");
    }
}