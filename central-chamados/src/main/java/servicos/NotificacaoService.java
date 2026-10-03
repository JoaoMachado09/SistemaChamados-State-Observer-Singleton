package servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import padroescomportamentais.observer.PainelChamados;
import padroescomportamentais.observer.Usuario;
import padroescomportamentais.state.Chamado;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificacaoService {

    private final PainelChamados painel;
    private final Map<Chamado, List<Usuario>> interessados = new LinkedHashMap<>();

    @Autowired
    public NotificacaoService(PainelChamados painel) {
        this.painel = painel;
    }

    public void acompanhar(Chamado chamado, String solicitante, String tecnico) {
        if (interessados.containsKey(chamado)) {
            throw new IllegalArgumentException("Chamado já possui interessados cadastrados");
        }
        Usuario usuarioSolicitante = new Usuario(solicitante);
        Usuario usuarioTecnico = new Usuario(tecnico);
        usuarioSolicitante.acompanhar(chamado);
        usuarioTecnico.acompanhar(chamado);
        painel.acompanhar(chamado);
        interessados.put(chamado, List.of(usuarioSolicitante, usuarioTecnico));
    }

    public List<String> listar(Chamado chamado) {
        List<String> mensagens = new ArrayList<>();
        for (Usuario usuario : obterInteressados(chamado)) {
            mensagens.addAll(usuario.getNotificacoes());
        }
        return List.copyOf(mensagens);
    }

    public List<String> ultimas(Chamado chamado) {
        List<String> mensagens = new ArrayList<>();
        for (Usuario usuario : obterInteressados(chamado)) {
            if (usuario.getUltimaNotificacao() != null) {
                mensagens.add(usuario.getUltimaNotificacao());
            }
        }
        return List.copyOf(mensagens);
    }

    public Map<Chamado, String> consultarPainel() {
        return painel.getEstados();
    }

    private List<Usuario> obterInteressados(Chamado chamado) {
        List<Usuario> usuarios = interessados.get(chamado);
        if (usuarios == null) {
            throw new IllegalArgumentException("Chamado sem acompanhamento cadastrado");
        }
        return usuarios;
    }
}
