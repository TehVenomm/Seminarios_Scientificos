package br.com.mauda.seminario.cientificos.bc;

import java.util.Date;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.model.Estudante;
import br.com.mauda.seminario.cientificos.model.Inscricao;
import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;

public class InscricaoBC extends PatternCrudBC<Inscricao> {

    private static InscricaoBC instance = new InscricaoBC();
    private static String er0003 = "ER0003";

    private InscricaoBC() {
        // Vazio
    }

    public static InscricaoBC getInstance() {
        return instance;
    }

    public void comprar(Inscricao inscricao, Estudante estudante, Boolean direitoMaterial) {
        if (inscricao == null) {
            throw new SeminariosCientificosException(er0003);
        }

        if (direitoMaterial == null) {
            throw new SeminariosCientificosException("ER0041");
        }

        if (inscricao.getSituacao() != SituacaoInscricaoEnum.DISPONIVEL) {
            throw new SeminariosCientificosException("ER0042");
        }

        if (inscricao.getSeminario().getData().before(new Date())) {
            throw new SeminariosCientificosException("ER0043");
        }

        if (estudante == null) {
            throw new SeminariosCientificosException(er0003);
        }

        estudante.validateForDataModification();
        inscricao.comprar(estudante, direitoMaterial);
    }

    public void cancelarCompra(Inscricao inscricao) {
        if (inscricao == null) {
            throw new SeminariosCientificosException(er0003);
        }

        if (inscricao.getSituacao() != SituacaoInscricaoEnum.COMPRADO) {
            throw new SeminariosCientificosException("ER0044");
        }

        if (inscricao.getSeminario().getData().before(new Date())) {
            throw new SeminariosCientificosException("ER0045");
        }

        inscricao.cancelarCompra();
    }

    public void realizarCheckIn(Inscricao inscricao) {
        if (inscricao == null) {
            throw new SeminariosCientificosException(er0003);
        }

        if (inscricao.getSituacao() != SituacaoInscricaoEnum.COMPRADO) {
            throw new SeminariosCientificosException("ER0046");
        }

        if (inscricao.getSeminario().getData().before(new Date())) {
            throw new SeminariosCientificosException("ER0047");
        }

        inscricao.realizarCheckIn();
    }

}