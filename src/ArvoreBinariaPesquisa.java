public class ArvoreBinariaPesquisa {
    private NoABP raiz;

    public void inserir(Pedido pedido) {
        raiz = inserirRecursivo(raiz, pedido);
    }

    private NoABP inserirRecursivo(NoABP raiz, Pedido pedido) {
        if (raiz == null) {
            raiz = new NoABP(pedido);
            return raiz;
        }

        if (pedido.getCodigo() < raiz.getPedido().getCodigo()) {
            raiz.setEsquerdo(inserirRecursivo(raiz.getEsquerdo(), pedido));
        } else if (pedido.getCodigo() > raiz.getPedido().getCodigo()) {
            raiz.setDireito(inserirRecursivo(raiz.getDireito(), pedido));
        }

        return raiz;
    }

    public void emOrdem() {
        emOrdemRecursivo(raiz);
    }

    private void emOrdemRecursivo(NoABP raiz) {
        if (raiz != null) {
            emOrdemRecursivo(raiz.getEsquerdo());
            System.out.println(raiz.getPedido().getCodigo());
            emOrdemRecursivo(raiz.getDireito());
        }
    }

    public void emOrdemParaLista(ListaDePedidos pedidos) {
        emOrdemParaListaRecursivo(raiz, pedidos);
    }

    private void emOrdemParaListaRecursivo(NoABP no, ListaDePedidos pedidos) {
        if (no != null) {
            emOrdemParaListaRecursivo(no.getEsquerdo(), pedidos);
            pedidos.adicionar(no.getPedido());
            emOrdemParaListaRecursivo(no.getDireito(), pedidos);
        }
    }

    public NoABP getRaiz() {
        return raiz;
    }

    public int contarNos() {
        return contarNosRecursivo(raiz);
    }

    private int contarNosRecursivo(NoABP raiz) {
        if (raiz == null) {
            return 0;
        }
        return 1 + contarNosRecursivo(raiz.getEsquerdo()) + contarNosRecursivo(raiz.getDireito());
    }

    public static class NoABP {
        private Pedido pedido;
        private NoABP esquerdo;
        private NoABP direito;

        public NoABP(Pedido pedido) {
            this.pedido = pedido;
        }

        public Pedido getPedido() {
            return pedido;
        }

        public NoABP getEsquerdo() {
            return esquerdo;
        }

        public void setEsquerdo(NoABP esquerdo) {
            this.esquerdo = esquerdo;
        }

        public NoABP getDireito() {
            return direito;
        }

        public void setDireito(NoABP direito) {
            this.direito = direito;
        }
    }
}
