import java.util.Scanner;

public class Simulador {
    private FilaDePedidos filaPedidos;
    private Pedido pedidoAtual;
    private int tempoAtual;
    private ArvoreBinariaPesquisa abp;
    private ListaDePedidos pedidos;
    private ListaDePedidos logSimulacao;
    private int totalTempoExecutado;

    public Simulador() {
        filaPedidos = new FilaDePedidos();
        abp = new ArvoreBinariaPesquisa();
        tempoAtual = 0;
        logSimulacao = new ListaDePedidos();
        totalTempoExecutado = 0;
    }

    public void executar() {
        LeitorCSV leitorCSV = new LeitorCSV();
        pedidos = leitorCSV.lerPedidos("pedidos_pizza_15.csv"); // Ajuste o caminho aqui

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite 'C' para simulação contínua ou pressione <ENTER> para simulação passo a passo:");

        String input = scanner.nextLine();

        if ("C".equalsIgnoreCase(input)) {
            // Simulação contínua
            simularContinuamente();
        } else {
            // Simulação passo a passo
            while (!filaPedidos.estaVazia() || pedidoAtual != null || tempoAtual <= obterUltimoInstante(pedidos)) {
                simularPasso();
                System.out.println("Pressione <ENTER> para o próximo passo (instante de tempo = " + tempoAtual + ") ou digite 'S' para sair:");
                input = scanner.nextLine();
                if ("S".equalsIgnoreCase(input)) {
                    break;
                }
            }
        }

        // Mostrar total de pedidos processados e tempo total executado
        System.out.println("Total de pedidos processados: " + abp.contarNos());
        System.out.println("Total de tempo executado: " + totalTempoExecutado);

        // Encontrar o pedido mais demorado
        Pedido pedidoMaisDemorado = encontrarPedidoMaisDemorado(pedidos);
        if (pedidoMaisDemorado != null) {
            System.out.println("Pedido mais demorado: " + pedidoMaisDemorado.getCodigo() + " - " + pedidoMaisDemorado.getSabor());
        }

        GeradorResultados geradorResultados = new GeradorResultados();
        geradorResultados.gerarCSVSituacaoFila(logSimulacao, "situacao_da_fila.csv");
        geradorResultados.gerarCSVABP(abp.getRaiz(), "em_ordem_central.csv");

        // Imprimir log da simulação
        // logSimulacao.listarPedidos(); // Remover para não imprimir o log completo
    }

    private Pedido encontrarPedidoMaisDemorado(ListaDePedidos pedidos) {
        Pedido pedidoMaisDemorado = null;
        ListaDePedidos.No atual = pedidos.cabeca;
        while (atual != null) {
            if (pedidoMaisDemorado == null || atual.pedido.getTempoPreparo() > pedidoMaisDemorado.getTempoPreparo()) {
                pedidoMaisDemorado = atual.pedido;
            }
            atual = atual.proximo;
        }
        return pedidoMaisDemorado;
    }

    public void simularPasso() {
        // Processar o pedido atual
        if (pedidoAtual != null) {
            pedidoAtual.decrementarTempoRestante();
            if (pedidoAtual.getTempoRestante() == 0) {
                abp.inserir(pedidoAtual);
                System.out.println("Pedido processado e inserido na ABP (instante de tempo = " + tempoAtual + "): " + pedidoAtual.getCodigo());
                pedidoAtual = null;
            }
        }

        // Adicionar novos pedidos à fila conforme o tempo atual
        ListaDePedidos.No atual = pedidos.cabeca;
        while (atual != null) {
            if (atual.pedido.getInstante() == tempoAtual) {
                filaPedidos.enfileirar(atual.pedido);
                System.out.println("Pedido enfileirado (instante de tempo = " + tempoAtual + "): " + atual.pedido.getCodigo());
            }
            atual = atual.proximo;
        }

        // Retirar um novo pedido da fila se o pizzaiolo estiver livre
        if (pedidoAtual == null && !filaPedidos.estaVazia()) {
            pedidoAtual = filaPedidos.desenfileirar();
            System.out.println("Pedido retirado da fila para produção (instante de tempo = " + tempoAtual + "): " + pedidoAtual.getCodigo());
        }

        registrarLogParaCSV();
        tempoAtual++;
        totalTempoExecutado++;
    }

    public void simularContinuamente() {
        while (!filaPedidos.estaVazia() || pedidoAtual != null || tempoAtual <= obterUltimoInstante(pedidos)) {
            simularPasso();
        }
    }

    private void registrarLogParaCSV() {
        StringBuilder log = new StringBuilder();
        log.append(tempoAtual).append(",");

        // Fila de pedidos
        FilaDePedidos.No atual = filaPedidos.frente;
        StringBuilder filaPedidosLog = new StringBuilder();
        while (atual != null) {
            filaPedidosLog.append(atual.pedido.getCodigo()).append(" ");
            atual = atual.proximo;
        }
        log.append(filaPedidosLog.toString().trim()).append(",");

        // Em produção
        if (pedidoAtual != null) {
            log.append(pedidoAtual.getCodigo());
        }
        log.append(",");

        // Prontos
        ListaDePedidos prontos = new ListaDePedidos();
        abp.emOrdemParaLista(prontos);
        ListaDePedidos.No noAtual = prontos.cabeca;
        StringBuilder prontosLog = new StringBuilder();
        while (noAtual != null) {
            prontosLog.append(noAtual.pedido.getCodigo()).append(" ");
            noAtual = noAtual.proximo;
        }
        log.append(prontosLog.toString().trim());

        logSimulacao.adicionar(new Pedido(tempoAtual, log.toString(), 0, 0));
    }



    private int obterUltimoInstante(ListaDePedidos pedidos) {
        int ultimoInstante = 0;
        ListaDePedidos.No atual = pedidos.cabeca;
        while (atual != null) {
            if (atual.pedido.getInstante() > ultimoInstante) {
                ultimoInstante = atual.pedido.getInstante();
            }
            atual = atual.proximo;
        }
        return ultimoInstante;
    }
}

