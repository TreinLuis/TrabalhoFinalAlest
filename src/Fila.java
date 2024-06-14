
public class Fila {
    class Nodo {
        Pedido item;
        Nodo proximo;
        public Nodo(Pedido item) {
            this.item = item;
        }
    }
    Nodo inicio;
    Nodo fim;
    int tamanho;
    public Fila() {
        tamanho = 0;
        inicio = null;
        fim = null;
    }
    public void enfileirar(Pedido item) {
        Nodo n = new Nodo(item);
        if(tamanho==0) {
            inicio = n;
            fim = n;
        }
        else {
            fim.proximo = n;
            fim = n;
        }
        tamanho++;
    }
    public Pedido desenfileirar() {
        //tira da fila e retorna o valor string
        Pedido item = null;
        if(tamanho>0) { //se tiver elementos na fila
            item = inicio.item; //esse valor que sera retornado
            if(inicio.proximo!=null) inicio = inicio.proximo;
            tamanho--;
        }
        return item;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo aux = inicio;
        while(aux!=null) {
            sb.append(aux.item).append(" ");
            aux = aux.proximo;
        }
        return sb.toString();
    }
}
