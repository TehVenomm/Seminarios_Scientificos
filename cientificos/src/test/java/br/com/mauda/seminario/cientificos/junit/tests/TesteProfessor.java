package br.com.mauda.seminario.cientificos.junit.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.junit.converter.ProfessorConverter;
import br.com.mauda.seminario.cientificos.junit.executable.ProfessorExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor;
import br.com.mauda.seminario.cientificos.model.Professor;
import br.com.mauda.seminario.cientificos.util.EnumUtils;

public class TesteProfessor {

    protected ProfessorConverter converter = new ProfessorConverter();

    @Tag("modelTest")
    @DisplayName("Criacao de um Professor")
    @ParameterizedTest
    @EnumSource(MassaProfessor.class)
    public void criar(@ConvertWith(ProfessorConverter.class) Professor object) {
        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new ProfessorExecutable(object));
    }

    @Tag("modelTest")
    @DisplayName("Atualizacao dos atributos de um Professor")
    @ParameterizedTest
    @EnumSource(MassaProfessor.class)
    public void atualizar(@ConvertWith(ProfessorConverter.class) Professor object) {
        // Cria o objeto
        this.criar(object);

        // Atualiza as informacoes de um objeto
        this.converter.update(object, EnumUtils.getInstanceRandomly(MassaProfessor.class));

        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new ProfessorExecutable(object));
    }
}