package br.com.mauda.seminario.cientificos.dao;

import org.hibernate.Hibernate;

import br.com.mauda.seminario.cientificos.model.Instituicao;

public class InstituicaoDAO extends PatternCrudDAO<Instituicao> {

    private static final long serialVersionUID = 2873167052844675578L;
    private static InstituicaoDAO instance = new InstituicaoDAO();

    private InstituicaoDAO() {
        super(Instituicao.class);
    }

    public static InstituicaoDAO getInstance() {
        return instance;
    }

    @Override
    public void inicializaLazyObjects(Instituicao instituicao) {
        Hibernate.initialize(instituicao);
    }
}