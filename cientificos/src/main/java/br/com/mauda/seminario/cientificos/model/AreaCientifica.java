package br.com.mauda.seminario.cientificos.model;

import java.util.ArrayList;
import java.util.List;

public class AreaCientifica {

    private Long id;
    private String nome;
    private List<Curso> cursos = new ArrayList<>();

    public AreaCientifica() {
        super();
    }

    public void adicionarCurso(Curso curso) {
        this.cursos.add(curso);
    }

    public Boolean possuiCurso(Curso curso) {
        return this.cursos.contains(curso);
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

    public List<Curso> getCursos() {
        return this.cursos;
    }

}