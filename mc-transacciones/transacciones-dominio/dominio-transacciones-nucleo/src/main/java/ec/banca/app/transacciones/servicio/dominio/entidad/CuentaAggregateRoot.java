package ec.banca.app.transacciones.servicio.dominio.entidad;

import ec.banca.app.excepcion.comun.dominio.entidad.AggregateRoot;
import ec.banca.app.excepcion.comun.dominio.valor.CuentaId;
import ec.banca.app.excepcion.comun.dominio.valor.TipoCuenta;
import ec.banca.app.transacciones.servicio.dominio.decorator.ICuenta;
import ec.banca.app.transacciones.servicio.dominio.exception.CuentaDomainException;

import java.math.BigDecimal;
import java.util.UUID;

public class CuentaAggregateRoot extends AggregateRoot<CuentaId> implements Cloneable, ICuenta {
    private Integer numeroCuenta;
    private String clienteId;
    private String tipoCuenta;
    private BigDecimal saldo;
    private Boolean estado;

    protected CuentaAggregateRoot(Builder builder) {
        super.setId(builder.cuentaId);
        this.numeroCuenta = builder.numeroCuenta;
        this.clienteId = builder.clienteId;
        this.tipoCuenta = builder.tipoCuenta;
        this.saldo = builder.saldo;
        this.estado = builder.estado;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getClienteId() {
        return clienteId;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void validar() {
        try {
            TipoCuenta.valueOf(tipoCuenta);
        } catch (IllegalArgumentException ex) {
            throw new CuentaDomainException("El tipo de cuenta debe ser AHORROS, CORRIENTE", ex);
        }
        if (saldo.compareTo(BigDecimal.ZERO) < 0)
            throw new CuentaDomainException("El saldo no puede ser negativo ");
    }

    public void inicializar() {
        setId(new CuentaId(UUID.randomUUID()));
    }



    public static class Builder {
        private CuentaId cuentaId;
        private Integer numeroCuenta;
        private String clienteId;
        private String tipoCuenta;
        private BigDecimal saldo;
        private Boolean estado;

        public Builder cuentaId(CuentaId val) {
            this.cuentaId = val;
            return this;
        }

        public Builder numeroCuenta(Integer val) {
            this.numeroCuenta = val;
            return this;
        }

        public Builder clienteId(String val) {
            this.clienteId = val;
            return this;
        }

        public Builder tipoCuenta(String val) {
            this.tipoCuenta = val;
            return this;
        }

        public Builder saldo(BigDecimal val) {
            this.saldo = val;
            return this;
        }

        public Builder estado(Boolean val) {
            this.estado = val;
            return this;
        }

        public CuentaAggregateRoot build() {
            return new CuentaAggregateRoot(this);
        }
    }

    @Override
    public CuentaAggregateRoot clone() {
        try {
            return (CuentaAggregateRoot) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String obtenerDescripcion() {
        return "";
    }

    @Override
    public BigDecimal obtenerSaldo() {
        return getSaldo();
    }

}
