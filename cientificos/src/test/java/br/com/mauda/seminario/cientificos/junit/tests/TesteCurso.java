package br.com.mauda.seminario.cientificos.junit.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import br.com.mauda.seminario.cientificos.junit.converter.CursoConverter;
import br.com.mauda.seminario.cientificos.junit.executable.CursoExecutable;
import br.com.mauda.seminario.cientificos.junit.massa.MassaCurso;
import br.com.mauda.seminario.cientificos.model.Curso;

public class TesteCurso {

    @Tag("modelTest")
    @DisplayName("Criacao de um Curso")
    @ParameterizedTest(name = "Criacao do Curso [{arguments}]")
    @EnumSource(MassaCurso.class)
    public void criar(@ConvertWith(CursoConverter.class) Curso object) {
        // Verifica se os atributos estao preenchidos corretamente
        Assertions.assertAll(new CursoExecutable(object));
    }
}