package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.dao.PatternCrudDAO;
import br.com.mauda.seminario.cientificos.model.IdentifierInterface;

public abstract class PatternCrudBC<T extends IdentifierInterface, DAO extends PatternCrudDAO<T>> {

    ///////////////////////////////////////////////////////////////////
    // METODOS UTILITARIOS
    ///////////////////////////////////////////////////////////////////

    /**
     * Utilizado para obter a instancia de DAO corretamente na filha da PatternCrudBC
     *
     * @return
     */
    protected DAO dao;

    ///////////////////////////////////////////////////////////////////
    // METODOS DE MODIFICACAO
    ///////////////////////////////////////////////////////////////////

    /**
     * Utilizado para realizar a validacao do object e a chamada do metodo de armazenamento correspondente da classe DAO
     *
     * Devera verificar se o objeto esta de acordo com as regras negociais para ser atualizado na base de dados.
     *
     * @param object
     */
    public void insert(T object) {
        this.validateForDataModification(object);
        this.dao.insert(object);
    }

    ///////////////////////////////////////////////////////////////////
    // METODOS DE BUSCA
    ///////////////////////////////////////////////////////////////////

    /**
     * Utilizado para verificar se o id passado nao eh nulo e chamar o metodo de busca correspondente da classe DAO
     *
     * Devera verificar se o id eh negativo ou null
     *
     * @param id
     * @return
     */
    public T findById(Long id) {
        if (id < 0) {
            return null;
        }
        return this.dao.findById(id);
    }

    ///////////////////////////////////////////////////////////////////
    // METODOS DE VALIDACAO
    ///////////////////////////////////////////////////////////////////

    /**
     * Realiza a validacao de um objeto para a insercao ou atualizacao correspondente da classe DAO
     *
     * As validacoes de regras de negocio deverao ser realizadas nesse metodo
     *
     * @param object
     */
    protected abstract void validateForDataModification(T object);
}
