package br.com.mauda.seminario.cientificos.model;

public class Curso {

    private Long id;
    private String nome;
    private AreaCientifica areaCientifica;

    public Curso(Long id, String nome, AreaCientifica areaCientifica) {
        this.id = id;
        this.nome = nome;
        this.areaCientifica = areaCientifica;
        this.areaCientifica.adicionarCurso(this);
    }

    public Curso(AreaCientifica areaCientifica) {
        this.areaCientifica = areaCientifica;
        this.areaCientifica.adicionarCurso(this);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AreaCientifica getAreaCientifica() {
        return this.areaCientifica;
    }

    public void setAreaCientifica(AreaCientifica areaCientifica) {
        this.areaCientifica = areaCientifica;
    }
}
