package br.com.mauda.seminario.cientificos.dao;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.mauda.seminario.cientificos.dao.util.HibernateUtil;
import br.com.mauda.seminario.cientificos.dto.InscricaoDTO;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.model.Inscricao;

public class InscricaoDAO extends PatternCrudDAO<Inscricao, InscricaoDTO> {

    private static final long serialVersionUID = -7975644423623757962L;
    private static InscricaoDAO instance = new InscricaoDAO();

    private InscricaoDAO() {
        super(Inscricao.class);
    }

    public static InscricaoDAO getInstance() {
        return instance;
    }

    @Override
    public void inicializaLazyObjects(Inscricao object) {
        if (object != null) {
            Hibernate.initialize(object.getSeminario().getProfessores());
            object.getSeminario().getProfessores().forEach(p -> Hibernate.initialize(p.getSeminarios()));
            Hibernate.initialize(object.getSeminario().getAreasCientificas());
            Hibernate.initialize(object.getSeminario().getInscricoes());

            if (object.getEstudante() != null) {
                Hibernate.initialize(object.getEstudante().getInscricoes());
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Inscricao> findByFilter(InscricaoDTO filter) {
        Session session = HibernateUtil.getSession();
        try {
            Criteria c = session.createCriteria(Inscricao.class);

            if (filter.getDireitoMaterial() != null) {
                c.add(Restrictions.eq("direitoMaterial", filter.getDireitoMaterial()));
            }

            if (filter.getId() != null) {
                c.add(Restrictions.eq("id", filter.getId()));
            }

            if (!filter.getSituacoes().isEmpty()) {
                c.add(Restrictions.in("situacao", filter.getSituacoes()));
            }

            if (StringUtils.isNotBlank(filter.getNomeEstudante())) {
                Criteria ce = session.createCriteria("estudante");
                ce.add(Restrictions.like("nome", filter.getNomeEstudante(), MatchMode.ANYWHERE));
            }

            if (filter.getDataSeminario() != null || StringUtils.isNotBlank(filter.getTituloSeminario())) {
                Criteria cs = session.createCriteria("seminario");

                if (filter.getDataSeminario() != null) {
                    cs.add(Restrictions.eq("data", filter.getDataSeminario()));
                }

                if (StringUtils.isNotBlank(filter.getTituloSeminario())) {
                    cs.add(Restrictions.like("titulo", filter.getTituloSeminario(), MatchMode.ANYWHERE));
                }
            }

            Collection<Inscricao> collection = c.list();

            for (Inscricao object : collection) {
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