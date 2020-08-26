package br.com.mauda.seminario.cientificos.model.enums;

public enum SituacaoInscricaoEnum {

    DISPONIVEL(1),
    COMPRADO(2),
    CHECKIN(3);

    private int id;

    public int getId() {
        return this.id;
    }

    SituacaoInscricaoEnum(int id) {
        this.id = id;
    }

}