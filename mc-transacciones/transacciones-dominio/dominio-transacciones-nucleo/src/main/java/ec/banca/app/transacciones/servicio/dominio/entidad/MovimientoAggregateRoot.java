package ec.banca.app.transacciones.servicio.dominio.entidad;

import ec.banca.app.excepcion.comun.dominio.entidad.AggregateRoot;
import ec.banca.app.excepcion.comun.dominio.valor.MovimientoId;
import ec.banca.app.excepcion.comun.dominio.valor.TipoMovimiento;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import ec.banca.app.transacciones.servicio.dominio.validators.IValidacionStrategy;

import java.math.BigDecimal;
import java.util.UUID;

public class MovimientoAggregateRoot extends AggregateRoot<MovimientoId> {
    private String numeroCuenta;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldoActual;
    private BigDecimal total;
    private IValidacionStrategy validacionStrategy;


    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public MovimientoAggregateRoot(Builder builder) {
        super.setId(builder.movimientoId);
        this.numeroCuenta = builder.numeroCuenta;
        this.tipoMovimiento = builder.tipoMovimiento;
        this.valor = builder.valor;
        this.saldoActual = builder.saldoActual;
        this.total = builder.total;
    }

    public void validar() {
        try {
            TipoMovimiento.valueOf(getTipoMovimiento());
        } catch (IllegalArgumentException ex) {
            throw new TransaccionDomainException("Tipo de movimiento incorrecto, tipo de movimiento correcto es DEBITO o CREDITO ", ex);
        }

        if (getValor().compareTo(BigDecimal.ONE) < 0) {
            throw new TransaccionDomainException("El valor no puede ser negativo o no puede ser 0");
        }
    }

    public void inicializar() {
        this.total = tipoMovimiento.equalsIgnoreCase(TipoMovimiento.CREDITO.getValue())
                ? saldoActual.add(getValor())
                : saldoActual.subtract(getValor());
        setId(new MovimientoId(UUID.randomUUID()));
    }

    public void setValidacionStrategy(IValidacionStrategy validacionStrategy) {
        this.validacionStrategy = validacionStrategy;
    }

    public void validarMovimiento() {
        if (validacionStrategy != null) {
            validacionStrategy.validarMovimiento(this);
        }
    }


    public static class Builder {
        private MovimientoId movimientoId;
        private String numeroCuenta;
        private String tipoMovimiento;
        private BigDecimal valor;
        private BigDecimal saldoActual;
        private BigDecimal total;

        public Builder total(BigDecimal total) {
            this.total = total;
            return this;
        }

        public Builder saldoActual(BigDecimal saldoActual) {
            this.saldoActual = saldoActual;
            return this;
        }

        public Builder movimientoId(MovimientoId val) {
            this.movimientoId = val;
            return this;
        }

        public Builder numeroCuenta(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
            return this;
        }

        public Builder tipoMovimiento(String tipoMovimiento) {
            this.tipoMovimiento = tipoMovimiento;
            return this;
        }

        public Builder valor(BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        public MovimientoAggregateRoot build() {
            return new MovimientoAggregateRoot(this);
        }

    }


}
