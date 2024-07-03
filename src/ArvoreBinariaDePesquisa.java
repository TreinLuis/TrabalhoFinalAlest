import java.io.FileWriter;
import java.io.IOException;

public class ArvoreBinariaDePesquisa {
    class Nodo {
        Pedido chave;
        Nodo esquerda;
        Nodo direita;

        public Nodo(Pedido chave) {
            this.chave = chave;
        }
    }

    private Nodo raiz;
    private int tamanho;

    public void adicionar(Pedido chave) {
        Nodo novoNodo = new Nodo(chave);
        if (raiz == null) {
            raiz = novoNodo;
        } else {
            adicionarRecursivo(raiz, novoNodo);
        }
        tamanho++;
    }

    private void adicionarRecursivo(Nodo atual, Nodo novoNodo) {
        if (novoNodo.chave.getInstante() <= atual.chave.getInstante()) {
            if (atual.esquerda == null) {
                atual.esquerda = novoNodo;
            } else {
                adicionarRecursivo(atual.esquerda, novoNodo);
            }
        } else {
            if (atual.direita == null) {
                atual.direita = novoNodo;
            } else {
                adicionarRecursivo(atual.direita, novoNodo);
            }
        }
    }

    public int getTamanho() {
        return tamanho;
    }

    public void ordemCentralCSV(FileWriter writer) throws IOException {
        percorrerEmOrdemCentral(raiz, writer);
    }

    private void percorrerEmOrdemCentral(Nodo nodo, FileWriter writer) throws IOException {
        if (nodo != null) {
            percorrerEmOrdemCentral(nodo.esquerda, writer);
            writer.write(nodo.chave.getCodigo() + ",");
            percorrerEmOrdemCentral(nodo.direita, writer);
        }
    }
}
