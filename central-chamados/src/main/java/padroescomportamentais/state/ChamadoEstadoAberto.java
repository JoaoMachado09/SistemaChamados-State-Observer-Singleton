package padroescomportamentais.state;

public class ChamadoEstadoAberto extends ChamadoEstado {

    private ChamadoEstadoAberto() {}

    private static final ChamadoEstadoAberto instance = new ChamadoEstadoAberto();

    public static ChamadoEstadoAberto getInstance() {
        return instance;
    }

    public String getEstado() {
        return "Aberto";
    }

    public boolean iniciarAtendimento(Chamado chamado) {
        chamado.setEstado(ChamadoEstadoEmAtendimento.getInstance());
        return true;
    }

    public boolean cancelar(Chamado chamado) {
        chamado.setEstado(ChamadoEstadoCancelado.getInstance());
        return true;
    }
}
