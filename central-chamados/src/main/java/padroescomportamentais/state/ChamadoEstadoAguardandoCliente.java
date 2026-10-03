package padroescomportamentais.state;

public class ChamadoEstadoAguardandoCliente extends ChamadoEstado {

    private ChamadoEstadoAguardandoCliente() {}

    private static final ChamadoEstadoAguardandoCliente instance = new ChamadoEstadoAguardandoCliente();

    public static ChamadoEstadoAguardandoCliente getInstance() {
        return instance;
    }

    public String getEstado() {
        return "Aguardando cliente";
    }

    public boolean retomarAtendimento(Chamado chamado) {
        chamado.setEstado(ChamadoEstadoEmAtendimento.getInstance());
        return true;
    }

    public boolean cancelar(Chamado chamado) {
        chamado.setEstado(ChamadoEstadoCancelado.getInstance());
        return true;
    }
}
