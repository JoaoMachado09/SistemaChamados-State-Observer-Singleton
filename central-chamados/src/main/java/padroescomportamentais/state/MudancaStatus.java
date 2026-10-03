package padroescomportamentais.state;

/** Dados imutáveis da mudança enviados aos observadores. */
public class MudancaStatus {

    private final String titulo;
    private final String estadoAnterior;
    private final String estadoAtual;

    public MudancaStatus(String titulo, String estadoAnterior, String estadoAtual) {
        this.titulo = titulo;
        this.estadoAnterior = estadoAnterior;
        this.estadoAtual = estadoAtual;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public String getEstadoAtual() {
        return estadoAtual;
    }

    public String toString() {
        return "Chamado '" + titulo + "': " + estadoAnterior + " -> " + estadoAtual;
    }
}
