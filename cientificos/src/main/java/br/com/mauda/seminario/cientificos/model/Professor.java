package br.com.mauda.seminario.cientificos.model;

import java.util.ArrayList;
import java.util.List;

public class Professor {

    private Long id;
    private String email;
    private String nome;
    private Double salario;
    private String telefone;
    private Instituicao instituicao;
    private List<Seminario> seminarios = new ArrayList<>();

    public Professor(Long id, String email, String nome, Double salario, String telefone, Instituicao instituicao) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.salario = salario;
        this.telefone = telefone;
        this.instituicao = instituicao;
    }

    public void adicionarSeminario(Seminario seminario) {
        this.seminarios.add(seminario);
        // this.seminarios.get(this.seminarios.size()-1).adicionarProfessor(this);
    }

    public Boolean possuiSeminario(Seminario seminario) {
        return this.seminarios.contains(seminario);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSalario() {
        return this.salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Instituicao getInstituicao() {
        return this.instituicao;
    }

    public void setInstituicoes(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public List<Seminario> getSeminarios() {
        return this.seminarios;
    }
}
