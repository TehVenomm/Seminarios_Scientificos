package br.com.mauda.seminario.cientificos.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;

@Entity
@Table(name = "TB_INSTITUICAO")
public class Instituicao implements Serializable, DataValidation {

    private static final long serialVersionUID = 997084310847650620L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sigla;
    private String cidade;
    private String estado;
    private String pais;

    private static String er0050 = "ER0050";
    private static String er0051 = "ER0051";
    private static String er0052 = "ER0052";
    private static String er0053 = "ER0053";
    private static String er0054 = "ER0054";

    public Instituicao() {
        super();
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

    public String getSigla() {
        return this.sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
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

    public String getPais() {
        return this.pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Instituicao other = (Instituicao) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public void validateForDataModification() {
        if (StringUtils.isBlank(this.cidade) || this.cidade.length() > 50) {
            throw new SeminariosCientificosException(er0050);
        }

        if (StringUtils.isBlank(this.estado) || this.estado.length() > 50) {
            throw new SeminariosCientificosException(er0051);
        }

        if (StringUtils.isBlank(this.nome) || this.nome.length() > 100) {
            throw new SeminariosCientificosException(er0052);
        }

        if (StringUtils.isBlank(this.pais) || this.pais.length() > 50) {
            throw new SeminariosCientificosException(er0053);
        }

        if (StringUtils.isBlank(this.sigla) || this.sigla.length() > 10) {
            throw new SeminariosCientificosException(er0054);
        }
    }
}