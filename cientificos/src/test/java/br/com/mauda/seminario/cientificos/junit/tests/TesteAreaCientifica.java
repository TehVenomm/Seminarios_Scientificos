package br.com.mauda.seminario.cientificos.junit.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.junit.converter.AreaCientificaConverter;
import br.com.mauda.seminario.cientificos.junit.executable.AreaCientificaExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaAreaCientifica;
import br.com.mauda.seminario.cientificos.model.AreaCientifica;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteAreaCientifica {

    protected AreaCientificaConverter converter = new AreaCientificaConverter();

    @Tag("modelTest")
    @DisplayName("Criacao de uma Area Cientifica")
    @ParameterizedTest
    @EnumSource(MassaAreaCientifica.class)
    public void criar(@ConvertWith(AreaCientificaConverter.class) AreaCientifica object) {
        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new AreaCientificaExecutable(object));
    }

    @Tag("modelTest")
    @DisplayName("Atualizacao dos atributos de uma Area Cientifica")
    @ParameterizedTest
    @EnumSource(MassaAreaCientifica.class)
    public void atualizar(@ConvertWith(AreaCientificaConverter.class) AreaCientifica object) {
        // Cria o objeto
        this.criar(object);

        // Atualiza as informacoes de um objeto
        this.converter.update(object, EnumUtils.getInstanceRandomly(MassaAreaCientifica.class));

        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new AreaCientificaExecutable(object));
    }
}