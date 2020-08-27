package br.com.mauda.seminario.cientificos.model;

import java.util.ArrayList;
import java.util.List;

public class Estudante {

    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private Instituicao instituicao;
    private List<Inscricao> inscricoes = new ArrayList<>();

    public Estudante(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public void adicionarInscricao(Inscricao inscricao) {
        this.inscricoes.add(inscricao);
    }

    public Boolean possuiInscricao(Inscricao inscricao) {
        return this.inscricoes.contains(inscricao);
    }

    public void removerInscricao(Inscricao inscricao) {
        this.inscricoes.remove(inscricao);
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

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instituicao getInstituicao() {
        return this.instituicao;
    }

    public List<Inscricao> getInscricoes() {
        return this.inscricoes;
    }
}
