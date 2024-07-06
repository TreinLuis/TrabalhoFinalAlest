public class ListaDePedidos {
    No cabeca;
    No cauda;

    public ListaDePedidos() {
        this.cabeca = null;
        this.cauda = null;
    }

    public void adicionar(Pedido pedido) {
        No novoNo = new No(pedido);
        if (cabeca == null) {
            cabeca = novoNo;
            cauda = novoNo;
        } else {
            cauda.proximo = novoNo;
            cauda = novoNo;
        }
    }

    public void listarPedidos() {
        No atual = cabeca;
        while (atual != null) {
            System.out.println(atual.pedido.getCodigo() + " - " + atual.pedido.getSabor());
            atual = atual.proximo;
        }
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
