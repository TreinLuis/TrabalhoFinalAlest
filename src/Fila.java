public class Fila {
    class Nodo {
        Pedido item;
        Nodo proximo;

        public Nodo(Pedido item) {
            this.item = item;
        }
    }

    private Nodo inicio;
    private Nodo fim;
    private int tamanho;

    public Fila() {
        this.tamanho = 0;
        this.inicio = null;
        this.fim = null;
    }

    public void enfileirar(Pedido item) {
        Nodo novoNodo = new Nodo(item);
        if (tamanho == 0) {
            inicio = novoNodo;
            fim = novoNodo;
        } else {
            fim.proximo = novoNodo;
            fim = novoNodo;
        }
        tamanho++;
    }

    public Pedido desenfileirar() {
        if (tamanho == 0) {
            return null;
        }
        Pedido item = inicio.item;
        inicio = inicio.proximo;
        tamanho--;
        if (tamanho == 0) {
            fim = null;
        }
        return item;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo aux = inicio;
        while (aux != null) {
            sb.append(aux.item).append(" ");
            aux = aux.proximo;
        }
        return sb.toString().trim();
    }

    public int getTamanho() {
        return tamanho;
    }

    public Fila copiar() {
        Fila novaFila = new Fila();
        Nodo temp = inicio;
        while (temp != null) {
            novaFila.enfileirar(temp.item);
            temp = temp.proximo;
        }
        return novaFila;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }
}
