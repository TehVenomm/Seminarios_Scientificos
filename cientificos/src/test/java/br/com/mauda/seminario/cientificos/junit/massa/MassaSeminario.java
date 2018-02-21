package br.com.mauda.seminario.cientificos.junit.massa;

import static br.com.mauda.seminario.cientificos.junit.massa.MassaAreaCientifica.AC_LINGUAS;
import static br.com.mauda.seminario.cientificos.junit.massa.MassaAreaCientifica.AC_MATEMATICA;
import static br.com.mauda.seminario.cientificos.junit.massa.MassaAreaCientifica.AC_TECNOLOGIAS_GESTAO_INFORMACAO;
import static br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor.PROFESSOR_01;
import static br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor.PROFESSOR_02;
import static br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor.PROFESSOR_03;
import static br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor.PROFESSOR_04;
import static br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor.PROFESSOR_05;
import static br.com.mauda.seminario.cientificos.junit.massa.MassaProfessor.PROFESSOR_06;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public enum MassaSeminario {

    SEMINARIO_TECNOLOGIAS_GESTAO_INFORMACAO(
        "Seminario de Tecnologias de Gestao da Informacao",
        DateUtils.addDays(new Date(), 1),
        "Seminario Especializado em Tecnologias de Gestao da Informacao",
        false,
        10,
        PROFESSOR_01,
        AC_TECNOLOGIAS_GESTAO_INFORMACAO),

    SEMINARIO_JAVA(
        "Seminario de Java",
        DateUtils.addDays(new Date(), 2),
        "Seminario Especializado na Linguagem Java",
        false,
        10,
        PROFESSOR_02,
        AC_TECNOLOGIAS_GESTAO_INFORMACAO),

    SEMINARIO_MATEMATICA_DISCRETA(
        "Seminario de Discreta, a Matematica",
        DateUtils.addDays(new Date(), 3),
        "Seminario de Matematica Discreta",
        true,
        15,
        PROFESSOR_03,
        AC_MATEMATICA),

    SEMINARIO_MATEMATICA(
        "Seminario de Matematica",
        DateUtils.addDays(new Date(), 4),
        "Seminario Especializado em Matematica",
        true,
        15,
        PROFESSOR_04,
        AC_MATEMATICA),

    SEMINARIO_LINGUAS(
        "Seminario de Linguas",
        DateUtils.addDays(new Date(), 5),
        "Seminario Especializado em Linguas",
        true,
        15,
        PROFESSOR_05,
        AC_LINGUAS),

    SEMINARIO_INGLES(
        "Seminario de Lingua Inglesa",
        DateUtils.addDays(new Date(), 6),
        "Seminario Especializado em Lingua Inglesa",
        true,
        15,
        PROFESSOR_06,
        AC_LINGUAS);

    private MassaAreaCientifica areaCientifica;
    private Date data;
    private String descricao;
    private Boolean mesaRedonda;
    private MassaProfessor professor;
    private int qtdInscricoes;
    private String titulo;

    private MassaSeminario(String titulo, Date data, String descricao, Boolean mesaRedonda,
        int qtdInscricoes, MassaProfessor professor,
        MassaAreaCientifica areaCientifica) {
        this.areaCientifica = areaCientifica;
        this.data = data;
        this.descricao = descricao;
        this.mesaRedonda = mesaRedonda;
        this.professor = professor;
        this.qtdInscricoes = qtdInscricoes;
        this.titulo = titulo;
    }

    public MassaAreaCientifica getAreaCientifica() {
        return this.areaCientifica;
    }

    public Date getData() {
        return this.data;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Boolean getMesaRedonda() {
        return this.mesaRedonda;
    }

    public MassaProfessor getProfessor() {
        return this.professor;
    }

    public Integer getQtdInscricoes() {
        return this.qtdInscricoes;
    }

    public String getTitulo() {
        return this.titulo;
    }
}