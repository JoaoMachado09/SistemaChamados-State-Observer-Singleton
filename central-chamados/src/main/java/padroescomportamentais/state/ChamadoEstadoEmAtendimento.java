package padroescomportamentais.state;

public class ChamadoEstadoEmAtendimento extends ChamadoEstado {

    private ChamadoEstadoEmAtendimento() {}

    private static final ChamadoEstadoEmAtendimento instance = new ChamadoEstadoEmAtendimento();

    public static ChamadoEstadoEmAtendimento getInstance() {
        return instance;
    }

    public String getEstado() {
        return "Em atendimento";
    }

    public boolean aguardarCliente(Chamado chamado) {
        chamado.setEstado(ChamadoEstadoAguardandoCliente.getInstance());
        return true;
    }

    public boolean resolver(Chamado chamado) {
        chamado.setEstado(ChamadoEstadoResolvido.getInstance());
        return true;
    }

    public boolean cancelar(Chamado chamado) {
        chamado.setEstado(ChamadoEstadoCancelado.getInstance());
        return true;
    }
}
