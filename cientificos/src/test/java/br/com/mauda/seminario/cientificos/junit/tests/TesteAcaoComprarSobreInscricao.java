package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertEquals;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertThrows;

import java.util.Date;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import br.com.mauda.seminario.cientificos.junit.massa.MassaInscricaoComprar;
import br.com.mauda.seminario.cientificos.model.Inscricao;
import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteAcaoComprarSobreInscricao {

    protected InscricaoBC bc = InscricaoBC.getInstance();
    protected AcaoInscricaoDTOConverter converter = new AcaoInscricaoDTOConverter();
    protected AcaoInscricaoDTO acaoInscricaoDTO;

    @BeforeEach
    void beforeEach() {
        this.acaoInscricaoDTO = this.converter.create(EnumUtils.getInstanceRandomly(MassaInscricaoComprar.class));
    }

    @Tag("MapeamentoDAOTest")
    @DisplayName("Compra de uma inscricao para o Seminario")
    @ParameterizedTest(name = "Compra da inscricao [{arguments}] para o Seminario")
    @EnumSource(MassaInscricaoComprar.class)
    public void comprarInscricao(@ConvertWith(AcaoInscricaoDTOConverter.class) AcaoInscricaoDTO dto) {
        Inscricao inscricao = dto.getInscricao();

        // Compra a inscricao pro seminario
        this.bc.comprar(inscricao, dto.getEstudante(), dto.getDireitoMaterial());

        // Verifica se a situacao da inscricao ficou como comprado
        assertEquals(inscricao.getSituacao(), SituacaoInscricaoEnum.COMPRADO,
            "Situacao da inscricao nao eh comprado - trocar a situacao no metodo comprar()");

        // Verifica se os atributos estao preenchidos
        assertAll(new InscricaoExecutable(inscricao));
    }

    @Tag("MapeamentoDAOTest")
    @Test
    @DisplayName("Compra com inscricao nula")
    public void validarCompraComInscricaoNula() {
        assertThrows(() -> this.bc.comprar(null, this.acaoInscricaoDTO.getEstudante(), this.acaoInscricaoDTO.getDireitoMaterial()), "ER0003");
    }

    @Tag("MapeamentoDAOTest")
    @Test
    @DisplayName("Compra com estudante nulo")
    public void validarCompraComEstudanteNulo() {
        assertThrows(() -> this.bc.comprar(this.acaoInscricaoDTO.getInscricao(), null, this.acaoInscricaoDTO.getDireitoMaterial()), "ER0003");
    }

    @Tag("MapeamentoDAOTest")
    @Test
    @DisplayName("Compra com direito material nulo")
    public void validarCompraComDireitoMaterialNulo() {
        assertThrows(() -> this.bc.comprar(this.acaoInscricaoDTO.getInscricao(), this.acaoInscricaoDTO.getEstudante(), null), "ER0041");
    }

    @Tag("MapeamentoDAOTest")
    @Test
    @DisplayName("Compra com situacao da inscricao diferente de Disponivel")
    public void validarCompraComSituacaoInscricaoNaoDisponivel() throws IllegalAccessException {
        Inscricao inscricao = this.acaoInscricaoDTO.getInscricao();

        // Metodo que seta a situacao da inscricao como COMPRADO usando reflections
        FieldUtils.writeDeclaredField(inscricao, "situacao", SituacaoInscricaoEnum.COMPRADO, true);
        assertThrows(() -> this.bc.comprar(inscricao, this.acaoInscricaoDTO.getEstudante(),
            this.acaoInscricaoDTO.getDireitoMaterial()), "ER0042");

        // Metodo que seta a situacao da inscricao como CHECKIN usando reflections
        FieldUtils.writeDeclaredField(inscricao, "situacao", SituacaoInscricaoEnum.CHECKIN, true);
        assertThrows(() -> this.bc.comprar(inscricao, this.acaoInscricaoDTO.getEstudante(),
            this.acaoInscricaoDTO.getDireitoMaterial()), "ER0042");
    }

    @Tag("MapeamentoDAOTest")
    @Test
    @DisplayName("Compra de uma inscricao após a data do Seminario")
    public void validarCompraAposDataSeminario() {
        Inscricao inscricao = this.acaoInscricaoDTO.getInscricao();

        // Diminui a data do seminario em 30 dias
        this.acaoInscricaoDTO.getSeminario().setData(DateUtils.addDays(new Date(), -30));

        assertThrows(() -> this.bc.comprar(inscricao, this.acaoInscricaoDTO.getEstudante(), this.acaoInscricaoDTO.getDireitoMaterial()), "ER0043");
    }
}