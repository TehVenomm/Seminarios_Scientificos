package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.model.Professor;

public class ProfessorBC extends PatternCrudBC<Professor> {

    private static ProfessorBC instance = new ProfessorBC();

    private ProfessorBC() {
        super();
    }

    public static ProfessorBC getInstance() {
        return instance;
    }

}