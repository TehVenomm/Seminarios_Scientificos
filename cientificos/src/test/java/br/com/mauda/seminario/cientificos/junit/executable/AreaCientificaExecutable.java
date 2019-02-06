package br.com.mauda.seminario.cientificos.junit.executable;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import br.com.mauda.seminario.cientificos.junit.massa.MassaAreaCientifica;
import br.com.mauda.seminario.cientificos.junit.util.MensagensUtils;
import br.com.mauda.seminario.cientificos.model.AreaCientifica;

public class AreaCientificaExecutable implements Executable {

    private AreaCientifica areaCientifica;
    private MassaAreaCientifica areaCientificaEnum;

    public AreaCientificaExecutable(AreaCientifica areaCientifica) {
        this.areaCientifica = areaCientifica;
    }

    public AreaCientificaExecutable(AreaCientifica areaCientifica, MassaAreaCientifica enumm) {
        this(areaCientifica);
        this.areaCientificaEnum = enumm;
    }

    public void basicVerification(AreaCientifica areaCientifica) throws Throwable {
        Assertions.assertNotNull(areaCientifica, MensagensUtils.getErrorMessage("Uma Area Cientifica nao pode ser nula"));

        Assertions.assertTrue(areaCientifica.getCursos() != null, MensagensUtils.getErrorMessage("É necessário inicializar a lista de cursos"));

        Assertions.assertTrue(StringUtils.isNotBlank(areaCientifica.getNome()),
            MensagensUtils.getErrorMessage("O nome de uma Area Cientifica nao pode ser nulo ou em branco"));
    }

    @Override
    public void execute() throws Throwable {
        this.basicVerification(this.areaCientifica);

        if (this.areaCientificaEnum != null) {
            Assertions.assertEquals(this.areaCientificaEnum.getNome(), this.areaCientifica.getNome(),
                MensagensUtils.getErrorMessage("Nomes das areas cientificas nao sao iguais"));
            return;
        }
    }
}
