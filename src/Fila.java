
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
    public boolean estaVazia(){
        if(inicio==null) {
            return true;
        }
        return false;
    }
}
