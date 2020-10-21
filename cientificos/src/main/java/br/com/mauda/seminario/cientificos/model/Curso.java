package br.com.mauda.seminario.cientificos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;

@Entity
@Table(name = "TB_CURSO")
public class Curso implements DataValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "ID_AREA_CIENTIFICA")
    private AreaCientifica areaCientifica;

    private static String er0003 = "ER0003";
    private static String er0020 = "ER0020";

    @SuppressWarnings("unused")
    private Curso() {
        super();
    }

    public Curso(AreaCientifica areaCientifica) {
        this.areaCientifica = areaCientifica;
        this.areaCientifica.adicionarCurso(this);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AreaCientifica getAreaCientifica() {
        return this.areaCientifica;
    }

    @Override
    public void validateForDataModification() {
        if (StringUtils.isBlank(this.nome) || this.nome.length() > 50) {
            throw new SeminariosCientificosException(er0020);
        }

        if (this.areaCientifica == null) {
            throw new SeminariosCientificosException(er0003);
        }

        this.areaCientifica.validateForDataModification();
    }
}
