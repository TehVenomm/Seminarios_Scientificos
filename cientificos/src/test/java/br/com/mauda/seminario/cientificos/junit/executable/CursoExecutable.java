package br.com.mauda.seminario.cientificos.junit.executable;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import br.com.mauda.seminario.cientificos.junit.massa.MassaCurso;
import br.com.mauda.seminario.cientificos.junit.util.MensagensUtils;
import br.com.mauda.seminario.cientificos.model.Curso;

public class CursoExecutable implements Executable {

    private Curso curso;
    private MassaCurso cursoEnum;

    public CursoExecutable(Curso curso) {
        this.curso = curso;
    }

    public CursoExecutable(Curso curso, MassaCurso enumm) {
        this(curso);
        this.cursoEnum = enumm;
    }

    public void basicVerification(Curso curso) throws Throwable {
        Assertions.assertNotNull(curso, MensagensUtils.getErrorMessage("Um Curso nao pode ser nulo"));
        Assertions.assertTrue(StringUtils.isNotBlank(curso.getNome()),
            MensagensUtils.getErrorMessage("O nome do Curso nao pode ser nulo ou em branco"));

        // Verifica se a area cientifica dentro do curso esta preenchida corretamente
        Assertions.assertAll(new AreaCientificaExecutable(curso.getAreaCientifica()));

        // Verifica a associacao bidirecional com area cientifica
        Assertions.assertTrue(curso.getAreaCientifica().getCursos().contains(curso), MensagensUtils
            .getErrorMessage("A lista de Cursos nao contem o curso em questao - associacao bidirecional no construtor nao foi realizada"));
    }

    @Override
    public void execute() throws Throwable {
        this.basicVerification(this.curso);

        if (this.cursoEnum != null) {
            Assertions.assertEquals(this.cursoEnum.getNome(), this.curso.getNome(),
                MensagensUtils.getErrorMessage("Nomes dos cursos nao sao iguais"));

            Assertions.assertAll(new AreaCientificaExecutable(this.curso.getAreaCientifica(), this.cursoEnum.getAreaCientifica()));
            return;
        }
    }
}
