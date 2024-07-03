public class Pedido {
    private int codigo;
    private String sabor;
    private int instante;
    private int tempoPreparo;

    public Pedido(int codigo, String sabor, int instante, int tempoPreparo) {
        this.codigo = codigo;
        this.sabor = sabor;
        this.instante = instante;
        this.tempoPreparo = tempoPreparo;
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

    public void setTempoPreparo(int tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    @Override
    public String toString() {
        return codigo + " - " + sabor;
    }
}
