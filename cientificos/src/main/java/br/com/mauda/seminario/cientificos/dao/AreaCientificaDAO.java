package br.com.mauda.seminario.cientificos.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.mauda.seminario.cientificos.dao.util.HibernateUtil;
import br.com.mauda.seminario.cientificos.dto.AreaCientificaDTO;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.model.AreaCientifica;

public class AreaCientificaDAO extends PatternCrudDAO<AreaCientifica, AreaCientificaDTO> {

    private static final long serialVersionUID = -7892112461904038500L;
    private static AreaCientificaDAO instance = new AreaCientificaDAO();

    private AreaCientificaDAO() {
        super(AreaCientifica.class);
    }

    public static AreaCientificaDAO getInstance() {
        return instance;
    }

    @Override
    public void inicializaLazyObjects(AreaCientifica areaCientifica) {
        if (areaCientifica == null) {
            return;
        }

        Hibernate.initialize(areaCientifica.getCursos());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<AreaCientifica> findByFilter(AreaCientificaDTO filter) {
        Session session = HibernateUtil.getSession();

        try {
            StringBuilder sb = new StringBuilder("FROM AreaCientifica as ac WHERE 1=1 ");
            Map<String, Object> parameters = new HashMap<>();

            if (filter.getId() != null) {
                sb.append(" AND ac.id = :id ");
                parameters.put("id", filter.getId());
            }

            if (StringUtils.isNotBlank(filter.getNome())) {
                sb.append(" AND ac.nome like :nome ");
                parameters.put("nome", "%" + filter.getNome() + "%");
            }

            Query listQuery = session.createQuery(sb.toString());

            for (Entry<String, Object> entry : parameters.entrySet()) {
                listQuery.setParameter(entry.getKey(), entry.getValue());
            }

            Collection<AreaCientifica> collection = listQuery.list();

            for (AreaCientifica object : collection) {
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