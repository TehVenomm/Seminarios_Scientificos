package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.model.IdentifierInterface;

public abstract class PatternCrudBC<T extends IdentifierInterface> {

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
