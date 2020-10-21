package br.com.mauda.seminario.cientificos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;

public class Seminario implements DataValidation {

    private Long id;
    private String titulo;
    private Date data;
    private String descricao;
    private Boolean mesaRedonda;
    private Integer qtdInscricoes;
    private List<Professor> professores = new ArrayList<>();
    private List<AreaCientifica> areasCientificas = new ArrayList<>();
    private List<Inscricao> inscricoes = new ArrayList<>();

    private static String er0003 = "ER0003";
    private static String er0070 = "ER0070";
    private static String er0071 = "ER0071";
    private static String er0072 = "ER0072";
    private static String er0073 = "ER0073";
    private static String er0074 = "ER0074";
    private static String er0075 = "ER0075";
    private static String er0076 = "ER0076";

    public Seminario(AreaCientifica areaCientifica, Professor professor, Integer qtdInscricoes) {
        this.areasCientificas.add(areaCientifica);
        this.professores.add(professor);
        professor.adicionarSeminario(this);
        this.qtdInscricoes = qtdInscricoes;

        for (long i = 0; i < qtdInscricoes; i++) {
            new Inscricao(this);
        }
    }

    public void adicionarAreaCientifica(AreaCientifica area) {
        this.areasCientificas.add(area);
    }

    public void adicionarInscricao(Inscricao inscricao) {
        this.inscricoes.add(inscricao);
    }

    public void adicionarProfessor(Professor professor) {
        this.professores.add(professor);
    }

    public Boolean possuiAreaCientifica(AreaCientifica area) {
        return this.areasCientificas.contains(area);
    }

    public Boolean possuiInscricao(Inscricao inscricao) {
        return this.inscricoes.contains(inscricao);
    }

    public Boolean possuiProfessor(Professor professor) {
        return this.professores.contains(professor);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getMesaRedonda() {
        return this.mesaRedonda;
    }

    public void setMesaRedonda(Boolean mesaRedonda) {
        this.mesaRedonda = mesaRedonda;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getQtdInscricoes() {
        return this.qtdInscricoes;
    }

    // Fui previamente instruido a retirar este metodo pois era redundante, mas os novos testes estao utilizando o setQtdInscricoes agora
    // Não sei se foi erro meu ou do professor, mas decidi resolver o erro que o eclipse estava acusando no projeto.
    public void setQtdInscricoes(Integer qtdInscricoes) {
        this.qtdInscricoes = qtdInscricoes;
    }

    public List<Inscricao> getInscricoes() {
        return this.inscricoes;
    }

    public List<Professor> getProfessores() {
        return this.professores;
    }

    public List<AreaCientifica> getAreasCientificas() {
        return this.areasCientificas;
    }

    private void validateAreaCientifica() {
        if (this.areasCientificas == null || this.areasCientificas.isEmpty()) {
            throw new SeminariosCientificosException(er0076);
        }

        for (AreaCientifica item : this.areasCientificas) {
            if (item == null) {
                throw new SeminariosCientificosException(er0003);
            }

            item.validateForDataModification();
        }
    }

    private void validateProfessores() {
        if (this.professores == null || this.professores.isEmpty()) {
            throw new SeminariosCientificosException(er0075);
        }

        for (Professor item : this.professores) {
            if (item == null) {
                throw new SeminariosCientificosException(er0003);
            }

            item.validateForDataModification();
        }
    }

    @Override
    public void validateForDataModification() {
        if (this.data == null || this.data.before(new Date())) {
            throw new SeminariosCientificosException(er0070);
        }

        if (StringUtils.isBlank(this.descricao) || this.descricao.length() > 200) {
            throw new SeminariosCientificosException(er0071);
        }

        if (StringUtils.isBlank(this.titulo) || this.titulo.length() > 50) {
            throw new SeminariosCientificosException(er0072);
        }

        if (this.mesaRedonda == null) {
            throw new SeminariosCientificosException(er0073);
        }

        if (this.qtdInscricoes == null || this.qtdInscricoes <= 0) {
            throw new SeminariosCientificosException(er0074);
        }

        this.validateAreaCientifica();

        this.validateProfessores();
    }
}
