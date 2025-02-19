package ec.banca.app.transacciones.servicio.dominio.entidad;

import java.math.BigDecimal;

public class CuentaAhorrosAggregateRoot extends CuentaAggregateRoot {

    private CuentaAhorrosAggregateRoot(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String obtenerDescripcion() {
        return "Cuenta de ahorros";
    }

    @Override
    public BigDecimal obtenerSaldo() {
        return getSaldo();
    }

    public static final class Builder extends CuentaAggregateRoot.Builder {
        @Override
        public CuentaAhorrosAggregateRoot build() {
            return new CuentaAhorrosAggregateRoot(this);
        }
    }

    @Override
    public CuentaAhorrosAggregateRoot clone() {
        return (CuentaAhorrosAggregateRoot) super.clone();
    }
}
