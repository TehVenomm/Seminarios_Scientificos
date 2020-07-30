package br.com.mauda.seminario.cientificos.junit;

import static br.com.mauda.seminario.cientificos.junit.util.AssertionsMauda.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

import br.com.mauda.seminario.cientificos.junit.util.ErrorTestManager;

/**
 * Essa classe realizara os testes de uma so vez, em uma determinada ordem.<br/>
 * Para que tudo ocorra com sucesso, a base devera estar vazia<br/>
 * Os metodos no pacote Queries necessitam que o numero de instancias da classe X esteja igual ao numero descrito no Enum de testes da classe X.<br/>
 * Para limpar a base basta deletar os arquivos que se encontram na pasta banco e executar novamente o script do banco de dados.
 *
 * @author Mauda
 */

class RunnerTestes {

    static final String TESTE_PACKAGE = "br.com.mauda.seminario.cientificos.junit.tests.";

    @Test
    void execucao() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteAreaCientifica"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteCurso"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteInstituicao"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteEstudante"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteProfessor"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteSeminario"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteAcaoComprarSobreInscricao"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteAcaoCancelarCompraSobreInscricao"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteAcaoCheckInSobreInscricao"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteAreaCientificaQueries"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteCursoQueries"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteInstituicaoQueries"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteEstudanteQueries"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteProfessorQueries"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteSeminarioQueries"),
                DiscoverySelectors.selectClass(TESTE_PACKAGE + "TesteInscricaoQueries"))
            .build();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request, listener);

        List<Failure> falhas = listener.getSummary().getFailures();
        ErrorTestManager errorTestManager = new ErrorTestManager(falhas);
        errorTestManager.printErrors();
        errorTestManager.finalReport(listener);

        if (!falhas.isEmpty()) {
            fail("Falha no teste! Existem erros, por favor verifique no console!");
        }
    }
}