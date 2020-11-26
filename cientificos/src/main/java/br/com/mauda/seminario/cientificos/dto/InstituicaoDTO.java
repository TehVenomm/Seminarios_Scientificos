package br.com.mauda.seminario.cientificos.dto;

import org.junit.platform.commons.util.StringUtils;

public class InstituicaoDTO implements FilterValidation {

    private Long id;
    private String cidade;
    private String estado;
    private String nome;
    private String pais;
    private String sigla;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPais() {
        return this.pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getSigla() {
        return this.sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public boolean validateForFindData() {
        if (this.id != null) {
            return true;
        }

        if (StringUtils.isNotBlank(this.estado)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.nome)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.cidade)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.pais)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.sigla)) {
            return true;
        }

        return false;
    }
}
