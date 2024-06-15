import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Simulador {

    int instanteAtual = 0;
    int tempoTotal = 1;
    Scanner in = new Scanner(System.in);
    int maiorTempo = 0;

    String nome = "pedidos.csv";
    Fila filaPedidos = carregarPedidos();

    ArvoreBinariaDePesquisa arvore = new ArvoreBinariaDePesquisa();

    public void executar(){
        carregarPedidos();
        menu();
        pedidoMaisDemorados(carregarPedidos());
    }
    public void menu() {
        int opcao = 0;
        String menu = "Bem-vindo ao Simulador de Pedidos de Pizza!\n" +
                "Escolha uma opção:\n" +
                "1 - Executar passo a passo\n" +
                "2 - Executar de forma contínua\n" +
                "Opção: ";
        System.out.println(menu);
        opcao = in.nextInt();
        in.nextLine();
        geraCsvCaminhamentoCentral(processarNaArvore(filaPedidos, opcao));
    }

    public Fila carregarPedidos() {
        Fila fila = new Fila();

        boolean primeiraLinha = true;

        try (BufferedReader br = new BufferedReader(new FileReader(nome))) {
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
                    fila.enfileirar(new Pedido(codigo, sabor, instante, tempoPreparo));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao processar linha: " + line + ", " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fila;
    }

    public ArvoreBinariaDePesquisa processarNaArvore(Fila filaAux, int opcao) {//Precisar ser implementado
        String controlador;
        ArvoreBinariaDePesquisa processada = new ArvoreBinariaDePesquisa();

        int tempo = 0;
        if(opcao == 2){
            return processarAutomatizado(filaPedidos);
        } else {
            Pedido pedido = filaAux.desenfileirar();
            while (pedido != null) {
                System.out.println("Tempo atual: " + tempo);
                // Processa o pedido conforme o tempo de preparo
                for (int i = 0; i < pedido.getTempoPreparo(); i++) {
                    System.out.println("Pressione <ENTER> para avançar uma unidade de tempo... (Tempo de preparo restante: " + (pedido.getTempoPreparo() - i) + ")");
                    controlador = in.nextLine().trim();
                    tempo++;
                    // Verifica se o usuário deseja terminar de forma automática
                    if (controlador.equalsIgnoreCase("C")) {
                        return processarAutomatizado(filaPedidos);
                    }
                }
                // Adiciona o pedido à árvore após o tempo de preparo completo
                processada.adicionar(pedido);
                System.out.println("Pedido processado: " + pedido.getCodigo() + " - Tempo de preparo: " + pedido.getTempoPreparo());
                // Obtém o próximo pedido da fila
                pedido = filaAux.desenfileirar();
            }
        }
        arvore = processada;
        System.out.println(processada.getTamanho());
        return processada;
    }

    public Fila pedidoMaisDemorados(Fila filaAux) {
        Fila pedidosMaisDemorados = new Fila();
        System.out.println(filaAux.getTamanho());
        Pedido pedido = new Pedido();
        do {
            if (pedido.getTempoPreparo() == maiorTempo) {
                pedidosMaisDemorados.enfileirar(pedido);
            }
            pedido = filaAux.desenfileirar();
        } while (pedido != null);
        System.out.println(pedidosMaisDemorados.toString());
        return pedidosMaisDemorados;
    }


    public void geraCsvComSituacao(String nomeArquivo, Fila filaPedidos) {

    }
    public ArvoreBinariaDePesquisa processarAutomatizado(Fila filaAux) {//precisamos melhor ainda vic
        ArvoreBinariaDePesquisa processada = new ArvoreBinariaDePesquisa();
        Pedido pedido = filaAux.desenfileirar();
        int tempo = 0;
        while (pedido != null) {
            tempo += pedido.getTempoPreparo(); // Avança o tempo de acordo com o tempo de preparo do pedido
            System.out.println("Pedido processado automaticamente: " + pedido.getCodigo() + " - Tempo de preparo: " + pedido.getTempoPreparo());
            processada.adicionar(pedido);
            pedido = filaAux.desenfileirar(); // Obtém o próximo pedido
        }
        return processada;
    }
    public void geraCsvCaminhamentoCentral(ArvoreBinariaDePesquisa aux) {
        String nomeArquivo = "caminhamento_central.csv";

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            aux.ordemCentralCSV(writer);
            System.out.println("Arquivo '" + nomeArquivo + "' gerado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}







