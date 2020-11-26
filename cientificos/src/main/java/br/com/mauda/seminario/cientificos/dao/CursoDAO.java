package br.com.mauda.seminario.cientificos.dao;

import org.hibernate.Hibernate;

import br.com.mauda.seminario.cientificos.model.Curso;

public class CursoDAO extends PatternCrudDAO<Curso> {

    private static final long serialVersionUID = 2902065142786583474L;
    private static CursoDAO instance = new CursoDAO();

    private CursoDAO() {
        super(Curso.class);
    }

    public static CursoDAO getInstance() {
        return instance;
    }

    @Override
    public void inicializaLazyObjects(Curso curso) {
        Hibernate.initialize(curso.getAreaCientifica().getCursos());
    }
}
