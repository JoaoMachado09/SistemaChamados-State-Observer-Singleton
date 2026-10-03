package padroescomportamentais.observer;

import padroescomportamentais.state.Chamado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PainelChamadosTest {

    @Test
    public void deveMostrarEstadoInicial() {
        Chamado chamado = new Chamado("Acesso");
        PainelChamados painel = new PainelChamados();
        painel.acompanhar(chamado);
        assertEquals("Aberto", painel.getEstados().get(chamado));
    }

    @Test
    public void deveAtualizarEstado() {
        Chamado chamado = new Chamado("Acesso");
        PainelChamados painel = new PainelChamados();
        painel.acompanhar(chamado);
        chamado.cancelar();
        assertEquals("Cancelado", painel.getEstados().get(chamado));
    }

    @Test
    public void deveDistinguirChamadosComMesmoTitulo() {
        Chamado primeiro = new Chamado("Acesso");
        Chamado segundo = new Chamado("Acesso");
        PainelChamados painel = new PainelChamados();
        painel.acompanhar(primeiro);
        painel.acompanhar(segundo);
        primeiro.cancelar();
        assertEquals(2, painel.getEstados().size());
        assertEquals("Cancelado", painel.getEstados().get(primeiro));
        assertEquals("Aberto", painel.getEstados().get(segundo));
    }

    @Test
    public void deveRemoverAcompanhamento() {
        Chamado chamado = new Chamado("Acesso");
        PainelChamados painel = new PainelChamados();
        painel.acompanhar(chamado);
        painel.deixarDeAcompanhar(chamado);
        chamado.cancelar();
        assertTrue(painel.getEstados().isEmpty());
    }
}
