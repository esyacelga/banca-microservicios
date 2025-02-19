package ec.banca.app.transacciones.servicio.dominio.entidad;



import java.math.BigDecimal;

public class CuentaCorrienteAggregateRoot extends CuentaAggregateRoot  {

    private CuentaCorrienteAggregateRoot(Builder builder) {
        super(builder);

    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String obtenerDescripcion() {
        return "Cuenta de Corriente";
    }

    @Override
    public BigDecimal obtenerSaldo() {
        return getSaldo();
    }

    public static final class Builder extends CuentaAggregateRoot.Builder {
        @Override
        public CuentaCorrienteAggregateRoot build() {
            return new CuentaCorrienteAggregateRoot(this);
        }
    }

    @Override
    public CuentaAhorrosAggregateRoot clone() {
        return (CuentaAhorrosAggregateRoot) super.clone();
    }
}

