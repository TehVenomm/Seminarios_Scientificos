package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.model.AreaCientifica;

public class AreaCientificaBC extends PatternCrudBC<AreaCientifica> {

    private static AreaCientificaBC instance = new AreaCientificaBC();

    private AreaCientificaBC() {
        // Vazio
    }

    public static AreaCientificaBC getInstance() {
        return instance;
    }

}
