package padroescriacao.singleton;

import padroescomportamentais.state.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParametrosTest {

    @Test
    public void deveRetornarMesmaInstancia() {
        assertSame(Parametros.getInstance(), Parametros.getInstance());
    }

    @Test
    public void deveCompartilharNomeSistema() {
        Parametros primeiro = Parametros.getInstance();
        Parametros segundo = Parametros.getInstance();
        try {
            primeiro.setNomeSistema("Suporte Acadêmico");
            assertEquals("Suporte Acadêmico", segundo.getNomeSistema());
        } finally {
            primeiro.setNomeSistema("Central de Chamados");
        }
    }

    @Test
    public void naoDeveAceitarNomeVazio() {
        try {
            Parametros.getInstance().setNomeSistema(" ");
            fail("Deveria rejeitar nome vazio");
        } catch (IllegalArgumentException erro) {
            assertEquals("Nome do sistema deve ser informado", erro.getMessage());
        }
    }

    @Test
    public void deveReutilizarInstanciaDeCadaEstado() {
        assertSame(ChamadoEstadoAberto.getInstance(), ChamadoEstadoAberto.getInstance());
        assertSame(ChamadoEstadoEmAtendimento.getInstance(), ChamadoEstadoEmAtendimento.getInstance());
        assertSame(ChamadoEstadoAguardandoCliente.getInstance(), ChamadoEstadoAguardandoCliente.getInstance());
        assertSame(ChamadoEstadoResolvido.getInstance(), ChamadoEstadoResolvido.getInstance());
        assertSame(ChamadoEstadoFechado.getInstance(), ChamadoEstadoFechado.getInstance());
        assertSame(ChamadoEstadoCancelado.getInstance(), ChamadoEstadoCancelado.getInstance());
    }
}
