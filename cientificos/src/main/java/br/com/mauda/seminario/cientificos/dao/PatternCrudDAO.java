package br.com.mauda.seminario.cientificos.dao;

import java.io.Serializable;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mauda.seminario.cientificos.dao.util.HibernateUtil;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.model.DataValidation;

public abstract class PatternCrudDAO<T extends DataValidation> implements Serializable {

    private static final long serialVersionUID = 3723942253378506052L;
    private final String findByIdHQL = "FROM " + this.getClass().getName() + " as c WHERE c.id = :id";

    protected PatternCrudDAO() {
    }

    ////////////////////////////////////////////////////////////
    // METODOS AUXILIARES
    ////////////////////////////////////////////////////////////
    /**
     * Metodo auxiliar para inicializar um objeto que eh lazy
     *
     * @param collection
     */
    public abstract void inicializaLazyObjects(T object);

    ////////////////////////////////////////////////////////////
    // METODOS DML DE RECUPERACAO DE INFORMACAO
    ////////////////////////////////////////////////////////////

    /**
     * Metodo que realiza uma busca pelo id na tabela da entidade T
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public T findById(Long id) {
        Session session = HibernateUtil.getSession();
        try {
            Query byIdQuery = session.createQuery(this.findByIdHQL);
            byIdQuery.setParameter("id", id);
            T object = (T) byIdQuery.uniqueResult();
            this.inicializaLazyObjects(object);
            return object;

        } catch (Exception e) {
            throw new SeminariosCientificosException(e);
        } finally {
            session.close();
        }
    }

    /////////////////////////////////////////
    // METODOS DML COM ALTERACAO NA BASE
    /////////////////////////////////////////

    /**
     * Metodo que realiza um insert na tabela da entidade T
     *
     * @param obj
     * @return
     */
    public void insert(T obj) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(obj);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw new SeminariosCientificosException(ex);
        } finally {
            session.close();
        }
    }
}