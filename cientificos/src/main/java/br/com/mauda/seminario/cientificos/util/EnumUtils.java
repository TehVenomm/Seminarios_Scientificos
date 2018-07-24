package br.com.mauda.seminario.cientificos.util;

import java.util.Random;

public interface EnumUtils {

    /**
     * Metodo que retorna uma instancia do enum de forma randomizada
     *
     * @param enumm
     * @return
     */
    static <E extends Enum<E>> E getInstanceRandomly(Class<E> enumm) {
        E[] array = enumm.getEnumConstants();
        return array[new Random().nextInt(array.length)];
    }

}
