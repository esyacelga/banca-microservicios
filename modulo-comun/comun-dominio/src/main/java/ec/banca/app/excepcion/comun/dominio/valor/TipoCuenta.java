package ec.banca.app.excepcion.comun.dominio.valor;

public enum TipoCuenta {
    AHORROS("AHORROS"),
    CORRIENTE("CORRIENTE");

    private final String tipo;

    TipoCuenta(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}