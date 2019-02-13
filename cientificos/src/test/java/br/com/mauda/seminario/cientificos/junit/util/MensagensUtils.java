package br.com.mauda.seminario.cientificos.junit.util;

import java.util.function.Supplier;

public final class MensagensUtils {

    private MensagensUtils() {
    }

    public static Supplier<String> getErrorMessage(String mensagem) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        String nomeClasse = stack[2].getClassName();
        String nomeMetodo = stack[2].getMethodName();
        int linha = stack[2].getLineNumber();

        return () -> String.format("Mensagem Erro: [%s]. Classe:[%s]. Metodo:[%s]. Linha:[%d] ", mensagem, nomeClasse, nomeMetodo, linha);
    }

}
