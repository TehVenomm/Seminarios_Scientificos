package br.com.mauda.seminario.cientificos.junit.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.junit.converter.InstituicaoConverter;
import br.com.mauda.seminario.cientificos.junit.executable.InstituicaoExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaInstituicao;
import br.com.mauda.seminario.cientificos.model.Instituicao;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteInstituicao {

    protected InstituicaoConverter converter = new InstituicaoConverter();

    @Tag("modelTest")
    @DisplayName("Criacao de uma Instituicao")
    @ParameterizedTest
    @EnumSource(MassaInstituicao.class)
    public void criar(@ConvertWith(InstituicaoConverter.class) Instituicao object) {
        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new InstituicaoExecutable(object));
    }

    @Tag("modelTest")
    @DisplayName("Atualizacao dos atributos de uma Instituicao")
    @ParameterizedTest
    @EnumSource(MassaInstituicao.class)
    public void atualizar(@ConvertWith(InstituicaoConverter.class) Instituicao object) {
        // Cria o objeto
        this.criar(object);

        // Atualiza as informacoes de um objeto
        this.converter.update(object, EnumUtils.getInstanceRandomly(MassaInstituicao.class));

        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new InstituicaoExecutable(object));
    }
}