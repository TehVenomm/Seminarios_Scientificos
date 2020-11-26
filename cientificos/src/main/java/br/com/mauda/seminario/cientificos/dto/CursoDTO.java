package br.com.mauda.seminario.cientificos.dto;

import org.junit.platform.commons.util.StringUtils;

public class CursoDTO implements FilterValidation {

    private Long id;
    private Long idAreaCientifica;
    private String nome;
    private String nomeAreaCientifica;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAreaCientifica() {
        return this.idAreaCientifica;
    }

    public void setIdAreaCientifica(Long idAreaCientifica) {
        this.idAreaCientifica = idAreaCientifica;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeAreaCientifica() {
        return this.nomeAreaCientifica;
    }

    public void setNomeAreaCientifica(String nomeAreaCientifica) {
        this.nomeAreaCientifica = nomeAreaCientifica;
    }

    @Override
    public boolean validateForFindData() {
        if (this.id != null) {
            return true;
        }

        if (StringUtils.isNotBlank(this.nome)) {
            return true;
        }

        if (this.idAreaCientifica != null) {
            return true;
        }

        return StringUtils.isNotBlank(this.nomeAreaCientifica);
    }
}
