package aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import servicos.ChamadoService;
import java.util.Scanner;

@Component
@ConditionalOnProperty(name = "app.console.enabled", havingValue = "true", matchIfMissing = true)
public class ConsoleAplicacao implements CommandLineRunner {

    private final ChamadoService chamados;

    @Autowired
    public ConsoleAplicacao(ChamadoService chamados) {
        this.chamados = chamados;
    }

    @Override
    public void run(String... args) {
        for (String argumento : args) {
            if (argumento.equals("--demo")) {
                demonstrar();
                return;
            }
        }
        executarMenu();
    }

    private void demonstrar() {
        int numero = chamados.abrir("Falha no acesso", "Ana", "Equipe de suporte");
        chamados.iniciarAtendimento(numero);
        chamados.aguardarCliente(numero);
        chamados.retomarAtendimento(numero);
        chamados.resolver(numero);
        chamados.reabrir(numero);
        chamados.resolver(numero);
        chamados.fechar(numero);
        chamados.notificacoes(numero).forEach(System.out::println);
        System.out.println("Reabrir após fechamento: " + chamados.reabrir(numero));
        System.out.println("Histórico: " + chamados.historico(numero).size() + " mudanças");
        System.out.println("Painel: " + chamados.painel());
    }

    private void executarMenu() {
        Integer atual = null;
        try (Scanner entrada = new Scanner(System.in, "UTF-8")) {
            while (true) {
                System.out.println("\n=== CENTRAL DE CHAMADOS ===");
                if (atual != null) System.out.println("#" + atual + " " + chamados.buscar(atual));
                System.out.println("1 Novo | 2 Listar | 3 Selecionar | 4 Iniciar | 5 Aguardar cliente");
                System.out.println("6 Retomar | 7 Resolver | 8 Reabrir | 9 Fechar | 10 Cancelar");
                System.out.println("11 Histórico | 12 Painel | 13 Notificações | 0 Sair");
                if (!entrada.hasNextLine()) return;
                String opcao = entrada.nextLine().trim();
                if (opcao.equals("0")) return;
                try {
                    if (opcao.equals("1")) {
                        System.out.println("Título:");
                        if (!entrada.hasNextLine()) return;
                        String titulo = entrada.nextLine();
                        System.out.println("Nome do solicitante:");
                        if (!entrada.hasNextLine()) return;
                        String solicitante = entrada.nextLine();
                        System.out.println("Nome do técnico ou equipe:");
                        if (!entrada.hasNextLine()) return;
                        atual = chamados.abrir(titulo, solicitante, entrada.nextLine());
                        System.out.println("Chamado criado: #" + atual);
                    } else if (opcao.equals("2")) {
                        chamados.listar().forEach((numero, chamado) -> System.out.println(numero + " - " + chamado));
                    } else if (opcao.equals("3")) {
                        System.out.println("Número do chamado:");
                        if (!entrada.hasNextLine()) return;
                        int numero = Integer.parseInt(entrada.nextLine());
                        chamados.buscar(numero);
                        atual = numero;
                    } else if (opcao.equals("12")) {
                        System.out.println(chamados.painel());
                    } else if (opcao.equals("11")) {
                        exigirChamado(atual);
                        chamados.historico(atual).forEach(System.out::println);
                    } else if (opcao.equals("13")) {
                        exigirChamado(atual);
                        chamados.notificacoes(atual).forEach(System.out::println);
                    } else {
                        exigirChamado(atual);
                        boolean alterado = switch (opcao) {
                            case "4" -> chamados.iniciarAtendimento(atual);
                            case "5" -> chamados.aguardarCliente(atual);
                            case "6" -> chamados.retomarAtendimento(atual);
                            case "7" -> chamados.resolver(atual);
                            case "8" -> chamados.reabrir(atual);
                            case "9" -> chamados.fechar(atual);
                            case "10" -> chamados.cancelar(atual);
                            default -> throw new IllegalArgumentException("Opção inválida");
                        };
                        if (alterado) {
                            chamados.ultimasNotificacoes(atual).forEach(System.out::println);
                        } else {
                            System.out.println("Operação não permitida no estado " + chamados.buscar(atual).getNomeEstado());
                        }
                    }
                } catch (IllegalArgumentException | IllegalStateException erro) {
                    System.out.println("Aviso: " + erro.getMessage());
                }
            }
        }
    }

    private void exigirChamado(Integer numero) {
        if (numero == null) throw new IllegalStateException("Crie ou selecione um chamado");
    }
}
