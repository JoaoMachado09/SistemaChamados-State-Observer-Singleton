package padroescomportamentais.state;

public abstract class ChamadoEstado {

    public abstract String getEstado();

    public boolean iniciarAtendimento(Chamado chamado) {
        return false;
    }

    public boolean aguardarCliente(Chamado chamado) {
        return false;
    }

    public boolean retomarAtendimento(Chamado chamado) {
        return false;
    }

    public boolean resolver(Chamado chamado) {
        return false;
    }

    public boolean reabrir(Chamado chamado) {
        return false;
    }

    public boolean fechar(Chamado chamado) {
        return false;
    }

    public boolean cancelar(Chamado chamado) {
        return false;
    }
}
