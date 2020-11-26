package br.com.mauda.seminario.cientificos.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.platform.commons.util.StringUtils;

import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;

public class InscricaoDTO implements FilterValidation {

    private Long id;
    private Date dataSeminario;
    private Boolean direitoMaterial;
    private String nomeEstudante;
    private String nome;
    private String tituloSeminario;
    private List<SituacaoInscricaoEnum> situacoes = new ArrayList<>();

    public List<SituacaoInscricaoEnum> getSituacoes() {
        return this.situacoes;
    }

    public void setSituacoes(List<SituacaoInscricaoEnum> situacoes) {
        this.situacoes = situacoes;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataSeminario() {
        return this.dataSeminario;
    }

    public void setDataSeminario(Date dataSeminario) {
        this.dataSeminario = dataSeminario;
    }

    public Boolean getDireitoMaterial() {
        return this.direitoMaterial;
    }

    public void setDireitoMaterial(Boolean direitoMaterial) {
        this.direitoMaterial = direitoMaterial;
    }

    public String getNomeEstudante() {
        return this.nomeEstudante;
    }

    public void setNomeEstudante(String nomeEstudante) {
        this.nomeEstudante = nomeEstudante;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTituloSeminario() {
        return this.tituloSeminario;
    }

    public void setTituloSeminario(String tituloSeminario) {
        this.tituloSeminario = tituloSeminario;
    }

    @Override
    public boolean validateForFindData() {
        if (this.id != null) {
            return true;
        }

        if (this.dataSeminario != null) {
            return true;
        }

        if (this.direitoMaterial != null) {
            return true;
        }

        if (StringUtils.isNotBlank(this.nomeEstudante)) {
            return true;
        }

        if (StringUtils.isNotBlank(this.tituloSeminario)) {
            return true;
        }

        if (!this.situacoes.isEmpty()) {
            return true;
        }

        if (StringUtils.isNotBlank(this.nome)) {
            return true;
        }

        return false;
    }

}
