package br.com.mauda.seminario.cientificos.dao;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.mauda.seminario.cientificos.dao.util.HibernateUtil;
import br.com.mauda.seminario.cientificos.dto.ProfessorDTO;
import br.com.mauda.seminario.cientificos.model.Professor;
import br.com.mauda.seminario.cientificos.model.Seminario;

public class ProfessorDAO extends PatternCrudDAO<Professor, ProfessorDTO> {

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
        if (professor == null) {
            return;
        }

        for (Seminario seminario : professor.getSeminarios()) {
            Hibernate.initialize(seminario.getProfessores());
        }
    }

    @Override
    public Collection<Professor> findByFilter(ProfessorDTO filter) {
        Session session = HibernateUtil.getSession();

        try {
            Criteria c = session.createCriteria(Professor.class);
            Criteria cInstituicao = c.createCriteria("instituicao");

            if (StringUtils.isNotBlank(filter.getCidade())) {
                cInstituicao.add(Restrictions.like("cidade", filter.getCidade(), MatchMode.ANYWHERE));
            }

            if (StringUtils.isNotBlank(filter.getEstado())) {
                cInstituicao.add(Restrictions.like("estado", filter.getEstado(), MatchMode.ANYWHERE));
            }

            if (StringUtils.isNotBlank(filter.getNomeInstituicao())) {
                cInstituicao.add(Restrictions.like("nome", filter.getNomeInstituicao(), MatchMode.ANYWHERE));
            }

            if (StringUtils.isNotBlank(filter.getPais())) {
                cInstituicao.add(Restrictions.like("pais", filter.getPais(), MatchMode.ANYWHERE));
            }

            if (StringUtils.isNotBlank(filter.getEmail())) {
                c.add(Restrictions.like("email", filter.getEmail(), MatchMode.ANYWHERE));
            }

            if (filter.getId() != null) {
                c.add(Restrictions.eq("id", filter.getId()));
            }

            if (StringUtils.isNotBlank(filter.getNome())) {
                c.add(Restrictions.like("nome", filter.getNome(), MatchMode.ANYWHERE));
            }

            if (filter.getSalario() != null) {
                c.add(Restrictions.eq("salario", filter.getSalario()));
            }

            if (filter.getTelefone() != null) {
                c.add(Restrictions.eq("telefone", filter.getTelefone()));
            }

            Collection<Professor> collection = c.list();

            for (Professor object : collection) {
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