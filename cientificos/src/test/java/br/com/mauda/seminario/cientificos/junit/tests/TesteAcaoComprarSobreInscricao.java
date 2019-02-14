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
import br.com.mauda.seminario.cientificos.junit.executable.SeminarioExecutable;
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

    @Tag("businessTest")
    @DisplayName("Compra de uma inscricao para o Seminario")
    @ParameterizedTest(name = "Compra da inscricao [{arguments}] para o Seminario")
    @EnumSource(MassaInscricaoComprar.class)
    public void comprarInscricao(@ConvertWith(AcaoInscricaoDTOConverter.class) AcaoInscricaoDTO object) {
        // Verifica se os atributos estao preenchidos
        Assertions.assertAll(new SeminarioExecutable(object.getSeminario()));

        // Verifica se os atributos estao preenchidos
        Assertions.assertAll(new EstudanteExecutable(object.getEstudante()));

        Inscricao inscricao = object.getInscricao();

        // Significa que as inscricoes nao foram geradas automaticamente pelo construtor do seminario
        Assertions.assertNotNull(inscricao);

        // Verifica se os atributos estao preenchidos
        Assertions.assertAll(new InscricaoExecutable(inscricao));

        // Compra a inscricao pro seminario
        this.bc.comprar(inscricao, object.getEstudante(), object.getDireitoMaterial());

        // Verifica se os atributos estao preenchidos
        Assertions.assertAll(new InscricaoExecutable(inscricao));

        // Verifica se a situacao da inscricao ficou como comprado
        Assertions.assertEquals(inscricao.getSituacao(), SituacaoInscricaoEnum.COMPRADO);
    }

    @Tag("businessTest")
    @Test
    @DisplayName("Compra com inscricao nula")
    public void validarCompraComInscricaoNula() {
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class,
            () -> this.bc.comprar(null, this.acaoInscricaoDTO.getEstudante(), this.acaoInscricaoDTO.getDireitoMaterial()));
        Assertions.assertEquals("ER0040", exception.getMessage());
    }

    @Tag("businessTest")
    @Test
    @DisplayName("Compra com estudante nulo")
    public void validarCompraComEstudanteNulo() {
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class,
            () -> this.bc.comprar(this.acaoInscricaoDTO.getInscricao(), null, this.acaoInscricaoDTO.getDireitoMaterial()));
        Assertions.assertEquals("ER0003", exception.getMessage());
    }

    @Tag("businessTest")
    @Test
    @DisplayName("Compra com direito material nulo")
    public void validarCompraComDireitoMaterialNulo() {
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class,
            () -> this.bc.comprar(this.acaoInscricaoDTO.getInscricao(), this.acaoInscricaoDTO.getEstudante(), null));
        Assertions.assertEquals("ER0041", exception.getMessage());
    }

    @Tag("businessTest")
    @Test
    @DisplayName("Compra com situacao da inscricao diferente de Disponivel")
    public void validarCompraComSituacaoInscricaoNaoDisponivel() {
        this.acaoInscricaoDTO.getInscricao().setSituacao(SituacaoInscricaoEnum.COMPRADO);
        SeminariosCientificosException exception = Assertions.assertThrows(SeminariosCientificosException.class,
            () -> this.bc.comprar(this.acaoInscricaoDTO.getInscricao(), this.acaoInscricaoDTO.getEstudante(),
                this.acaoInscricaoDTO.getDireitoMaterial()));
        Assertions.assertEquals("ER0042", exception.getMessage());

        this.acaoInscricaoDTO.getInscricao().setSituacao(SituacaoInscricaoEnum.CHECKIN);
        exception = Assertions.assertThrows(SeminariosCientificosException.class,
            () -> this.bc.comprar(this.acaoInscricaoDTO.getInscricao(), this.acaoInscricaoDTO.getEstudante(),
                this.acaoInscricaoDTO.getDireitoMaterial()));
        Assertions.assertEquals("ER0042", exception.getMessage());
    }
}