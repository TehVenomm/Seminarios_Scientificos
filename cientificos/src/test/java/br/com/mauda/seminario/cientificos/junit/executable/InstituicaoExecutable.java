package br.com.mauda.seminario.cientificos.junit.executable;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import br.com.mauda.seminario.cientificos.junit.massa.MassaInstituicao;
import br.com.mauda.seminario.cientificos.junit.util.MensagensUtils;
import br.com.mauda.seminario.cientificos.model.Instituicao;

public class InstituicaoExecutable implements Executable {

    private Instituicao instituicao;
    private MassaInstituicao instituicaoEnum;

    public InstituicaoExecutable(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public InstituicaoExecutable(Instituicao instituicao, MassaInstituicao enumm) {
        this(instituicao);
        this.instituicaoEnum = enumm;
    }

    public void basicVerification(Instituicao instituicao) throws Throwable {
        Assertions.assertNotNull(instituicao, MensagensUtils.getErrorMessage("Uma Instituicao nao pode ser nula"));

        Assertions.assertTrue(StringUtils.isNotBlank(instituicao.getCidade()),
            MensagensUtils.getErrorMessage("A cidade de uma Instituicao nao pode ser nula ou em branco"));

        Assertions.assertTrue(StringUtils.isNotBlank(instituicao.getEstado()),
            MensagensUtils.getErrorMessage("O estado de uma Instituicao nao pode ser nulo ou em branco"));

        Assertions.assertTrue(StringUtils.isNotBlank(instituicao.getNome()),
            MensagensUtils.getErrorMessage("O nome de uma Instituicao nao pode ser nulo ou em branco"));

        Assertions.assertTrue(StringUtils.isNotBlank(instituicao.getPais()),
            MensagensUtils.getErrorMessage("O pais de uma Instituicao nao pode ser nulo ou em branco"));

        Assertions.assertTrue(StringUtils.isNotBlank(instituicao.getSigla()),
            MensagensUtils.getErrorMessage("A sigla de uma Instituicao nao pode ser nula ou em branco"));
    }

    @Override
    public void execute() throws Throwable {
        this.basicVerification(this.instituicao);

        if (this.instituicaoEnum != null) {
            Assertions.assertEquals(this.instituicaoEnum.getCidade(), this.instituicao.getCidade(),
                MensagensUtils.getErrorMessage("Cidades das instituicoes nao sao iguais"));

            Assertions.assertEquals(this.instituicaoEnum.getEstado(), this.instituicao.getEstado(),
                MensagensUtils.getErrorMessage("Estados das instituicoes nao sao iguais"));

            Assertions.assertEquals(this.instituicaoEnum.getNome(), this.instituicao.getNome(),
                MensagensUtils.getErrorMessage("Nomes das instituicoes nao sao iguais"));

            Assertions.assertEquals(this.instituicaoEnum.getPais(), this.instituicao.getPais(),
                MensagensUtils.getErrorMessage("Paises das instituicoes nao sao iguais"));

            Assertions.assertEquals(this.instituicaoEnum.getSigla(), this.instituicao.getSigla(),
                MensagensUtils.getErrorMessage("Siglas das instituicoes nao sao iguais"));
            return;
        }
    }
}