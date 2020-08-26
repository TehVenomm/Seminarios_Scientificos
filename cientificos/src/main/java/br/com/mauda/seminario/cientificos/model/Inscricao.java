package br.com.mauda.seminario.cientificos.model;

import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;

public class Inscricao {

    private Long id;
    private Boolean direitoMaterial;
    private Estudante estudante;
    private Seminario seminario;
    private SituacaoInscricaoEnum situacao;

    public Inscricao(Long id, Boolean direitoMaterial, Estudante estudante, Seminario seminario, SituacaoInscricaoEnum situacao) {
        this.id = id;
        this.direitoMaterial = direitoMaterial;
        this.estudante = estudante;
        this.estudante.adicionarInscricao(this);
        this.seminario = seminario;
        this.situacao = situacao;
    }

    public Inscricao(Long id, Boolean direitoMaterial, Seminario seminario) {
        this.id = id;
        this.direitoMaterial = direitoMaterial;
        this.seminario = seminario;
        this.seminario.adicionarInscricao(this);
        this.situacao = SituacaoInscricaoEnum.DISPONIVEL;
    }

    public Inscricao(Long id, Seminario seminario) {
        this.id = id;
        this.seminario = seminario;
        this.seminario.adicionarInscricao(this);
        this.situacao = SituacaoInscricaoEnum.DISPONIVEL;
    }

    public void comprar(Estudante estudante, Boolean direitoMaterial) {
        this.situacao = SituacaoInscricaoEnum.COMPRADO;
        this.estudante = estudante;
        this.estudante.adicionarInscricao(this);
        this.direitoMaterial = direitoMaterial;
    }

    public void cancelarCompra() {
        this.situacao = SituacaoInscricaoEnum.DISPONIVEL;
        this.estudante.removerInscricao(this);
        this.estudante = null;
        this.direitoMaterial = null;
    }

    public void realizarCheckIn() {
        this.situacao = SituacaoInscricaoEnum.CHECKIN;
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

    public SituacaoInscricaoEnum getSituacao() {
        return this.situacao;
    }

    public Seminario getSeminario() {
        return this.seminario;
    }
}
