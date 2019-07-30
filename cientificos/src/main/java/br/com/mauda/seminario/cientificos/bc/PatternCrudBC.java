package br.com.mauda.seminario.cientificos.bc;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.model.DataValidation;

public abstract class PatternCrudBC<T extends DataValidation> {

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
        if (object == null) {
            throw new SeminariosCientificosException("ER0003");
        }
        object.validateForDataModification();
    }

}
