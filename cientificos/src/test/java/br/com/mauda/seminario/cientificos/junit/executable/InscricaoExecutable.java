package br.com.mauda.seminario.cientificos.junit.executable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import br.com.mauda.seminario.cientificos.junit.massa.MassaInscricao;
import br.com.mauda.seminario.cientificos.junit.util.MensagensUtils;
import br.com.mauda.seminario.cientificos.model.Inscricao;
import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;

public class InscricaoExecutable implements Executable {

    private Inscricao inscricao;
    private MassaInscricao inscricaoEnum;

    public InscricaoExecutable(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public InscricaoExecutable(Inscricao inscricao, MassaInscricao enumm) {
        this(inscricao);
        this.inscricaoEnum = enumm;
    }

    public void basicVerification(Inscricao inscricao) throws Throwable {
        Assertions.assertNotNull(inscricao, MensagensUtils.getErrorMessage("Uma Inscricao nao pode ser nula"));

        Assertions.assertNotNull(inscricao.getSituacao(), MensagensUtils.getErrorMessage("A situacao de uma Inscricao nao pode ser nulo"));

        if (SituacaoInscricaoEnum.DISPONIVEL.equals(inscricao.getSituacao())) {
            Assertions.assertNull(inscricao.getEstudante(),
                MensagensUtils.getErrorMessage("Um estudante nao deve estar atribuido a uma inscricao disponivel"));
        } else {
            Assertions.assertNotNull(inscricao.getDireitoMaterial(),
                MensagensUtils.getErrorMessage("O direito ao material de uma Inscricao nao pode ser nulo"));

            // Verifica se o estudante dentro do inscricao esta preenchido corretamente
            Assertions.assertAll(new EstudanteExecutable(inscricao.getEstudante()));

            // Verifica a associacao bidirecional com estudante
            Assertions.assertTrue(inscricao.getEstudante().possuiInscricao(inscricao),
                MensagensUtils.getErrorMessage("A Lista de inscricoes do Estudante " + inscricao.getEstudante().getNome()
                    + " nao contem a inscricao em questao - associacao bidirecional nao foi realizada"));
        }

        Assertions.assertAll(new SeminarioExecutable(inscricao.getSeminario()));
    }

    @Override
    public void execute() throws Throwable {
        this.basicVerification(this.inscricao);

        if (this.inscricaoEnum != null) {
            Assertions.assertEquals(this.inscricaoEnum.isDireitoMaterial(), this.inscricao.getDireitoMaterial(),
                MensagensUtils.getErrorMessage("Direito ao Material das inscricoes nao sao iguais"));

            Assertions.assertEquals(this.inscricaoEnum.getSituacao(), this.inscricao.getSituacao(),
                MensagensUtils.getErrorMessage("Situacao das inscricoes nao sao iguais"));

            // SENAO for a situacao disponivel
            if (!SituacaoInscricaoEnum.DISPONIVEL.equals(this.inscricao.getSituacao())) {
                Assertions.assertAll(new EstudanteExecutable(this.inscricao.getEstudante(), this.inscricaoEnum.getEstudante()));
            }

            Assertions.assertAll(new SeminarioExecutable(this.inscricao.getSeminario(), this.inscricaoEnum.getSeminario()));
            return;
        }
    }
}
