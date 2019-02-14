package br.com.mauda.seminario.cientificos.junit;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import br.com.mauda.seminario.cientificos.junit.tests.TesteAcaoCheckInSobreInscricao;
import br.com.mauda.seminario.cientificos.junit.tests.TesteAcaoComprarSobreInscricao;
import br.com.mauda.seminario.cientificos.junit.tests.TesteAreaCientifica;
import br.com.mauda.seminario.cientificos.junit.tests.TesteCurso;
import br.com.mauda.seminario.cientificos.junit.tests.TesteEstudante;
import br.com.mauda.seminario.cientificos.junit.tests.TesteInstituicao;
import br.com.mauda.seminario.cientificos.junit.tests.TesteProfessor;
import br.com.mauda.seminario.cientificos.junit.tests.TesteSeminario;
import br.com.mauda.seminario.cientificos.junit.util.ErrorTestManager;

/**
 * Essa classe realizara os testes de uma so vez, em uma determinada ordem.<br/>
 * Para que tudo ocorra com sucesso, a base devera estar vazia<br/>
 * Os metodos no pacote Queries necessitam que o numero de instancias da classe X esteja igual ao numero descrito no Enum de testes da classe X.<br/>
 * Para limpar a base basta deletar os arquivos que se encontram na pasta banco e executar novamente o script do banco de dados.
 *
 * @author Mauda
 *
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
                DiscoverySelectors.selectClass(TesteAcaoCheckInSobreInscricao.class))
            .build();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request, listener);

        ErrorTestManager errorTestManager = new ErrorTestManager(listener.getSummary().getFailures());
        errorTestManager.printErrors();

        System.out.println("###################################################");
        System.out.println("Quantidade de Testes executados: \t\t" + listener.getSummary().getTestsStartedCount() / 2);
        System.out.println("Quantidade de Testes executados com falha: \t" + listener.getSummary().getTestsFailedCount() / 2);
        System.out.println("Quantidade de Testes executados com sucesso: \t" + listener.getSummary().getTestsSucceededCount() / 2);
        System.out.println("###################################################");

        if (!listener.getSummary().getFailures().isEmpty()) {
            fail("Falha no teste! Existem erros, por favor verifique no console!");
        }
    }
}