import java.io.FileWriter;
import java.io.IOException;

public class ArvoreBinariaDePesquisa {
    class Nodo {
        Pedido chave;
        Nodo esquerda;
        Nodo direita;
        Nodo pai;

        public Nodo(Pedido chave) {
            this.chave = chave;
        }
    }

    private Nodo raiz;
    private int tamanho;
    private String caminhoPreOrdem;
    private String caminhoPosOrdem;
    private String caminhoCentral;

    public void adicionar(Pedido chave) {
        Nodo n = new Nodo(chave);
        if (raiz == null) { // primeiro nodo, logo é a raiz
            raiz = n;
        } else {
            Nodo aux = raiz;
            Nodo paiDoAux = null;
            while (aux != null) {
                paiDoAux = aux;

                if (chave.getInstante() <= aux.chave.getInstante()) {
                    aux = aux.esquerda; // quando não tiver filhos à esquerda o aux será null
                    if (aux == null) { // achei o local para inserir
                        paiDoAux.esquerda = n;
                    }
                } else {
                    aux = aux.direita;
                    if (aux == null) { // achei o local para inserir
                        paiDoAux.direita = n;
                    }
                }
            }
        }
        tamanho++;
    }

    public void adicionar2(Pedido chave) { // usando recursividade
        Nodo n = new Nodo(chave);
        if (raiz == null) {
            raiz = n;
        } else {
            adicionarRecursivo(n, raiz);
        }
        tamanho++;
    }

    private void adicionarRecursivo(Nodo n, Nodo pai) {
        if (n.chave.getInstante() <= pai.chave.getInstante()) {
            if (pai.esquerda == null) {
                pai.esquerda = n;
                n.pai = pai;
            } else
                adicionarRecursivo(n, pai.esquerda);
        } else {
            if (pai.direita == null) {
                pai.direita = n;
                n.pai = pai;
            } else
                adicionarRecursivo(n, pai.direita);
        }

    }

    public int getTamanho() {
        return tamanho;
    }

    public void percorrerEmProfundidade() {
        caminhoPreOrdem = "";
        caminhoPosOrdem = "";
        caminhoCentral = "";
        percorrerEmProfundidade(raiz);
    }

    private void percorrerEmProfundidade(Nodo n) {
        if (n != null) {
            percorrerEmProfundidade(n.esquerda);
            caminhoPreOrdem += n.chave.getCodigo() + ",";
            percorrerEmProfundidade(n.direita);
        }
    }

    public String getCaminhoPreOrdem() {
        return caminhoPreOrdem;
    }

    public String getCaminhoPosOrdem() {
        return caminhoPosOrdem;
    }

    public String getCaminhoCentral() {
        return caminhoCentral;
    }

    public void imprimirArvore() {
        imprimirArvoreRecursivamente(this.raiz, 0);
    }

    private void imprimirArvoreRecursivamente(Nodo raiz, int nivel) {
        if (raiz == null)
            return;
        nivel += 5;
        imprimirArvoreRecursivamente(raiz.direita, nivel);
        System.out.print("\n");
        for (int i = 5; i < nivel; i++)
            System.out.print(" ");
        System.out.print(raiz.chave + "\n");
        for (int i = 5; i < nivel; i++)
            System.out.print(" ");
        imprimirArvoreRecursivamente(raiz.esquerda, nivel);
    }
    public void ordemCentralCSV(FileWriter writer) throws IOException {
        percorrerEmOrdemCentral(raiz, writer);
    }
    private void percorrerEmOrdemCentral(Nodo n, FileWriter writer) throws IOException {
        StringBuilder  sb = new StringBuilder();
        if (n != null) {
            percorrerEmOrdemCentral(n.esquerda, writer);
            writer.write(n.chave.getCodigo() + ","); // Escreve o código do pedido no arquivo
            percorrerEmOrdemCentral(n.direita, writer);
        }
    }

}
