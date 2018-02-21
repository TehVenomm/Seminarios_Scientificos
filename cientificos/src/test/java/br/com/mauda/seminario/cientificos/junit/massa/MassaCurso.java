package br.com.mauda.seminario.cientificos.junit.massa;

import static br.com.mauda.seminario.cientificos.junit.massa.MassaAreaCientifica.AC_LINGUAS;
import static br.com.mauda.seminario.cientificos.junit.massa.MassaAreaCientifica.AC_MATEMATICA;
import static br.com.mauda.seminario.cientificos.junit.massa.MassaAreaCientifica.AC_TECNOLOGIAS_GESTAO_INFORMACAO;

public enum MassaCurso {

    CURSO_ANALISE_SISTEMAS("Analise de Sistemas", AC_TECNOLOGIAS_GESTAO_INFORMACAO),
    CURSO_CIENCIA_COMPUTACAO("Ciencia da Computacao", AC_TECNOLOGIAS_GESTAO_INFORMACAO),
    CURSO_ENGENHARIA_COMPUTACAO("Engenharia da Computacao", AC_TECNOLOGIAS_GESTAO_INFORMACAO),

    CURSO_CALCULO("Calculo", AC_MATEMATICA),
    CURSO_DISCRETA("Matematica Discreta", AC_MATEMATICA),
    CURSO_FINANCEIRA("Matematica Financeira", AC_MATEMATICA),

    CURSO_INGLES("Ingles", AC_LINGUAS),
    CURSO_FRANCES("Frances", AC_LINGUAS),
    CURSO_PORTUGUES("Portugues", AC_LINGUAS);

    private MassaAreaCientifica areaCientifica;
    private String nome;

    private MassaCurso(String nome, MassaAreaCientifica areaCientifica) {
        this.nome = nome;
        this.areaCientifica = areaCientifica;
    }

    public MassaAreaCientifica getAreaCientifica() {
        return this.areaCientifica;
    }

    public String getNome() {
        return this.nome;
    }
}