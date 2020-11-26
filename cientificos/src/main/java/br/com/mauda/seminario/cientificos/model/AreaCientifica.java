package br.com.mauda.seminario.cientificos.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import br.com.mauda.seminario.cientificos.exception.SeminariosCientificosException;

@Entity
@Table(name = "TB_AREA_CIENTIFICA")
public class AreaCientifica implements DataValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(mappedBy = "areaCientifica")
    private List<Curso> cursos = new ArrayList<>();

    private static String er0010 = "ER0010";

    public AreaCientifica() {
        super();
    }

    public void adicionarCurso(Curso curso) {
        this.cursos.add(curso);
    }

    public Boolean possuiCurso(Curso curso) {
        return this.cursos.contains(curso);
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

    public List<Curso> getCursos() {
        return this.cursos;
    }

    @Override
    public void validateForDataModification() {
        if (StringUtils.isBlank(this.nome) || this.nome.length() > 50) {
            throw new SeminariosCientificosException(er0010);
        }
    }

}