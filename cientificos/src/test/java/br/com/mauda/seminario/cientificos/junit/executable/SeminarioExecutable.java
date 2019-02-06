package br.com.mauda.seminario.cientificos.junit.executable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import br.com.mauda.seminario.cientificos.junit.massa.MassaSeminario;
import br.com.mauda.seminario.cientificos.junit.util.MensagensUtils;
import br.com.mauda.seminario.cientificos.model.AreaCientifica;
import br.com.mauda.seminario.cientificos.model.Inscricao;
import br.com.mauda.seminario.cientificos.model.Professor;
import br.com.mauda.seminario.cientificos.model.Seminario;

public class SeminarioExecutable implements Executable {

    private Seminario seminario;
    private MassaSeminario seminarioEnum;

    public SeminarioExecutable(Seminario seminario) {
        this.seminario = seminario;
    }

    public SeminarioExecutable(Seminario seminario, MassaSeminario enumm) {
        this(seminario);
        this.seminarioEnum = enumm;
    }

    public void basicVerification(Seminario seminario) throws Throwable {
        Assertions.assertNotNull(seminario, MensagensUtils.getErrorMessage("Um Seminario nao pode ser nulo"));

        Assertions.assertTrue(seminario.getAreasCientificas() != null,
            MensagensUtils.getErrorMessage("É necessário inicializar a lista de areas cientificas"));

        Assertions.assertTrue(seminario.getInscricoes() != null, MensagensUtils.getErrorMessage("É necessário inicializar a lista de inscricoes"));

        Assertions.assertTrue(seminario.getProfessores() != null, MensagensUtils.getErrorMessage("É necessário inicializar a lista de professores"));

        Assertions.assertNotNull(seminario.getData(), MensagensUtils.getErrorMessage("A data de um Seminario nao pode ser nula"));

        Assertions.assertNotNull(seminario.getQtdInscricoes(),
            MensagensUtils.getErrorMessage("A quantidade de inscricoes de um Seminario nao pode ser nulo"));

        Assertions.assertTrue(seminario.getQtdInscricoes() > 0,
            MensagensUtils.getErrorMessage("A quantidade de inscricoes de um Seminario deve ser maior que zero"));

        Assertions.assertTrue(StringUtils.isNotBlank(seminario.getDescricao()),
            MensagensUtils.getErrorMessage("A descricao de um Seminario nao pode ser nulo ou em branco"));

        Assertions.assertTrue(StringUtils.isNotBlank(seminario.getTitulo()),
            MensagensUtils.getErrorMessage("O titulo de um Seminario nao pode ser nulo ou em branco"));

        for (Professor professor : seminario.getProfessores()) {
            Assertions.assertAll(new ProfessorExecutable(professor));

            // Verifica a associacao bidirecional com professor
            Assertions.assertTrue(professor.possuiSeminario(seminario), MensagensUtils.getErrorMessage("A lista de Seminarios do Professor "
                + professor.getNome() + " nao contem o seminario em questao - associacao bidirecional nao foi realizada"));
        }

        for (AreaCientifica area : seminario.getAreasCientificas()) {
            Assertions.assertAll(new AreaCientificaExecutable(area));
        }

        // Verifica se a lista de inscricoes contem a quantidade gerada
        Assertions.assertEquals(seminario.getQtdInscricoes(), new Integer(seminario.getInscricoes().size()),
            MensagensUtils.getErrorMessage("A lista de inscricoes nao contem todas as inscricoes de acordo com a quantidade estipulada"));

        for (Inscricao inscricao : seminario.getInscricoes()) {
            // Verifica a associacao bidirecional com inscricao
            Assertions.assertTrue(inscricao.getSeminario().equals(seminario),
                MensagensUtils.getErrorMessage("A inscricao nao contem o seminario em questao - associacao bidirecional nao foi realizada"));
        }
    }

    @Override
    public void execute() throws Throwable {
        this.basicVerification(this.seminario);

        if (this.seminarioEnum != null) {
            Assertions.assertTrue(DateUtils.isSameDay(this.seminarioEnum.getData(), this.seminario.getData()),
                MensagensUtils.getErrorMessage("Datas dos seminarios nao sao iguais"));

            Assertions.assertEquals(this.seminarioEnum.getDescricao(), this.seminario.getDescricao(),
                MensagensUtils.getErrorMessage("Descricao dos seminarios nao sao iguais"));

            Assertions.assertEquals(this.seminarioEnum.getQtdInscricoes(), this.seminario.getQtdInscricoes(),
                MensagensUtils.getErrorMessage("Quantidade de inscricoes nao sao iguais"));

            Assertions.assertEquals(this.seminarioEnum.getTitulo(), this.seminario.getTitulo(),
                MensagensUtils.getErrorMessage("Titulo dos seminarios nao sao iguais"));

            boolean naoAchou = true;
            for (Professor professor : this.seminario.getProfessores()) {
                if (professor.getNome().equals(this.seminarioEnum.getProfessor().getNome())) {
                    Assertions.assertAll(new ProfessorExecutable(professor, this.seminarioEnum.getProfessor()));
                    naoAchou = false;
                    break;
                }
            }
            if (naoAchou) {
                Assertions.fail("Nao encontrou professor correspondente");
            }

            naoAchou = true;
            for (AreaCientifica area : this.seminario.getAreasCientificas()) {
                if (area.getNome().equals(this.seminarioEnum.getAreaCientifica().getNome())) {
                    Assertions.assertAll(new AreaCientificaExecutable(area, this.seminarioEnum.getAreaCientifica()));
                    naoAchou = false;
                    break;
                }
            }
            if (naoAchou) {
                Assertions.fail("Nao encontrou area cientifica correspondente");
            }
            return;
        }
    }
}