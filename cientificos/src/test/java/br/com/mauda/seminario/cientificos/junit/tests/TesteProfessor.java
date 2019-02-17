package br.com.mauda.seminario.cientificos.junit.tests;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.junit.converter.ProfessorConverter;
import br.com.mauda.seminario.cientificos.junit.executable.ProfessorExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor;
import br.com.mauda.seminario.cientificos.model.Professor;

public class TesteProfessor {

    protected ProfessorConverter converter = new ProfessorConverter();

    @Tag("modelTest")
    @DisplayName("Criacao de um Professor")
    @ParameterizedTest(name = "Criacao do Professor [{arguments}]")
    @EnumSource(MassaProfessor.class)
    public void criar(@ConvertWith(ProfessorConverter.class) Professor object) {
        // Verifica se os atributos estao preenchidos corretamente
        assertAll(new ProfessorExecutable(object));
    }
}