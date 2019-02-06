package br.com.mauda.seminario.cientificos.junit.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

public class ErrorTestManager {

    private Map<String, ErrorTest> errors = new HashMap<>();

    public ErrorTestManager(List<Failure> failure) {
        for (Failure fail : failure) {
            String messageError = fail.getException().getMessage();
            ErrorTest errorTest = this.errors.get(messageError);
            if (errorTest == null) {
                this.errors.put(messageError, new ErrorTest(fail));
            } else {
                errorTest.addTest(fail.getTestIdentifier().getDisplayName());
            }
        }
    }

    public void printErrors() {
        for (ErrorTest errorTest : this.errors.values()) {
            System.out.println("#######################################");
            System.out.println("Falha no(s) Teste(s): \t" + errorTest.getTests());
            System.out.println("Informacoes detalhadas: " + errorTest.getDetails());
            System.out.println("Mensagem de Erro: " + errorTest.getErrorMessage());
            System.out.println("stackTrace: \n" + errorTest.getCause());
            System.out.println("#######################################");
        }
    }
}

class ErrorTest {

    private String errorMessage, details;
    private Collection<String> tests = new LinkedHashSet<>();
    private Throwable cause;

    protected ErrorTest(Failure fail) {
        this.addTest(fail.getTestIdentifier().getDisplayName());
        this.details = fail.getTestIdentifier().getSource().toString();
        this.errorMessage = fail.getException().getMessage();
        this.cause = fail.getException().getCause();
    }

    public void addTest(String test) {
        this.tests.add(test);
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getDetails() {
        return this.details;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public String getTests() {
        return StringUtils.join(this.tests, ",\n\t\t\t ");
    }
}
