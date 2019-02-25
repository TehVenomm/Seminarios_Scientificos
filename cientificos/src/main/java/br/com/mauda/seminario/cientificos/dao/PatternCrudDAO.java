package br.com.mauda.seminario.cientificos.dao;

import java.io.Serializable;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.mauda.seminario.cientificos.dao.util.HibernateUtil;
import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.model.DataValidation;

public abstract class PatternCrudDAO<T extends DataValidation, DTO> implements Serializable {

    private static final long serialVersionUID = 3723942253378506052L;
    protected String entityClassName;
    protected static Logger LOGGER;

    public PatternCrudDAO(Class<T> entityClass) {
        PatternCrudDAO.LOGGER = LogManager.getLogger(entityClass);
        this.entityClassName = entityClass.getName();
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
            Query byIdQuery = session.createQuery("FROM " + this.entityClassName + " as c WHERE c.id = :id");
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

    /**
     * Utilizado para buscas com o filtro da entidade, onde este contera as informacoes relacionadas com a filtragem de dados
     *
     * @param filter
     * @return
     */
    public abstract Collection<T> findByFilter(DTO filter);

    /**
     * Metodo que realiza a busca de todas as entidades da tabela da entidade T
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Collection<T> findAll() {
        Session session = HibernateUtil.getSession();
        try {
            Query listQuery = session.createQuery("FROM " + this.entityClassName + " as c");
            Collection<T> collection = listQuery.list();
            for (T object : collection) {
                this.inicializaLazyObjects(object);
            }
            return collection;
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
            PatternCrudDAO.LOGGER.debug("Nova Linha: " + obj + ", foi comitada. ");
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw new SeminariosCientificosException(ex);
        } finally {
            session.close();
        }
    }

    /**
     * Metodo que realiza um update na tabela da entidade T
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public void update(T obj) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            obj = (T) session.merge(obj);
            tx.commit();
            PatternCrudDAO.LOGGER.debug("Linha: " + obj + ", foi atualizada. ");
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw new SeminariosCientificosException(ex);
        } finally {
            session.close();
        }
    }

    /**
     * Metodo que realiza um delete na tabela da entidade T
     *
     * @param obj
     * @return
     */

    @SuppressWarnings("unchecked")
    public void delete(T obj) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            obj = (T) session.merge(obj);
            session.delete(obj);
            tx.commit();
            PatternCrudDAO.LOGGER.debug("Linha: " + obj + ", foi deletada. ");
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