package servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import padroescomportamentais.state.Chamado;
import padroescomportamentais.state.MudancaStatus;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChamadoService {

    private final NotificacaoService notificacoes;
    private final Map<Integer, Chamado> chamados = new LinkedHashMap<>();
    private int proximoNumero = 1;

    @Autowired
    public ChamadoService(NotificacaoService notificacoes) {
        this.notificacoes = notificacoes;
    }

    public int abrir(String titulo, String solicitante, String tecnico) {
        Chamado chamado = new Chamado(titulo);
        notificacoes.acompanhar(chamado, solicitante, tecnico);
        int numero = proximoNumero++;
        chamados.put(numero, chamado);
        return numero;
    }

    public Chamado buscar(int numero) {
        Chamado chamado = chamados.get(numero);
        if (chamado == null) {
            throw new IllegalArgumentException("Chamado não encontrado: " + numero);
        }
        return chamado;
    }

    public Map<Integer, Chamado> listar() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(chamados));
    }

    public boolean iniciarAtendimento(int numero) {
        return buscar(numero).iniciarAtendimento();
    }

    public boolean aguardarCliente(int numero) {
        return buscar(numero).aguardarCliente();
    }

    public boolean retomarAtendimento(int numero) {
        return buscar(numero).retomarAtendimento();
    }

    public boolean resolver(int numero) {
        return buscar(numero).resolver();
    }

    public boolean reabrir(int numero) {
        return buscar(numero).reabrir();
    }

    public boolean fechar(int numero) {
        return buscar(numero).fechar();
    }

    public boolean cancelar(int numero) {
        return buscar(numero).cancelar();
    }

    public List<MudancaStatus> historico(int numero) {
        return buscar(numero).getHistorico();
    }

    public List<String> notificacoes(int numero) {
        return notificacoes.listar(buscar(numero));
    }

    public List<String> ultimasNotificacoes(int numero) {
        return notificacoes.ultimas(buscar(numero));
    }

    public Map<Chamado, String> painel() {
        return notificacoes.consultarPainel();
    }
}
