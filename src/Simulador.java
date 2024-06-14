import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Simulador {
    Fila filaPedidos = new Fila();
    public void carregarPedidos(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int codigo = Integer.parseInt(values[0].trim());
                String sabor = values[1].trim();
                int instante = Integer.parseInt(values[2].trim());
                int tempoPreparo = Integer.parseInt(values[3].trim());
                filaPedidos.enfileirar(new Pedido(codigo, sabor, instante, tempoPreparo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
