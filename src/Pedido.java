public class Pedido {

        int codigo;
        String sabor;
        int instante;
        int tempoPreparo;

    public Pedido(int codigo, String sabor, int instante, int tempoPreparo) {
        this.codigo = codigo;
        this.sabor = sabor;
        this.instante = instante;
        this.tempoPreparo = tempoPreparo;
    }

    public Pedido() {

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public int getInstante() {
        return instante;
    }

    public void setInstante(int instante) {
        this.instante = instante;
    }

    public int getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(int tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }
}
