package br.com.mauda.seminario.cientificos.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;
import br.com.mauda.seminario.cientificos.util.EmailUtils;

public class Estudante implements DataValidation {

    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private Instituicao instituicao;
    private List<Inscricao> inscricoes = new ArrayList<>();

    private static String er0003 = "ER0003";
    private static String er0030 = "ER0030";
    private static String er0031 = "ER0031";
    private static String er0032 = "ER0032";

    public Estudante(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public void adicionarInscricao(Inscricao inscricao) {
        this.inscricoes.add(inscricao);
    }

    public Boolean possuiInscricao(Inscricao inscricao) {
        return this.inscricoes.contains(inscricao);
    }

    public void removerInscricao(Inscricao inscricao) {
        this.inscricoes.remove(inscricao);
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

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instituicao getInstituicao() {
        return this.instituicao;
    }

    public List<Inscricao> getInscricoes() {
        return this.inscricoes;
    }

    @Override
    public void validateForDataModification() {
        if (StringUtils.isBlank(this.email) || this.email.length() > 50) {
            throw new SeminariosCientificosException(er0030);
        }

        if (!this.email.matches(EmailUtils.EMAIL_PATTERN)) { // Regex...
            throw new SeminariosCientificosException(er0030);
        }

        if (StringUtils.isBlank(this.nome) || this.nome.length() > 50) {
            throw new SeminariosCientificosException(er0031);
        }

        if (StringUtils.isBlank(this.telefone) || this.telefone.length() > 15) {
            throw new SeminariosCientificosException(er0032);
        }

        if (this.instituicao == null) {
            throw new SeminariosCientificosException(er0003);
        }

        this.instituicao.validateForDataModification();
    }
}
