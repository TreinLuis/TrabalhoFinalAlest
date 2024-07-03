import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Simulador {
    private int instanteAtual;
    private int tempoTotal;
    private int maiorTempo;
    private Fila filaPedidos;
    private ArvoreBinariaDePesquisa arvore;
    private Scanner in;

    public Simulador() {
        this.instanteAtual = 0;
        this.tempoTotal = 1;
        this.maiorTempo = 0;
        this.filaPedidos = new Fila();
        this.arvore = new ArvoreBinariaDePesquisa();
        this.in = new Scanner(System.in);
    }

    public void executar() {
        System.out.println("Digite o caminho do arquivo CSV de pedidos:");
        String filePath = in.nextLine();
        carregarPedidos(filePath);
        menu();
        pedidoMaisDemorados();
    }

    private void menu() {
        System.out.println("Bem-vindo ao Simulador de Pedidos de Pizza!\n" +
                "Escolha uma opção:\n" +
                "1 - Executar passo a passo\n" +
                "2 - Executar de forma contínua\n" +
                "Opção: ");
        int opcao = in.nextInt();
        in.nextLine();
        processarNaArvore(opcao);
        geraCsvCaminhamentoCentral("caminhamento_central.csv");
    }

    private void carregarPedidos(String caminho) {
        boolean primeiraLinha = true;

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                String[] values = line.split(",");
                if (values.length < 4) {
                    System.err.println("Erro: Linha incompleta ignorada: " + line);
                    continue;
                }
                try {
                    int codigo = Integer.parseInt(values[0].trim());
                    String sabor = values[1].trim();
                    int instante = Integer.parseInt(values[2].trim());
                    int tempoPreparo = Integer.parseInt(values[3].trim());

                    tempoTotal += tempoPreparo;
                    if (tempoPreparo > maiorTempo) {
                        maiorTempo = tempoPreparo;
                    }
                    filaPedidos.enfileirar(new Pedido(codigo, sabor, instante, tempoPreparo));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao processar linha: " + line + ", " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processarNaArvore(int opcao) {
        if (opcao == 2) {
            processarAutomatizado();
        } else {
            processarPassoAPasso();
        }
    }

    private void processarPassoAPasso() {
        int tempo = 0;
        Pedido pedido = filaPedidos.desenfileirar();

        while (pedido != null) {
            System.out.println("Tempo atual: " + tempo);
            for (int i = 0; i < pedido.getTempoPreparo(); i++) {
                System.out.println("Pressione <ENTER> para avançar uma unidade de tempo... (Tempo de preparo restante: " + (pedido.getTempoPreparo() - i) + ")");
                String controlador = in.nextLine().trim();
                tempo++;
                if (controlador.equalsIgnoreCase("C")) {
                    processarAutomatizado();
                    return;
                }
            }
            arvore.adicionar(pedido);
            System.out.println("Pedido processado: " + pedido.getCodigo() + " - Tempo de preparo: " + pedido.getTempoPreparo());
            pedido = filaPedidos.desenfileirar();
        }
    }

    private void processarAutomatizado() {
        int tempo = 0;
        Pedido pedido = filaPedidos.desenfileirar();

        while (pedido != null) {
            tempo += pedido.getTempoPreparo();
            System.out.println("Pedido processado automaticamente: " + pedido.getCodigo() + " - Tempo de preparo: " + pedido.getTempoPreparo());
            arvore.adicionar(pedido);
            pedido = filaPedidos.desenfileirar();
        }
    }

    private void pedidoMaisDemorados() {
        Fila filaAux = filaPedidos.copiar();
        Fila pedidosMaisDemorados = new Fila();
        Pedido pedido = filaAux.desenfileirar();

        while (pedido != null) {
            if (pedido.getTempoPreparo() == maiorTempo) {
                pedidosMaisDemorados.enfileirar(pedido);
            }
            pedido = filaAux.desenfileirar();
        }
        System.out.println("Pedidos mais demorados: " + pedidosMaisDemorados.toString());
    }

    private void geraCsvComSituacao(String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("Instante, Fila de Pedidos, Em Produção, Prontos\n");
            int instante = 0;
            Pedido pedidoAtual = null;
            Fila filaCopia = filaPedidos.copiar();
            Fila filaProntos = new Fila();

            while (!filaCopia.estaVazia() || pedidoAtual != null) {
                if (pedidoAtual == null && !filaCopia.estaVazia()) {
                    pedidoAtual = filaCopia.desenfileirar();
                }

                String filaString = filaCopia.toString();
                String prontosString = filaProntos.toString();
                String emProducaoString = (pedidoAtual != null) ? pedidoAtual.toString() : "";

                writer.write(instante + "," + filaString + "," + emProducaoString + "," + prontosString + "\n");

                if (pedidoAtual != null && pedidoAtual.getTempoPreparo() == 0) {
                    filaProntos.enfileirar(pedidoAtual);
                    pedidoAtual = null;
                }

                if (pedidoAtual != null) {
                    pedidoAtual.setTempoPreparo(pedidoAtual.getTempoPreparo() - 1);
                }

                instante++;
            }

            System.out.println("Arquivo '" + nomeArquivo + "' gerado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void geraCsvCaminhamentoCentral(String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            arvore.ordemCentralCSV(writer);
            System.out.println("Arquivo '" + nomeArquivo + "' gerado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





