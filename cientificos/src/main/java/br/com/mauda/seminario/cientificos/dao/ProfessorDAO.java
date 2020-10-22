package br.com.mauda.seminario.cientificos.dao;

import org.hibernate.Hibernate;

import br.com.mauda.seminario.cientificos.model.Professor;
import br.com.mauda.seminario.cientificos.model.Seminario;

public class ProfessorDAO extends PatternCrudDAO<Professor> {

    private static final long serialVersionUID = -5482740380674330939L;
    private static ProfessorDAO instance = new ProfessorDAO();

    private ProfessorDAO() {
        super(Professor.class);
    }

    public static ProfessorDAO getInstance() {
        return instance;
    }

    @Override
    public void inicializaLazyObjects(Professor professor) {
        for (Seminario seminario : professor.getSeminarios()) {
            Hibernate.initialize(seminario.getProfessores());
        }
    }
}