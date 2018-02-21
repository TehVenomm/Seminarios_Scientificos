package br.com.mauda.seminario.cientificos.junit.massa;

public enum MassaAreaCientifica {

    AC_TECNOLOGIAS_GESTAO_INFORMACAO("Tecnologias de Gestao da Informacao"),
    AC_MATEMATICA("Matematica"),
    AC_LINGUAS("Linguas");

    private String nome;

    private MassaAreaCientifica(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }
}