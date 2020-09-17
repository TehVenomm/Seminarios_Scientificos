package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.model.Curso;

public class CursoBC extends PatternCrudBC<Curso> {

    private static CursoBC instance = new CursoBC();

    private CursoBC() {
        // Vazio
    }

    public static CursoBC getInstance() {
        return instance;
    }

}