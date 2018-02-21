package br.com.mauda.seminario.cientificos.junit.massa;

public enum MassaInstituicao {

    UEL("UEL", "Universidade Estadual de Londrina", "Londrina", "PR", "Brasil"),
    UEM("UEM", "Universidade Estadual de Maringa", "Maringa", "PR", "Brasil"),
    UFPR("UFPR", "Universidade Federal do Parana", "Curitiba", "PR", "Brasil"),
    UEPG("UEPG", "Universidade Estadual de Ponta Grossa", "Ponta Grossa", "PR", "Brasil"),
    UTFPR_PATO_BRANCO("UTFPR-PB", "Universidade Tecnologica Federal do Parana de Pato Branco", "Pato Branco", "PR", "Brasil"),
    UTFPR_TOLEDO("UTFPR-T", "Universidade Tecnologica Federal do Parana de Toledo", "Toledo", "PR", "Brasil");

    private String cidade;
    private String estado;
    private String nome;
    private String pais;
    private String sigla;

    private MassaInstituicao(String sigla, String nome, String cidade, String estado, String pais) {
        this.nome = nome;
        this.sigla = sigla;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    public String getCidade() {
        return this.cidade;
    }

    public String getEstado() {
        return this.estado;
    }

    public String getNome() {
        return this.nome;
    }

    public String getPais() {
        return this.pais;
    }

    public String getSigla() {
        return this.sigla;
    }
}