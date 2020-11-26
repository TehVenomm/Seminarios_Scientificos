package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.dao.ProfessorDAO;
import br.com.mauda.seminario.cientificos.dto.ProfessorDTO;
import br.com.mauda.seminario.cientificos.model.Professor;

public class ProfessorBC extends PatternCrudBC<Professor, ProfessorDTO, ProfessorDAO> {

    private static ProfessorBC instance = new ProfessorBC();

    private ProfessorBC() {
        this.dao = ProfessorDAO.getInstance();
    }

    public static ProfessorBC getInstance() {
        return instance;
    }
}