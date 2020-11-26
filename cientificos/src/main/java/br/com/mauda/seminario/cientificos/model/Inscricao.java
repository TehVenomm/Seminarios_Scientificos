package br.com.mauda.seminario.cientificos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.model.enums.SituacaoInscricaoEnum;

@Entity
@Table(name = "TB_INSCRICAO")
public class Inscricao implements DataValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DIREITO_MATERIAL")
    private Boolean direitoMaterial;

    @ManyToOne
    @JoinColumn(name = "ID_ESTUDANTE")
    private Estudante estudante;

    @ManyToOne
    @JoinColumn(name = "ID_SEMINARIO")
    private Seminario seminario;

    @Enumerated(EnumType.STRING)
    private SituacaoInscricaoEnum situacao;

    private static String er0003 = "ER0003";
    private static String er0040 = "ER0040";
    private static String er0041 = "ER0041";

    @SuppressWarnings("unused")
    private Inscricao() {
        super();
    }

    public Inscricao(Seminario seminario) {
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

    public SituacaoInscricaoEnum getSituacao() {
        return this.situacao;
    }

    public Seminario getSeminario() {
        return this.seminario;
    }

    @Override
    public void validateForDataModification() {
        if (this.situacao == null) {
            throw new SeminariosCientificosException(er0040);
        }

        if (this.seminario == null) {
            throw new SeminariosCientificosException(er0003);
        }

        if (!SituacaoInscricaoEnum.DISPONIVEL.equals(this.situacao)) {
            if (this.direitoMaterial == null) {
                throw new SeminariosCientificosException(er0041);
            }

            if (this.estudante == null) {
                throw new SeminariosCientificosException(er0003);
            }

            this.estudante.validateForDataModification();
        }

        this.seminario.validateForDataModification();
    }
}
