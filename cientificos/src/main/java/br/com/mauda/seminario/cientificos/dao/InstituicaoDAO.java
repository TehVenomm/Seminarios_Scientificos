package br.com.mauda.seminario.cientificos.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.mauda.seminario.cientificos.dao.util.HibernateUtil;
import br.com.mauda.seminario.cientificos.dto.InstituicaoDTO;
import br.com.mauda.seminario.cientificos.model.Instituicao;

public class InstituicaoDAO extends PatternCrudDAO<Instituicao, InstituicaoDTO> {

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
        // nada a inicializar
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Instituicao> findByFilter(InstituicaoDTO filter) {
        Session session = HibernateUtil.getSession();

        try {
            StringBuilder sb = new StringBuilder("FROM Instituicao i WHERE 1=1");
            Map<String, Object> parameters = new HashMap<>();

            if (filter.getId() != null) {
                sb.append(" AND i.id = :id ");
                parameters.put("id", filter.getId());
            }

            if (StringUtils.isNotBlank(filter.getCidade())) {
                sb.append(" AND i.cidade like :cidade ");
                parameters.put("cidade", "%" + filter.getCidade() + "%");
            }

            if (StringUtils.isNotBlank(filter.getEstado())) {
                sb.append(" AND i.estado like :estado ");
                parameters.put("estado", "%" + filter.getEstado() + "%");
            }

            if (StringUtils.isNotBlank(filter.getNome())) {
                sb.append(" AND i.nome like :nome ");
                parameters.put("nome", "%" + filter.getNome() + "%");
            }

            if (StringUtils.isNotBlank(filter.getPais())) {
                sb.append(" AND i.pais like :pais ");
                parameters.put("pais", "%" + filter.getPais() + "%");
            }

            if (StringUtils.isNotBlank(filter.getSigla())) {
                sb.append(" AND i.sigla like :sigla ");
                parameters.put("sigla", "%" + filter.getSigla() + "%");
            }

            Query query = session.createQuery(sb.toString());

            for (Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            Collection<Instituicao> collection = query.list();
            for (Instituicao object : collection) {
                this.inicializaLazyObjects(object);
            }
            return collection;
        } finally {
            session.close();
        }
    }
}