package br.com.mauda.seminario.cientificos.dao;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.mauda.seminario.cientificos.dao.util.HibernateUtil;
import br.com.mauda.seminario.cientificos.dto.CursoDTO;
import br.com.mauda.seminario.cientificos.model.Curso;

public class CursoDAO extends PatternCrudDAO<Curso, CursoDTO> {

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
        if (curso == null) {
            return;
        }

        Hibernate.initialize(curso.getAreaCientifica().getCursos());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Curso> findByFilter(CursoDTO filter) {
        Session session = HibernateUtil.getSession();

        try {
            Criteria c = session.createCriteria(Curso.class);

            if (filter.getId() != null) {
                c.add(Restrictions.eq("id", filter.getId()));
            }

            if (StringUtils.isNotBlank(filter.getNome())) {
                c.add(Restrictions.like("nome", filter.getNome(), MatchMode.ANYWHERE));
            }

            if (filter.getIdAreaCientifica() != null || StringUtils.isNotBlank(filter.getNomeAreaCientifica())) {
                Criteria cAreaCientifica = c.createCriteria("areaCientifica");

                if (filter.getIdAreaCientifica() != null) {
                    cAreaCientifica.add(Restrictions.eq("id", filter.getIdAreaCientifica()));
                }

                if (StringUtils.isNotBlank(filter.getNomeAreaCientifica())) {
                    cAreaCientifica.add(Restrictions.like("nome", filter.getNomeAreaCientifica(), MatchMode.ANYWHERE));
                }
            }

            Collection<Curso> collection = c.list();

            for (Curso object : collection) {
                this.inicializaLazyObjects(object);
            }

            return collection;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }
    }
}
