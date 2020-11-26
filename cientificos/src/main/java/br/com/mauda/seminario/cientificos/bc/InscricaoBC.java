package br.com.mauda.seminario.cientificos.bc;

import java.util.Date;

import br.com.mauda.seminario.cientificos.dao.InscricaoDAO;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.model.Estudante;
import br.com.mauda.seminario.cientificos.model.Inscricao;
import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;

public class InscricaoBC extends PatternCrudBC<Inscricao, InscricaoDAO> {

    private static InscricaoBC instance = new InscricaoBC();
    private static String er0003 = "ER0003";

    private InscricaoBC() {
        this.dao = InscricaoDAO.getInstance();
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

        if (!SituacaoInscricaoEnum.DISPONIVEL.equals(inscricao.getSituacao())) {
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

        if (!SituacaoInscricaoEnum.COMPRADO.equals(inscricao.getSituacao())) {
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

        if (!SituacaoInscricaoEnum.COMPRADO.equals(inscricao.getSituacao())) {
            throw new SeminariosCientificosException("ER0046");
        }

        if (inscricao.getSeminario().getData().before(new Date())) {
            throw new SeminariosCientificosException("ER0047");
        }

        inscricao.realizarCheckIn();
    }

}