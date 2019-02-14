package br.com.mauda.seminario.cientificos.junit.executable;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertEquals;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertNotNull;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertNull;
import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import br.com.mauda.seminario.cientificos.junit.massa.MassaInscricao;
import br.com.mauda.seminario.cientificos.model.Inscricao;
import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;

public class InscricaoExecutable implements Executable {

    private Inscricao inscricao, inscricaoBD;
    private MassaInscricao inscricaoEnum;

    public InscricaoExecutable(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public InscricaoExecutable(Inscricao inscricao, MassaInscricao enumm) {
        this(inscricao);
        this.inscricaoEnum = enumm;
    }

    public InscricaoExecutable(Inscricao inscricao, Inscricao inscricaoBD) {
        this(inscricao);
        this.inscricaoBD = inscricaoBD;
    }

    public void basicVerification(Inscricao inscricao) throws Throwable {
        assertNotNull(inscricao, "Uma Inscricao nao pode ser nula");
        assertNotNull(inscricao.getSituacao(), "A situacao de uma Inscricao nao pode ser nula");

        if (SituacaoInscricaoEnum.DISPONIVEL.equals(inscricao.getSituacao())) {
            assertNull(inscricao.getEstudante(), "Um estudante nao deve estar atribuido a uma inscricao disponivel");

        } else {
            assertNotNull(inscricao.getDireitoMaterial(), "O direito ao material de uma Inscricao nao pode ser nulo");

            // Verifica se o estudante dentro do inscricao esta preenchido corretamente
            Assertions.assertAll(new EstudanteExecutable(inscricao.getEstudante()));

            // Verifica a associacao bidirecional com estudante
            assertTrue(inscricao.getEstudante().getInscricoes().contains(inscricao), "A Lista de inscricoes do Estudante "
                + inscricao.getEstudante().getNome() + " nao contem a inscricao em questao - associacao bidirecional nao foi realizada");
        }

        Assertions.assertAll(new SeminarioExecutable(inscricao.getSeminario()));
    }

    @Override
    public void execute() throws Throwable {
        this.basicVerification(this.inscricao);

        if (this.inscricaoEnum != null) {
            assertEquals(this.inscricaoEnum.isDireitoMaterial(), this.inscricao.getDireitoMaterial(),
                "Direito ao Material das inscricoes nao sao iguais");
            assertEquals(this.inscricaoEnum.getSituacao(), this.inscricao.getSituacao(), "Situacao das inscricoes nao sao iguais");

            // SENAO for a situacao disponivel
            if (!SituacaoInscricaoEnum.DISPONIVEL.equals(this.inscricao.getSituacao())) {
                Assertions.assertAll(new EstudanteExecutable(this.inscricao.getEstudante(), this.inscricaoEnum.getEstudante()));
            }
            Assertions.assertAll(new SeminarioExecutable(this.inscricao.getSeminario(), this.inscricaoEnum.getSeminario()));
            return;
        }

        if (this.inscricaoBD != null) {
            this.basicVerification(this.inscricaoBD);

            assertEquals(this.inscricaoBD.getDireitoMaterial(), this.inscricao.getDireitoMaterial(),
                "Direito ao Material das inscricoes nao sao iguais");
            assertEquals(this.inscricaoBD.getSituacao(), this.inscricao.getSituacao(), "Situacao das inscricoes nao sao iguais");

            // SENAO for a situacao disponivel
            if (!SituacaoInscricaoEnum.DISPONIVEL.equals(this.inscricao.getSituacao())) {
                Assertions.assertAll(new EstudanteExecutable(this.inscricao.getEstudante(), this.inscricaoBD.getEstudante()));
            }
            Assertions.assertAll(new SeminarioExecutable(this.inscricao.getSeminario(), this.inscricaoBD.getSeminario()));
        }
    }
}
