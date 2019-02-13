package br.com.mauda.seminario.cientificos.junit.tests;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.junit.converter.InstituicaoConverter;
import br.com.mauda.seminario.cientificos.junit.executable.InstituicaoExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaInstituicao;
import br.com.mauda.seminario.cientificos.model.Instituicao;

public class TesteInstituicao {

    @Tag("modelTest")
    @DisplayName("Criacao de uma Instituicao")
    @ParameterizedTest(name = "Criacao da Instituicao [{arguments}]")
    @EnumSource(MassaInstituicao.class)
    public void criar(@ConvertWith(InstituicaoConverter.class) Instituicao object) {
        // Verifica se os atributos estao preenchidos corretamente
        assertAll(new InstituicaoExecutable(object));
    }
}