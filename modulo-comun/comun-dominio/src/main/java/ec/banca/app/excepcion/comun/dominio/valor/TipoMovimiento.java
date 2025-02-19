package ec.banca.app.excepcion.comun.dominio.valor;

public enum TipoMovimiento {
    CREDITO("CREDITO"),
    DEBITO("DEBITO");

    private final String value;

    TipoMovimiento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}