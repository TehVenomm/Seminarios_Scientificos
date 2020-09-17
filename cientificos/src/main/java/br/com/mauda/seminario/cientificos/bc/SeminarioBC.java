package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.model.Seminario;

public class SeminarioBC extends PatternCrudBC<Seminario> {

    private static SeminarioBC instance = new SeminarioBC();

    private SeminarioBC() {
        // Vazio
    }

    public static SeminarioBC getInstance() {
        return instance;
    }

}