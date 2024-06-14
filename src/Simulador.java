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
    String nome = "pedidos.txt";


    public void menu() {
        int opcao = 0;
        String teste;
        String menu = "Bem-vindo ao Simulador de Pedidos de Pizza!\n"+
                "Escolha uma opção:\n"+
                "1 - Executar passo a passo\n"+
                "2 - Executar de forma contínua\n"+
                "Opção: ";
        System.out.println(menu);
        opcao = in.nextInt();
        teste = in.nextLine();
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
                if(tempoPreparo < maiorTempo) {
                    maiorTempo = tempoPreparo;
                }
                filaPedidos.enfileirar(new Pedido(codigo, sabor, instante, tempoPreparo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }








    public Fila pedidoMaisDemorado(){
        Fila pedidosMaisDemorados = new Fila();
        Pedido pedido = new Pedido();
        while (pedido != null){
            pedido = filaPedidos.desenfileirar();
            if(pedido.getTempoPreparo() == maiorTempo){
                pedidosMaisDemorados.enfileirar(pedido);
            }
        }
        return pedidosMaisDemorados;
    }



    public void geraCsvComSituacao(){

    }
    public void geraCsvCaminhamentoCentral(){

    }

}
