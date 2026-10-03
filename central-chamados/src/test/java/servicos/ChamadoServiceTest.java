package servicos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import padroescomportamentais.observer.PainelChamados;
import padroescriacao.singleton.Parametros;
import static org.junit.jupiter.api.Assertions.*;

public class ChamadoServiceTest {

    ChamadoService chamados;

    @BeforeEach
    public void setUp() {
        Parametros.getInstance().setNomeSistema("Central de Chamados");
        chamados = new ChamadoService(new NotificacaoService(new PainelChamados()));
    }

    @Test
    public void deveCriarChamadoComDoisInteressados() {
        int numero = chamados.abrir("Acesso", "Ana", "Suporte");
        assertEquals("Aberto", chamados.buscar(numero).getNomeEstado());
        assertTrue(chamados.iniciarAtendimento(numero));
        assertEquals(2, chamados.notificacoes(numero).size());
        assertEquals("[Central de Chamados] Ana, Chamado 'Acesso': Aberto -> Em atendimento",
                chamados.notificacoes(numero).get(0));
    }

    @Test
    public void deveIsolarNotificacoesEntreChamados() {
        int a = chamados.abrir("Acesso", "Ana", "Suporte");
        int b = chamados.abrir("Impressora", "Bruno", "Equipe");
        chamados.iniciarAtendimento(a);
        assertEquals(2, chamados.notificacoes(a).size());
        assertTrue(chamados.notificacoes(b).isEmpty());
        assertEquals("Aberto", chamados.buscar(b).getNomeEstado());
    }

    @Test
    public void naoDeveCadastrarChamadoComInteressadoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> chamados.abrir("Acesso", "Ana", " "));
        assertTrue(chamados.listar().isEmpty());
        assertTrue(chamados.painel().isEmpty());
    }

    @Test
    public void naoDeveEncontrarChamadoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> chamados.buscar(42));
    }

    @Test
    public void naoDeveNotificarOperacaoRecusada() {
        int numero = chamados.abrir("Acesso", "Ana", "Suporte");
        assertFalse(chamados.fechar(numero));
        assertTrue(chamados.notificacoes(numero).isEmpty());
        assertTrue(chamados.historico(numero).isEmpty());
    }

    @Test
    public void deveAtualizarPainelPeloObserver() {
        int numero = chamados.abrir("Acesso", "Ana", "Suporte");
        chamados.cancelar(numero);
        assertEquals("Cancelado", chamados.painel().get(chamados.buscar(numero)));
    }

    @Test
    public void deveExecutarFluxoComReabertura() {
        int numero = chamados.abrir("Acesso", "Ana", "Suporte");
        assertTrue(chamados.iniciarAtendimento(numero));
        assertTrue(chamados.aguardarCliente(numero));
        assertTrue(chamados.retomarAtendimento(numero));
        assertTrue(chamados.resolver(numero));
        assertTrue(chamados.reabrir(numero));
        assertTrue(chamados.resolver(numero));
        assertTrue(chamados.fechar(numero));
        assertFalse(chamados.reabrir(numero));
        assertEquals(7, chamados.historico(numero).size());
        assertEquals(14, chamados.notificacoes(numero).size());
        assertEquals(2, chamados.ultimasNotificacoes(numero).size());
    }

    @Test
    public void naoDevePermitirAlteracaoExternaDosRegistros() {
        int numero = chamados.abrir("Acesso", "Ana", "Suporte");
        assertThrows(UnsupportedOperationException.class, () -> chamados.listar().clear());
        assertThrows(UnsupportedOperationException.class, () -> chamados.notificacoes(numero).clear());
        assertEquals(1, chamados.listar().size());
    }
}
