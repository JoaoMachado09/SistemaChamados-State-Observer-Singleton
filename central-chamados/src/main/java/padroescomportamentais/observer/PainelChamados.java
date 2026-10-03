package padroescomportamentais.observer;

import padroescomportamentais.state.Chamado;
import padroescomportamentais.state.MudancaStatus;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class PainelChamados implements Observer {

    private final Map<Chamado, String> estados = new LinkedHashMap<>();

    public void acompanhar(Chamado chamado) {
        chamado.addObserver(this);
        estados.put(chamado, chamado.getNomeEstado());
    }

    public void deixarDeAcompanhar(Chamado chamado) {
        chamado.deleteObserver(this);
        estados.remove(chamado);
    }

    @Override
    public void update(Observable chamado, Object argumento) {
        MudancaStatus mudanca = (MudancaStatus) argumento;
        estados.put((Chamado) chamado, mudanca.getEstadoAtual());
    }

    public Map<Chamado, String> getEstados() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(estados));
    }
}
