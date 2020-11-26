package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.dao.SeminarioDAO;
import br.com.mauda.seminario.cientificos.dto.SeminarioDTO;
import br.com.mauda.seminario.cientificos.model.Seminario;

public class SeminarioBC extends PatternCrudBC<Seminario, SeminarioDTO, SeminarioDAO> {

    private static SeminarioBC instance = new SeminarioBC();

    private SeminarioBC() {
        this.dao = SeminarioDAO.getInstance();
    }

    public static SeminarioBC getInstance() {
        return instance;
    }

}