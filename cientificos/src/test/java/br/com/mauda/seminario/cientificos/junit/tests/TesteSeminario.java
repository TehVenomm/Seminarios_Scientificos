package br.com.mauda.seminario.cientificos.junit.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.junit.converter.SeminarioConverter;
import br.com.mauda.seminario.cientificos.junit.executable.SeminarioExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaSeminario;
import br.com.mauda.seminario.cientificos.model.Seminario;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteSeminario {

    protected SeminarioConverter converter = new SeminarioConverter();

    @Tag("modelTest")
    @DisplayName("Criacao de um Seminario")
    @ParameterizedTest
    @EnumSource(MassaSeminario.class)
    public void criar(@ConvertWith(SeminarioConverter.class) Seminario object) {
        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new SeminarioExecutable(object));
    }

    @Tag("modelTest")
    @DisplayName("Atualizacao dos atributos de um Seminario")
    @ParameterizedTest
    @EnumSource(MassaSeminario.class)
    public void atualizar(@ConvertWith(SeminarioConverter.class) Seminario object) {
        // Cria o objeto
        this.criar(object);

        // Atualiza as informacoes de um objeto
        this.converter.update(object, EnumUtils.getInstanceRandomly(MassaSeminario.class));

        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new SeminarioExecutable(object));
    }
}