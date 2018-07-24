package br.com.mauda.seminario.cientificos.junit.executable;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import br.com.mauda.seminario.cientificos.junit.massa.MassaEstudante;
import br.com.mauda.seminario.cientificos.model.Estudante;

public class EstudanteExecutable implements Executable {

    private Estudante estudante, estudanteBD;
    private MassaEstudante estudanteEnum;

    public EstudanteExecutable(Estudante estudante) {
        this.estudante = estudante;
    }

    public EstudanteExecutable(Estudante estudante, MassaEstudante enumm) {
        this(estudante);
        this.estudanteEnum = enumm;
    }

    public EstudanteExecutable(Estudante estudante, Estudante estudanteBD) {
        this(estudante);
        this.estudanteBD = estudanteBD;
    }

    public void basicVerification(Estudante estudante) throws Throwable {
        Assertions.assertNotNull(estudante, "Um Estudante nao pode ser nulo");
        Assertions.assertTrue(StringUtils.isNotBlank(estudante.getEmail()), "O email de um Estudante nao pode ser nulo ou em branco");
        Assertions.assertTrue(StringUtils.isNotBlank(estudante.getNome()), "O nome de um Estudante nao pode ser nulo ou em branco");
        Assertions.assertTrue(StringUtils.isNotBlank(estudante.getTelefone()), "O telefone de um Estudante nao pode ser nulo ou em branco");

        // Verifica se a instituicao dentro do estudante esta preenchida corretamente
        Assertions.assertAll(new InstituicaoExecutable(estudante.getInstituicao()));
    }

    @Override
    public void execute() throws Throwable {
        this.basicVerification(this.estudante);

        if (this.estudanteEnum != null) {
            Assertions.assertEquals(this.estudanteEnum.getEmail(), this.estudante.getEmail(), "Emails dos estudantes nao sao iguais");
            Assertions.assertEquals(this.estudanteEnum.getNome(), this.estudante.getNome(), "Nomes dos estudantes nao sao iguais");
            Assertions.assertEquals(this.estudanteEnum.getTelefone(), this.estudante.getTelefone(), "Telefones dos estudantes nao sao iguais");

            Assertions.assertAll(new InstituicaoExecutable(this.estudante.getInstituicao(), this.estudanteEnum.getInstituicao()));
            return;
        }

        if (this.estudanteBD != null) {
            this.basicVerification(this.estudanteBD);
            Assertions.assertEquals(this.estudanteBD.getEmail(), this.estudante.getEmail(), "Emails dos estudantes nao sao iguais");
            Assertions.assertEquals(this.estudanteBD.getId(), this.estudante.getId(), "Ids dos estudantes nao sao iguais");
            Assertions.assertEquals(this.estudanteBD.getNome(), this.estudante.getNome(), "Nomes dos estudantes nao sao iguais");
            Assertions.assertEquals(this.estudanteBD.getTelefone(), this.estudante.getTelefone(), "Telefones dos estudantes nao sao iguais");

            Assertions.assertAll(new InstituicaoExecutable(this.estudante.getInstituicao(), this.estudanteBD.getInstituicao()));
        }
    }
}
