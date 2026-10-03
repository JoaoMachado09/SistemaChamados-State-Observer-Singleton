package padroescomportamentais.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/** Context do State e objeto observado do Observer. */
@SuppressWarnings("deprecation")
public class Chamado extends Observable {

    private String titulo;
    private ChamadoEstado estado;
    private final List<MudancaStatus> historico = new ArrayList<>();

    public Chamado() {
        this.titulo = "Novo chamado";
        this.estado = ChamadoEstadoAberto.getInstance();
    }

    public Chamado(String titulo) {
        this();
        this.setTitulo(titulo);
    }

    // Centraliza a alteração de estado, o histórico e a notificação dos observadores.
    public void setEstado(ChamadoEstado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("Estado não pode ser nulo");
        }
        if (this.estado == estado) {
            return;
        }
        MudancaStatus mudanca = new MudancaStatus(this.titulo,
                this.estado.getEstado(), estado.getEstado());
        this.estado = estado;
        this.historico.add(mudanca);
        this.setChanged();
        this.notifyObservers(mudanca);
    }

    public boolean iniciarAtendimento() {
        return estado.iniciarAtendimento(this);
    }

    public boolean aguardarCliente() {
        return estado.aguardarCliente(this);
    }

    public boolean retomarAtendimento() {
        return estado.retomarAtendimento(this);
    }

    public boolean resolver() {
        return estado.resolver(this);
    }

    public boolean reabrir() {
        return estado.reabrir(this);
    }

    public boolean fechar() {
        return estado.fechar(this);
    }

    public boolean cancelar() {
        return estado.cancelar(this);
    }

    public String getNomeEstado() {
        return estado.getEstado();
    }

    public ChamadoEstado getEstado() {
        return estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título deve ser informado");
        }
        this.titulo = titulo.trim();
    }

    public List<MudancaStatus> getHistorico() {
        return Collections.unmodifiableList(new ArrayList<>(historico));
    }

    @Override
    public String toString() {
        return "Chamado{titulo='" + titulo + "', estado='" + getNomeEstado() + "'}";
    }
}
