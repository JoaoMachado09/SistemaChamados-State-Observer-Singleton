package aplicacao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import padroescomportamentais.observer.PainelChamados;
import padroescriacao.singleton.Parametros;
import servicos.ChamadoService;
import servicos.NotificacaoService;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class, properties = "app.console.enabled=false")
public class SpringIntegracaoTest {

    @Autowired
    ChamadoService chamados;

    @Autowired
    NotificacaoService notificacoes;

    @Autowired
    PainelChamados painel;

    @Autowired
    ApplicationContext contexto;

    @Test
    public void deveInjetarServicosEPreservarSingleton() {
        assertSame(chamados, contexto.getBean(ChamadoService.class));
        assertSame(notificacoes, contexto.getBean(NotificacaoService.class));
        assertSame(Parametros.getInstance(), contexto.getBean(Parametros.class));
        assertFalse(contexto.containsBean("consoleAplicacao"));
    }

    @Test
    public void deveIntegrarSpringStateObserverESingleton() {
        Parametros.getInstance().setNomeSistema("Central de Chamados");
        int numero = chamados.abrir("Teste Spring", "Ana", "Equipe");
        assertTrue(chamados.iniciarAtendimento(numero));
        assertEquals("Em atendimento", painel.getEstados().get(chamados.buscar(numero)));
        assertEquals(2, notificacoes.listar(chamados.buscar(numero)).size());
        assertEquals("[Central de Chamados] Ana, Chamado 'Teste Spring': Aberto -> Em atendimento",
                chamados.notificacoes(numero).get(0));
    }
}
