package br.com.mauda.seminario.cientificos.junit.executable;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor;
import br.com.mauda.seminario.cientificos.model.Professor;

public class ProfessorExecutable implements Executable {

    private Professor professor, professorBD;
    private MassaProfessor professorEnum;

    public ProfessorExecutable(Professor professor) {
        this.professor = professor;
    }

    public ProfessorExecutable(Professor professor, MassaProfessor enumm) {
        this(professor);
        this.professorEnum = enumm;
    }

    public ProfessorExecutable(Professor professor, Professor professorBD) {
        this(professor);
        this.professorBD = professorBD;
    }

    public void basicVerification(Professor professor) throws Throwable {
        Assertions.assertNotNull(professor, "Um Professor nao pode ser nulo");
        Assertions.assertTrue(StringUtils.isNotBlank(professor.getEmail()), "O email de um Professor nao pode ser nulo ou em branco");
        Assertions.assertTrue(StringUtils.isNotBlank(professor.getNome()), "O nome de um Professor nao pode ser nulo ou em branco");
        Assertions.assertTrue(StringUtils.isNotBlank(professor.getTelefone()), "O telefone de um Professor nao pode ser nulo ou em branco");
        Assertions.assertNotNull(professor.getSalario(), "O salario de um Professor nao pode ser nulo");
        Assertions.assertTrue(professor.getSalario() > 0, "O salario de um Professor deve ser maior que zero");

        // Verifica se a instituicao dentro do professor esta preenchida corretamente
        Assertions.assertAll(new InstituicaoExecutable(professor.getInstituicao()));
    }

    @Override
    public void execute() throws Throwable {
        this.basicVerification(this.professor);

        if (this.professorEnum != null) {
            Assertions.assertEquals(this.professorEnum.getEmail(), this.professor.getEmail(), "Emails dos professores nao sao iguais");
            Assertions.assertEquals(this.professorEnum.getNome(), this.professor.getNome(), "Nomes dos professores nao sao iguais");
            Assertions.assertEquals(this.professorEnum.getTelefone(), this.professor.getTelefone(), "Telefones dos professores nao sao iguais");
            Assertions.assertEquals(this.professorEnum.getSalario(), this.professor.getSalario(), "Salario dos professores nao sao iguais");

            Assertions.assertAll(new InstituicaoExecutable(this.professor.getInstituicao(), this.professorEnum.getInstituicao()));
            return;
        }

        if (this.professorBD != null) {
            this.basicVerification(this.professorBD);
            Assertions.assertEquals(this.professorBD.getEmail(), this.professor.getEmail(), "Emails dos professores nao sao iguais");
            Assertions.assertEquals(this.professorBD.getId(), this.professor.getId(), "Ids dos professores nao sao iguais");
            Assertions.assertEquals(this.professorBD.getNome(), this.professor.getNome(), "Nomes dos professores nao sao iguais");
            Assertions.assertEquals(this.professorBD.getTelefone(), this.professor.getTelefone(), "Telefones dos professores nao sao iguais");
            Assertions.assertEquals(this.professorBD.getSalario(), this.professor.getSalario(), "Salario dos professores nao sao iguais");

            Assertions.assertAll(new InstituicaoExecutable(this.professor.getInstituicao(), this.professor.getInstituicao()));
        }
    }
}
