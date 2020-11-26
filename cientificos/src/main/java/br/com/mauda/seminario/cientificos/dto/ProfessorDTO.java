package br.com.mauda.seminario.cientificos.dto;

import org.junit.platform.commons.util.StringUtils;

public class ProfessorDTO implements FilterValidation {

    private Long id;
    private String cidade;
    private String email;
    private String estado;
    private String nome;
    private String nomeInstituicao;
    private String pais;
    private Double salario;
    private String telefone;

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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNomeInstituicao() {
        return this.nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public String getPais() {
        return this.pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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

    @Override
    public boolean validateForFindData() {
        if (this.id != null) {
            return true;
        }

        if (StringUtils.isNotBlank(this.cidade)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.email)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.estado)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.nome)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.nomeInstituicao)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.pais)) {
            return true;
        }

        if (this.salario != null) {
            return true;
        }

        if (StringUtils.isNotBlank(this.telefone)) {
            return true;
        }

        return false;
    }

}
