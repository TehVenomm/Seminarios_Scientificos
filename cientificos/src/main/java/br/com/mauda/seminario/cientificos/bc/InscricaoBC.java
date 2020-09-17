package br.com.mauda.seminario.cientificos.bc;

import java.util.Date;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.model.Estudante;
import br.com.mauda.seminario.cientificos.model.Inscricao;
import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;

public class InscricaoBC extends PatternCrudBC<Inscricao> {

    private static InscricaoBC instance = new InscricaoBC();

    private static String Er0003 = "ER0003";
    private static String Er0041 = "ER0041";
    private static String Er0042 = "ER0042";
    private static String Er0043 = "ER0043";
    private static String Er0044 = "ER0044";
    private static String Er0045 = "ER0045";
    private static String Er0046 = "ER0046";
    private static String Er0047 = "ER0047";

    private InscricaoBC() {
        // Vazio
    }

    public static InscricaoBC getInstance() {
        return instance;
    }

    public void comprar(Inscricao inscricao, Estudante estudante, Boolean direitoMaterial) {
        if (inscricao == null) {
            throw new SeminariosCientificosException(Er0003);
        }

        if (direitoMaterial == null) {
            throw new SeminariosCientificosException(Er0041);
        }

        if (inscricao.getSituacao() != SituacaoInscricaoEnum.DISPONIVEL) {
            throw new SeminariosCientificosException(Er0042);
        }

        if (inscricao.getSeminario().getData().before(new Date())) {
            throw new SeminariosCientificosException(Er0043);
        }

        if (estudante == null) {
            throw new SeminariosCientificosException(Er0003);
        }

        estudante.validateForDataModification();
        inscricao.comprar(estudante, direitoMaterial);
    }

    public void cancelarCompra(Inscricao inscricao) {
        if (inscricao == null) {
            throw new SeminariosCientificosException(Er0003);
        }

        if (inscricao.getSituacao() != SituacaoInscricaoEnum.COMPRADO) {
            throw new SeminariosCientificosException(Er0044);
        }

        if (inscricao.getSeminario().getData().before(new Date())) {
            throw new SeminariosCientificosException(Er0045);
        }

        inscricao.cancelarCompra();
    }

    public void realizarCheckIn(Inscricao inscricao) {
        if (inscricao == null) {
            throw new SeminariosCientificosException(Er0003);
        }

        if (inscricao.getSituacao() != SituacaoInscricaoEnum.COMPRADO) {
            throw new SeminariosCientificosException(Er0046);
        }

        if (inscricao.getSeminario().getData().before(new Date())) {
            throw new SeminariosCientificosException(Er0047);
        }

        inscricao.realizarCheckIn();
    }

}