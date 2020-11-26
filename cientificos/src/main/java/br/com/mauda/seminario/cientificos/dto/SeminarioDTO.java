package br.com.mauda.seminario.cientificos.dto;

import java.util.Date;

import org.junit.platform.commons.util.StringUtils;

public class SeminarioDTO implements FilterValidation {

    private Long id;
    private Date data;
    private String descricao;
    private Boolean mesaRedonda;
    private String nomeAreaCientifica;
    private String nomeProfessor;
    private String titulo;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public String getNomeAreaCientifica() {
        return this.nomeAreaCientifica;
    }

    public void setNomeAreaCientifica(String nomeAreaCientifica) {
        this.nomeAreaCientifica = nomeAreaCientifica;
    }

    public String getNomeProfessor() {
        return this.nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public boolean validateForFindData() {
        if (this.id != null) {
            return true;
        }

        if (this.data != null) {
            return true;
        }

        if (StringUtils.isNotBlank(this.descricao)) {
            return true;
        }

        if (this.mesaRedonda != null) {
            return true;
        }

        if (StringUtils.isNotBlank(this.nomeAreaCientifica)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.nomeProfessor)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.titulo)) {
            return true;
        }

        return false;
    }

}
