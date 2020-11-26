package br.com.mauda.seminario.cientificos.dto;

import org.junit.platform.commons.util.StringUtils;

public class AreaCientificaDTO implements FilterValidation {

    private String nome;
    private Long id;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean validateForFindData() {
        if (this.id != null) {
            return true;
        }

        return StringUtils.isNotBlank(this.nome);
    }
}