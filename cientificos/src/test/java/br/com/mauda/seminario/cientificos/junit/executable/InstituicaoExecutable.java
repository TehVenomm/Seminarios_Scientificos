package br.com.mauda.seminario.cientificos.junit.executable;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import br.com.mauda.seminario.cientificos.junit.massa.MassaInstituicao;
import br.com.mauda.seminario.cientificos.model.Instituicao;

public class InstituicaoExecutable implements Executable {

    private Instituicao instituicao, instituicaoBD;
    private MassaInstituicao instituicaoEnum;

    public InstituicaoExecutable(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public InstituicaoExecutable(Instituicao instituicao, MassaInstituicao enumm) {
        this(instituicao);
        this.instituicaoEnum = enumm;
    }

    public InstituicaoExecutable(Instituicao instituicao, Instituicao instituicaoBD) {
        this(instituicao);
        this.instituicaoBD = instituicaoBD;
    }

    public void basicVerification(Instituicao instituicao) throws Throwable {
        Assertions.assertNotNull(instituicao, "Uma Instituicao nao pode ser nula");
        Assertions.assertTrue(StringUtils.isNotBlank(instituicao.getCidade()), "A cidade de uma Instituicao nao pode ser nula ou em branco");
        Assertions.assertTrue(StringUtils.isNotBlank(instituicao.getEstado()), "O estado de uma Instituicao nao pode ser nulo ou em branco");
        Assertions.assertTrue(StringUtils.isNotBlank(instituicao.getNome()), "O nome de uma Instituicao nao pode ser nulo ou em branco");
        Assertions.assertTrue(StringUtils.isNotBlank(instituicao.getPais()), "O pais de uma Instituicao nao pode ser nulo ou em branco");
        Assertions.assertTrue(StringUtils.isNotBlank(instituicao.getSigla()), "A sigla de uma Instituicao nao pode ser nula ou em branco");
    }

    @Override
    public void execute() throws Throwable {
        this.basicVerification(this.instituicao);

        if (this.instituicaoEnum != null) {
            Assertions.assertEquals(this.instituicaoEnum.getCidade(), this.instituicao.getCidade(), "Cidades das instituicoes nao sao iguais");
            Assertions.assertEquals(this.instituicaoEnum.getEstado(), this.instituicao.getEstado(), "Estados das instituicoes nao sao iguais");
            Assertions.assertEquals(this.instituicaoEnum.getNome(), this.instituicao.getNome(), "Nomes das instituicoes nao sao iguais");
            Assertions.assertEquals(this.instituicaoEnum.getPais(), this.instituicao.getPais(), "Paises das instituicoes nao sao iguais");
            Assertions.assertEquals(this.instituicaoEnum.getSigla(), this.instituicao.getSigla(), "Siglas das instituicoes nao sao iguais");
            return;
        }

        if (this.instituicaoBD != null) {
            this.basicVerification(this.instituicaoBD);
            Assertions.assertEquals(this.instituicaoBD.getCidade(), this.instituicao.getCidade(), "Cidades das instituicoes nao sao iguais");
            Assertions.assertEquals(this.instituicaoBD.getEstado(), this.instituicao.getEstado(), "Estados das instituicoes nao sao iguais");
            Assertions.assertEquals(this.instituicaoBD.getId(), this.instituicao.getId(), "Ids das instituicoes nao sao iguais");
            Assertions.assertEquals(this.instituicaoBD.getNome(), this.instituicao.getNome(), "Nomes das instituicoes nao sao iguais");
            Assertions.assertEquals(this.instituicaoBD.getPais(), this.instituicao.getPais(), "Paises das instituicoes nao sao iguais");
            Assertions.assertEquals(this.instituicaoBD.getSigla(), this.instituicao.getSigla(), "Siglas das instituicoes nao sao iguais");
        }
    }
}