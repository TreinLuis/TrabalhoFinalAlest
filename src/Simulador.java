import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Simulador {
    Fila filaPedidos  = new Fila();
    int instanteAtual = 0;
    int tempoTotal = 1;
    Scanner in = new Scanner(System.in);
    int maiorTempo = 0;
    String nome = "pedidos.csv";
    ArvoreBinariaDePesquisa arvore = new ArvoreBinariaDePesquisa();


    public void menu() {
        int opcao = 0;
        String menu = "Bem-vindo ao Simulador de Pedidos de Pizza!\n"+
                "Escolha uma opção:\n"+
                "1 - Executar passo a passo\n"+
                "2 - Executar de forma contínua\n"+
                "Opção: ";
        System.out.println(menu);
        opcao = in.nextInt();
        carregarPedidos();
        pedidoMaisDemorados(filaPedidos);
        processarNaArvore(filaPedidos);

//        if(teste.equalsIgnoreCase("")) { isso para fazer o negocio do enter e do tempo
//        }
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
                if(tempoPreparo > maiorTempo) {
                    maiorTempo = tempoPreparo;
                }
                filaPedidos.enfileirar(new Pedido(codigo, sabor, instante, tempoPreparo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void processarNaArvoreContinuo(Fila filaPedidos) {//precisamos melhor ainda vic
        Fila fila = filaPedidos;
        Pedido pedido = filaPedidos.desenfileirar();
        do {
            arvore.adicionar(pedido);

            pedido = fila.desenfileirar();
        } while (pedido != null);
        System.out.println(arvore.toString());
    }
    public void processarNaArvoreManual(Fila filaPedidos) {//Precisar ser implementado
        Fila fila = filaPedidos;
        Pedido pedido = filaPedidos.desenfileirar();
        do {
            arvore.adicionar(pedido);

            pedido = fila.desenfileirar();
        } while (pedido != null);
        System.out.println(arvore.toString());
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

    public void geraCsvComSituacao(){

    }
    public void geraCsvCaminhamentoCentral(){

    }
}
