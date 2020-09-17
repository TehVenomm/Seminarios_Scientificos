package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.model.Estudante;

public class EstudanteBC extends PatternCrudBC<Estudante> {

    private static EstudanteBC instance = new EstudanteBC();

    private EstudanteBC() {
        super();
    }

    public static EstudanteBC getInstance() {
        return instance;
    }

}
