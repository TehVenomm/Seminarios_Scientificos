package br.com.mauda.seminario.cientificos.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.util.EmailUtils;

@Entity
@Table(name = "TB_PROFESSOR")
public class Professor implements DataValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nome;
    private Double salario;
    private String telefone;

    @OneToOne
    @JoinColumn(name = "ID_INSTITUICAO")
    private Instituicao instituicao;

    @ManyToMany(mappedBy = "professores")
    private List<Seminario> seminarios = new ArrayList<>();

    private static String er0003 = "ER0003";
    private static String er0060 = "ER0060";
    private static String er0061 = "ER0061";
    private static String er0062 = "ER0062";
    private static String er0063 = "ER0063";

    private Professor() {
    }

    public Professor(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public void adicionarSeminario(Seminario seminario) {
        this.seminarios.add(seminario);
    }

    public Boolean possuiSeminario(Seminario seminario) {
        return this.seminarios.contains(seminario);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Instituicao getInstituicao() {
        return this.instituicao;
    }

    @ManyToMany(mappedBy = "professores")
    public List<Seminario> getSeminarios() {
        return this.seminarios;
    }

    @Override
    public void validateForDataModification() {
        if (StringUtils.isBlank(this.email) || this.email.length() > 50) {
            throw new SeminariosCientificosException(er0060);
        }
        if (!this.email.matches(EmailUtils.EMAIL_PATTERN)) { // Regex...
            throw new SeminariosCientificosException(er0060);
        }

        if (StringUtils.isBlank(this.nome) || this.nome.length() > 50) {
            throw new SeminariosCientificosException(er0061);
        }

        if (StringUtils.isBlank(this.telefone) || this.telefone.length() > 15) {
            throw new SeminariosCientificosException(er0062);
        }

        if (this.salario == null || this.salario <= 0) {
            throw new SeminariosCientificosException(er0063);
        }

        if (this.instituicao == null) {
            throw new SeminariosCientificosException(er0003);
        }

        this.instituicao.validateForDataModification();
    }
}
