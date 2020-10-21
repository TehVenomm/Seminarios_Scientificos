package br.com.mauda.seminario.cientificos.dao;

import org.hibernate.Hibernate;

import br.com.mauda.seminario.cientificos.model.Professor;
import br.com.mauda.seminario.cientificos.model.Seminario;

public class SeminarioDAO extends PatternCrudDAO<Seminario> {

    private static final long serialVersionUID = 2244973651924610578L;
    private static SeminarioDAO instance = new SeminarioDAO();

    private SeminarioDAO() {
        super(Seminario.class);
    }

    public static SeminarioDAO getInstance() {
        return instance;
    }

    @Override
    public void inicializaLazyObjects(Seminario seminario) {
        Hibernate.initialize(seminario.getAreasCientificas());
        Hibernate.initialize(seminario.getInscricoes());

        for (Professor professor : seminario.getProfessores()) {
            Hibernate.initialize(professor.getSeminarios());

        }
    }
}
