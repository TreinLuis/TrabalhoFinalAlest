import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Simulador {
    Fila filaPedidos = new Fila();
    int instanteAtual = 0;
    int tempoTotal = 1;
    Scanner in = new Scanner(System.in);
    int maiorTempo = 0;

    String nome = "pedidos.csv";
    ArvoreBinariaDePesquisa arvore = new ArvoreBinariaDePesquisa();

    public void executar(){
        carregarPedidos();
        menu();
        pedidoMaisDemorados(filaPedidos);
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
        processarNaArvore(filaPedidos, opcao);
    }


    public void carregarPedidos() {
        try (BufferedReader br = new BufferedReader(new FileReader(nome))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int codigo = Integer.parseInt(values[0].trim());
                String sabor = values[1].trim();
                int instante = Integer.parseInt(values[2].trim());
                int tempoPreparo = Integer.parseInt(values[3].trim());
                tempoTotal += tempoPreparo;
                if (tempoPreparo > maiorTempo) {
                    maiorTempo = tempoPreparo;
                }
                filaPedidos.enfileirar(new Pedido(codigo, sabor, instante, tempoPreparo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArvoreBinariaDePesquisa processarNaArvore(Fila filaPedidos, int opcao) {//Precisar ser implementado
        String controlador;
        ArvoreBinariaDePesquisa processada = new ArvoreBinariaDePesquisa();
        Fila fila = filaPedidos;
        Pedido pedido = fila.desenfileirar();
        int tempo = 0;
        if(opcao == 2){
            while (pedido != null) {
                return processarAutomatizado(fila);
            }
        } else {
            while (pedido != null) {
                System.out.println("Tempo atual: " + tempo);
                // Processa o pedido conforme o tempo de preparo
                for (int i = 0; i < pedido.getTempoPreparo(); i++) {
                    System.out.println("Pressione <ENTER> para avançar uma unidade de tempo... (Tempo de preparo restante: " + (pedido.getTempoPreparo() - i) + ")");
                    controlador = in.nextLine().trim();
                    tempo++;
                    // Verifica se o usuário deseja terminar de forma automática
                    if (controlador.equalsIgnoreCase("C")) {
                        return processarAutomatizado(fila);
                    }
                }
                // Adiciona o pedido à árvore após o tempo de preparo completo
                processada.adicionar(pedido);
                System.out.println("Pedido processado: " + pedido.getCodigo() + " - Tempo de preparo: " + pedido.getTempoPreparo());
                // Obtém o próximo pedido da fila
                pedido = fila.desenfileirar();
            }
        }
        System.out.println(processada.getTamanho());
        return processada;
    }

    public Fila pedidoMaisDemorados(Fila filaPedidos) {
        Fila pedidosMaisDemorados = new Fila();
        Fila fila = filaPedidos;
        Pedido pedido = new Pedido();
        do {
            if (pedido.getTempoPreparo() == maiorTempo) {
                pedidosMaisDemorados.enfileirar(pedido);
            }
            pedido = fila.desenfileirar();
        } while (pedido != null);
        System.out.println(pedidosMaisDemorados.toString());
        return pedidosMaisDemorados;
    }


    public void geraCsvComSituacao(String nomeArquivo, Fila filaPedidos) {

    }

    public void geraCsvCaminhamentoCentral() {

    }
    public ArvoreBinariaDePesquisa processarAutomatizado(Fila filaPedidos) {//precisamos melhor ainda vic
        ArvoreBinariaDePesquisa processada = new ArvoreBinariaDePesquisa();
        Fila fila = filaPedidos;
        Pedido pedido = fila.desenfileirar();
        int tempo = 0;
        while (pedido != null) {
            tempo += pedido.getTempoPreparo(); // Avança o tempo de acordo com o tempo de preparo do pedido
            System.out.println("Pedido processado automaticamente: " + pedido.getCodigo() + " - Tempo de preparo: " + pedido.getTempoPreparo());
            processada.adicionar(pedido);
            pedido = fila.desenfileirar(); // Obtém o próximo pedido
        }
        return processada;
    }

}







