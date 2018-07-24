package br.com.mauda.seminario.cientificos.junit.converter.dao;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import br.com.mauda.seminario.cientificos.junit.converter.AreaCientificaConverter;
import br.com.mauda.seminario.cientificos.junit.converter.CursoConverter;
import br.com.mauda.seminario.cientificos.junit.massa.MassaCurso;
import br.com.mauda.seminario.cientificos.model.AreaCientifica;
import br.com.mauda.seminario.cientificos.model.Curso;

public class CursoDAOConverter implements ArgumentConverter {

    protected CursoConverter converter = new CursoConverter();
    protected AreaCientificaConverter areaConverter = new AreaCientificaConverter();

    @Override
    public Object convert(Object input, ParameterContext parameterContext) throws ArgumentConversionException {
        if (input instanceof MassaCurso) {
            MassaCurso massaCurso = (MassaCurso) input;

            // Cria uma Area Cientifica temporaria com ID para facilitar o mapeamento
            AreaCientifica areaCientifica = this.areaConverter.create(massaCurso.getAreaCientifica());
            areaCientifica.setId(massaCurso.getAreaCientifica().getId());

            // Cria um curso com a Area Cientifica
            Curso curso = new Curso(areaCientifica);

            // Atualiza as informacoes de acordo com o enum
            this.converter.update(curso, massaCurso);

            // Retorna o curso
            return curso;
        }
        throw new ArgumentConversionException(input + " nao eh uma massa de curso valida");
    }
}