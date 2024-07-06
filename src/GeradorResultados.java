import java.io.*;

public class GeradorResultados {

    public void gerarCSVFila(ListaDePedidos pedidos, String caminhoArquivo) {
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            ListaDePedidos.No atual = pedidos.cabeca;
            while (atual != null) {
                writer.append(String.valueOf(atual.pedido.getCodigo()))
                        .append(",")
                        .append(atual.pedido.getSabor())
                        .append(",")
                        .append(String.valueOf(atual.pedido.getInstante()))
                        .append(",")
                        .append(String.valueOf(atual.pedido.getTempoPreparo()))
                        .append("\n");
                atual = atual.proximo;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gerarCSVSituacaoFila(ListaDePedidos logSimulacao, String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new File(nomeArquivo))) {
            StringBuilder sb = new StringBuilder();

            // Adicionar cabeçalhos
            sb.append(String.format("%-20s,%-50s,%-20s,%-50s\n", "Instante de Tempo", "Fila de pedidos", "Em produção", "Prontos"));

            // Adicionar dados
            ListaDePedidos.No atual = logSimulacao.cabeca;
            while (atual != null) {
                String[] campos = atual.pedido.getSabor().split(",");
                if (campos.length == 4) {
                    sb.append(String.format("%-20s,%-50s,%-20s,%-50s\n", campos[0], campos[1], campos[2], campos[3]));
                } else {
                    // Tratamento para o caso de campos inesperados
                    sb.append(String.format("%-20s,%-50s,%-20s,%-50s\n",
                            campos.length > 0 ? campos[0] : "",
                            campos.length > 1 ? campos[1] : "",
                            campos.length > 2 ? campos[2] : "",
                            campos.length > 3 ? campos[3] : ""));
                }
                atual = atual.proximo;
            }

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void gerarCSVABP(ArvoreBinariaPesquisa.NoABP raiz, String caminhoArquivo) {
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            gerarCSVABPRecursivo(raiz, writer);
            writer.append("\n"); // Adicionar nova linha ao final
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gerarCSVABPRecursivo(ArvoreBinariaPesquisa.NoABP no, FileWriter writer) throws IOException {
        if (no != null) {
            gerarCSVABPRecursivo(no.getEsquerdo(), writer);
            writer.append(String.valueOf(no.getPedido().getCodigo())).append(",");
            gerarCSVABPRecursivo(no.getDireito(), writer);
        }
    }
}
