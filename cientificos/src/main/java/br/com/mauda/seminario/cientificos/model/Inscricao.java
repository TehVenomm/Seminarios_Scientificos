package br.com.mauda.seminario.cientificos.model;

import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;

public class Inscricao {

    private Long id;
    private Boolean direitoMaterial;
    private Estudante estudante;
    private Seminario seminario;
    private SituacaoInscricaoEnum situacaoInscricao;

    public Inscricao(Long id, Boolean direitoMaterial, Seminario seminario, SituacaoInscricaoEnum situacaoInscricao) {
        this.id = id;
        this.direitoMaterial = direitoMaterial;
        this.seminario = seminario;
        this.seminario.adicionarInscricao(this);
        this.situacaoInscricao = situacaoInscricao;
    }

    public void comprar(Estudante estudante, Boolean direitoMaterial) {
        this.estudante = estudante;
        this.direitoMaterial = direitoMaterial;
        this.situacaoInscricao = SituacaoInscricaoEnum.COMPRADO;
    }

    public void cancelarCompra() {
        this.situacaoInscricao = SituacaoInscricaoEnum.DISPONIVEL;
    }

    public void realizarCheckIn() {
        this.situacaoInscricao = SituacaoInscricaoEnum.CHECKIN;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDireitoMaterial() {
        return this.direitoMaterial;
    }

    public void setDireitoMaterial(Boolean direitoMaterial) {
        this.direitoMaterial = direitoMaterial;
    }

    public Estudante getEstudante() {
        return this.estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

}
