package br.com.mauda.seminario.cientificos.junit.tests;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.junit.converter.dto.AcaoInscricaoDTOConverter;
import br.com.mauda.seminario.cientificos.junit.dto.AcaoInscricaoDTO;
import br.com.mauda.seminario.cientificos.junit.executable.InscricaoExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaInscricaoComprar;
import br.com.mauda.seminario.cientificos.junit.util.MensagensUtils;
import br.com.mauda.seminario.cientificos.model.Inscricao;
import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;

public class TesteAcaoComprarSobreInscricao {

    @Tag("modelTest")
    @DisplayName("Compra de uma inscricao para o Seminario")
    @ParameterizedTest(name = "Compra da inscricao [{arguments}] para o Seminario")
    @EnumSource(MassaInscricaoComprar.class)
    public void comprarInscricao(@ConvertWith(AcaoInscricaoDTOConverter.class) AcaoInscricaoDTO dto) {
        Inscricao inscricao = dto.getInscricao();

        // Compra a inscricao pro seminario
        inscricao.comprar(dto.getEstudante(), dto.getDireitoMaterial());

        this.validarCompra(inscricao);
    }

    private void validarCompra(Inscricao inscricao) {
        // Verifica se os atributos estao preenchidos
        Assertions.assertAll(new InscricaoExecutable(inscricao));

        Assertions.assertNotNull(inscricao.getDataCompra(),
            MensagensUtils.getErrorMessage("A data de compra deve estar preenchida em uma inscricao com situacao comprado"));

        Assertions.assertTrue(DateUtils.isSameDay(inscricao.getDataCompra(), new Date()),
            MensagensUtils.getErrorMessage("Data da compra nao eh igual a hoje"));

        // Verifica se a situacao da inscricao ficou como comprado
        Assertions.assertEquals(inscricao.getSituacao(), SituacaoInscricaoEnum.COMPRADO,
            MensagensUtils.getErrorMessage("Situacao da inscricao nao eh comprado - trocar a situacao no metodo comprar()"));

    }
}