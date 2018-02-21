package br.com.mauda.seminario.cientificos.junit.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.junit.converter.EstudanteConverter;
import br.com.mauda.seminario.cientificos.junit.executable.EstudanteExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaEstudante;
import br.com.mauda.seminario.cientificos.model.Estudante;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteEstudante {

    protected EstudanteConverter converter = new EstudanteConverter();

    @Tag("modelTest")
    @DisplayName("Criacao de um Estudante")
    @ParameterizedTest
    @EnumSource(MassaEstudante.class)
    public void criar(@ConvertWith(EstudanteConverter.class) Estudante object) {
        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new EstudanteExecutable(object));
    }

    @Tag("modelTest")
    @DisplayName("Atualizacao dos atributos de um Estudante")
    @ParameterizedTest
    @EnumSource(MassaEstudante.class)
    public void atualizar(@ConvertWith(EstudanteConverter.class) Estudante object) {
        // Cria o objeto
        this.criar(object);

        // Atualiza as informacoes de um objeto
        this.converter.update(object, EnumUtils.getInstanceRandomly(MassaEstudante.class));

        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new EstudanteExecutable(object));
    }
}