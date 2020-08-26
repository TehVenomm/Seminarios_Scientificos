package br.com.mauda.seminario.cientificos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Seminario {

    private Long id;
    private String titulo;
    private String descricao;
    private Boolean mesaRedonda;
    private Date data;
    private int qtdInscricoes;
    private List<Inscricao> inscricoes = new ArrayList<>();
    private List<Professor> professores = new ArrayList<>();
    private List<AreaCientifica> areasCientificas = new ArrayList<>();

    public Seminario(Long id, String titulo, String descricao, Boolean mesaRedonda, Date data, Professor professor, AreaCientifica areaCientifica) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.mesaRedonda = mesaRedonda;
        this.data = data;
        this.professores.add(professor);
        this.professores.get(0).adicionarSeminario(this);
        this.areasCientificas.add(areaCientifica);
        this.areasCientificas.get(0).adicionarSeminario(this);
    }

    public void adicionarAreaCientifica(AreaCientifica area) {
        this.areasCientificas.add(area);
    }

    public void adicionarInscricao(Inscricao inscricao) {
        this.inscricoes.add(inscricao);
    }

    public void adicionarProfessor(Professor professor) {
        this.professores.add(professor);
    }

    public Boolean possuiAreaCientifica(AreaCientifica area) {
        return this.areasCientificas.contains(area);
    }

    public Boolean possuiInscricao(Inscricao inscricao) {
        return this.inscricoes.contains(inscricao);
    }

    public Boolean possuiProfessor(Professor professor) {
        return this.professores.contains(professor);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getMesaRedonda() {
        return this.mesaRedonda;
    }

    public void setMesaRedonda(Boolean mesaRedonda) {
        this.mesaRedonda = mesaRedonda;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getQtdInscricoes() {
        return this.qtdInscricoes;
    }
}
