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

import br.com.mauda.seminario.cientificos.junit.tests.TesteAcaoCancelarCompraSobreInscricao;
import br.com.mauda.seminario.cientificos.junit.tests.TesteAcaoCheckInSobreInscricao;
import br.com.mauda.seminario.cientificos.junit.tests.TesteAcaoComprarSobreInscricao;
import br.com.mauda.seminario.cientificos.junit.tests.TesteAreaCientifica;
import br.com.mauda.seminario.cientificos.junit.tests.TesteCurso;
import br.com.mauda.seminario.cientificos.junit.tests.TesteEstudante;
import br.com.mauda.seminario.cientificos.junit.tests.TesteInstituicao;
import br.com.mauda.seminario.cientificos.junit.tests.TesteProfessor;
import br.com.mauda.seminario.cientificos.junit.tests.TesteSeminario;
import br.com.mauda.seminario.cientificos.junit.tests.queries.TesteAreaCientificaQueries;
import br.com.mauda.seminario.cientificos.junit.tests.queries.TesteCursoQueries;
import br.com.mauda.seminario.cientificos.junit.tests.queries.TesteEstudanteQueries;
import br.com.mauda.seminario.cientificos.junit.tests.queries.TesteInscricaoQueries;
import br.com.mauda.seminario.cientificos.junit.tests.queries.TesteInstituicaoQueries;
import br.com.mauda.seminario.cientificos.junit.tests.queries.TesteProfessorQueries;
import br.com.mauda.seminario.cientificos.junit.tests.queries.TesteSeminarioQueries;
import br.com.mauda.seminario.cientificos.junit.util.ErrorTestManager;

/**
 * Essa classe realizara os testes de uma so vez, em uma determinada ordem.<br/>
 * Para que tudo ocorra com sucesso, a base devera estar vazia<br/>
 * Os metodos no pacote Queries necessitam que o numero de instancias da classe X esteja igual ao numero descrito no Enum de testes da classe X.<br/>
 * Para limpar a base basta deletar os arquivos que se encontram na pasta banco e executar novamente o script do banco de dados.
 *
 * @author Mauda
 */

public class RunnerTestes {

    @Test
    public void execucao() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(
                DiscoverySelectors.selectClass(TesteAreaCientifica.class),
                DiscoverySelectors.selectClass(TesteCurso.class),
                DiscoverySelectors.selectClass(TesteInstituicao.class),
                DiscoverySelectors.selectClass(TesteEstudante.class),
                DiscoverySelectors.selectClass(TesteProfessor.class),
                DiscoverySelectors.selectClass(TesteSeminario.class),
                DiscoverySelectors.selectClass(TesteAcaoComprarSobreInscricao.class),
                DiscoverySelectors.selectClass(TesteAcaoCancelarCompraSobreInscricao.class),
                DiscoverySelectors.selectClass(TesteAcaoCheckInSobreInscricao.class),
                DiscoverySelectors.selectClass(TesteAreaCientificaQueries.class),
                DiscoverySelectors.selectClass(TesteCursoQueries.class),
                DiscoverySelectors.selectClass(TesteInstituicaoQueries.class),
                DiscoverySelectors.selectClass(TesteEstudanteQueries.class),
                DiscoverySelectors.selectClass(TesteProfessorQueries.class),
                DiscoverySelectors.selectClass(TesteSeminarioQueries.class),
                DiscoverySelectors.selectClass(TesteInscricaoQueries.class))
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