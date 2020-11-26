package br.com.mauda.seminario.cientificos.dao;

import br.com.mauda.seminario.cientificos.model.Estudante;

public class EstudanteDAO extends PatternCrudDAO<Estudante> {

    private static final long serialVersionUID = -4391058069669296307L;
    private static EstudanteDAO instance = new EstudanteDAO();

    private EstudanteDAO() {
        super(Estudante.class);
    }

    public static EstudanteDAO getInstance() {
        return instance;
    }

    @Override
    public void inicializaLazyObjects(Estudante estudante) {
        // Nada a inicializar
    }
}