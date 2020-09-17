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

    @Override
    public void validateForDataModification() {
        if (this.data == null || this.data.before(new Date())) {
            throw new SeminariosCientificosException("ER0070");
        }

        if (StringUtils.isBlank(this.descricao) || this.descricao.length() > 200) {
            throw new SeminariosCientificosException("ER0071");
        }

        if (StringUtils.isBlank(this.titulo) || this.titulo.length() > 50) {
            throw new SeminariosCientificosException("ER0072");
        }

        if (this.mesaRedonda == null) {
            throw new SeminariosCientificosException("ER0073");
        }

        if (this.qtdInscricoes == null || this.qtdInscricoes == 0 || this.qtdInscricoes < 0) {
            throw new SeminariosCientificosException("ER0074");
        }

        if (this.professores == null || this.professores.isEmpty()) {
            throw new SeminariosCientificosException("ER0075");
        }

        if (this.areasCientificas == null || this.areasCientificas.isEmpty()) {
            throw new SeminariosCientificosException("ER0076");
        }

        for (AreaCientifica item : this.areasCientificas) {
            if (item == null) {
                throw new SeminariosCientificosException("ER0003");
            }

            item.validateForDataModification();
        }

        for (Professor item : this.professores) {
            if (item == null) {
                throw new SeminariosCientificosException("ER0003");
            }

            item.validateForDataModification();
        }
    }
}
