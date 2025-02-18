package ec.neoris.app.excepcion.comun.dominio.valor;

public enum TipoMovimiento {
    CREDITO("CREDITO"),
    DEBITO("DEBITO");

    private final String value;

    // Constructor
    TipoMovimiento(String value) {
        this.value = value;
    }

    // Getter para el valor
    public String getValue() {
        return value;
    }
}