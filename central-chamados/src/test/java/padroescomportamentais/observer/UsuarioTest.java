package padroescomportamentais.observer;

import padroescomportamentais.state.*;
import padroescriacao.singleton.Parametros;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @BeforeEach
    public void setUp() {
        Parametros.getInstance().setNomeSistema("Central de Chamados");
    }

    @Test
    public void deveNotificarUmUsuario() {
        Chamado chamado = new Chamado("Falha no acesso");
        Usuario usuario = new Usuario("Ana");
        usuario.acompanhar(chamado);
        chamado.iniciarAtendimento();
        assertEquals("[Central de Chamados] Ana, Chamado 'Falha no acesso': Aberto -> Em atendimento",
                usuario.getUltimaNotificacao());
    }

    @Test
    public void deveNotificarUsuarios() {
        Chamado chamado = new Chamado("Falha no acesso");
        Usuario ana = new Usuario("Ana");
        Usuario tecnico = new Usuario("Técnico");
        ana.acompanhar(chamado);
        tecnico.acompanhar(chamado);
        chamado.iniciarAtendimento();
        assertEquals(1, ana.getNotificacoes().size());
        assertEquals(1, tecnico.getNotificacoes().size());
    }

    @Test
    public void naoDeveNotificarUsuarioNaoInscrito() {
        Chamado chamado = new Chamado("Falha no acesso");
        Usuario usuario = new Usuario("Ana");
        chamado.iniciarAtendimento();
        assertNull(usuario.getUltimaNotificacao());
    }

    @Test
    public void deveNotificarApenasUsuarioDoChamadoAlterado() {
        Chamado primeiro = new Chamado("Acesso");
        Chamado segundo = new Chamado("Impressora");
        Usuario ana = new Usuario("Ana");
        Usuario bruno = new Usuario("Bruno");
        ana.acompanhar(primeiro);
        bruno.acompanhar(segundo);
        primeiro.iniciarAtendimento();
        assertEquals(1, ana.getNotificacoes().size());
        assertNull(bruno.getUltimaNotificacao());
    }

    @Test
    public void naoDeveNotificarTransicaoRecusada() {
        Chamado chamado = new Chamado("Falha no acesso");
        Usuario usuario = new Usuario("Ana");
        usuario.acompanhar(chamado);
        assertFalse(chamado.resolver());
        assertNull(usuario.getUltimaNotificacao());
        assertEquals(0, chamado.getHistorico().size());
    }

    @Test
    public void naoDeveNotificarAposRemoverUsuario() {
        Chamado chamado = new Chamado("Falha no acesso");
        Usuario usuario = new Usuario("Ana");
        usuario.acompanhar(chamado);
        usuario.deixarDeAcompanhar(chamado);
        chamado.iniciarAtendimento();
        assertNull(usuario.getUltimaNotificacao());
    }

    @Test
    public void naoDeveDuplicarInscricao() {
        Chamado chamado = new Chamado("Falha no acesso");
        Usuario usuario = new Usuario("Ana");
        usuario.acompanhar(chamado);
        usuario.acompanhar(chamado);
        chamado.iniciarAtendimento();
        assertEquals(1, usuario.getNotificacoes().size());
    }

    @Test
    public void naoDeveNotificarMesmoEstado() {
        Chamado chamado = new Chamado("Falha no acesso");
        Usuario usuario = new Usuario("Ana");
        usuario.acompanhar(chamado);
        chamado.setEstado(ChamadoEstadoAberto.getInstance());
        assertNull(usuario.getUltimaNotificacao());
        assertEquals(0, chamado.getHistorico().size());
    }

    @Test
    public void devePreservarTodasAsNotificacoes() {
        Chamado chamado = new Chamado("Falha no acesso");
        Usuario usuario = new Usuario("Ana");
        usuario.acompanhar(chamado);
        chamado.iniciarAtendimento();
        chamado.resolver();
        chamado.fechar();
        assertEquals(3, usuario.getNotificacoes().size());
        assertEquals("[Central de Chamados] Ana, Chamado 'Falha no acesso': Resolvido -> Fechado",
                usuario.getUltimaNotificacao());
        assertEquals("Aberto", chamado.getHistorico().get(0).getEstadoAnterior());
    }

    @Test
    public void deveIntegrarSingletonNaNotificacao() {
        Parametros.getInstance().setNomeSistema("Suporte Acadêmico");
        try {
            Chamado chamado = new Chamado("Acesso");
            Usuario usuario = new Usuario("Ana");
            usuario.acompanhar(chamado);
            chamado.cancelar();
            assertEquals("[Suporte Acadêmico] Ana, Chamado 'Acesso': Aberto -> Cancelado",
                    usuario.getUltimaNotificacao());
        } finally {
            Parametros.getInstance().setNomeSistema("Central de Chamados");
        }
    }

    @Test
    public void deveNotificarReabertura() {
        Chamado chamado = new Chamado("Acesso");
        chamado.iniciarAtendimento();
        chamado.resolver();
        Usuario usuario = new Usuario("Ana");
        usuario.acompanhar(chamado);
        assertTrue(chamado.reabrir());
        assertEquals("[Central de Chamados] Ana, Chamado 'Acesso': Resolvido -> Em atendimento",
                usuario.getUltimaNotificacao());
    }
}
