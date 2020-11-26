package br.com.mauda.seminario.cientificos.dao;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.mauda.seminario.cientificos.dao.util.HibernateUtil;
import br.com.mauda.seminario.cientificos.dto.SeminarioDTO;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.model.Professor;
import br.com.mauda.seminario.cientificos.model.Seminario;

public class SeminarioDAO extends PatternCrudDAO<Seminario, SeminarioDTO> {

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
        if (seminario == null) {
            return;
        }

        Hibernate.initialize(seminario.getAreasCientificas());
        Hibernate.initialize(seminario.getInscricoes());

        for (Professor professor : seminario.getProfessores()) {
            Hibernate.initialize(professor.getSeminarios());
        }
    }

    @Override
    public Collection<Seminario> findByFilter(SeminarioDTO filter) {
        Session session = HibernateUtil.getSession();

        try {
            Criteria c = session.createCriteria(Seminario.class);
            Criteria cAreaCientifica = c.createCriteria("areasCientificas");
            Criteria cProfessor = c.createCriteria("professores");

            if (filter.getData() != null) {
                c.add(Restrictions.eq("data", filter.getData()));
            }

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                c.add(Restrictions.like("descricao", filter.getDescricao(), MatchMode.ANYWHERE));
            }

            if (filter.getId() != null) {
                c.add(Restrictions.eq("id", filter.getId()));
            }

            if (filter.getMesaRedonda() != null) {
                c.add(Restrictions.eq("mesaRedonda", filter.getMesaRedonda()));
            }

            if (StringUtils.isNotBlank(filter.getNomeAreaCientifica())) {
                cAreaCientifica.add(Restrictions.like("nome", filter.getNomeAreaCientifica(), MatchMode.ANYWHERE));
            }

            if (StringUtils.isNotBlank(filter.getNomeProfessor())) {
                cProfessor.add(Restrictions.like("nome", filter.getNomeProfessor(), MatchMode.ANYWHERE));
            }

            if (StringUtils.isNotBlank(filter.getTitulo())) {
                c.add(Restrictions.like("titulo", filter.getTitulo(), MatchMode.ANYWHERE));
            }

            Collection<Seminario> collection = c.list();

            for (Seminario object : collection) {
                this.inicializaLazyObjects(object);
            }

            return collection;
        } catch (Exception e) {
            throw new SeminariosCientificosException(e);
        } finally {
            session.close();
        }
    }
}
