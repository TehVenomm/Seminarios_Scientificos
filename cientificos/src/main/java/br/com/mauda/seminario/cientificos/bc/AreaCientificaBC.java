package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.dao.AreaCientificaDAO;
import br.com.mauda.seminario.cientificos.dto.AreaCientificaDTO;
import br.com.mauda.seminario.cientificos.model.AreaCientifica;

public class AreaCientificaBC extends PatternCrudBC<AreaCientifica, AreaCientificaDTO, AreaCientificaDAO> {

    private static AreaCientificaBC instance = new AreaCientificaBC();

    private AreaCientificaBC() {
        this.dao = AreaCientificaDAO.getInstance();
    }

    public static AreaCientificaBC getInstance() {
        return instance;
    }
}
