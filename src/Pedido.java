public class Pedido {
    private int codigo;
    private String sabor;
    private int instante;
    private int tempoPreparo;
    private int tempoRestante;

    public Pedido(int codigo, String sabor, int instante, int tempoPreparo) {
        this.codigo = codigo;
        this.sabor = sabor;
        this.instante = instante;
        this.tempoPreparo = tempoPreparo;
        this.tempoRestante = tempoPreparo; // Inicializa o tempo restante com o tempo de preparo
    }

    public int getCodigo() {
        return codigo;
    }

    public String getSabor() {
        return sabor;
    }

    public int getInstante() {
        return instante;
    }

    public int getTempoPreparo() {
        return tempoPreparo;
    }

    public int getTempoRestante() {
        return tempoRestante;
    }

    public void decrementarTempoRestante() {
        if (tempoRestante > 0) {
            tempoRestante--;
        }
    }
}
