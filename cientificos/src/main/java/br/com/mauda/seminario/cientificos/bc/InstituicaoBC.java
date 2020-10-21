package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.model.Instituicao;

public class InstituicaoBC extends PatternCrudBC<Instituicao> {

    private static InstituicaoBC instance = new InstituicaoBC();

    private InstituicaoBC() {
        super();
    }

    public static InstituicaoBC getInstance() {
        return instance;
    }

}