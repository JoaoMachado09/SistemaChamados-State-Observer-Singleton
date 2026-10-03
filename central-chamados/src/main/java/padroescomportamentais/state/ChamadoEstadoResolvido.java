package padroescomportamentais.state;

public class ChamadoEstadoResolvido extends ChamadoEstado {

    private ChamadoEstadoResolvido() {}

    private static final ChamadoEstadoResolvido instance = new ChamadoEstadoResolvido();

    public static ChamadoEstadoResolvido getInstance() {
        return instance;
    }

    public String getEstado() {
        return "Resolvido";
    }

    public boolean reabrir(Chamado chamado) {
        chamado.setEstado(ChamadoEstadoEmAtendimento.getInstance());
        return true;
    }

    public boolean fechar(Chamado chamado) {
        chamado.setEstado(ChamadoEstadoFechado.getInstance());
        return true;
    }
}
