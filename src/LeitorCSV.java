import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorCSV {
    public ListaDePedidos lerPedidos(String caminhoArquivo) {
        ListaDePedidos pedidos = new ListaDePedidos();
        String linha;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            // Ignorar a primeira linha (cabeçalho)
            br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                int codigo = Integer.parseInt(dados[0]);
                String sabor = dados[1];
                int instante = Integer.parseInt(dados[2]);
                int tempoPreparo = Integer.parseInt(dados[3]);

                Pedido pedido = new Pedido(codigo, sabor, instante, tempoPreparo);
                pedidos.adicionar(pedido);

                // Adicionar print para verificar a leitura dos pedidos
                System.out.println("Pedido lido: " + codigo + " - " + sabor + " - " + instante + " - " + tempoPreparo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pedidos;
    }
}
