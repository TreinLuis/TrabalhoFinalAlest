public class FilaDePedidos {
    No frente;
    No tras;

    public FilaDePedidos() {
        this.frente = null;
        this.tras = null;
    }

    public boolean estaVazia() {
        return frente == null;
    }

    public void enfileirar(Pedido pedido) {
        No novoNo = new No(pedido);
        if (estaVazia()) {
            frente = novoNo;
        } else {
            tras.proximo = novoNo;
        }
        tras = novoNo;
    }

    public Pedido desenfileirar() {
        if (estaVazia()) {
            return null;
        }
        Pedido pedido = frente.pedido;
        frente = frente.proximo;
        if (frente == null) {
            tras = null;
        }
        return pedido;
    }

    class No {
        Pedido pedido;
        No proximo;

        public No(Pedido pedido) {
            this.pedido = pedido;
            this.proximo = null;
        }
    }
}

