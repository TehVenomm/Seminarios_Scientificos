package br.com.mauda.seminario.cientificos.dao;

import br.com.mauda.seminario.cientificos.model.AreaCientifica;

public class AreaCientificaDAO extends PatternCrudDAO<AreaCientifica> {

    private static final long serialVersionUID = -7892112461904038500L;
    private static AreaCientificaDAO instance = new AreaCientificaDAO();

    private AreaCientificaDAO() {
        super(AreaCientifica.class);
    }

    public static AreaCientificaDAO getInstance() {
        return instance;
    }

    @Override
    public void inicializaLazyObjects(AreaCientifica areaCientifica) {
    }
}
