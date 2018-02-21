package br.com.mauda.seminario.cientificos.junit.converter.dto;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import br.com.mauda.seminario.cientificos.junit.converter.EstudanteConverter;
import br.com.mauda.seminario.cientificos.junit.converter.SeminarioConverter;
import br.com.mauda.seminario.cientificos.junit.dto.AcaoInscricaoDTO;
import br.com.mauda.seminario.cientificos.junit.massa.MassaInscricao;
import br.com.mauda.seminario.cientificos.junit.massa.MassaInscricaoCheckIn;
import br.com.mauda.seminario.cientificos.junit.massa.MassaInscricaoComprar;
import br.com.mauda.seminario.cientificos.model.Estudante;
import br.com.mauda.seminario.cientificos.model.Inscricao;
import br.com.mauda.seminario.cientificos.model.Seminario;

public class AcaoInscricaoDTOConverter implements ArgumentConverter {

    protected EstudanteConverter estudanteConverter = new EstudanteConverter();

    protected SeminarioConverter seminarioConverter = new SeminarioConverter();

    @Override
    public Object convert(Object input, ParameterContext parameterContext) throws ArgumentConversionException {
        if (input instanceof MassaInscricaoComprar) {
            return this.create((MassaInscricaoComprar) input);
        }
        if (input instanceof MassaInscricaoCheckIn) {
            return this.create((MassaInscricaoCheckIn) input);
        }
        throw new ArgumentConversionException(input + " nao eh uma massa de inscricao valida");
    }

    public AcaoInscricaoDTO create(MassaInscricaoComprar enumm) {
        return this.create(enumm.getInscricao());
    }

    public AcaoInscricaoDTO create(MassaInscricaoCheckIn enumm) {
        return this.create(enumm.getInscricao());
    }

    public AcaoInscricaoDTO create(MassaInscricao enumm) {
        // Cria o Seminario
        Seminario seminario = this.seminarioConverter.create(enumm.getSeminario());

        // Cria o Estudante
        Estudante estudante = this.estudanteConverter.create(enumm.getEstudante());

        // Obtem a inscricao
        Inscricao inscricao = seminario.getInscricoes().get(0);

        // Obtem o direito ao material
        Boolean direitoMaterial = enumm.isDireitoMaterial();

        return new AcaoInscricaoDTO(seminario, estudante, inscricao, direitoMaterial);
    }
}