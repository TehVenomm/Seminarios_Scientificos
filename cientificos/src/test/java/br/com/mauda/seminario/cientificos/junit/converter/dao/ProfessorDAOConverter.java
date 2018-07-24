package br.com.mauda.seminario.cientificos.junit.converter.dao;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import br.com.mauda.seminario.cientificos.junit.converter.InstituicaoConverter;
import br.com.mauda.seminario.cientificos.junit.converter.ProfessorConverter;
import br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor;
import br.com.mauda.seminario.cientificos.model.Instituicao;
import br.com.mauda.seminario.cientificos.model.Professor;

public class ProfessorDAOConverter implements ArgumentConverter {

    protected ProfessorConverter converter = new ProfessorConverter();
    protected InstituicaoConverter instituicaoConverter = new InstituicaoConverter();

    @Override
    public Object convert(Object input, ParameterContext parameterContext) throws ArgumentConversionException {
        if (input instanceof MassaProfessor) {
            MassaProfessor massaProfessor = (MassaProfessor) input;

            // Cria uma Instituicao temporaria com ID para facilitar o mapeamento
            Instituicao instituicao = this.instituicaoConverter.create(massaProfessor.getInstituicao());
            instituicao.setId(massaProfessor.getInstituicao().getId());
            Professor professor = new Professor(instituicao);

            // Atualiza as informacoes de acordo com o enum
            this.converter.update(professor, massaProfessor);

            return professor;
        }
        throw new ArgumentConversionException(input + " nao eh uma massa de professor valida");
    }
}