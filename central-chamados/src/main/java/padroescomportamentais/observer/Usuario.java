package padroescomportamentais.observer;

import padroescomportamentais.state.Chamado;
import padroescomportamentais.state.MudancaStatus;
import padroescriacao.singleton.Parametros;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Usuario implements Observer {

    private String nome;
    private String ultimaNotificacao;
    private final List<String> notificacoes = new ArrayList<>();

    public Usuario(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome deve ser informado");
        }
        this.nome = nome.trim();
    }

    public String getNome() {
        return nome;
    }

    public String getUltimaNotificacao() {
        return ultimaNotificacao;
    }

    public List<String> getNotificacoes() {
        return Collections.unmodifiableList(new ArrayList<>(notificacoes));
    }

    public void acompanhar(Chamado chamado) {
        chamado.addObserver(this);
    }

    public void deixarDeAcompanhar(Chamado chamado) {
        chamado.deleteObserver(this);
    }

    @Override
    public void update(Observable chamado, Object argumento) {
        MudancaStatus mudanca = (MudancaStatus) argumento;
        this.ultimaNotificacao = "[" + Parametros.getInstance().getNomeSistema() + "] "
                + this.nome + ", " + mudanca.toString();
        this.notificacoes.add(this.ultimaNotificacao);
    }
}
