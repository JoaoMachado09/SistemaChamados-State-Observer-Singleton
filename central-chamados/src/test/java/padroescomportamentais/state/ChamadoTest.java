package padroescomportamentais.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChamadoTest {

    Chamado chamado;

    @BeforeEach
    public void setUp() {
        chamado = new Chamado("Falha no acesso");
    }

    // Chamado Aberto

    @Test
    public void deveIniciarAtendimentoChamadoAberto() {
        chamado.setEstado(ChamadoEstadoAberto.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertTrue(chamado.iniciarAtendimento());
        assertEquals(ChamadoEstadoEmAtendimento.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior + 1, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveAguardarClienteChamadoAberto() {
        chamado.setEstado(ChamadoEstadoAberto.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.aguardarCliente());
        assertEquals(ChamadoEstadoAberto.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveRetomarAtendimentoChamadoAberto() {
        chamado.setEstado(ChamadoEstadoAberto.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.retomarAtendimento());
        assertEquals(ChamadoEstadoAberto.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveResolverChamadoAberto() {
        chamado.setEstado(ChamadoEstadoAberto.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.resolver());
        assertEquals(ChamadoEstadoAberto.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveReabrirChamadoAberto() {
        chamado.setEstado(ChamadoEstadoAberto.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.reabrir());
        assertEquals(ChamadoEstadoAberto.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveFecharChamadoAberto() {
        chamado.setEstado(ChamadoEstadoAberto.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.fechar());
        assertEquals(ChamadoEstadoAberto.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void deveCancelarChamadoAberto() {
        chamado.setEstado(ChamadoEstadoAberto.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertTrue(chamado.cancelar());
        assertEquals(ChamadoEstadoCancelado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior + 1, chamado.getHistorico().size());
    }

    // Chamado Em atendimento

    @Test
    public void naoDeveIniciarAtendimentoChamadoEmAtendimento() {
        chamado.setEstado(ChamadoEstadoEmAtendimento.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.iniciarAtendimento());
        assertEquals(ChamadoEstadoEmAtendimento.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void deveAguardarClienteChamadoEmAtendimento() {
        chamado.setEstado(ChamadoEstadoEmAtendimento.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertTrue(chamado.aguardarCliente());
        assertEquals(ChamadoEstadoAguardandoCliente.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior + 1, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveRetomarAtendimentoChamadoEmAtendimento() {
        chamado.setEstado(ChamadoEstadoEmAtendimento.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.retomarAtendimento());
        assertEquals(ChamadoEstadoEmAtendimento.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void deveResolverChamadoEmAtendimento() {
        chamado.setEstado(ChamadoEstadoEmAtendimento.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertTrue(chamado.resolver());
        assertEquals(ChamadoEstadoResolvido.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior + 1, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveReabrirChamadoEmAtendimento() {
        chamado.setEstado(ChamadoEstadoEmAtendimento.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.reabrir());
        assertEquals(ChamadoEstadoEmAtendimento.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveFecharChamadoEmAtendimento() {
        chamado.setEstado(ChamadoEstadoEmAtendimento.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.fechar());
        assertEquals(ChamadoEstadoEmAtendimento.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void deveCancelarChamadoEmAtendimento() {
        chamado.setEstado(ChamadoEstadoEmAtendimento.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertTrue(chamado.cancelar());
        assertEquals(ChamadoEstadoCancelado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior + 1, chamado.getHistorico().size());
    }

    // Chamado Aguardando cliente

    @Test
    public void naoDeveIniciarAtendimentoChamadoAguardandoCliente() {
        chamado.setEstado(ChamadoEstadoAguardandoCliente.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.iniciarAtendimento());
        assertEquals(ChamadoEstadoAguardandoCliente.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveAguardarClienteChamadoAguardandoCliente() {
        chamado.setEstado(ChamadoEstadoAguardandoCliente.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.aguardarCliente());
        assertEquals(ChamadoEstadoAguardandoCliente.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void deveRetomarAtendimentoChamadoAguardandoCliente() {
        chamado.setEstado(ChamadoEstadoAguardandoCliente.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertTrue(chamado.retomarAtendimento());
        assertEquals(ChamadoEstadoEmAtendimento.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior + 1, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveResolverChamadoAguardandoCliente() {
        chamado.setEstado(ChamadoEstadoAguardandoCliente.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.resolver());
        assertEquals(ChamadoEstadoAguardandoCliente.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveReabrirChamadoAguardandoCliente() {
        chamado.setEstado(ChamadoEstadoAguardandoCliente.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.reabrir());
        assertEquals(ChamadoEstadoAguardandoCliente.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveFecharChamadoAguardandoCliente() {
        chamado.setEstado(ChamadoEstadoAguardandoCliente.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.fechar());
        assertEquals(ChamadoEstadoAguardandoCliente.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void deveCancelarChamadoAguardandoCliente() {
        chamado.setEstado(ChamadoEstadoAguardandoCliente.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertTrue(chamado.cancelar());
        assertEquals(ChamadoEstadoCancelado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior + 1, chamado.getHistorico().size());
    }

    // Chamado Resolvido

    @Test
    public void naoDeveIniciarAtendimentoChamadoResolvido() {
        chamado.setEstado(ChamadoEstadoResolvido.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.iniciarAtendimento());
        assertEquals(ChamadoEstadoResolvido.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveAguardarClienteChamadoResolvido() {
        chamado.setEstado(ChamadoEstadoResolvido.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.aguardarCliente());
        assertEquals(ChamadoEstadoResolvido.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveRetomarAtendimentoChamadoResolvido() {
        chamado.setEstado(ChamadoEstadoResolvido.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.retomarAtendimento());
        assertEquals(ChamadoEstadoResolvido.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveResolverChamadoResolvido() {
        chamado.setEstado(ChamadoEstadoResolvido.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.resolver());
        assertEquals(ChamadoEstadoResolvido.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void deveReabrirChamadoResolvido() {
        chamado.setEstado(ChamadoEstadoResolvido.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertTrue(chamado.reabrir());
        assertEquals(ChamadoEstadoEmAtendimento.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior + 1, chamado.getHistorico().size());
    }

    @Test
    public void deveFecharChamadoResolvido() {
        chamado.setEstado(ChamadoEstadoResolvido.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertTrue(chamado.fechar());
        assertEquals(ChamadoEstadoFechado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior + 1, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveCancelarChamadoResolvido() {
        chamado.setEstado(ChamadoEstadoResolvido.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.cancelar());
        assertEquals(ChamadoEstadoResolvido.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    // Chamado Fechado

    @Test
    public void naoDeveIniciarAtendimentoChamadoFechado() {
        chamado.setEstado(ChamadoEstadoFechado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.iniciarAtendimento());
        assertEquals(ChamadoEstadoFechado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveAguardarClienteChamadoFechado() {
        chamado.setEstado(ChamadoEstadoFechado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.aguardarCliente());
        assertEquals(ChamadoEstadoFechado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveRetomarAtendimentoChamadoFechado() {
        chamado.setEstado(ChamadoEstadoFechado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.retomarAtendimento());
        assertEquals(ChamadoEstadoFechado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveResolverChamadoFechado() {
        chamado.setEstado(ChamadoEstadoFechado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.resolver());
        assertEquals(ChamadoEstadoFechado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveReabrirChamadoFechado() {
        chamado.setEstado(ChamadoEstadoFechado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.reabrir());
        assertEquals(ChamadoEstadoFechado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveFecharChamadoFechado() {
        chamado.setEstado(ChamadoEstadoFechado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.fechar());
        assertEquals(ChamadoEstadoFechado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveCancelarChamadoFechado() {
        chamado.setEstado(ChamadoEstadoFechado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.cancelar());
        assertEquals(ChamadoEstadoFechado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    // Chamado Cancelado

    @Test
    public void naoDeveIniciarAtendimentoChamadoCancelado() {
        chamado.setEstado(ChamadoEstadoCancelado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.iniciarAtendimento());
        assertEquals(ChamadoEstadoCancelado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveAguardarClienteChamadoCancelado() {
        chamado.setEstado(ChamadoEstadoCancelado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.aguardarCliente());
        assertEquals(ChamadoEstadoCancelado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveRetomarAtendimentoChamadoCancelado() {
        chamado.setEstado(ChamadoEstadoCancelado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.retomarAtendimento());
        assertEquals(ChamadoEstadoCancelado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveResolverChamadoCancelado() {
        chamado.setEstado(ChamadoEstadoCancelado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.resolver());
        assertEquals(ChamadoEstadoCancelado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveReabrirChamadoCancelado() {
        chamado.setEstado(ChamadoEstadoCancelado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.reabrir());
        assertEquals(ChamadoEstadoCancelado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveFecharChamadoCancelado() {
        chamado.setEstado(ChamadoEstadoCancelado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.fechar());
        assertEquals(ChamadoEstadoCancelado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveCancelarChamadoCancelado() {
        chamado.setEstado(ChamadoEstadoCancelado.getInstance());
        int totalAnterior = chamado.getHistorico().size();
        assertFalse(chamado.cancelar());
        assertEquals(ChamadoEstadoCancelado.getInstance(), chamado.getEstado());
        assertEquals(totalAnterior, chamado.getHistorico().size());
    }

    @Test
    public void deveIniciarAberto() {
        assertEquals("Aberto", chamado.getNomeEstado());
        assertEquals(0, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveAceitarTituloVazio() {
        try {
            chamado.setTitulo("  ");
            fail("Deveria rejeitar título vazio");
        } catch (IllegalArgumentException erro) {
            assertEquals("Título deve ser informado", erro.getMessage());
        }
    }

    @Test
    public void naoDeveAceitarEstadoNulo() {
        try {
            chamado.setEstado(null);
            fail("Deveria rejeitar estado nulo");
        } catch (IllegalArgumentException erro) {
            assertEquals("Estado não pode ser nulo", erro.getMessage());
        }
        assertEquals("Aberto", chamado.getNomeEstado());
    }

    @Test
    public void deveIsolarChamadosComEstadoCompartilhado() {
        Chamado outro = new Chamado("Impressora");
        assertSame(chamado.getEstado(), outro.getEstado());
        chamado.iniciarAtendimento();
        assertEquals("Aberto", outro.getNomeEstado());
        assertEquals(0, outro.getHistorico().size());
    }

    @Test
    public void naoDevePermitirAlteracaoExternaDoHistorico() {
        chamado.iniciarAtendimento();
        try {
            chamado.getHistorico().clear();
            fail("Histórico deveria ser imutável externamente");
        } catch (UnsupportedOperationException erro) {
            assertEquals(1, chamado.getHistorico().size());
        }
    }
}
