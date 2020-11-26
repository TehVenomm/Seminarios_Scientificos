package br.com.mauda.seminario.cientificos.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.platform.commons.util.StringUtils;

import br.com.mauda.seminario.cientificos.dao.util.HibernateUtil;
import br.com.mauda.seminario.cientificos.dto.EstudanteDTO;
import br.com.mauda.seminario.cientificos.model.Estudante;

public class EstudanteDAO extends PatternCrudDAO<Estudante, EstudanteDTO> {

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
        if (estudante == null) {
            return;
        }

        Hibernate.initialize(estudante.getInscricoes());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Estudante> findByFilter(EstudanteDTO filter) {
        Session session = HibernateUtil.getSession();

        try {
            StringBuilder sb = new StringBuilder("SELECT e FROM Estudante e JOIN e.instituicao i WHERE 1=1 ");
            Map<String, Object> parameters = new HashMap<>();

            if (filter.getId() != null) {
                sb.append(" AND e.id = :id ");
                parameters.put("id", filter.getId());
            }

            if (StringUtils.isNotBlank(filter.getNome())) {
                sb.append(" AND e.nome like :nomeEstudante ");
                parameters.put("nomeEstudante", "%" + filter.getNome() + "%");
            }

            if (StringUtils.isNotBlank(filter.getCidade())) {
                sb.append(" AND i.cidade like :cidade ");
                parameters.put("cidade", "%" + filter.getCidade() + "%");
            }

            if (StringUtils.isNotBlank(filter.getEstado())) {
                sb.append(" AND i.estado like :estado ");
                parameters.put("estado", "%" + filter.getEstado() + "%");
            }

            if (StringUtils.isNotBlank(filter.getPais())) {
                sb.append(" AND i.pais like :pais ");
                parameters.put("pais", "%" + filter.getPais() + "%");
            }

            if (StringUtils.isNotBlank(filter.getNomeInstituicao())) {
                sb.append(" AND i.nome like :nomeInstituicao ");
                parameters.put("nomeInstituicao", "%" + filter.getNomeInstituicao() + "%");
            }

            if (StringUtils.isNotBlank(filter.getEmail())) {
                sb.append(" AND e.email like :email ");
                parameters.put("email", "%" + filter.getEmail() + "%");
            }

            if (StringUtils.isNotBlank(filter.getTelefone())) {
                sb.append(" AND e.telefone like :telefone ");
                parameters.put("telefone", "%" + filter.getTelefone() + "%");
            }

            Query query = session.createQuery(sb.toString());

            for (Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            Collection<Estudante> estudantes = query.list();
            estudantes.forEach(this::inicializaLazyObjects);

            return estudantes;
        } finally {
            session.close();
        }
    }
}